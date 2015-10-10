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

package com.github.s4u.jfatek.registers;

import static com.github.s4u.jfatek.registers.RegName.D;
import static com.github.s4u.jfatek.registers.RegName.DD;
import static com.github.s4u.jfatek.registers.RegName.DF;
import static com.github.s4u.jfatek.registers.RegName.DR;
import static com.github.s4u.jfatek.registers.RegName.DRC;
import static com.github.s4u.jfatek.registers.RegName.DRT;
import static com.github.s4u.jfatek.registers.RegName.DWC;
import static com.github.s4u.jfatek.registers.RegName.DWM;
import static com.github.s4u.jfatek.registers.RegName.DWS;
import static com.github.s4u.jfatek.registers.RegName.DWT;
import static com.github.s4u.jfatek.registers.RegName.DWX;
import static com.github.s4u.jfatek.registers.RegName.DWY;
import static com.github.s4u.jfatek.registers.RegName.F;
import static com.github.s4u.jfatek.registers.RegName.R;
import static com.github.s4u.jfatek.registers.RegName.RC;
import static com.github.s4u.jfatek.registers.RegName.RT;
import static com.github.s4u.jfatek.registers.RegName.WC;
import static com.github.s4u.jfatek.registers.RegName.WM;
import static com.github.s4u.jfatek.registers.RegName.WS;
import static com.github.s4u.jfatek.registers.RegName.WT;
import static com.github.s4u.jfatek.registers.RegName.WX;
import static com.github.s4u.jfatek.registers.RegName.WY;

/**
 * @author Slawomir Jaranowski.
 */
@SuppressWarnings({"checkstyle:methodname", "PMD.TooManyStaticImports"})
public class DataReg extends Reg {


    protected DataReg(RegName name, int address, boolean a32bit, int digitCount) {

        super(name, address, a32bit, digitCount);
    }

    @Override
    public boolean isDiscrete() {

        return false;
    }

    public static DataReg WX(int address) {
        return new DataReg(WX, address, false, 4);
    }

    public static DataReg WY(int address) {
        return new DataReg(WY, address, false, 4);
    }

    public static DataReg WM(int address) {
        return new DataReg(WM, address, false, 4);
    }

    public static DataReg WS(int address) {
        return new DataReg(WS, address, false, 4);
    }

    public static DataReg WT(int address) {
        return new DataReg(WT, address, false, 4);
    }

    public static DataReg WC(int address) {
        return new DataReg(WC, address, false, 4);
    }

    public static DataReg RT(int address) {
        return new DataReg(RT, address, false, 4);
    }

    public static DataReg RC(int address) {
        return new DataReg(RC, address, false, 4);
    }

    public static DataReg R(int address) {
        return new DataReg(R, address, false, 5);
    }

    public static DataReg D(int address) {
        return new DataReg(D, address, false, 5);
    }

    public static DataReg F(int address) {
        return new DataReg(F, address, false, 5);
    }

    public static DataReg DWX(int address) {
        return new DataReg(DWX, address, true, 4);
    }

    public static DataReg DWY(int address) {
        return new DataReg(DWY, address, true, 4);
    }

    public static DataReg DWM(int address) {
        return new DataReg(DWM, address, true, 4);
    }

    public static DataReg DWS(int address) {
        return new DataReg(DWS, address, true, 4);
    }

    public static DataReg DWT(int address) {
        return new DataReg(DWT, address, true, 4);
    }

    public static DataReg DWC(int address) {
        return new DataReg(DWC, address, true, 4);
    }

    public static DataReg DRT(int address) {
        return new DataReg(DRT, address, true, 4);
    }

    public static DataReg DRC(int address) {
        return new DataReg(DRC, address, true, 4);
    }

    public static DataReg DR(int address) {
        return new DataReg(DR, address, true, 5);
    }

    public static DataReg DD(int address) {
        return new DataReg(DD, address, true, 5);
    }

    public static DataReg DF(int address) {
        return new DataReg(DF, address, true, 5);
    }
}
