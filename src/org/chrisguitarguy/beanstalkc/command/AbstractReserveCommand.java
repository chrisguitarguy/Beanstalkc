// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.Job;
import org.chrisguitarguy.beanstalkc.DefaultJob;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;
import org.chrisguitarguy.beanstalkc.exception.InvalidValueException;

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
        byte[] body = readLength(in, body_length);

        return new DefaultJob(job_id, body);
    }
}
