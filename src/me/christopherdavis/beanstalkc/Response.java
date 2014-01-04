// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

/**
 * Represents a raw response from the beanstalkd server.
 *
 * Commands know how to turn this into a nice, user-friendly response.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public interface Response
{
    /**
     * Get the command namd: the first word of the response from the server.
     *
     * @since   0.1
     * @return  The command name
     */
    public String getCommandName();

    /**
     * Get the remainder of the command line, without the command name and the
     * space that follows it.
     *
     * @since   0.1
     * @return  The rest of the command line.
     */
    public byte[] getCommandLine();

    /**
     * Get the data associated with the response -- which may be `null`, not all
     * responses have data.
     *
     * @since   0.1
     * @return  byte[] The data
     */
    public byte[] getData();
}
