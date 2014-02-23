// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Issue a reserve command to a the server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class ReserveCommand extends AbstractReserveCommand
{
    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    public void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write("reserve\r\n".getBytes());
    }
}
