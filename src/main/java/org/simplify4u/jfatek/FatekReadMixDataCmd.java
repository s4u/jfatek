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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.simplify4u.jfatek.io.FatekIOException;
import org.simplify4u.jfatek.io.FatekReader;
import org.simplify4u.jfatek.io.FatekWriter;
import org.simplify4u.jfatek.registers.Reg;
import org.simplify4u.jfatek.registers.RegValue;

/**
 * <p>Mixed read the random discrete status or register data.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekReadMixDataCmd extends FatekCommand<Map<Reg, RegValue>> {

    public static final int CMD_ID = 0x48;
    private final Reg[] regs;
    private Map<Reg, RegValue> result;
    private int nextRegIndex;
    private int lastRegIndex;

    /**
     * Create new command for mixed read the random discrete status or register data.
     *
     * @param fatekPLC connection manager to use
     * @param regs     regs name to read
     */
    public FatekReadMixDataCmd(FatekPLC fatekPLC, Reg... regs) {

        super(fatekPLC);
        this.regs = regs.clone();
    }

    /**
     * Create new command for mixed read the random discrete status or register data.
     *
     * @param fatekPLC connection manager to use
     * @param regs     list of regs name to read
     */
    public FatekReadMixDataCmd(FatekPLC fatekPLC, Collection<Reg> regs) {

        super(fatekPLC);
        this.regs = regs.toArray(new Reg[regs.size()]);
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected boolean isMoreDataToExecute() {
        return nextRegIndex < regs.length;
    }

    @Override
    protected void beforeExecute() {
        result = new LinkedHashMap<>();
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        // calculate max regs to one message
        StringBuilder message = new StringBuilder();
        int w = 64;

        lastRegIndex = -1;
        for (int i = nextRegIndex; i < regs.length && w > 0; i++) {

            if (regs[i].is32Bits()) {
                w -= 2;
            } else {
                w--;
            }

            if (w >= 0) {
                lastRegIndex = i;
                message.append(regs[i].toString());
            }
        }

        if (lastRegIndex >= 0) {
            writer.writeByte(lastRegIndex - nextRegIndex + 1);
            writer.write(message.toString());
        }
    }

    @Override
    protected void readData(FatekReader reader) throws FatekIOException {

        for (int i = nextRegIndex; i <= lastRegIndex; i++) {
            result.put(regs[i], RegValue.getForReg(regs[i], reader));
        }

        nextRegIndex = lastRegIndex + 1;
    }

    @Override
    public Map<Reg, RegValue> getResult() throws FatekNotSentException {

        checkSent();
        return Collections.unmodifiableMap(result);
    }
}
