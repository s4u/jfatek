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

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekConnectionFactoryLoaderTest {

    @DataProvider(name = "schemes")
    public Object[][] crateSchemesForTest() {

        return new Object[][]{
                {"loop", true},
                {"LOOP", true},
                {"Loop", true},
                {"test", true},
                {"TEST", true},
                {"Test", true},
                {"tcp", true},
                {"udp", true},
                {"NoExist", false}
        };
    }

    @Test(dataProvider = "schemes")
    public void testScheme(String scheme, boolean shouldExist) throws Exception {

        FatekConnectionFactory factory = FatekConnectionFactoryLoader.getByScheme(scheme);
        if (shouldExist) {
            assertNotNull(factory, "for scheme: " + scheme);
        } else {
            assertNull(factory, "schema should not exist: " + scheme);
        }
    }
}
