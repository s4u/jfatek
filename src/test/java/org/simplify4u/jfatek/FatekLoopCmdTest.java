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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.simplify4u.jfatek.io.LoopConnectionFactory;
import org.simplify4u.jfatek.io.MockConnectionFactory;

/**
 * @author Slawomir Jaranowski.
 */
class FatekLoopCmdTest {

    private static final MockConnectionFactory MOCK = new MockConnectionFactory();

    @BeforeAll
    static void setup() {
        FatekPLC.registerConnectionFactory(new LoopConnectionFactory());
        FatekPLC.registerConnectionFactory(MOCK);
    }

    @Test
    void testDefaultMsg() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("loop://test?plcId=1&t=1")) {

            // random message is sent, the command fails if the echoed response differs
            assertDoesNotThrow(() -> new FatekLoopCmd(fatekPLC).send());
        }
    }

    @Test
    void testMessage() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=014E0ABCDEFG")) {
            new FatekLoopCmd(fatekPLC, "ABCDEFG").send();
        }

        assertEquals("014E0ABCDEFG", MOCK.getSentData());
    }

    @Test
    void testMessageNotEqual() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=014E0GFEDCBA")) {

            FatekException exception = assertThrows(FatekException.class,
                    () -> new FatekLoopCmd(fatekPLC, "ABCDEFG").send());

            assertEquals("Response not equals", exception.getMessage());
        }
    }

    @Test
    void testMessageResLength() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcInData=014E0ABC")) {

            FatekException exception = assertThrows(FatekException.class,
                    () -> new FatekLoopCmd(fatekPLC, "ABCDEFG").send());

            assertEquals("Invalid response length", exception.getMessage());
        }
    }

}
