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

import java.io.IOException;

/**
 * <p>Connection factory for transport.</p>
 *
 * <p>To add new transport you must create file, which name match below pattern:<br/>
 * <code>jfatek/PROTOFatekConnectionFactory</code><br/>
 * Where PROTO is uppercase name of implementing protocol.
 * </p>
 *
 * <p>File must contain only class name which implements this interface.</p>
 *
 * <p>You can use new protocol as schema name in connection URI, eg.<br/>
 * <code>proto://192.168.1.100</code></p>
 *
 * @author Slawomir Jaranowski.
 */
public interface FatekConnectionFactory {

    /**
     * Return new connection to Fatek PLC.
     *
     * @param fatekConfig connection config
     * @return new connection
     * @throws IOException if problem during creating connection
     */
    FatekConnection getConnection(FatekConfig fatekConfig) throws IOException;
}
