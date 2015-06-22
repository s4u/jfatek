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

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.github.s4u.jfatek.FatekException;

/**
 * @author Slawomir Jaranowski.
 */
public abstract class Reg implements Cloneable {

    protected static final Map<String, RegDesc> REGS_DESC;

    private final String name;
    private int address;
    private final boolean a32bit;
    private final int digitCount;

    static {
        Map<String, RegDesc> map = new HashMap<>();

        map.put("X", RegDesc.DISC);
        map.put("Y", RegDesc.DISC);
        map.put("M", RegDesc.DISC);
        map.put("S", RegDesc.DISC);
        map.put("T", RegDesc.DISC);
        map.put("C", RegDesc.DISC);

        map.put("WX", RegDesc.DATA4_16B);
        map.put("WY", RegDesc.DATA4_16B);
        map.put("WM", RegDesc.DATA4_16B);
        map.put("WS", RegDesc.DATA4_16B);
        map.put("WT", RegDesc.DATA4_16B);
        map.put("WC", RegDesc.DATA4_16B);
        map.put("RT", RegDesc.DATA4_16B);
        map.put("RC", RegDesc.DATA4_16B);

        map.put("R", RegDesc.DATA5_16B);
        map.put("D", RegDesc.DATA5_16B);
        map.put("F", RegDesc.DATA5_16B);

        map.put("DWX", RegDesc.DATA4_32B);
        map.put("DWY", RegDesc.DATA4_32B);
        map.put("DWM", RegDesc.DATA4_32B);
        map.put("DWS", RegDesc.DATA4_32B);
        map.put("DWT", RegDesc.DATA4_32B);
        map.put("DWC", RegDesc.DATA4_32B);
        map.put("DRT", RegDesc.DATA4_32B);
        map.put("DRC", RegDesc.DATA4_32B);

        map.put("DR", RegDesc.DATA5_32B);
        map.put("DD", RegDesc.DATA5_32B);
        map.put("DF", RegDesc.DATA5_32B);

        REGS_DESC = Collections.unmodifiableMap(map);
    }

    static class RegDesc {

        private final boolean isDiscrete;
        private final boolean is32bit;
        private final int digitCount;

        public static final RegDesc DISC = new RegDesc(true, false, 0);
        public static final RegDesc DATA4_16B = new RegDesc(false, false, 4);
        public static final RegDesc DATA4_32B = new RegDesc(false, true, 4);
        public static final RegDesc DATA5_16B = new RegDesc(false, false, 5);
        public static final RegDesc DATA5_32B = new RegDesc(false, true, 5);

        public RegDesc(boolean isDiscrete, boolean is32bit, int digitCount) {

            this.isDiscrete = isDiscrete;
            this.is32bit = is32bit;
            this.digitCount = digitCount;
        }
    }


    protected Reg(String name, int address, boolean a32bit, int digitCount) {

        this.name = name;
        this.address = address;
        this.a32bit = a32bit;
        this.digitCount = digitCount;
    }

    /**
     * @param strReg string representation of register
     * @return parsed register
     * @throws UnknownRegNameException if problem with parse register
     */
    public static Reg parse(String strReg) throws UnknownRegNameException {

        if (null == strReg) {
            throw new UnknownRegNameException(strReg);
        }

        String trimStrReg = strReg.trim().toUpperCase(Locale.US);

        if (trimStrReg.length() == 0) {
            throw new UnknownRegNameException(strReg);
        }

        Pattern pattern = Pattern.compile("([A-Z]+)(\\d+)");
        Matcher matcher = pattern.matcher(trimStrReg);
        if (!matcher.find()) {
            throw new UnknownRegNameException(strReg);
        }

        String regName = matcher.group(1);

        RegDesc regDesc = REGS_DESC.get(regName);

        if (null == regDesc) {
            throw new UnknownRegNameException(strReg);
        }

        int regAddress = Integer.parseInt(matcher.group(2));

        if (regDesc.isDiscrete) {
            return new DisReg(regName, regAddress);
        } else {
            return new DataReg(regName, regAddress, regDesc.is32bit, regDesc.digitCount);
        }
    }

    public boolean is32Bits() {

        return a32bit;
    }

    public abstract boolean isDiscrete();

    public void inc(int number) {

        address += number;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reg reg = (Reg) o;
        return name.equals(reg.name) && address == reg.address && a32bit == reg.a32bit && digitCount == reg.digitCount;
    }

    @Override
    public int hashCode() {

        int result = name.hashCode();
        result = 31 * result + address;
        result = 31 * result + (a32bit ? 1 : 0);
        result = 31 * result + digitCount;
        return result;
    }

    @SuppressWarnings("unchecked")
    public <T extends Reg> T cloneReg() throws FatekException {

        try {
            return (T) clone();
        } catch (CloneNotSupportedException e) {
            throw new FatekException(e);
        }
    }

    @Override
    public String toString() {

        return String.format(String.format("%%s%%0%dd", digitCount), name, address);
    }
}
