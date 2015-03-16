/*
 * Copyright 2015 Slawomir Jaranowski
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
import static com.github.s4u.jfatek.registers.DataReg.R;
import static com.github.s4u.jfatek.registers.DisReg.M;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RegValueTest {

    @DataProvider(name = "regsClass")
    public Object[][] provideData() {

        return new Object[][]{
                {M(1), RegValueDis.class, true},
                {R(1), RegValue16.class, false},
                {DR(1), RegValue32.class, false},
        };
    }

    @Test(dataProvider = "regsClass")
    public void testForRegBool(Reg reg, Class<? extends RegValue> regValueClass, boolean isDiscrete) throws Exception {

        RegValue regValue = RegValue.getForReg(reg, true);
        assertEquals(regValue.getClass(), regValueClass);
        assertTrue(regValue.boolValue(), "boolValue");
        assertEquals(regValue.isDiscrete(), isDiscrete);
    }

    @Test(dataProvider = "regsClass")
    public void testForRegLong(Reg reg, Class<? extends RegValue> regValueClass, boolean isDiscrete) throws Exception {

        RegValue regValue = RegValue.getForReg(reg, 123);
        assertEquals(regValue.getClass(), regValueClass);
        assertTrue(regValue.boolValue(), "boolValue");
        assertEquals(regValue.isDiscrete(), isDiscrete);
    }
}