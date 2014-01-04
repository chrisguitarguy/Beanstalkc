// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

/**
 * The default implemenation of Response.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
class DefaultResponse implements Response
{
    private String command;
    private byte[] command_line;
    private byte[] data;

    public DefaultResponse(String command, byte[] command_line, byte[] data)
    {
        this.command = command;
        this.command_line = command_line;
        this.data = data;
    }

    public String getCommandName()
    {
        return command;
    }

    public byte[] getCommandLine()
    {
        return command_line;
    }

    public byte[] getData()
    {
        return data;
    }
}
