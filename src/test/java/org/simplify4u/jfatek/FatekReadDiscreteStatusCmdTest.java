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

import static org.simplify4u.jfatek.registers.DisReg.S;
import static org.simplify4u.jfatek.registers.DisReg.T;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekReadDiscreteStatusCmdTest {

    @Test
    public void testCmd1() throws Exception {

        List<Boolean> values;

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014303S1000&plcInData=01430101")) {

            values = new FatekReadDiscreteStatusCmd(fatekPLC, S(1000), 3).send();

            assertNotNull(values);

            assertEquals(values.size(), 3, "values size");
            assertEquals(values.toArray(new Boolean[3]), new Boolean[]{true, false, true});
        }
    }

    @Test
    public void testCmd2() throws Exception {

        StringBuilder tStr = new StringBuilder();
        List<Boolean> tList = new ArrayList<>(256);

        tStr.append("test://test?plcId=1&plcOutData=014300T1000&plcInData=01430");
        for (int i = 0; i < 256; i++) {
            if (i % 2 == 0) {
                tStr.append(0);
                tList.add(false);
            } else {
                tStr.append(1);
                tList.add(true);
            }
        }

        try (FatekPLC fatekPLC = new FatekPLC(tStr.toString())) {
            List<Boolean> values = new FatekReadDiscreteStatusCmd(fatekPLC, T(1000), 256).send();

            assertNotNull(values);

            assertEquals(values.size(), 256, "values size");
            assertEquals(values, tList);
        }
    }
}
