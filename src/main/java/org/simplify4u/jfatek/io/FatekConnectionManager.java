/*
 * Copyright 2017 Slawomir Jaranowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.simplify4u.jfatek.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.simplify4u.jfatek.FatekException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for manage connection.
 *
 * @author Slawomir Jaranowski.
 */
public abstract class FatekConnectionManager implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(FatekConnectionManager.class);

    private FatekConfig fatekConfig;
    private FatekConnectionFactory connectionFactory;
    private FatekConnection connection;

    private FatekException stack;

    protected FatekConnectionManager(URI uri) throws FatekIOException {

        init(uri);
    }

    protected FatekConnectionManager(String uriStr) throws FatekIOException {

        try {
            init(new URI(uriStr));
        } catch (URISyntaxException e) {
            throw new FatekIOException(e);
        }
    }

    /**
     * Close open connection to FatekPLC.<br>
     * This method should be called always when we end work with current object.
     *
     * @throws FatekIOException if problem with connection
     */
    @Override
    public void close() throws FatekIOException {

        try {
            if (connection != null) {
                connection.close();
            }
        } finally {
            fatekConfig = null;
            connectionFactory = null;
            connection = null;
        }
    }

    protected FatekConnection getConnection0() throws FatekIOException {

        if (connection != null && connection.isConnected()) {
            return connection;
        }

        try {
            synchronized (this) {

                // try again in synchronized block
                if (connection != null && connection.isConnected()) {
                    return connection;
                }

                connection = connectionFactory.getConnection(fatekConfig);
            }
            LOG.trace("Create new connection: {}", connection);
            return connection;
        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }

    // private methods

    private void init(URI uri) throws FatekIOException {

        fatekConfig = new FatekConfig(uri);
        connectionFactory = findConnectionFactory();
        if (LOG.isDebugEnabled()) {
            stack = new FatekException();
        }
    }

    private FatekConnectionFactory findConnectionFactory() throws FatekIOException {

        String scheme = fatekConfig.getScheme();

        FatekConnectionFactory fcf = FatekConnectionFactoryLoader.getByScheme(scheme);
        if (fcf == null) {
            throw new FatekIOException("Unknown connection factory for scheme: %s", scheme);
        }
        return fcf;
    }

    @Override
    protected void finalize() throws Throwable {

        if (connection != null) {
            LOG.debug("Please invoke close on Fatek PLC to terminate open connections.", stack);
            try {
                close();
            } catch (FatekIOException e) {
                LOG.error("Close connection in finalize error", e);
            }
        }
        super.finalize();
    }
}
