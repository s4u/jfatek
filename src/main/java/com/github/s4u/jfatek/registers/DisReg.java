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

import static com.github.s4u.jfatek.registers.RegName.C;
import static com.github.s4u.jfatek.registers.RegName.M;
import static com.github.s4u.jfatek.registers.RegName.S;
import static com.github.s4u.jfatek.registers.RegName.T;
import static com.github.s4u.jfatek.registers.RegName.X;
import static com.github.s4u.jfatek.registers.RegName.Y;

/**
 * @author Slawomir Jaranowski.
 */
@SuppressWarnings({"checkstyle:methodname", "PMD.TooManyStaticImports"})
public class DisReg extends Reg {

    protected DisReg(RegName name, int address) {
        super(name, address, false, 4);
    }

    @Override
    public boolean isDiscrete() {
        return true;
    }

    public static DisReg X(int address) {
        return new DisReg(X, address);
    }

    public static DisReg Y(int address) {
        return new DisReg(Y, address);
    }

    public static DisReg M(int address) {
        return new DisReg(M, address);
    }

    public static DisReg S(int address) {
        return new DisReg(S, address);
    }

    public static DisReg T(int address) {
        return new DisReg(T, address);
    }

    public static DisReg C(int address) {
        return new DisReg(C, address);
    }
}
