// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Issue an ignore command to the server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class IgnoreCommand extends AbstractWatchingCommand
{
    private String tube;

    public IgnoreCommand(String tube)
    {
        this.tube = tube;
    }

    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    public void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write(String.format(
            "ignore %s\r\n",
            tube
        ).getBytes());
    }
}
