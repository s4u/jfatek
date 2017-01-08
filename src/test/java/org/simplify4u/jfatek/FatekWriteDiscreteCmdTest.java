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

import static org.simplify4u.jfatek.registers.DisReg.X;
import static org.simplify4u.jfatek.registers.DisReg.Y;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekWriteDiscreteCmdTest {

    @Test
    public void testCmd1() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014503Y0010101&plcInData=01450")) {
            new FatekWriteDiscreteCmd(fatekPLC, Y(10), true, false, true).send();
        }
    }

    @Test
    public void testCmd2() throws Exception {

        StringBuilder tStr = new StringBuilder();
        List<Boolean> tList = new ArrayList<>(256);

        tStr.append("test://test?plcId=1&plcOutData=014500X0100");
        for (int i = 0; i < 256; i++) {
            if (i % 2 == 0) {
                tStr.append(0);
                tList.add(false);
            } else {
                tStr.append(1);
                tList.add(true);
            }
        }

        tStr.append("&plcInData=01450");

        try (FatekPLC fatekPLC = new FatekPLC(tStr.toString())) {
            new FatekWriteDiscreteCmd(fatekPLC, X(100), tList.toArray(new Boolean[256])).send();
        }
    }
}
