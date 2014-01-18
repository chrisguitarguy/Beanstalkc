// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import java.io.Closeable;
import java.io.IOException;

/**
 * Adapters wrap up the write/read response lifecycle of talking with the
 * beanstalkd server.
 *
 * The idea is that the write/read cycle would be hidden behind a single method
 * so we can use things like connection pools transparently within Client
 * implementations. Adapters should also understand global error response as
 * defined in the beanstalkd spec and throw exceptions accordingly. Adapters
 * also understand how to deal with with the various responses and when to
 * fetch the line of data, etc.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public interface Adapter extends Closeable
{
    /**
     * Perform a command.
     *
     * @since   0.1
     * @param   req The request to send
     * @return  The response.
     */
    public <T> T perform(Command<T> cmd) throws BeanstalkcException;

    /**
     * Close whatever connections exist in this adapter.
     *
     * @since   0.1
     */
    public void close() throws IOException;

    /**
     * Check to see whether the adapter is open.
     *
     * @since   0.1
     * @return  boolean
     */
    public boolean isClosed();
}
