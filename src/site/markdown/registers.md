Fatek PLC symbols of register
=============================

The status of discrete
----------------------

| Symbol | Name             | Address       | 16 bit address  | 32 bit address    |
| :----: | ---------------- | :-----------: | :-------------: | :---------------: |
| X      | Input discrete   | X0000 - X9999 | WX0000 - WX9984 | DWX0000 - DWX9968 |
| Y      | Output relay     | Y0000 - Y9999 | WY0000 - WY9984 | DWY0000 - DWY9968 |
| M      | Internal relay   | M0000 - M9999 | WM0000 - WM9984 | DWM0000 - DWM9968 |
| S      | Step relay       | S0000 - S9999 | WS0000 - WS9984 | DWS0000 - DWS9968 |
| T      | Timer discrete   | T0000 - T9999 | WT0000 - WT9984 | DWT0000 - DWT9968 |
| C      | Counter discrete | C0000 - C9999 | WC0000 - WC9984 | DWC0000 - DWC9968 |


The value of register
---------------------

| Symbol | Name             | 16 bit address  | 32 bit address    |
| :----: | ---------------- | :-------------: | :---------------: |
| TMR    | Timer register   | RT0000 - RT9999 | DRT0000 - DRT9999 |
| CTR    | Counter register | RC0000 - RC9999 | DRC0000 - DRC9999 |
| HR     | Data register    | R00000 - R65535 | DR00000 - DR65535 |
| DR     | Data register    | D00000 - D65535 | DD00000 - DD65535 |

