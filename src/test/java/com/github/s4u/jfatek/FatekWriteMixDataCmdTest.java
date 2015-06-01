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

import java.util.LinkedHashMap;
import java.util.Map;

import static com.github.s4u.jfatek.registers.DataReg.DF;
import static com.github.s4u.jfatek.registers.DataReg.R;
import static com.github.s4u.jfatek.registers.DataReg.WY;
import static com.github.s4u.jfatek.registers.DisReg.X;
import static com.github.s4u.jfatek.registers.DisReg.Y;

import com.github.s4u.jfatek.registers.Reg;
import com.github.s4u.jfatek.registers.RegValue;
import com.github.s4u.jfatek.registers.RegValue16;
import com.github.s4u.jfatek.registers.RegValue32;
import com.github.s4u.jfatek.registers.RegValueDis;
import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekWriteMixDataCmdTest {

    @Test
    public void testCmd() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1"
                + "&plcOutData=014904Y00001Y00010WY00085555DF00002000000FF"
                + "&plcInData=01490")) {

            Map<Reg, RegValue> map = new LinkedHashMap<>();
            map.put(Y(0), RegValueDis.TRUE);
            map.put(Y(1), RegValueDis.FALSE);
            map.put(WY(8), new RegValue16(0x5555));
            map.put(DF(2), new RegValue32(0x000000FFL));

            new FatekWriteMixDataCmd(fatekPLC, map).send();
        }
    }

    @Test
    public void testCmdAdd() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1"
                + "&plcOutData=014906Y00001Y00010WY00085555DF00002000000FFX00101R000100000"
                + "&plcInData=01490")) {

            FatekWriteMixDataCmd cmd = new FatekWriteMixDataCmd(fatekPLC);
            cmd.addReg(Y(0), RegValueDis.TRUE);
            cmd.addReg(Y(1), RegValueDis.FALSE);
            cmd.addReg(WY(8), 0x5555);
            cmd.addReg(DF(2), 0x000000FFL);
            cmd.addReg(X(10), true);
            cmd.addReg(R(10), false);
            cmd.send();
        }
    }

    @Test
    public void testCmdArgs() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1"
                + "&plcOutData=014901Y00001"
                + "&plcInData=01490")) {

            new FatekWriteMixDataCmd(fatekPLC, Y(0), RegValueDis.TRUE).send();
        }
    }
}
