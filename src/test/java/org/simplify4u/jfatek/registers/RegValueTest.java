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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.simplify4u.jfatek.registers.DataReg.DR;
import static org.simplify4u.jfatek.registers.DataReg.R;
import static org.simplify4u.jfatek.registers.DisReg.M;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class RegValueTest {

    public static Stream<Arguments> regsClass() {

        return Stream.of(
                Arguments.of(M(1), RegValueDis.class, true),
                Arguments.of(R(1), RegValue16.class, false),
                Arguments.of(DR(1), RegValue32.class, false)
        );
    }

    @ParameterizedTest
    @MethodSource("regsClass")
    public void testForRegBool(Reg reg, Class<? extends RegValue> regValueClass, boolean isDiscrete) throws Exception {

        RegValue regValue = RegValue.getForReg(reg, true);
        assertEquals(regValueClass, regValue.getClass());
        assertTrue(regValue.boolValue(), "boolValue");
        assertEquals(isDiscrete, regValue.isDiscrete());
    }

    @ParameterizedTest
    @MethodSource("regsClass")
    public void testForRegLong(Reg reg, Class<? extends RegValue> regValueClass, boolean isDiscrete) throws Exception {

        RegValue regValue = RegValue.getForReg(reg, 123);
        assertEquals(regValueClass, regValue.getClass());
        assertTrue(regValue.boolValue(), "boolValue");
        assertEquals(isDiscrete, regValue.isDiscrete());
    }
}
