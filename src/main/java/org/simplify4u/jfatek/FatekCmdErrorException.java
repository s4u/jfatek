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

/**
 * @author Slawomir Jaranowski.
 */
public class FatekCmdErrorException extends FatekException {

    private final int error;

    public FatekCmdErrorException(int error) {

        super(prepareMessage(error));
        this.error = error;
    }

    public int getError() {

        return error;
    }

    private static String prepareMessage(int error) {

        StringBuilder sb = new StringBuilder(28);
        sb.append("Error: ").append(String.format("%X", error)).append(" - ");

        switch (error) {
            // FatekPLC PLC error codes
            case 0:
                sb.append("Error free");
                break;
            case 2:
                sb.append("Illegal value");
                break;
            case 4:
                sb.append("Illegal format, or communication command can not execute");
                break;
            case 5:
                sb.append("Can not run(Ladder Checksum error when run PLC)");
                break;
            case 6:
                sb.append("Can not run(PLC ID != Ladder ID when run PLC)");
                break;
            case 7:
                sb.append("Can not run(Syntax check error when run PLC)");
                break;
            case 9:
                sb.append("Can not run(Function not supported)");
                break;
            case 10:
                sb.append("Illegal address");
                break;
            default:
                sb.append("Unknown error code");
                break;
        }
        return sb.toString();
    }
}
