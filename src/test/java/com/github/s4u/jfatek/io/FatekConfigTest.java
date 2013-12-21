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

package com.github.s4u.jfatek.io;

import java.net.URI;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */

public class FatekConfigTest {

    private FatekConfig fatekConfig;

    @BeforeMethod
    public void setUp() throws Exception {

        fatekConfig = new FatekConfig(new URI("test://test:1234?plcId=123"));
    }

    @DataProvider(name = "queryStrings", parallel = true)
    public static Object[][] queryStrings() {

        return new Object[][]{
                {"test://test?key=value", "key", "value"},
                {"test://test?key=va%22lue", "key", "va\"lue"},
                {"test://test?key=va%26lue", "key", "va&lue"},
                {"test://test?key=value&key2=value2", "key2", "value2"},
                {"test://test?key=va%22lue&key2=va%22lue2", "key2", "va\"lue2"},
                {"test://test?key=va%26lue&key2=va%26lue2", "key2", "va&lue2"},
                {"test://test?key=va%26lue&key=&key2=val2", "key", ""}
        };
    }

    @Test(dataProvider = "queryStrings")
    public void testParams(String testUri, String key, String value) throws Exception {

        FatekConfig fc = new FatekConfig(new URI(testUri));
        assertEquals(fc.getParam(key), value);
    }

    @Test
    public void testGetScheme() throws Exception {

        assertEquals(fatekConfig.getScheme(), "test");
    }

    @Test
    public void testGetHost() throws Exception {

        assertEquals(fatekConfig.getHost(), "test");
    }

    @Test
    public void testGetPort() throws Exception {

        assertEquals(fatekConfig.getPort(9999), 1234);

        FatekConfig fatekConfig2 = new FatekConfig(new URI("test://test"));
        assertEquals(fatekConfig2.getPort(9999), 9999);
    }

    @Test
    public void testGetPlcId() throws Exception {

        assertEquals(fatekConfig.getPlcId(), 123);

        FatekConfig fatekConfig2 = new FatekConfig(new URI("test://test"));
        assertEquals(fatekConfig2.getPlcId(), FatekConfig.DEFAULT_PLC_ID);
    }
}
