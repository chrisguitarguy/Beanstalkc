// This file is part of me.christopherdavis.beanstalkc
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
 * Delete a job from the server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class DeleteCommand extends AbstractCommand<Boolean>
{
    private int job_id;

    public DeleteCommand(int job_id)
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
            "delete %d\r\n",
            job_id
        ).getBytes());
    }

    /**
     * @see     AbstractCommand#readResponse
     * @throws  ServerErrorException if the job was not found or any other response
     *          but deleted was returned
     */
    @Override
    public Boolean readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line[0].equals("DELETED")) {
            return true;
        }

        if (first_line[0].equals("NOT_FOUND")) {
            throw new JobNotFoundException(String.format(
                "Job with ID %d not found. It may have expired, be burried, or simply not exist",
                job_id
            ));
        }

        throw new ServerErrorException("Unrecognized response on delete job");
    }
}
