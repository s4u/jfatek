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

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.simplify4u.jfatek.registers.DisReg.C;
import static org.simplify4u.jfatek.registers.DisReg.S;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simplify4u.jfatek.io.MockConnectionFactory;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekReadDiscreteCmdTest {

    private static final MockConnectionFactory MOCK = new MockConnectionFactory();

    @BeforeAll
    public static void setup() {
        FatekPLC.registerConnectionFactory(MOCK);
    }

    @Test
    public void testCmd1() throws Exception {

        List<Boolean> values;

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=01440101")) {

            values = new FatekReadDiscreteCmd(fatekPLC, S(1000), 3).send();

            assertNotNull(values);

            assertEquals(3, values.size(), "values size");
            assertArrayEquals(new Boolean[]{true, false, true}, values.toArray(new Boolean[3]));
        }

        assertEquals("014403S1000", MOCK.getSentData());
    }

    @Test
    public void testCmd2() throws Exception {

        StringBuilder tStr = new StringBuilder();
        List<Boolean> tList = new ArrayList<>(256);

        tStr.append("test://test?plcId=1&plcInData=01440");
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
            List<Boolean> values = new FatekReadDiscreteCmd(fatekPLC, C(1000), 256).send();

            assertNotNull(values);

            assertEquals(256, values.size(), "values size");
            assertEquals(tList, values);
        }

        assertEquals("014400C1000", MOCK.getSentData());
    }
}

