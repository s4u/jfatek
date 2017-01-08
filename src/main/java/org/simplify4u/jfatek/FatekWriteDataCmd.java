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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.simplify4u.jfatek.io.FatekIOException;
import org.simplify4u.jfatek.io.FatekWriter;
import org.simplify4u.jfatek.registers.DataReg;
import org.simplify4u.jfatek.registers.RegValue;
import org.simplify4u.jfatek.registers.RegValueData;

/**
 * <p>Write the data to continuous data registers.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekWriteDataCmd extends FatekCommand<Void> {

    public static final int CMD_ID = 0x47;
    private final DataReg startReg;
    private final List<RegValueData> values = new ArrayList<>();

    /**
     * Write command. After this constructor we need call addValue.
     *
     * @param fatekPLC plc connection
     * @param startReg first register to write
     */
    public FatekWriteDataCmd(FatekPLC fatekPLC, DataReg startReg) {

        super(fatekPLC);
        this.startReg = startReg;
    }

    /**
     * Write the data to continuous registers.
     *
     * @param fatekPLC plc connection
     * @param startReg first register to write
     * @param values values list
     */
    public FatekWriteDataCmd(FatekPLC fatekPLC, DataReg startReg, RegValueData... values) {

        super(fatekPLC);
        this.startReg = startReg;
        this.values.addAll(Arrays.asList(values));
    }

    /**
     * Write the data to continuous registers.
     *
     * @param fatekPLC plc connection
     * @param startReg first register to write
     * @param values values list
     */
    public FatekWriteDataCmd(FatekPLC fatekPLC, DataReg startReg, long... values) {

        super(fatekPLC);
        this.startReg = startReg;
        for (long value : values) {
            this.values.add((RegValueData) RegValue.getForReg(startReg, value));
        }
    }

    /**
     * Add next value to write.
     *
     * @param value value
     * @return this object
     */
    public FatekWriteDataCmd addValue(RegValueData value) {
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
        values.add((RegValueData) RegValue.getForReg(startReg, value));
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

        for (RegValueData value : values) {
            if (value.is32Bit() != startReg.is32Bits()) {
                throw new FatekException("Invalid value type");
            }
            writer.write(value.toFatekString());
        }
    }
}
