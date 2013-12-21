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

import com.github.s4u.jfatek.FatekException;

/**
 * @author Slawomir Jaranowski.
 */
public abstract class Reg implements Cloneable {

    private final String name;
    private int address;
    private final boolean a32bit;
    private final int digitCount;

    protected Reg(String name, int address, boolean a32bit, int digitCount) {

        this.name = name;
        this.address = address;
        this.a32bit = a32bit;
        this.digitCount = digitCount;
    }

    public boolean is32Bits() {

        return a32bit;
    }

    public abstract boolean isDiscrete();

    public void inc(int number) {

        address += number;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reg reg = (Reg) o;
        return name.equals(reg.name) && address == reg.address && a32bit == reg.a32bit && digitCount == reg.digitCount;
    }

    @Override
    public int hashCode() {

        int result = name.hashCode();
        result = 31 * result + address;
        result = 31 * result + (a32bit ? 1 : 0);
        result = 31 * result + digitCount;
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends Reg> T cloneReg() throws FatekException {

        try {
            return (T) clone();
        } catch (CloneNotSupportedException e) {
            throw new FatekException(e);
        }
    }

    @Override
    public String toString() {

        return String.format(String.format("%%s%%0%dd", digitCount), name, address);
    }
}
