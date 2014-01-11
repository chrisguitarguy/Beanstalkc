// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.JobNotFoundException;

/**
 * Bury a job
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class BuryCommand extends AbstractCommand<Boolean>
{
    private int job_id;
    private int priority;

    public BuryCommand(int job_id, int priority)
    {
        this.job_id = job_id;
        this.priority = priority;
    }

    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write(String.format(
            "bury %d %d\r\n",
            job_id,
            priority
        ).getBytes());
    }

    /**
     * @see     AbstractCommand#readResponse
     * @throws  JobNotFoundException if a NOT_FOUND response is returned
     */
    @Override
    public Boolean readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line[0].equals("BURIED")) {
            return true;
        } else if (first_line[0].equals("NOT_FOUND")) {
            throw new JobNotFoundException(String.format(
                "Job %d not found, it may not exist or not be reserved by this client",
                job_id
            ));
        }

        return false;
    }
}
