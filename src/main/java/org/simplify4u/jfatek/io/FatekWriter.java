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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekWriter {

    private static final Logger LOG = LoggerFactory.getLogger(FatekWriter.class);

    private final OutputStream output;
    private final ByteArrayOutputStream outputBuf;

    FatekWriter(OutputStream output) {

        this.output = output;
        this.outputBuf = new ByteArrayOutputStream();
        this.outputBuf.write(0x02);
    }

    public void write(String string) throws FatekIOException {

        try {
            outputBuf.write(string.getBytes("ASCII"));
        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }

    public void write(Boolean bool) throws FatekIOException {

        if (bool == null) {
            write("0");
        } else {

            if (bool) {
                write("1");
            } else {
                write("0");
            }
        }
    }


    public void writeByte(int val) throws FatekIOException {

        write(String.format("%02X", val & 0xff));
    }

    public void writeNibble(int val) throws FatekIOException {

        write(String.format("%X", val & 0x0f));
    }

    public void flush() throws FatekIOException {

        try {
            byte[] bytes = outputBuf.toByteArray();
            write(String.format("%02X", FatekUtils.countCRC(bytes)));
            outputBuf.write(0x03);

            outputBuf.writeTo(output);
            output.flush();

            if (LOG.isTraceEnabled()) {
                LOG.trace("write:\n\t-> {}", FatekUtils.byteArrayToString(outputBuf.toByteArray()));
            }

            outputBuf.reset();

        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }
}
