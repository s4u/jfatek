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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekReaderTest {


    @Test
    public void testReadByte() throws Exception {

        String input = "\0020140C7\003";
        FatekReader fatekReader = new FatekReader(new ByteArrayInputStream(input.getBytes("ASCII")));

        fatekReader.readNextMessage();
        assertEquals(fatekReader.readByte(), 0x01);
        assertEquals(fatekReader.readByte(), 0x40);
    }

    @Test(expectedExceptions = FatekCRCException.class)
    public void testWrongCRC() throws Exception {

        String input = "\0020140AA\003";
        FatekReader fatekReader = new FatekReader(new ByteArrayInputStream(input.getBytes("ASCII")));

        fatekReader.readNextMessage();
    }
}
