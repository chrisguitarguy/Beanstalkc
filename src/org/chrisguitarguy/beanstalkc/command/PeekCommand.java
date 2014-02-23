// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.Job;
import org.chrisguitarguy.beanstalkc.DefaultJob;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.JobNotFoundException;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;

/**
 * peek at a job
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class PeekCommand extends AbstractCommand<Job>
{
    private int job_id;

    public PeekCommand(int job_id)
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
            "peek %d\r\n",
            job_id
        ).getBytes());
    }

    /**
     * @see     AbstractCommand#readResponse
     * @throws  JobNotFoundException if a NOT_FOUND response is returned
     */
    @Override
    public Job readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line[0].equals("NOT_FOUND")) {
            throw new JobNotFoundException(String.format(
                "Job %d not found, it may not exist or not be reserved by this client",
                job_id
            ));
        } else if (first_line.length < 3) {
            throw new ServerErrorException("The beanstalkd server sent an invalid response line");
        }

        int job_id = parseInt(first_line[1], "peek job ID");
        byte[] body = readLength(in, parseInt(first_line[2], "peek job body length"));

        return new DefaultJob(job_id, body);
    }
}
