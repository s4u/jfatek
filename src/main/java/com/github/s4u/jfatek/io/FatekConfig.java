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

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a connection configuration.
 *
 * @author Slawomir Jaranowski.
 */
public class FatekConfig {

    public static final String PARAM_PLC_ID = "plcId";
    public static final String PARAM_TIMEOUT = "timeout";

    public static final int DEFAULT_PLC_ID = 1;
    public static final int DEFAULT_TIMEOUT = 5000;

    private final URI uri;

    private final Map<String, String> params = new HashMap<>();

    FatekConfig(URI uri) {

        this.uri = uri;
        parseQuery();
    }

    /**
     * Returns the scheme component of current URI.
     *
     * @return The scheme component of this URI, or null if the scheme is undefined.
     * @see java.net.URI#getScheme()
     */
    public String getScheme() {

        return uri.getScheme();
    }

    /**
     * Returns the host component of current URI.
     *
     * @return The host component of this URI, or null if the host is undefined
     * @see java.net.URI#getHost()
     */
    public String getHost() {

        return uri.getHost();
    }

    public int getPort(int defaultPort) {

        int port = uri.getPort();
        if (port < 0) {
            port = defaultPort;
        }
        return port;
    }

    public int getPlcId() {

        if (params.containsKey(PARAM_PLC_ID)) {
            return Integer.parseInt(params.get(PARAM_PLC_ID));
        }
        return DEFAULT_PLC_ID;
    }

    /**
     * Connection timeout in milliseconds.
     * Default value is DEFAULT_TIMEOUT
     *
     * @return connection timeout
     * @see FatekConfig#DEFAULT_TIMEOUT
     */
    public int getTimeout() {

        if (params.containsKey(PARAM_TIMEOUT)) {
            return Integer.parseInt(params.get(PARAM_TIMEOUT));
        }
        return DEFAULT_TIMEOUT;
    }

    public String getParam(String key) {

        return params.get(key);
    }

    private void parseQuery() {

        String query = uri.getRawQuery();
        if (query != null) {

            Pattern p = Pattern.compile("^\\s*(\\w+)=(.*)$");

            for (String param : query.split("&")) {
                Matcher matcher = p.matcher(param);
                if (matcher.find()) {
                    String key = matcher.group(1);
                    String val = matcher.group(2);

                    try {
                        val = URLDecoder.decode(val, "ASCII");
                    } catch (UnsupportedEncodingException e) {
                        // not possible
                    }
                    params.put(key, val);
                }
            }
        }
    }

    public SocketAddress getSocketAddress(int defaultPort) {

        return new InetSocketAddress(getHost(), getPort(defaultPort));
    }

    //TODO - add params

    public int getMaxConnection() {

        return 10;
    }

    public int getClearConnInterval() {

        return 15;
    }

    public int getMinConnection() {

        return 1;
    }
}
