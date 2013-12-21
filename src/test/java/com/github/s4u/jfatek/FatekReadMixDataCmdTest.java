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

package com.github.s4u.jfatek;

import java.util.Map;

import static com.github.s4u.jfatek.registers.DataReg.DWM;
import static com.github.s4u.jfatek.registers.DataReg.R;
import static com.github.s4u.jfatek.registers.DisReg.Y;
import static org.testng.Assert.assertEquals;

import com.github.s4u.jfatek.registers.Reg;
import com.github.s4u.jfatek.registers.RegValue;
import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekReadMixDataCmdTest {

    @Test
    public void testCmd() throws Exception {

        Map<Reg, RegValue> map;
        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1" +
                "&plcOutData=014803R00001Y0009DWM0000&plcInData=014805C341003547BA")) {

            map = new FatekReadMixDataCmd(fatekPLC, R(1), Y(9), DWM(0)).send();
        }

        assertEquals(map.size(), 3);
        assertEquals(map.get(R(1)).intValueUnsigned(), 0x5c34);
        assertEquals(map.get(Y(9)).boolValue(), true);
        assertEquals(map.get(DWM(0)).longValueUnsigned(), 0x003547BAL);
    }
}
