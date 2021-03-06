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
public class RegValue16 extends RegValueData {

    private static final int BITS = 16;

    public RegValue16(long value) {

        super(value, BITS);
    }

    private static RegValue16 factory(int value) {

        return new RegValue16(value);
    }

    public static RegValue16[] asArray(int... values) {

        RegValue16[] ret = new RegValue16[values.length];
        for (int i = 0; i < values.length; i++) {
            ret[i] = factory(values[i]);
        }
        return ret;
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("Only 32 bits registers support float");
    }

    @Override
    public String toFatekString() {

        return String.format("%04X", longValueUnsigned());
    }
}
