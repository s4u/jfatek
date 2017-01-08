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

import java.util.List;

import static org.simplify4u.jfatek.registers.DataReg.D;
import static org.simplify4u.jfatek.registers.DataReg.DD;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.simplify4u.jfatek.registers.RegValue;
import org.simplify4u.jfatek.registers.RegValue16;
import org.simplify4u.jfatek.registers.RegValue32;
import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekReadDataCmdTest {

    @Test
    public void testCmdValue16() throws Exception {
        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014603D00012"
                + "&plcInData=0146010A57FC40001")) {

            List<RegValue> list = new FatekReadDataCmd(fatekPLC, D(12), 3).send();

            System.out.println(list.get(0).intValueUnsigned());

            assertNotNull(list);
            assertEquals(list.toArray(), RegValue16.asArray(0x10A5, 0x7FC4, 0x0001));
        }
    }

    @Test
    public void testCmdValue32() throws Exception {
        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014603DD00012"
                + "&plcInData=0146010A510A57FC47FC400010001")) {

            List<RegValue> list = new FatekReadDataCmd(fatekPLC, DD(12), 3).send();

            assertNotNull(list);
            assertEquals(list.toArray(), RegValue32.asArray(0x10A510A5L, 0x7FC47FC4L, 0x00010001L));
        }
    }
}
