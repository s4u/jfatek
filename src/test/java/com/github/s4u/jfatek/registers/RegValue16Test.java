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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class RegValue16Test {

    @Test
    public void testUnSign() throws Exception {

        assertEquals(new RegValue16(0).intValueUnsigned(), 0);
        assertEquals(new RegValue16(1).intValueUnsigned(), 1);
        assertEquals(new RegValue16(0x8000).intValueUnsigned(), 0x8000);
        assertEquals(new RegValue16(0xffff).intValueUnsigned(), 0xffff);
        assertEquals(new RegValue16(-1).intValueUnsigned(), 0xffff);
    }

    @Test
    public void testSign() throws Exception {

        assertEquals(new RegValue16(0).intValue(), 0);
        assertEquals(new RegValue16(1).intValue(), 1);
        assertEquals(new RegValue16(-1).intValue(), -1);
        assertEquals(new RegValue16(0x8000).intValue(), -(1 << 15));
        assertEquals(new RegValue16(0xffff).intValue(), -1);
    }

    @Test
    public void testBufConstructor() throws Exception {

        assertEquals(new RegValue16(new char[]{'A', 'A', 'A', 'A'}).intValueUnsigned(), 0xaaaa);
    }

    @Test
    public void testToFatekString() throws Exception {

        assertEquals(new RegValue16(0).toFatekString(), "0000");
        assertEquals(new RegValue16(-1).toFatekString(), "FFFF");
    }

    @Test
    public void testEquals() throws Exception {

        RegValue val00 = new RegValue16(0);
        RegValue val01 = new RegValue16(0);

        RegValue val10 = new RegValue16(1);
        RegValue val11 = new RegValue32(1);


        assertNotEquals(null, val00);
        assertNotEquals(val00, null);
        assertEquals(val00, val00);

        assertEquals(val00, val01);
        assertEquals(val01, val00);

        assertNotEquals(val10, val11);
        assertNotEquals(val11, val10);
    }

    @Test
    public void testHashCode() throws Exception {

        RegValue val00 = new RegValue16(0);
        RegValue val01 = new RegValue16(0);

        RegValue val10 = new RegValue16(1);
        RegValue val11 = new RegValue32(1);

        assertEquals(val00.hashCode(), val01.hashCode());
        assertNotEquals(val00.hashCode(), val10.hashCode());
        assertNotEquals(val10.hashCode(), val11.hashCode());
    }
}
