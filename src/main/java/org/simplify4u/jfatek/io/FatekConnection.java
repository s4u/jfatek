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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.SocketAddress;
import java.util.Optional;

/**
 * @author Slawomir Jaranowski.
 */
public abstract class FatekConnection {

    private final FatekConfig fatekConfig;

    protected FatekConnection(FatekConfig fatekConfig) {

        this.fatekConfig = fatekConfig;
    }

    protected abstract InputStream getInputStream() throws IOException;

    protected abstract OutputStream getOutputStream() throws IOException;

    protected abstract void closeConnection() throws IOException;

    public abstract boolean isConnected();

    public final FatekWriter getWriter() throws FatekIOException {

        try {
            return new FatekWriter(getOutputStream());
        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }

    public final FatekReader getReader() throws FatekIOException {

        try {
            return new FatekReader(getInputStream());
        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }

    public final void close() throws FatekIOException {

        try {
            closeConnection();
        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }

    public int getPlcId() {
        return fatekConfig.getPlcId();
    }

    public Optional<String> getParam(String key) {
        return fatekConfig.getParam(key);
    }

    public Optional<Integer> getParamAsInt(String key) {
        return fatekConfig.getParamAsInt(key);
    }

    public String getFullName() {
        return fatekConfig.getFullName();
    }

    public SocketAddress getSocketAddress(int defaultPort) {
        return fatekConfig.getSocketAddress(defaultPort);
    }

    public int getTimeout() {
        return fatekConfig.getTimeout();
    }
}
