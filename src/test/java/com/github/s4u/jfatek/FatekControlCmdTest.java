/*
 * Copyright 2013 Slawomir Jaranowski
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

package com.github.s4u.jfatek;

import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekControlCmdTest {

    @Test
    public void testCmd() throws Exception {

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=01411&plcInData=01410")) {
            new FatekControlCmd(fatekPLC, true).send();
        }

        try (FatekPLC fatekPLC = new FatekPLC("test://test?plcId=1&plcOutData=01410&plcInData=01410")) {
            new FatekControlCmd(fatekPLC, false).send();
        }
    }
}
