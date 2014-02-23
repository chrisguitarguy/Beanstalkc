// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.JobNotFoundException;

/**
 * Kick a job
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class KickJobCommand extends AbstractCommand<Boolean>
{
    private int job_id;

    public KickJobCommand(int job_id)
    {
        this.job_id = job_id;
    }

    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write(String.format(
            "kick-job %d\r\n",
            job_id
        ).getBytes());
    }

    /**
     * @see     AbstractCommand#readResponse
     * @throws  JobNotFoundException if a NOT_FOUND response is returned
     */
    @Override
    public Boolean readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line[0].equals("KICKED")) {
            return true;
        } else if (first_line[0].equals("NOT_FOUND")) {
            throw new JobNotFoundException(String.format(
                "Job %d not found, it may not exist or not be in a kickable state",
                job_id
            ));
        }

        return false;
    }
}
