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

/**
 * @author Slawomir Jaranowski.
 */
public class FatekGistSystemStatus {

    private int status1;
    private int status2;
    private int status3;

    FatekGistSystemStatus() {
        // restrict create to package
    }

    void setStatus1(int status1, int status2, int status3) {

        this.status1 = status1;
        this.status2 = status2;
        this.status3 = status3;
    }

    public int getStatus1() {

        return status1;
    }

    public int getStatus2() {

        return status2;
    }

    public int getStatus3() {

        return status3;
    }

    public boolean isRun() {

        return (status1 & 1) != 0;
    }

    public boolean isLadderChecksumError() {

        return (status1 & (1 << 2)) != 0;
    }

    public boolean isUseRomPack() {

        return (status1 & (1 << 3)) != 0;
    }

    public boolean isWDTTimeout() {

        return (status1 & (1 << 4)) != 0;
    }

    public boolean isSetId() {

        return (status1 & (1 << 5)) != 0;
    }

    public boolean isEmergencyStop() {

        return (status1 & (1 << 6)) != 0;
    }
}
