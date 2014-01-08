// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

/**
 * Represents a single interaction with the server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public interface Command<T>
{
    /**
     * Given the input an output streams (usually from a socket), create a
     * request, send it, then turn it into a response.
     *
     * @since   0.1
     * @param   in The input stream
     * @param   out The output stream
     * @throws  BeanstalkcException if some sort of error response was received
     *          from the server or something happened in process
     * @throws  IOException if something went wrong during the communication
     *          with the server -- generally this is from the streams.
     * @return  A response type appropriate to the command
     */
    public T execute(InputStream in, OutputStream out) throws BeanstalkcException, IOException;
}
