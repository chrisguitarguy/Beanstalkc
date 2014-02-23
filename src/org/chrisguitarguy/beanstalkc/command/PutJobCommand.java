// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.Job;
import org.chrisguitarguy.beanstalkc.DefaultJob;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;

/**
 * Put a new job into the queue.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class PutJobCommand extends AbstractCommand<Job>
{
    private int priority;
    private int delay;
    private int ttr;
    private byte[] data;

    public PutJobCommand(int priority, int delay, int ttr, byte[] data)
    {
        this.priority = priority;
        this.delay = delay;
        this.ttr = ttr;
        this.data = data;
    }

    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write(String.format(
            "put %d %d %d %d\r\n",
            priority,
            delay,
            ttr,
            data.length
        ).getBytes());
        out.write(appendCrlf(data));
    }

    protected Job readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        // all of the error responses are a single item
        if (first_line.length < 2) {
            throw new ServerErrorException(getErrorMessage(first_line[0]));
        }

        int job_id = parseInt(first_line[1], "job ID");

        return new DefaultJob(job_id, data, first_line[0].equals("BURIED"));
    }

    private byte[] appendCrlf(byte[] orig)
    {
        byte[] n = new byte[orig.length+2];

        for (int i = 0; i < orig.length; i++) {
            n[i] = orig[i];
        }

        n[orig.length] = '\r';
        n[orig.length+1] = '\n';

        return n;
    }

    private String getErrorMessage(String resp)
    {
        String msg;

        if (resp.equals("EXPECTED_CRLF")) {
            msg = "PUT command must have a CRLF after the job body";
        } else if (resp.equals("JOB_TOO_BIG")) {
            msg = "The job body was larger the max-job-size bytes";
        } else if (resp.equals("DRAINING")) {
            msg = "Server is in \"Drain Mode\" and is no longer accepting new jobs";
        } else {
            msg = "Unknown error";
        }

        return msg;
    }
}
