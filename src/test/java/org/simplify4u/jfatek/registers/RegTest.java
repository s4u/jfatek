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

package org.simplify4u.jfatek.registers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.simplify4u.jfatek.registers.DataReg.D;
import static org.simplify4u.jfatek.registers.DataReg.DD;
import static org.simplify4u.jfatek.registers.DataReg.DF;
import static org.simplify4u.jfatek.registers.DataReg.DR;
import static org.simplify4u.jfatek.registers.DataReg.DRC;
import static org.simplify4u.jfatek.registers.DataReg.DRT;
import static org.simplify4u.jfatek.registers.DataReg.DWC;
import static org.simplify4u.jfatek.registers.DataReg.DWM;
import static org.simplify4u.jfatek.registers.DataReg.DWS;
import static org.simplify4u.jfatek.registers.DataReg.DWT;
import static org.simplify4u.jfatek.registers.DataReg.DWX;
import static org.simplify4u.jfatek.registers.DataReg.DWY;
import static org.simplify4u.jfatek.registers.DataReg.F;
import static org.simplify4u.jfatek.registers.DataReg.R;
import static org.simplify4u.jfatek.registers.DataReg.RC;
import static org.simplify4u.jfatek.registers.DataReg.RT;
import static org.simplify4u.jfatek.registers.DataReg.WC;
import static org.simplify4u.jfatek.registers.DataReg.WM;
import static org.simplify4u.jfatek.registers.DataReg.WS;
import static org.simplify4u.jfatek.registers.DataReg.WT;
import static org.simplify4u.jfatek.registers.DataReg.WX;
import static org.simplify4u.jfatek.registers.DataReg.WY;
import static org.simplify4u.jfatek.registers.DisReg.C;
import static org.simplify4u.jfatek.registers.DisReg.M;
import static org.simplify4u.jfatek.registers.DisReg.S;
import static org.simplify4u.jfatek.registers.DisReg.T;
import static org.simplify4u.jfatek.registers.DisReg.X;
import static org.simplify4u.jfatek.registers.DisReg.Y;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.simplify4u.jfatek.FatekException;

class RegTest {


    @Test
    void testClone() throws FatekException {

        Reg r1 = X(1);
        Reg r2 = r1.cloneReg();

        assertEquals(r1, r2);
        assertNotSame(r1, r2);
    }

    static Stream<Arguments> provideRegsOK() {

        return Stream.of(
                Arguments.of("X1", X(1)),
                Arguments.of("Y1", Y(1)),
                Arguments.of("M1", M(1)),
                Arguments.of("S1", S(1)),
                Arguments.of("T1", T(1)),
                Arguments.of("C1", C(1)),

                Arguments.of("WX1", WX(1)),
                Arguments.of("WY1", WY(1)),
                Arguments.of("WM1", WM(1)),
                Arguments.of("WS1", WS(1)),
                Arguments.of("WT1", WT(1)),
                Arguments.of("WC1", WC(1)),
                Arguments.of("RT1", RT(1)),
                Arguments.of("RC1", RC(1)),
                Arguments.of("R1", R(1)),
                Arguments.of("D1", D(1)),
                Arguments.of("F1", F(1)),

                Arguments.of("DWX1", DWX(1)),
                Arguments.of("DWY1", DWY(1)),
                Arguments.of("DWM1", DWM(1)),
                Arguments.of("DWS1", DWS(1)),
                Arguments.of("DWT1", DWT(1)),
                Arguments.of("DWC1", DWC(1)),
                Arguments.of("DRT1", DRT(1)),
                Arguments.of("DRC1", DRC(1)),
                Arguments.of("DR1", DR(1)),
                Arguments.of("DD1", DD(1)),
                Arguments.of("DF1", DF(1))
        );
    }

    @ParameterizedTest
    @MethodSource("provideRegsOK")
    void testParse(String strReg, Reg reg) throws Exception {

        assertEquals(reg, Reg.parse(strReg));
    }

    static Stream<Arguments> provideRegsWrong() {

        return Stream.of(
                Arguments.of((Object) null),
                Arguments.of(""),
                Arguments.of(" "),
                Arguments.of("X"),
                Arguments.of("O2")
        );
    }

    @ParameterizedTest
    @MethodSource("provideRegsWrong")
    void testParseError(String strReg) {

        assertThrows(UnknownRegNameException.class, () -> Reg.parse(strReg));
    }


    static Stream<Arguments> compareData() {

        return Stream.of(
                Arguments.of(R(0), R(0), 0),
                Arguments.of(R(0), R(1), -1),
                Arguments.of(R(1), R(0), 1),
                Arguments.of(R(100), DR(1), -1),
                Arguments.of(X(2), R(0), -1)
        );
    }

    @ParameterizedTest
    @MethodSource("compareData")
    void testCompare(Reg thisObject, Reg specifiedObject, int result) {

        int compareResult = thisObject.compareTo(specifiedObject);

        // normalize result value
        if (compareResult > 0) {
            compareResult = 1;
        } else if (compareResult < 0) {
            compareResult = -1;
        }

        assertEquals(result, compareResult);
    }

    @Test
    void testSortRegName() {
        List<Reg> regNameList = Arrays.asList(R(0), R(100), R(25), D(24), D(8), X(100), Y(20));

        Collections.sort(regNameList);

        Reg[] regNameSorted = regNameList.toArray(new Reg[regNameList.size()]);

        assertArrayEquals(new Reg[]{X(100), Y(20), R(0), R(25), R(100), D(8), D(24)}, regNameSorted);
    }

    @Test
    void testImmutableInc() {

        Reg r = X(10);
        Reg r2 = r.incAddress(10);

        assertEquals(10, r.getAddress());
        assertEquals(20, r2.getAddress());
    }
}
