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

import static com.github.s4u.jfatek.registers.DataReg.DWX;
import static com.github.s4u.jfatek.registers.DataReg.F;
import static com.github.s4u.jfatek.registers.DataReg.WX;

import com.github.s4u.jfatek.registers.RegValue16;
import com.github.s4u.jfatek.registers.RegValue32;
import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekWriteDataCmdTest {

    @Test
    public void testCmdValue16() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014702F00012AAAA5555&plcInData=01470")) {
            new FatekWriteDataCmd(fatekPLC, F(12), RegValue16.asArray(0xaaaa, 0x5555)).send();
        }

    }

    @Test
    public void testCmdValue32() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014702DWX0012AAAAAAAA55555555" +
                "&plcInData=01470")) {
            new FatekWriteDataCmd(fatekPLC, DWX(12), RegValue32.asArray(0xaaaaaaaaL, 0x55555555L)).send();
        }
    }

    @Test(expectedExceptions = FatekException.class, expectedExceptionsMessageRegExp = "Invalid value length")
    public void testCmdWrongValueType() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=&plcInData=01470")) {
            new FatekWriteDataCmd(fatekPLC, WX(12), RegValue32.asArray(0xaaaa, 0x5555)).send();
        }
    }
}
