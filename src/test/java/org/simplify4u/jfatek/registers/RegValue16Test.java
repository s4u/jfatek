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

package org.simplify4u.jfatek.registers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

/**
 * @author Slawomir Jaranowski.
 */
class RegValue16Test {

    @Test
    void testUnSign() throws Exception {

        assertEquals(0, new RegValue16(0).intValueUnsigned());
        assertEquals(1, new RegValue16(1).intValueUnsigned());
        assertEquals(0x8000, new RegValue16(0x8000).intValueUnsigned());
        assertEquals(0xffff, new RegValue16(0xffff).intValueUnsigned());
        assertEquals(0xffff, new RegValue16(-1).intValueUnsigned());
    }

    @Test
    void testSign() throws Exception {

        assertEquals(0, new RegValue16(0).intValue());
        assertEquals(1, new RegValue16(1).intValue());
        assertEquals(-1, new RegValue16(-1).intValue());
        assertEquals(-(1 << 15), new RegValue16(0x8000).intValue());
        assertEquals(-1, new RegValue16(0xffff).intValue());
    }

    @Test
    void testToFatekString() throws Exception {

        assertEquals("0000", new RegValue16(0).toFatekString());
        assertEquals("FFFF", new RegValue16(-1).toFatekString());
    }

    @Test
    void testEquals() throws Exception {

        RegValue val00 = new RegValue16(0);
        RegValue val01 = new RegValue16(0);

        RegValue val10 = new RegValue16(1);
        RegValue val11 = new RegValue32(1);


        assertEquals(val00, val00);

        assertEquals(val00, val01);
        assertEquals(val01, val00);

        assertNotEquals(val10, val11);
        assertNotEquals(val11, val10);
    }

    @Test
    void testHashCode() {

        RegValue val00 = new RegValue16(0);
        RegValue val01 = new RegValue16(0);

        RegValue val10 = new RegValue16(1);
        RegValue val11 = new RegValue32(1);

        assertEquals(val00.hashCode(), val01.hashCode());
        assertNotEquals(val00.hashCode(), val10.hashCode());
        assertNotEquals(val10.hashCode(), val11.hashCode());
    }
}
