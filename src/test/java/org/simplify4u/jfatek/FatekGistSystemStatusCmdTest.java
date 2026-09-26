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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simplify4u.jfatek.io.MockConnectionFactory;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekGistSystemStatusCmdTest {

    private static final MockConnectionFactory MOCK = new MockConnectionFactory();

    @BeforeAll
    public static void setup() {
        FatekPLC.registerConnectionFactory(MOCK);
    }

    @Test
    public void testCmd() throws Exception {
        try (FatekPLC fatekPLC = new FatekPLC(
                String.format("test://test?plcId=1&plcInData=01400%02X%02X%02X", 0x29, 0xaa, 0xbb))) {

            FatekGistSystemStatus cmdSystemStatus = new FatekGistSystemStatusCmd(fatekPLC).send();

            assertTrue(cmdSystemStatus.isRun(), "SystemStatus.isRun");
            assertFalse(cmdSystemStatus.isLadderChecksumError(), "SystemStatus.isLadderChecksumError");
            assertTrue(cmdSystemStatus.isUseRomPack(), "SystemStatus.isUseRomPack");
            assertFalse(cmdSystemStatus.isWDTTimeout(), "SystemStatus.isWDTTimeout");
            assertTrue(cmdSystemStatus.isSetId(), "SystemStatus.isSetId");
            assertFalse(cmdSystemStatus.isEmergencyStop(), "SystemStatus.isEmergencyStop");

            assertEquals(0x29, cmdSystemStatus.getStatus1(), "SystemStatus.getStatus1");
            assertEquals(0xaa, cmdSystemStatus.getStatus2(), "SystemStatus.getStatus2");
            assertEquals(0xbb, cmdSystemStatus.getStatus3(), "SystemStatus.getStatus3");
        }

        assertEquals("0140", MOCK.getSentData());


    }
}
