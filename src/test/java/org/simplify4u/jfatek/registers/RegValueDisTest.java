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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class RegValueDisTest {

    @Test
    public void testBoolValue() {
        assertTrue(new RegValueDis(true).boolValue());
        assertFalse(new RegValueDis(false).boolValue());
    }

    @Test
    public void testIntValue() {
        assertEquals(new RegValueDis(true).intValue(), 1);
        assertEquals(new RegValueDis(false).intValue(), 0);
    }

    @Test
    public void testIntValueUnsigned() {
        assertEquals(new RegValueDis(true).intValueUnsigned(), 1);
        assertEquals(new RegValueDis(false).intValueUnsigned(), 0);
    }

    @Test
    public void testLongValue() {
        assertEquals(new RegValueDis(true).longValue(), 1);
        assertEquals(new RegValueDis(false).longValue(), 0);
    }

    @Test
    public void testLongValueUnsigned() {
        assertEquals(new RegValueDis(true).longValueUnsigned(), 1);
        assertEquals(new RegValueDis(false).longValueUnsigned(), 0);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testFload() {
        assertEquals(new RegValueDis(true).floatValue(), 1.0);
    }

    @Test
    public void testToFatekString() {
        assertEquals(new RegValueDis(true).toFatekString(), "1");
        assertEquals(new RegValueDis(false).toFatekString(), "0");
    }

    @Test
    public void testEquals() {

        RegValue val00 = new RegValueDis(false);
        RegValue val01 = new RegValueDis(false);

        RegValue val10 = new RegValueDis(true);
        RegValue val11 = new RegValueDis(true);


        assertNotEquals(val00, null);
        assertNotEquals(null, val01);
        assertEquals(val00, val01);
        assertEquals(val10, val11);
    }

    @Test
    public void testHashCode() {

        RegValue val00 = new RegValueDis(false);
        RegValue val01 = new RegValueDis(false);

        RegValue val10 = new RegValueDis(true);
        RegValue val11 = new RegValueDis(true);


        assertEquals(val00.hashCode(), val01.hashCode());
        assertEquals(val10.hashCode(), val11.hashCode());
        assertNotEquals(val00.hashCode(), val10.hashCode());
    }
}
