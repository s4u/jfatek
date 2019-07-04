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

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.simplify4u.jfatek.io.MockConnectionFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekGistSystemStatusCmdTest {

    @BeforeClass
    public void setup() {
        FatekPLC.registerConnectionFactory(new MockConnectionFactory());
    }

    @Test
    public void testCmd() throws Exception {
        try (FatekPLC fatekPLC = new FatekPLC(
                String.format("test://test?plcId=1&plcOutData=0140&plcInData=01400%02X%02X%02X", 0x29, 0xaa, 0xbb))) {

            FatekGistSystemStatus cmdSystemStatus = new FatekGistSystemStatusCmd(fatekPLC).send();

            assertTrue(cmdSystemStatus.isRun(), "SystemStatus.isRun");
            assertFalse(cmdSystemStatus.isLadderChecksumError(), "SystemStatus.isLadderChecksumError");
            assertTrue(cmdSystemStatus.isUseRomPack(), "SystemStatus.isUseRomPack");
            assertFalse(cmdSystemStatus.isWDTTimeout(), "SystemStatus.isWDTTimeout");
            assertTrue(cmdSystemStatus.isSetId(), "SystemStatus.isSetId");
            assertFalse(cmdSystemStatus.isEmergencyStop(), "SystemStatus.isEmergencyStop");

            assertEquals(cmdSystemStatus.getStatus1(), 0x29, "SystemStatus.getStatus1");
            assertEquals(cmdSystemStatus.getStatus2(), 0xaa, "SystemStatus.getStatus2");
            assertEquals(cmdSystemStatus.getStatus3(), 0xbb, "SystemStatus.getStatus3");
        }


    }
}
