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

package com.github.s4u.jfatek.registers;

import static com.github.s4u.jfatek.registers.DataReg.DR;
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class RegValue32Test {

    @Test
    public void testUnSign() throws Exception {

        assertEquals(new RegValue32(0).longValueUnsigned(), 0);
        assertEquals(new RegValue32(1).longValueUnsigned(), 1);
        assertEquals(new RegValue32(0x80000000L).longValueUnsigned(), 0x80000000L);
        assertEquals(new RegValue32(0xffffffffL).longValueUnsigned(), 0xffffffffL);
        assertEquals(new RegValue32(-1).longValueUnsigned(), 0xffffffffL);
    }

    @Test
    public void testSign() throws Exception {

        assertEquals(new RegValue32(0).longValue(), 0);
        assertEquals(new RegValue32(1).longValue(), 1);
        assertEquals(new RegValue32(-1).longValue(), -1);
        assertEquals(new RegValue32(0x80000000L).longValue(), -(1L << 31));
        assertEquals(new RegValue32(0xffffffffL).longValue(), -1);
    }

    @Test
    public void testToFatekString() throws Exception {

        assertEquals(new RegValue32(0).toFatekString(), "00000000");
        assertEquals(new RegValue32(-1).toFatekString(), "FFFFFFFF");

        // float tests
        assertEquals(RegValue.getForReg(DR(1), 0.5f).toFatekString(), "3F000000");
        assertEquals(RegValue.getForReg(DR(1), -500.125f).toFatekString(), "C3FA1000");
    }

    @Test
    public void testFloatValue() throws Exception {

        // examples from Fatek manual
        assertEquals(new RegValue32(0x3F800000L).floatValue(), 1.0f);
        assertEquals(new RegValue32(0x3F000000L).floatValue(), 0.5f);
        assertEquals(new RegValue32(0xC3FA1000L).floatValue(), -500.125f);
    }

    @Test
    public void testValueForFloat() throws Exception {

        // examples from Fatek manual
        assertEquals(RegValue.getForReg(DR(1), 1.0f).floatValue(), 1.0f);
        assertEquals(RegValue.getForReg(DR(1), 0.5f).floatValue(), 0.5f);
        assertEquals(RegValue.getForReg(DR(1), -500.125f).floatValue(), -500.125f);
    }
}
