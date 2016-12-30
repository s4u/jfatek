Fatek URI syntax
================

To connect with Fatek PLC you should prepare URI connection with below format:

    scheme://ip.address:port?param1=value1&paramN=ValueN

Where:

 * __schema__ - is transport protocol, tcp or udp
 * __ip.address__ - Fatek PLC address
 * __port__ - Fatek PLC port for chosen protocol
 * __param__ can be one of:
    * __plcId__ - Fatek PLC station ID - default __1__
    * __timeout__ - connection timeout in milliseconds - default __5000__

Example:

    tcp://192.168.9.9?plcId=1&timeout=3000

Example of usage
================

Read the data from continuous data registers
--------------------------------------------

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        List<RegValue> list = new FatekReadDataCmd(fatekPLC, R(100), 3).send();

        System.out.println(list.get(0).intValueUnsigned());
        System.out.println(list.get(1).intValueUnsigned());
        System.out.println(list.get(2).intValueUnsigned());
    }

Write the data to continuous data registers
-------------------------------------------

Values for writing as parameter list:

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        new FatekWriteDataCmd(fatekPLC, R(100), 1, 2, 3).send();
    }

Values for writing in sequence:

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        FatekWriteDataCmd writeDataCmd = new FatekWriteDataCmd(fatekPLC, R(100));

        writeDataCmd.addValue(1);
        writeDataCmd.addValue(2);
        writeDataCmd.addValue(3);

        writeDataCmd.send();
    }

Read the status from continuous discrete registers
--------------------------------------------------

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        List<Boolean> values = new FatekReadDiscreteCmd(fatekPLC, X(0), 4).send();
    }

Write the status to continuous discrete registers
-------------------------------------------------

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        new FatekWriteDiscreteCmd(fatekPLC, X(0), true, false, true, false).send();
    }

Mixed read the random discrete status or register data
------------------------------------------------------

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        Map<Reg, RegValue> valueMap = new FatekReadMixDataCmd(fatekPLC, X(0), R(100), D(1)).send();

        System.out.println(valueMap.get(X(0)));
        System.out.println(valueMap.get(R(100)));
        System.out.println(valueMap.get(D(1)));
    }

Mixed write the random discrete status or register data
-------------------------------------------------------

Add values to write to command:

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        FatekWriteMixDataCmd writeMixDataCmd = new FatekWriteMixDataCmd(fatekPLC);

        writeMixDataCmd.addReg(X(0), true);
        writeMixDataCmd.addReg(R(100), 1000);
        writeMixDataCmd.addReg(D(1), 2000);
        writeDataCmd.send();
    }

Value to write as Map:

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {

        Map<Reg, RegValue> valueMap = new HashMap<>();
        valueMap.put(X(0), RegValueDis.TRUE);
        valueMap.put(R(100), new RegValue16(1000));
        valueMap.put(DD(100), new RegValue32(1000));

        new FatekWriteMixDataCmd(fatekPLC, valueMap).send();
    }
