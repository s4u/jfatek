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

/**
 * @author Slawomir Jaranowski.
 */
public abstract class RegValue {

    private long value;
    private final int bits;


    protected RegValue(long value, int bits) {

        this.value = value;
        this.bits = bits;
        if (this.value < 0) {
            this.value += 1L << this.bits;
        }
    }

    public int intValueUnsigned() {

        return (int) longValueUnsigned();
    }

    public int intValue() {

        return (int) longValue();
    }

    public long longValueUnsigned() {

        return value;
    }

    @SuppressWarnings("PMD.UselessParentheses") // PMD 5.0.2 error
    public long longValue() {

        if (value >= (1L << (bits - 1))) {
            return value - (1L << bits);
        }
        return value;
    }

    public boolean boolValue() {

        return value == 1;
    }

    public boolean is32Bit() {

        return bits == 32;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RegValue regValue = (RegValue) o;
        return value == regValue.value && bits == regValue.bits;
    }

    @Override
    public int hashCode() {

        int result = (int) (value ^ (value >>> 32));
        result = 31 * result + bits;
        return result;
    }

    @Override
    public String toString() {

        return String.valueOf(longValue());
    }

    public abstract String toFatekString();
}
