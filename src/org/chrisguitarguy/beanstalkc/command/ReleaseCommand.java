// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;
import org.chrisguitarguy.beanstalkc.exception.JobNotFoundException;

/**
 * Release a job back into a "ready" state.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class ReleaseCommand extends AbstractCommand<Boolean>
{
    private int job_id;
    private int priority;
    private int delay;

    public ReleaseCommand(int job_id, int priority, int delay)
    {
        this.job_id = job_id;
        this.priority = priority;
        this.delay = delay;
    }

    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write(String.format(
            "release %d %d %d\r\n",
            job_id,
            priority,
            delay
        ).getBytes());
    }

    /**
     * @see     AbstractCommand#sendRequest
     * @throws  ServerErrorException if an urecognized response was returned
     * @throws  JobNotFoundException if a NOT_FOUND response was given
     * @return  True if the job was released, false if it was burried
     */
    @Override
    public Boolean readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line[0].equals("RELEASED")) {
            return true;
        } else if (first_line[0].equals("NOT_FOUND")) {
            throw new JobNotFoundException(String.format(
                "Job with id %d could not be found, it may be burried, or was not reserved.",
                job_id
            ));
        }

        return false;
    }
}
