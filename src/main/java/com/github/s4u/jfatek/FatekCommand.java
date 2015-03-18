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

import com.github.s4u.jfatek.io.FatekConnection;
import com.github.s4u.jfatek.io.FatekIOException;
import com.github.s4u.jfatek.io.FatekReader;
import com.github.s4u.jfatek.io.FatekWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Slawomir Jaranowski.
 */
public abstract class FatekCommand<T> {

    private static final Logger LOG = LoggerFactory.getLogger(FatekCommand.class);

    private final FatekPLC fatekPLC;

    private boolean alreadySent;

    protected FatekCommand(FatekPLC fatekPLC) {

        this.fatekPLC = fatekPLC;
        this.alreadySent = false;
    }

    /**
     * Fatek command ID.
     *
     * @return fatek command ID
     */
    public abstract int getID();

    /**
     * Write data which command want to send to Fatek PLC.
     *
     * @param writer output writer
     * @throws FatekException
     * @throws FatekIOException
     */
    protected abstract void writeData(final FatekWriter writer) throws FatekException, FatekIOException;

    @SuppressWarnings("PMD.EmptyMethodInAbstractClassShouldBeAbstract")
    protected void readData(final FatekReader reader) throws FatekException, FatekIOException {
        // default do nothing
    }

    protected void setAlreadySent(boolean alreadySent) {

        this.alreadySent = alreadySent;
    }

    /**
     * Test if command was sent.
     *
     * @throws FatekNotSentException if command wasn't sent.
     */
    public void checkSent() throws FatekNotSentException {

        if (!alreadySent) {
            throw new FatekNotSentException();
        }
    }

    /**
     * Send command to Fatek PLC.
     *
     * @throws FatekIOException if problem with connection
     * @throws FatekException   if another problem
     */
    public T send() throws FatekIOException, FatekException {


        long startTime = System.currentTimeMillis();
        setAlreadySent(false);

        FatekConnection conn = null;
        try {
            conn = fatekPLC.getConnection();
            execute(conn);
            setAlreadySent(true);
        } catch (FatekIOException e) {
            if (conn != null) {
                conn.close();
            }
            throw e;
        } finally {
            fatekPLC.returnConnection(conn);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Process command ID: 0x{} work time: {} ms",
                        Integer.toHexString(getID()).toUpperCase(), System.currentTimeMillis() - startTime);
            }
        }

        return getResult();
    }

    /**
     * Read data from fatek PLC.
     *
     * @return read data
     * @throws FatekNotSentException when didn't run <code>send</code> method earlier
     */
    public T getResult() throws FatekNotSentException {

        checkSent();
        return null;
    }

    protected void execute(FatekConnection conn) throws FatekIOException, FatekException {

        FatekWriter writer = conn.getWriter();
        writer.writeByte(conn.getPlcId());
        writer.writeByte(getID());
        writeData(writer);
        // write whole message from internal buffer to stream
        writer.flush();

        FatekReader reader = conn.getReader();
        // before start we must read next message to internal buffer
        reader.readNextMessage();

        // check plc id in response
        if (reader.readByte() != conn.getPlcId()) {
            throw new FatekException("Incorrect return PLC ID");
        }

        // check command id in response
        if (reader.readByte() != getID()) {
            throw new FatekException("Incorrect return CMD ID");
        }

        // check status code in response
        int errorCode = reader.readNibble();
        if (errorCode != 0) {
            throw new FatekCmdErrorException(errorCode);
        }
        readData(reader);
    }
}
