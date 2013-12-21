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

package com.github.s4u.jfatek.io.transport;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.github.s4u.jfatek.io.FatekConfig;
import com.github.s4u.jfatek.io.FatekConnection;
import com.github.s4u.jfatek.io.FatekConnectionFactory;

/**
 * Connection factory for TCP transport.
 *
 * @author Slawomir Jaranowski.
 */
public class TCPFatekConnectionFactory implements FatekConnectionFactory {

    class Connection extends FatekConnection {

        private static final int DEFAULT_PORT = 500;
        private final Socket socket;

        public Connection(FatekConfig fatekConfig) throws IOException {

            super(fatekConfig);
            int timeOut = fatekConfig.getTimeout();

            socket = new Socket();
            socket.setSoTimeout(timeOut);
            socket.connect(fatekConfig.getSocketAddress(DEFAULT_PORT), timeOut);
        }

        @Override
        protected InputStream getInputStream() throws IOException {

            return socket.getInputStream();
        }

        @Override
        protected OutputStream getOutputStream() throws IOException {

            return socket.getOutputStream();
        }

        @Override
        protected void closeConnection() throws IOException {

            socket.close();
        }

        @Override
        public boolean isConnected() {

            return socket.isConnected() && socket.isBound() && !socket.isClosed();
        }
    }

    @Override
    public FatekConnection getConnection(FatekConfig fatekConfig) throws IOException {

        return new Connection(fatekConfig);
    }
}
