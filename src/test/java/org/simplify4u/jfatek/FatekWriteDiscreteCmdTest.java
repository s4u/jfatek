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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.simplify4u.jfatek.registers.DisReg.X;
import static org.simplify4u.jfatek.registers.DisReg.Y;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simplify4u.jfatek.io.MockConnectionFactory;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekWriteDiscreteCmdTest {

    private static final MockConnectionFactory MOCK = new MockConnectionFactory();

    @BeforeAll
    public static void setup() {
        FatekPLC.registerConnectionFactory(MOCK);
    }

    @Test
    public void testCmd1() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=01450")) {
            new FatekWriteDiscreteCmd(fatekPLC, Y(10), true, false, true).send();
        }

        assertEquals("014503Y0010101", MOCK.getSentData());
    }

    @Test
    public void testCmd2() throws Exception {

        StringBuilder expectedOut = new StringBuilder();
        List<Boolean> tList = new ArrayList<>(256);

        expectedOut.append("014500X0100");
        for (int i = 0; i < 256; i++) {
            if (i % 2 == 0) {
                expectedOut.append(0);
                tList.add(false);
            } else {
                expectedOut.append(1);
                tList.add(true);
            }
        }

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=01450")) {
            new FatekWriteDiscreteCmd(fatekPLC, X(100), tList.toArray(new Boolean[256])).send();
        }

        assertEquals(expectedOut.toString(), MOCK.getSentData());
    }
}
