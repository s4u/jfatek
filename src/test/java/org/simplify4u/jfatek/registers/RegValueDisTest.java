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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * @author Slawomir Jaranowski.
 */
class RegValueDisTest {

    @Test
    void testBoolValue() {
        assertTrue(new RegValueDis(true).boolValue());
        assertFalse(new RegValueDis(false).boolValue());
    }

    @Test
    void testIntValue() {
        assertEquals(1, new RegValueDis(true).intValue());
        assertEquals(0, new RegValueDis(false).intValue());
    }

    @Test
    void testIntValueUnsigned() {
        assertEquals(1, new RegValueDis(true).intValueUnsigned());
        assertEquals(0, new RegValueDis(false).intValueUnsigned());
    }

    @Test
    void testLongValue() {
        assertEquals(1, new RegValueDis(true).longValue());
        assertEquals(0, new RegValueDis(false).longValue());
    }

    @Test
    void testLongValueUnsigned() {
        assertEquals(1, new RegValueDis(true).longValueUnsigned());
        assertEquals(0, new RegValueDis(false).longValueUnsigned());
    }

    @Test
    void testFload() {

        RegValueDis regValueDis = new RegValueDis(true);

        assertThrows(UnsupportedOperationException.class, regValueDis::floatValue);
    }

    @Test
    void testToFatekString() {
        assertEquals("1", new RegValueDis(true).toFatekString());
        assertEquals("0", new RegValueDis(false).toFatekString());
    }

    @Test
    void testEquals() {

        RegValue val00 = new RegValueDis(false);
        RegValue val01 = new RegValueDis(false);

        RegValue val10 = new RegValueDis(true);
        RegValue val11 = new RegValueDis(true);


        assertEquals(val00, val01);
        assertEquals(val10, val11);
    }

    @Test
    void testHashCode() {

        RegValue val00 = new RegValueDis(false);
        RegValue val01 = new RegValueDis(false);

        RegValue val10 = new RegValueDis(true);
        RegValue val11 = new RegValueDis(true);


        assertEquals(val00.hashCode(), val01.hashCode());
        assertEquals(val10.hashCode(), val11.hashCode());
        assertNotEquals(val00.hashCode(), val10.hashCode());
    }
}
