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

import java.util.Arrays;

import org.simplify4u.jfatek.io.FatekIOException;
import org.simplify4u.jfatek.io.FatekReader;
import org.simplify4u.jfatek.io.FatekWriter;

/**
 * <p>Testing loop back.</p>
 *
 * <p>This command makes PLC respond all test data back to Master.
 * It is only for testing the communication condition between Master and PLC
 * and it will not influence the PLC function.</p>
 *
 * @author Slawomir Jaranowski.
 */
public class FatekLoopCmd extends FatekCommand<Void> {

    public static final int CMD_ID = 0x4E;
    private final String message;

    /**
     * Create loop back command.
     *
     * @param fatekPLC connection manager to use
     * @param message test message
     */
    FatekLoopCmd(FatekPLC fatekPLC, String message) {

        // TODO range check
        super(fatekPLC);
        this.message = message;
    }

    /**
     * Create loop back command with random message.
     *
     * @param fatekPLC connection manager to use
     */
    public FatekLoopCmd(FatekPLC fatekPLC) {
        super(fatekPLC);
        message = String.format("%X", System.currentTimeMillis());
    }

    @Override
    public int getID() {

        return CMD_ID;
    }

    @Override
    protected void writeData(FatekWriter writer) throws FatekIOException {

        writer.writeNibble('0'); // will be return as status
        writer.write(message);
    }

    @Override
    protected void readData(FatekReader reader) throws FatekException, FatekIOException {

        char[] buf = new char[message.length()];

        if (reader.read(buf) != buf.length) {
            throw new FatekException("Invalid response length");
        }

        if (!Arrays.equals(message.toCharArray(), buf)) {
            throw new FatekException("Response not equals");
        }
    }
}
