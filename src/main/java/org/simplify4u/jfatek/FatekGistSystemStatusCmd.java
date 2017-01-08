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

import org.simplify4u.jfatek.io.FatekIOException;
import org.simplify4u.jfatek.io.FatekReader;
import org.simplify4u.jfatek.io.FatekWriter;

/** The gist read the system status of PLC.
 *
 * @author Slawomir Jaranowski.
 */
public class FatekGistSystemStatusCmd extends FatekCommand<FatekGistSystemStatus> {

    public static final int CMD_ID = 0x40;
    private final FatekGistSystemStatus status;

    public FatekGistSystemStatusCmd(FatekPLC fatekPLC) {
        super(fatekPLC);
        status = new FatekGistSystemStatus();
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekException, FatekIOException {
        // nothing to send by this command
    }

    @Override
    protected void readData(FatekReader reader) throws FatekIOException {

        status.setStatus1(reader.readByte(), reader.readByte(), reader.readByte());
    }

    @Override
    public FatekGistSystemStatus getResult() {

        return status;
    }
}
