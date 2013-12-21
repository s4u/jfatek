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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import static org.testng.Assert.assertEquals;

/**
 * @author Slawomir Jaranowski.
 */
@SuppressWarnings("unused")
public class MockConnectionFactory implements FatekConnectionFactory {

    private class MockConnection extends FatekConnection {

        private ByteArrayOutputStream outputStream;

        public MockConnection(FatekConfig fatekConfig) {

            super(fatekConfig);
        }

        @Override
        protected InputStream getInputStream() throws IOException {

            return new ByteArrayInputStream(getTestMessageByte());
        }

        @Override
        protected OutputStream getOutputStream() throws IOException {

            outputStream = new ByteArrayOutputStream();
            return outputStream;
        }

        @Override
        protected void closeConnection() throws IOException {

            String outActual = outputStream.toString("ASCII");
            String outExpected = getParam("plcOutData");
            if (outActual.length() > 3) {
                outActual = outActual.substring(1, outActual.length() - 3);
            }
            assertEquals(outActual, outExpected, "Out to PLC");
            outputStream = null;
        }

        @Override
        public boolean isConnected() {

            return outputStream != null;
        }

        private byte[] getTestMessageByte() {

            String rawMsg = getParam("plcInData");

            StringBuilder msg = new StringBuilder();
            msg.append((char) 0x02);
            msg.append(rawMsg);

            int crc = 0;
            try {
                crc = FatekUtils.countCRC(msg.toString().getBytes("ASCII"));
            } catch (UnsupportedEncodingException e) {
                return null;
            }
            msg.append(String.format("%02X", crc));
            msg.append((char) 0x03);

            try {
                return msg.toString().getBytes("ASCII");
            } catch (UnsupportedEncodingException e) {
                return null;
            }
        }
    }

    @Override
    public FatekConnection getConnection(FatekConfig fatekConfig) throws IOException {

        return new MockConnection(fatekConfig);
    }
}
