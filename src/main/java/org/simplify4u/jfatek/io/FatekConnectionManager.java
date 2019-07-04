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
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for manage connection.
 *
 * @author Slawomir Jaranowski.
 */
public abstract class FatekConnectionManager implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(FatekConnectionManager.class);

    private static final Map<String, FatekConnectionFactory> CONNECTION_FACTORY_MAP = new HashMap<>();

    static {
        // register connection factory for supported protocols
        registerConnectionFactory(new TCPConnectionFactory());
        registerConnectionFactory(new UDPConnectionFactory());
    }

    private FatekConfig fatekConfig;
    private FatekConnectionFactory connectionFactory;
    private FatekConnection connection;

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

    protected static void registerConnectionFactory(FatekConnectionFactory connectionFactory) {
        CONNECTION_FACTORY_MAP.put(connectionFactory.getSchema().toUpperCase(Locale.ENGLISH), connectionFactory);
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

    private FatekConnectionFactory findConnectionFactory() throws FatekIOException {

        String scheme = fatekConfig.getScheme();

        return Optional.ofNullable(CONNECTION_FACTORY_MAP.get(scheme.toUpperCase(Locale.ENGLISH)))
                .orElseThrow(() -> new FatekIOException("Unknown connection factory for scheme: %s", scheme));
    }

    private void init(URI uri) throws FatekIOException {

        fatekConfig = new FatekConfig(uri);
        connectionFactory = findConnectionFactory();
    }
}
