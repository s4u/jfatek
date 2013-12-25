Example of usage
================

    try (FatekPLC fatekPLC = new FatekPLC("tcp://192.168.9.9?plcId=1")) {
        List<RegValue> list = new FatekReadDataCmd(fatekPLC, R(100), 3).send();

        System.out.println(list.get(0).intValueUnsigned());
        System.out.println(list.get(1).intValueUnsigned());
        System.out.println(list.get(2).intValueUnsigned());
    }

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
