// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.OutputStream;
import java.io.IOException;
import me.christopherdavis.beanstalkc.BeanstalkcException;

/**
 * Issue a `peek-buried` requst to the server
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class PeekBuriedCommand extends AbstractPeekCommand
{
    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    public void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write("peek-buried\r\n".getBytes());
    }
}
