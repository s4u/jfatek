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

/**
 * @author Slawomir Jaranowski.
 */
public class RegValue32 extends RegValueData {

    private static final int BITS = 32;

    public RegValue32(long value) {

        super(value, BITS);
    }

    private static RegValue32 factory(long value) {

        return new RegValue32(value);
    }

    public static RegValue32[] asArray(long... values) {

        RegValue32[] ret = new RegValue32[values.length];
        for (int i = 0; i < values.length; i++) {
            ret[i] = factory(values[i]);
        }
        return ret;
    }

    @Override
    public float floatValue() {
        return Float.intBitsToFloat(intValue());
    }

    @Override
    public String toFatekString() {

        return String.format("%08X", longValueUnsigned());
    }
}
