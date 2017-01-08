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

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekWriterTest {

    @Test
    public void testWrite() throws Exception {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        FatekWriter writer = new FatekWriter(stream);
        writer.write("0140");
        writer.flush();

        String out = stream.toString("ASCII");
        assertEquals(out, "\0020140C7\003");
    }

    @Test
    public void testWriteByte() throws Exception {

        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        FatekWriter writer = new FatekWriter(stream);
        writer.writeByte(0x01);
        writer.writeByte(0x40);
        writer.flush();

        String out = stream.toString("ASCII");
        assertEquals(out, "\0020140C7\003");
    }
}
