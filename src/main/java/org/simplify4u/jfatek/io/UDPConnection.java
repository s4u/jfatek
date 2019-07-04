/*
 * Copyright 2019 Slawomir Jaranowski
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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

class UDPConnection extends FatekConnection {

    public static final int DEFAULT_PORT = 500;

    private final DatagramSocket socket;

    UDPConnection(FatekConfig fatekConfig) throws IOException {

        super(fatekConfig);
        socket = new DatagramSocket();
        socket.setSoTimeout(fatekConfig.getTimeout());
    }

    @Override
    protected InputStream getInputStream() throws IOException {

        byte[] buf = new byte[512];
        DatagramPacket packet = new DatagramPacket(buf, buf.length);
        socket.receive(packet);
        return new ByteArrayInputStream(packet.getData());
    }

    @Override
    protected OutputStream getOutputStream() throws IOException {

        return new ByteArrayOutputStream() {
            @Override
            public void flush() throws IOException {

                DatagramPacket packet = new DatagramPacket(toByteArray(), size(), getSocketAddress(DEFAULT_PORT));
                socket.send(packet);
                reset();
            }
        };
    }

    @Override
    protected void closeConnection() throws IOException {
        socket.close();
    }

    @Override
    public boolean isConnected() {
        return !socket.isClosed();
    }
}
