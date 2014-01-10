// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.IOException;
import me.christopherdavis.beanstalkc.Job;
import me.christopherdavis.beanstalkc.DefaultJob;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.ServerErrorException;
import me.christopherdavis.beanstalkc.exception.InvalidValueException;

/**
 * Implements the read response part of a reserve command.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
abstract class AbstractReserveCommand extends AbstractCommand<Job>
{
    /**
     * @see     AbstractCommand#readResponse
     */
    @Override
    protected Job readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line[0].equals("DEADLINE_SOON")) {
            throw new ServerErrorException("Current job time-to-run is nearly expired");
        } else if (first_line[0].equals("TIMED_OUT")) {
            return null;
        } else if (!first_line[0].equals("RESERVED") || first_line.length < 3) {
            throw new ServerErrorException("The server returned an invalid response line");
        }

        int job_id = parseInt(first_line[1], "job ID");
        int body_length = parseInt(first_line[2], "job body length");
        byte[] body = readLine(in);

        if (body.length != body_length) {
            throw new InvalidValueException(String.format(
                "Expected job body to be %d bytes long, was %d bytes long",
                body_length,
                body.length
            ));
        }

        return new DefaultJob(job_id, body);
    }
}
