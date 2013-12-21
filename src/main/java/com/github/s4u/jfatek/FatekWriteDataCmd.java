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
import com.github.s4u.jfatek.registers.DataReg;
import com.github.s4u.jfatek.registers.RegValue;

/**
 * <p>Write the data to continuous registers.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekWriteDataCmd extends FatekCommand {

    public static final int CMD_ID = 0x47;
    private final DataReg startReg;
    private final RegValue[] values;

    public FatekWriteDataCmd(FatekPLC fatekPLC, DataReg startReg, RegValue... values) {

        super(fatekPLC);
        this.startReg = startReg;
        this.values = values;
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException, FatekException {

        writer.writeByte(values.length);
        writer.write(startReg.toString());
        for (RegValue value : values) {
            if (value.is32Bit() != startReg.is32Bits()) {
                throw new FatekException("Invalid value length");
            }
            writer.write(value.toFatekString());
        }
    }
}
