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

package org.simplify4u.jfatek.io;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * @author Slawomir Jaranowski.
 */

public class FatekConfigTest {

    private FatekConfig fatekConfig;

    @BeforeEach
    public void setUp() throws Exception {

        fatekConfig = new FatekConfig(new URI("test://test:1234?plcId=123"));
    }

    public static Stream<Arguments> queryStrings() {

        return Stream.of(
                Arguments.of("test://test?key=value", "key", "value"),
                Arguments.of("test://test?key=va%22lue", "key", "va\"lue"),
                Arguments.of("test://test?key=va%26lue", "key", "va&lue"),
                Arguments.of("test://test?key=value&key2=value2", "key2", "value2"),
                Arguments.of("test://test?key=va%22lue&key2=va%22lue2", "key2", "va\"lue2"),
                Arguments.of("test://test?key=va%26lue&key2=va%26lue2", "key2", "va&lue2"),
                Arguments.of("test://test?key=va%26lue&key=&key2=val2", "key", "")
        );
    }

    @ParameterizedTest
    @MethodSource("queryStrings")
    public void testParams(String testUri, String key, String value) throws Exception {

        FatekConfig fc = new FatekConfig(new URI(testUri));
        assertEquals(value, fc.getParam(key).get());
    }

    @Test
    public void testGetScheme() throws Exception {

        assertEquals("test", fatekConfig.getScheme());
    }

    @Test
    public void testGetHost() throws Exception {

        assertEquals("test", fatekConfig.getHost());
    }

    @Test
    public void testGetPort() throws Exception {

        assertEquals(1234, fatekConfig.getPort(9999));

        FatekConfig fatekConfig2 = new FatekConfig(new URI("test://test"));
        assertEquals(9999, fatekConfig2.getPort(9999));
    }

    @Test
    public void testGetPlcId() throws Exception {

        assertEquals(123, fatekConfig.getPlcId());

        FatekConfig fatekConfig2 = new FatekConfig(new URI("test://test"));
        assertEquals(FatekConfig.DEFAULT_PLC_ID, fatekConfig2.getPlcId());
    }

    public static Stream<Arguments> name() {

        return Stream.of(
                Arguments.of("test://test1", "test1"),
                Arguments.of("test://test1/test2", "test1/test2"),
                Arguments.of("test:///test3/test4", "/test3/test4"),
                Arguments.of("test://test1/", "test1/")
        );
    }

    @ParameterizedTest
    @MethodSource("name")
    public void testGetFullName(String testUri, String name) throws URISyntaxException {
        FatekConfig fc = new FatekConfig(new URI(testUri));

        assertEquals(name, fc.getFullName());
    }
}
