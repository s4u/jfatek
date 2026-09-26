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
import static org.simplify4u.jfatek.registers.DataReg.DR;

import org.junit.jupiter.api.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class RegValue32Test {

    @Test
    public void testUnSign() throws Exception {

        assertEquals(0, new RegValue32(0).longValueUnsigned());
        assertEquals(1, new RegValue32(1).longValueUnsigned());
        assertEquals(0x80000000L, new RegValue32(0x80000000L).longValueUnsigned());
        assertEquals(0xffffffffL, new RegValue32(0xffffffffL).longValueUnsigned());
        assertEquals(0xffffffffL, new RegValue32(-1).longValueUnsigned());
    }

    @Test
    public void testSign() throws Exception {

        assertEquals(0, new RegValue32(0).longValue());
        assertEquals(1, new RegValue32(1).longValue());
        assertEquals(-1, new RegValue32(-1).longValue());
        assertEquals(-(1L << 31), new RegValue32(0x80000000L).longValue());
        assertEquals(-1, new RegValue32(0xffffffffL).longValue());
    }

    @Test
    public void testToFatekString() throws Exception {

        assertEquals("00000000", new RegValue32(0).toFatekString());
        assertEquals("FFFFFFFF", new RegValue32(-1).toFatekString());

        // float tests
        assertEquals("3F000000", RegValue.getForReg(DR(1), 0.5f).toFatekString());
        assertEquals("C3FA1000", RegValue.getForReg(DR(1), -500.125f).toFatekString());
    }

    @Test
    public void testFloatValue() throws Exception {

        // examples from Fatek manual
        assertEquals(1.0f, new RegValue32(0x3F800000L).floatValue());
        assertEquals(0.5f, new RegValue32(0x3F000000L).floatValue());
        assertEquals(-500.125f, new RegValue32(0xC3FA1000L).floatValue());
    }

    @Test
    public void testValueForFloat() throws Exception {

        // examples from Fatek manual
        assertEquals(1.0f, RegValue.getForReg(DR(1), 1.0f).floatValue());
        assertEquals(0.5f, RegValue.getForReg(DR(1), 0.5f).floatValue());
        assertEquals(-500.125f, RegValue.getForReg(DR(1), -500.125f).floatValue());
    }
}
