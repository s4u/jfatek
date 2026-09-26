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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.simplify4u.jfatek.registers.DisReg.M;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simplify4u.jfatek.io.MockConnectionFactory;
import org.simplify4u.jfatek.registers.DisRunCode;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekDiscreteControlCmdTest {

    private static final MockConnectionFactory MOCK = new MockConnectionFactory();

    @BeforeAll
    public static void setup() {
        FatekPLC.registerConnectionFactory(MOCK);
    }

    @Test
    public void testCmd() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=01420")) {
            new FatekDiscreteControlCmd(fatekPLC, M(123), DisRunCode.Disable).send();
        }

        assertEquals("01421M0123", MOCK.getSentData());

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=01420")) {
            new FatekDiscreteControlCmd(fatekPLC, M(123), DisRunCode.Enable).send();
        }

        assertEquals("01422M0123", MOCK.getSentData());

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=01420")) {
            new FatekDiscreteControlCmd(fatekPLC, M(123), DisRunCode.Set).send();
        }

        assertEquals("01423M0123", MOCK.getSentData());

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=01420")) {
            new FatekDiscreteControlCmd(fatekPLC, M(123), DisRunCode.Reset).send();
        }

        assertEquals("01424M0123", MOCK.getSentData());
    }
}
