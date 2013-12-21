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

import com.github.s4u.jfatek.io.FatekIOException;
import com.github.s4u.jfatek.io.FatekWriter;
import com.github.s4u.jfatek.registers.DisReg;

/**
 * <p>Write the status to continuous discrete.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekWriteDiscreteCmd extends FatekCommand<Void> {

    public static final int CMD_ID = 0x45;
    private final DisReg startDiscrete;
    private final Boolean[] statuses;

    public FatekWriteDiscreteCmd(FatekPLC fatekPLC, DisReg startDiscrete, Boolean... statuses) {

        super(fatekPLC);

        this.startDiscrete = startDiscrete;
        this.statuses = statuses;
        // TODO - check range
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        int number = statuses.length;
        writer.writeByte(number == 256 ? 0 : number);
        writer.write(startDiscrete.toString());

        for (Boolean status : statuses) {
            writer.write(status);
        }
    }
}
