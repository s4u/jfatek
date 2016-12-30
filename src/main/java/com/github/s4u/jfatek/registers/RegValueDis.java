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
 * Discrete register value.
 *
 * @author Slawomir Jaranowski.
 */
public class RegValueDis extends RegValue {

    public static final RegValue TRUE = new RegValueDis(true);
    public static final RegValue FALSE = new RegValueDis(false);

    public RegValueDis(Boolean value) {

        super(value ? 1 : 0, 1);
    }

    @Override
    public float floatValue() {
        throw new UnsupportedOperationException("Only 32 bits registers support float");
    }


    @Override
    public long longValue() {
        return boolValue() ? 1 : 0;
    }

    @Override
    public String toString() {

        return String.valueOf(boolValue());
    }

    @Override
    public String toFatekString() {

        return String.format("%d", longValueUnsigned());
    }
}
