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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * @author Slawomir Jaranowski.
 */
class FatekReaderTest {


    @Test
    void testReadByte() throws Exception {

        String input = "\0020140C7\003";
        FatekReader fatekReader = new FatekReader(new ByteArrayInputStream(input.getBytes("ASCII")));

        fatekReader.readNextMessage();
        assertEquals(0x01, fatekReader.readByte());
        assertEquals(0x40, fatekReader.readByte());
    }

    @Test
    void testWrongCRC() throws Exception {

        String input = "\0020140AA\003";
        FatekReader fatekReader = new FatekReader(new ByteArrayInputStream(input.getBytes("ASCII")));

        assertThrows(FatekCRCException.class, fatekReader::readNextMessage);
    }
}
