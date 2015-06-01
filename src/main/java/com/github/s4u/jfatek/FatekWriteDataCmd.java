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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private final List<RegValue> values = new ArrayList<>();

    /**
     * Write command. After this constructor we need call addValue.
     *
     * @param fatekPLC fatek connection
     * @param startReg register to startWrite
     */
    public FatekWriteDataCmd(FatekPLC fatekPLC, DataReg startReg) {

        super(fatekPLC);
        this.startReg = startReg;
    }

    public FatekWriteDataCmd(FatekPLC fatekPLC, DataReg startReg, RegValue... values) {

        super(fatekPLC);
        this.startReg = startReg;
        this.values.addAll(Arrays.asList(values));
    }

    public FatekWriteDataCmd(FatekPLC fatekPLC, DataReg startReg, long... longValues) {

        super(fatekPLC);
        this.startReg = startReg;
        for (long value : longValues) {
            values.add(RegValue.getForReg(startReg, value));
        }
    }

    /**
     * Add next value to write.
     *
     * @param value value
     * @return this object
     */
    public FatekWriteDataCmd addValue(RegValue value) {
        values.add(value);
        return this;
    }

    /**
     * Add next value to write.
     *
     * @param value value
     * @return this object
     */
    public FatekWriteDataCmd addValue(long value) {
        values.add(RegValue.getForReg(startReg, value));
        return this;
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException, FatekException {

        writer.writeByte(values.size());
        writer.write(startReg.toString());

        for (RegValue value : values) {
            if (value.is32Bit() != startReg.is32Bits() || value.isDiscrete() != startReg.isDiscrete()) {
                throw new FatekException("Invalid value type");
            }
            writer.write(value.toFatekString());
        }
    }
}
