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

        private ByteArrayOutputStream outputStream = null;
        private ByteArrayInputStream inputStream = null;

        MockConnection(FatekConfig fatekConfig) {

            super(fatekConfig);
        }

        @Override
        protected InputStream getInputStream() throws IOException {

            if (inputStream == null) {
                inputStream = new ByteArrayInputStream(getTestMessageByte());
            }
            return inputStream;
        }

        @Override
        protected OutputStream getOutputStream() throws IOException {

            if (outputStream == null) {
                outputStream = new ByteArrayOutputStream();
            }
            return outputStream;
        }

        @Override
        protected void closeConnection() throws IOException {

            String outActual = outputStream.toString("ASCII");
            String outExpected = getParam("plcOutData");

            // first remove all start char
            outActual = outActual.replaceAll("\\x02", "");
            String[] outs = outActual.split("\\x03");

            StringBuilder out2Test = new StringBuilder();
            for (String s : outs) {
                if (s.length() > 1) {
                    out2Test.append(s.substring(0, s.length() - 2));
                }
            }
            assertEquals(out2Test.toString(), outExpected, "Out to PLC");
            outputStream = null;
        }

        @Override
        public boolean isConnected() {

            return outputStream != null;
        }

        private byte[] getTestMessageByte() {

            String rawMsg = getParam("plcInData");
            StringBuilder allMsg = new StringBuilder();

            for (String rawMsgItem : rawMsg.split(";")) {

                StringBuilder msg = new StringBuilder();

                msg.append((char) 0x02);
                msg.append(rawMsgItem);

                int crc = 0;
                try {
                    crc = FatekUtils.countCRC(msg.toString().getBytes("ASCII"));
                } catch (UnsupportedEncodingException e) {
                    return null;
                }
                msg.append(String.format("%02X", crc));
                msg.append((char) 0x03);
                allMsg.append(msg);
            }

            try {
                return allMsg.toString().getBytes("ASCII");
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
