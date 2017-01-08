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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author Slawomir Jaranowski.
 */
public class LoopConnectionFactory implements FatekConnectionFactory {

    private class LoopConnection extends FatekConnection {

        private ByteArrayOutputStream outStream;

        protected LoopConnection(FatekConfig fatekConfig) {

            super(fatekConfig);
        }

        @Override
        protected InputStream getInputStream() throws IOException {

            return new ByteArrayInputStream(outStream.toByteArray());
        }

        @Override
        protected OutputStream getOutputStream() throws IOException {

            outStream = new ByteArrayOutputStream();
            return outStream;
        }

        @Override
        protected void closeConnection() throws IOException {
            outStream = null;
        }

        @Override
        public boolean isConnected() {

            return outStream != null;
        }
    }

    @Override
    public FatekConnection getConnection(FatekConfig fatekConfig) throws IOException {

        return new LoopConnection(fatekConfig);
    }
}
