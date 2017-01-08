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

import java.io.IOException;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekIOException extends IOException {

    protected FatekIOException() {

        super();
    }

    public FatekIOException(Throwable e) {

        super(e);
    }

    public FatekIOException(String message) {

        super(message);
    }

    public FatekIOException(String message, Object... args) {

        super(String.format(message, args));
    }
}
