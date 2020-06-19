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

package org.simplify4u.jfatek;

/**
 * @author Slawomir Jaranowski.
 */
public class FatekException extends Exception {

    private static final long serialVersionUID = 8976724460883070966L;

    public FatekException(Exception e) {

        super(e);
    }

    public FatekException(String message, Object... args) {

        super(String.format(message, args));
    }


    public FatekException() {

        super();
    }
}
