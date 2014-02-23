// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Issue a list-tube-used command to the server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class ListTubeUsedCommand extends AbstractCommand<String>
{
    /**
     * @see AbstractCommand#sendRequest
     */
    @Override
    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write("list-tube-used\r\n".getBytes());
    }

    /**
     * @see     AbstractCommand#readResponse
     * @return  The name of the tube currently being used or null on failure
     */
    @Override
    protected String readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (!first_line[0].equals("USING") || first_line.length < 2) {
            return null;
        }

        return first_line[1];
    }
}
