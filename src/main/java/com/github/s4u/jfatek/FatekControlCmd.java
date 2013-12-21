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

/**
 * Control RUN/STOP of PLC.
 *
 * @author Slawomir Jaranowski.
 */
public class FatekControlCmd extends FatekCommand<Void> {

    public static final int CMD_ID = 0x41;
    private final Boolean run;

    public FatekControlCmd(FatekPLC fatekPLC, boolean run) {

        super(fatekPLC);
        this.run = run;
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        writer.writeNibble(run ? 1 : 0);
    }
}
