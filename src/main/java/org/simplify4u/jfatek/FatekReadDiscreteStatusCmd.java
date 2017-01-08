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
import org.simplify4u.jfatek.registers.DisReg;

/**
 * <p>The status reading of ENABLE/DISABLE of continuous discrete.</p>
 *
 * <p>Use this command to read the ENABLE/DISABLE status of continuous adding discrete.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekReadDiscreteStatusCmd extends FatekCommand<List<Boolean>> {

    public static final int CMD_ID = 0x43;

    private final DisReg discrete;
    private final int number;

    private List<Boolean> returnData;

    public FatekReadDiscreteStatusCmd(FatekPLC fatekPLC, DisReg discrete, int number) {

        super(fatekPLC);
        this.discrete = discrete;
        this.number = number;
        // TODO range check
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    public List<Boolean> getResult() throws FatekNotSentException {

        checkSent();
        return Collections.unmodifiableList(returnData);
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        writer.writeByte(number == 256 ? 0 : number);
        writer.write(discrete.toString());
    }

    @Override
    protected void readData(FatekReader reader) throws FatekIOException {

        returnData = new ArrayList<>(number);

        for (int i = 0; i < number; i++) {
            returnData.add(reader.readBool());
        }
    }
}
