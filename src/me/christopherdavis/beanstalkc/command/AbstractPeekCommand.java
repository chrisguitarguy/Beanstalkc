// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.IOException;
import me.christopherdavis.beanstalkc.Job;
import me.christopherdavis.beanstalkc.DefaultJob;
import me.christopherdavis.beanstalkc.BeanstalkcException;

/**
 * An abstract base class for peek commands since they all have the same
 * responses
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
abstract class AbstractPeekCommand extends AbstractCommand<Job>
{
    /**
     * @see     AbstractCommand#readResponse
     */
    @Override
    public Job readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (!first_line[0].equals("FOUND") || first_line.length < 3) {
            return null;
        }

        int job_id = parseInt(first_line[1], "peek job ID");
        byte[] body = readLength(in, parseInt(first_line[2], "peek job body length"));

        return new DefaultJob(job_id, body);
    }
}
