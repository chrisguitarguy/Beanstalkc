// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;

/**
 * Issue a `use` command
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class UseTubeCommand extends AbstractCommand<Boolean>
{
    private String tube;

    public UseTubeCommand(String tube)
    {
        this.tube = tube;
    }

    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write(String.format(
            "use %s\r\n",
            tube
        ).getBytes());
    }

    protected Boolean readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (!first_line[0].equals("USING")) {
            throw new ServerErrorException(String.format(
                "Expeced a \"USING\" response, got \"%s\"",
                first_line[0]
            ));
        }

        return true;
    }
}
