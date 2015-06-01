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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Locale;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author Slawomir Jaranowski.
 */
final class FatekConnectionFactoryLoader {

    private static final Map<String, FatekConnectionFactory> CACHE = new WeakHashMap<>();

    private FatekConnectionFactoryLoader() {
        // it is utils class
    }

    public static FatekConnectionFactory getByScheme(String scheme) throws FatekIOException {

        if (scheme == null) {
            return null;
        }

        String schemeUp = scheme.toUpperCase(Locale.ENGLISH);
        synchronized (CACHE) {
            FatekConnectionFactory factory = CACHE.get(schemeUp);

            if (factory != null) {
                return factory;
            }

            URL url;
            String resName = String.format("jfatek/%sFatekConnectionFactory", schemeUp);

            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader == null) {
                classLoader = ClassLoader.getSystemClassLoader();
            }

            url = classLoader.getResource(resName);

            if (url == null) {
                return null;
            }

            String className;
            try {
                className = readClassName(url);
                if (className == null) {
                    return null;
                }

                Class<?> aClass = classLoader.loadClass(className);
                factory = FatekConnectionFactory.class.cast(aClass.newInstance());
                CACHE.put(schemeUp, factory);

                return factory;

            } catch (IOException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
                throw new FatekIOException(e);
            }
        }
    }

    private static String readClassName(URL url) throws IOException {

        try (BufferedReader buf = new BufferedReader(new InputStreamReader(url.openStream()))) {
            while (true) {
                String line = buf.readLine();
                if (line == null) {
                    return null;
                }

                line = line.replaceAll("#.*$", "").trim();

                if (line.length() > 0) {
                    return line;
                }
            }
        }
    }
}
