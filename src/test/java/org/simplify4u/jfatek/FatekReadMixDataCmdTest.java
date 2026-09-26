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

package org.simplify4u.jfatek;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.simplify4u.jfatek.registers.DataReg.DR;
import static org.simplify4u.jfatek.registers.DataReg.DWM;
import static org.simplify4u.jfatek.registers.DataReg.R;
import static org.simplify4u.jfatek.registers.DisReg.Y;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simplify4u.jfatek.io.MockConnectionFactory;
import org.simplify4u.jfatek.registers.Reg;
import org.simplify4u.jfatek.registers.RegValue;

/**
 * @author Slawomir Jaranowski.
 */
class FatekReadMixDataCmdTest {

    private static final MockConnectionFactory MOCK = new MockConnectionFactory();

    @BeforeAll
    static void setup() {
        FatekPLC.registerConnectionFactory(MOCK);
    }

    @Test
    void testCmd() throws Exception {

        Map<Reg, RegValue> map;
        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1"
                + "&plcInData=014805C341003547BA")) {

            map = new FatekReadMixDataCmd(fatekPLC, R(1), Y(9), DWM(0)).send();
        }

        assertEquals("014803R00001Y0009DWM0000", MOCK.getSentData());

        assertEquals(3, map.size());
        assertEquals(0x5c34, map.get(R(1)).intValueUnsigned());
        assertTrue(map.get(Y(9)).boolValue());
        assertEquals(0x003547BAL, map.get(DWM(0)).longValueUnsigned());
    }

    @Test
    void testLongMessage1() throws Exception {

        StringBuilder outRegs = new StringBuilder();
        StringBuilder inRegs = new StringBuilder();
        List<Reg> regs = new ArrayList<>();

        outRegs.append("014840");
        inRegs.append("01480");
        for (int i = 0; i < 64; i++) {
            outRegs.append(String.format("R%05d", i));
            inRegs.append(String.format("%04X", i));
            regs.add(R(i));
        }

        outRegs.append("014840");
        inRegs.append(";").append("01480");
        for (int i = 64; i < 128; i++) {
            outRegs.append(String.format("R%05d", i));
            inRegs.append(String.format("%04X", i));
            regs.add(R(i));
        }

        outRegs.append("014810");
        inRegs.append(";").append("01480");
        for (int i = 128; i < 144; i++) {
            outRegs.append(String.format("DR%05d", i));
            inRegs.append(String.format("%08X", i));
            regs.add(DR(i));
        }

        Map<Reg, RegValue> result;

        try (FatekPLC fatekPLC = new FatekPLC(String.format("test://test?plcId=1&plcInData=%s", inRegs))) {
            result = new FatekReadMixDataCmd(fatekPLC, regs).send();
        }

        assertEquals(outRegs.toString(), MOCK.getSentData());

        for (int i = 0; i < regs.size(); i++) {
            assertEquals(i, result.get(regs.get(i)).intValue());
        }
    }
}
