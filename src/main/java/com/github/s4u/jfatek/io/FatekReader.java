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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.github.s4u.jfatek.registers.RegValue16;
import com.github.s4u.jfatek.registers.RegValue32;
import com.github.s4u.jfatek.registers.RegValueDis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Slawomir Jaranowski
 */
public class FatekReader {

    private static final Logger LOG = LoggerFactory.getLogger(FatekReader.class);

    private final InputStream input;

    private char[] msgBuf;
    private int msgBufOutPos;

    FatekReader(InputStream input) {

        this.input = input;
        this.msgBuf = new char[0];
    }

    public int read(char... buf) throws FatekIOException {

        int lenToCopy = Math.min(buf.length, msgBuf.length - msgBufOutPos - 3);
        if (lenToCopy <= 0) {
            return -1;
        }

        System.arraycopy(msgBuf, msgBufOutPos, buf, 0, lenToCopy);
        msgBufOutPos += lenToCopy;

        return lenToCopy;
    }

    public int readByte() throws FatekIOException {

        char[] buf = new char[2];
        if (read(buf) != 2) {
            throw new FatekUnexpectedEOSException();
        }

        return (Character.digit(buf[0], 16) << 4 | Character.digit(buf[1], 16)) & 0xff;
    }

    public int readNibble() throws FatekIOException {

        char[] buf = new char[1];

        int n = read(buf);
        if (n < 0) {
            throw new FatekUnexpectedEOSException();
        }
        return Character.digit(buf[0], 16) & 0xff;
    }

    public Boolean readBool() throws FatekIOException {

        int n = readNibble();
        switch (n) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new FatekIOException("Invalid value %d for boolean", n);
        }
    }

    public RegValueDis readRegValueDis() throws FatekIOException {

        return new RegValueDis(readBool());
    }

    public RegValue16 readRegVal16() throws FatekIOException {

        char[] buf = new char[4];

        int n = read(buf);
        if (n != 4) {
            throw new FatekUnexpectedEOSException();
        }
        return new RegValue16(Long.parseLong(new String(buf, 0, 4), 16));
    }

    public RegValue32 readRegVal32() throws FatekIOException {

        char[] buf = new char[8];

        int n = read(buf);
        if (n != 8) {
            throw new FatekUnexpectedEOSException();
        }
        return new RegValue32(Long.parseLong(new String(buf, 0, 8), 16));
    }

    /**
     * Read whole fatek message from STX char to ETX char to internal buffer.
     */
    public void readNextMessage() throws FatekIOException {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            int t = input.read();
            if (t != 0x02) {
                throw new FatekIOException("No STX");
            }
            buf.write(t);

            while (t != 0x03) {
                t = input.read();
                if (t < 0) {
                    throw new FatekUnexpectedEOSException();
                }
                buf.write(t);
            }

            byte[] bufArray = buf.toByteArray();
            if (LOG.isTraceEnabled()) {
                LOG.trace("read:\n\t<- {}", FatekUtils.byteArrayToString(bufArray));
            }

            if (bufArray.length < 8) {
                throw new FatekIOException("Message to short");
            }

            int crc0 = FatekUtils.countCRC(bufArray, bufArray.length - 3);
            int crc = Integer.parseInt(new String(bufArray, bufArray.length - 3, 2), 16);

            if (crc != crc0) {
                throw new FatekCRCException(crc, crc0);
            }

            msgBuf = new String(bufArray, "ASCII").toCharArray();
            msgBufOutPos = 1;
        } catch (FatekIOException e) {
            throw e;
        } catch (IOException e) {
            throw new FatekIOException(e);
        }
    }
}
