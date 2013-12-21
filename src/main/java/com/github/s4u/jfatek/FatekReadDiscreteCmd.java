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

import com.github.s4u.jfatek.registers.DisReg;

/**
 * <p>The status reading of continuous discrete.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekReadDiscreteCmd extends FatekReadDiscreteStatusCmd {

    public static final int CMD_ID = 0x44;

    public FatekReadDiscreteCmd(FatekPLC fatekPLC, DisReg discrete, int number) {

        super(fatekPLC, discrete, number);
    }

    @Override
    public int getID() {

        return CMD_ID;
    }
}
