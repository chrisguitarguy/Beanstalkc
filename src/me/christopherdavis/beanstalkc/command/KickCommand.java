// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.ServerErrorException;

/**
 * Kick a number of buried jobs out of the queue.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class KickCommand extends AbstractCommand<Integer>
{
    private int to_kick;

    public KickCommand(int to_kick)
    {
        this.to_kick = to_kick;
    }

    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write(String.format(
            "kick %d\r\n",
            to_kick
        ).getBytes());
    }

    /**
     * @see     AbstractCommand#readResponse
     * @return  An integer > 0 if the command was successful. -1 otherwise.
     * @throws  InvalidValueException if we can't parse the <count> value
     *          from the server
     * @throws  ServerErrorException if we get a server response line we can't
     *          parse or understand.
     */
    @Override
    public Integer readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line.length < 2) {
            throw new ServerErrorException(String.format(
                "Expected a response line of 2 items, got %d",
                first_line.length
            ));
        }

        if (first_line[0].equals("KICKED")) {
            return parseInt(first_line[1], "kicked job number");
        }

        return -1;
    }
}
