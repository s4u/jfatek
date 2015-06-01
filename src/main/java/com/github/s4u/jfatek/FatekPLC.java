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

package com.github.s4u.jfatek;

import java.net.URI;

import com.github.s4u.jfatek.io.FatekConnection;
import com.github.s4u.jfatek.io.FatekConnectionManager;
import com.github.s4u.jfatek.io.FatekIOException;

/**
 * Main class for FatekPLC client.
 *
 * @author Slawomir Jaranowski.
 */

public class FatekPLC extends FatekConnectionManager {

    /**
     * Create new FatekPLC client instance.
     *
     * @param uri fatekPLC address
     * @throws FatekIOException when some thing wrongs
     */
    public FatekPLC(URI uri) throws FatekIOException {

        super(uri);
    }

    /**
     * Create new FatekPLC client instance.
     *
     * @param uriStr fatekPLC address
     * @throws FatekIOException when some thing wrongs
     */
    public FatekPLC(String uriStr) throws FatekIOException {

        super(uriStr);
    }

    FatekConnection getConnection() throws FatekIOException {

        return super.getConnection0();
    }

    void returnConnection(FatekConnection conn) throws FatekIOException {

        super.returnConnection0(conn);
    }
}
