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
import java.util.Collections;
import java.util.List;

import org.simplify4u.jfatek.io.FatekIOException;
import org.simplify4u.jfatek.io.FatekReader;
import org.simplify4u.jfatek.io.FatekWriter;
import org.simplify4u.jfatek.registers.DataReg;
import org.simplify4u.jfatek.registers.RegValue;

/**
 * <p>Read the data from continuous registers.</p>
 *
 * @author Slawomir Jaranowski.
 */

public class FatekReadDataCmd extends FatekCommand<List<RegValue>> {

    public static final int CMD_ID = 0x46;

    private final DataReg startReg;
    private final int number;

    private List<RegValue> result;

    /**
     * Read the data from continuous registers.
     *
     * @param fatekPLC  PLC connection
     * @param startReg start register
     * @param number registers number to read
     */
    public FatekReadDataCmd(FatekPLC fatekPLC, DataReg startReg, int number) {

        super(fatekPLC);
        this.startReg = startReg;
        this.number = number;
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    public List<RegValue> getResult() throws FatekNotSentException {

        checkSent();
        return Collections.unmodifiableList(result);
    }

    // protected methods

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        writer.writeByte(number);
        writer.write(startReg.toString());
    }

    @Override
    protected void readData(FatekReader reader) throws FatekIOException {

        result = new ArrayList<>(number);

        for (int i = 0; i < number; i++) {
            result.add(RegValue.getForReg(startReg, reader));
        }
    }
}
