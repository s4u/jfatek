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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import com.github.s4u.jfatek.io.FatekIOException;
import com.github.s4u.jfatek.io.FatekReader;
import com.github.s4u.jfatek.io.FatekWriter;
import com.github.s4u.jfatek.registers.Reg;
import com.github.s4u.jfatek.registers.RegValue;

/**
 * <p>Mixed read the random discrete status or register data.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekReadMixDataCmd extends FatekCommand<Map<Reg, RegValue>> {

    public static final int CMD_ID = 0x48;
    private final Reg[] regs;
    private Map<Reg, RegValue> result;

    public FatekReadMixDataCmd(FatekPLC fatekPLC, Reg... regs) {

        super(fatekPLC);
        this.regs = regs.clone();
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        writer.writeByte(regs.length);
        for (Reg r : regs) {
            writer.write(r.toString());
        }
    }

    @Override
    protected void readData(FatekReader reader) throws FatekIOException {

        result = new LinkedHashMap<>();

        for (Reg r : regs) {
            if (r.isDiscrete()) {
                result.put(r, reader.readRegValueDis());
            } else if (r.is32Bits()) {
                result.put(r, reader.readRegVal32());
            } else {
                result.put(r, reader.readRegVal16());
            }
        }
    }

    public Map<Reg, RegValue> getResult() {

        return Collections.unmodifiableMap(result);
    }
}
