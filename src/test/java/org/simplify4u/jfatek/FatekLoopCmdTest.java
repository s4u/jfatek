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

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekLoopCmdTest {

    @Test
    public void testDefaultMsg() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("loop://test?plcId=1&t=1")) {
            new FatekLoopCmd(fatekPLC).send();
        }
    }

    @Test
    public void testMessage() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014E0ABCDEFG&plcInData=014E0ABCDEFG")) {
            new FatekLoopCmd(fatekPLC, "ABCDEFG").send();
        }
    }

    @Test(expectedExceptions = FatekException.class, expectedExceptionsMessageRegExp = "Response not equals")
    public void testMessageNotEqual() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014E0ABCDEFG&plcInData=014E0GFEDCBA")) {
            new FatekLoopCmd(fatekPLC, "ABCDEFG").send();
        }
    }

    @Test(expectedExceptions = FatekException.class, expectedExceptionsMessageRegExp = "Invalid response length")
    public void testMessageResLength() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=014E0ABCDEFG&plcInData=014E0ABC")) {
            new FatekLoopCmd(fatekPLC, "ABCDEFG").send();
        }
    }

}
