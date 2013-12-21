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

import java.util.Map;

import com.github.s4u.jfatek.io.FatekIOException;
import com.github.s4u.jfatek.io.FatekWriter;
import com.github.s4u.jfatek.registers.Reg;
import com.github.s4u.jfatek.registers.RegValue;

/**
 * <p>Mixed write the random discrete status or register data.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekWriteMixDataCmd extends FatekCommand {

    public static final int CMD_ID = 0x49;
    private final Map<? extends Reg, ? extends RegValue> values;

    public FatekWriteMixDataCmd(FatekPLC fatekPLC, Map<? extends Reg, ? extends RegValue> values) {

        super(fatekPLC);
        this.values = values;
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        writer.writeByte(values.size());

        for (Map.Entry<? extends Reg, ? extends RegValue> ve : values.entrySet()) {
            writer.write(ve.getKey().toString());
            writer.write(ve.getValue().toFatekString());
        }
    }
}
