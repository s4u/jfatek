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
