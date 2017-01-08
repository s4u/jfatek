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

import org.simplify4u.jfatek.io.FatekIOException;
import org.simplify4u.jfatek.io.FatekWriter;
import org.simplify4u.jfatek.registers.DisReg;
import org.simplify4u.jfatek.registers.DisRunCode;

/**
 * Single discrete control.
 *
 * @author Slawomir Jaranowski.
 */
public class FatekDiscreteControlCmd extends FatekCommand<Void> {

    public static final int CMD_ID = 0x42;
    private final DisRunCode runningCode;
    private final DisReg discrete;

    public FatekDiscreteControlCmd(FatekPLC fatekPLC, DisReg discrete, DisRunCode runningCode) {

        super(fatekPLC);
        this.discrete = discrete;
        this.runningCode = runningCode;
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        writer.writeNibble(runningCode.getCode());
        writer.write(discrete.toString());
    }
}
