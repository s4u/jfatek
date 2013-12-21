/*
 * Copyright 2013 Slawomir Jaranowski
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

package com.github.s4u.jfatek.io;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.s4u.jfatek.FatekException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is responsible for manage connection pool to Fatek PLC.
 *
 * @author Slawomir Jaranowski.
 */
public abstract class FatekConnectionManager implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(FatekConnectionManager.class);

    private FatekConfig fatekConfig;
    private FatekConnectionFactory connectionFactory;

    private BlockingDeque<FatekConnection> connectionPool;
    private ScheduledExecutorService executorService;
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
     * Close all open connection to FatekPLC.<br/>
     * This method should be called always when we end work with current object.
     *
     * @throws FatekIOException
     */
    @Override
    public void close() throws FatekIOException {

        if (executorService != null) {
            executorService.shutdown();
            executorService = null;
        }

        FatekIOException lastException = null;
        FatekConnection conn;
        while ((conn = connectionPool.pollFirst()) != null) {
            try {
                conn.close();
            } catch (FatekIOException e) {
                LOG.error("close", e);
                lastException = e;
            }
        }

        fatekConfig = null;
        connectionPool = null;
        connectionFactory = null;

        if (lastException != null) {
            throw new FatekIOException(lastException);
        }
    }

    protected FatekConnection _getConnection() throws FatekIOException {

        try {
            FatekConnection conn;
            do {
                conn = connectionPool.pollFirst();
            } while (conn != null && !conn.isConnected());

            if (conn == null) {
                conn = connectionFactory.getConnection(fatekConfig);
                LOG.trace("Create new connection: {}", conn);
            }

            return conn;
        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }

    protected void _returnConnection(FatekConnection conn) throws FatekIOException {

        if (conn == null || !conn.isConnected()) {
            return;
        }

        if (!connectionPool.offerFirst(conn)) {
            conn.close();
        }
    }

    // private methods

    private void init(URI uri) throws FatekIOException {

        fatekConfig = new FatekConfig(uri);
        connectionFactory = findConnectionFactory();
        connectionPool = new LinkedBlockingDeque<>(fatekConfig.getMaxConnection());
        stack = new FatekException();
        prepareExecutorService();
    }

    private FatekConnectionFactory findConnectionFactory() throws FatekIOException {

        String scheme = fatekConfig.getScheme();

        FatekConnectionFactory fcf = FatekConnectionFactoryLoader.getByScheme(scheme);
        if (fcf == null) {
            throw new FatekIOException("Unknown connection factory for scheme: %s", scheme);
        }
        return fcf;
    }

    private void prepareExecutorService() {

        if (executorService != null) {
            executorService.shutdown();
        }

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new ExecutorTask(),
                fatekConfig.getClearConnInterval(), fatekConfig.getClearConnInterval(), TimeUnit.SECONDS);
    }

    @Override
    protected void finalize() throws Throwable {

        super.finalize();
        if (connectionPool != null) {
            close();
            LOG.warn("Please invoke close on Fatek PLC to terminate open connections.", stack);
        }
    }


    private class ExecutorTask implements Runnable {

        @Override
        public void run() {

            if (connectionPool.size() < fatekConfig.getMinConnection()) {
                try {
                    _returnConnection(_getConnection());
                } catch (FatekIOException e) {
                    LOG.error("ExecutorTask", e);
                }
            }
        }
    }
}
