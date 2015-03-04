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

/**
 * @author Slawomir Jaranowski.
 */
public final class FatekUtils {

    private FatekUtils() {

    }

    public static int countCRC(byte[] buf, int len) {

        int crc = 0;
        for (int i = 0; i < len; i++) {
            crc += buf[i] & 0xff;
            crc &= 0xff;
        }
        return crc;
    }

    public static int countCRC(byte... buf) {

        return countCRC(buf, buf.length);
    }

    /**
     * Convert input array to string, replace STX(0x02) and ETX(0x03) chars
     * to special string ^B and ^C.
     * <p/>
     * This method is used for debug purpose.
     *
     * @param bytes input array
     * @return string build from input array
     */
    public static String byteArrayToString(byte... bytes) {

        StringBuilder ret = new StringBuilder(bytes.length + 8);
        for (byte b : bytes) {
            switch (b) {
                case 0x02:
                    ret.append("^B");
                    break;
                case 0x03:
                    ret.append("^C");
                    break;
                default:
                    ret.append((char) b);
                    break;
            }
        }
        return ret.toString();
    }
}
