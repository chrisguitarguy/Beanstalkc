// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import java.io.InputStream;
import java.io.OutputStream;
import me.christopherdavis.beanstalkc.Command;
import me.christopherdavis.beanstalkc.Job;

/**
 * Put a new job into the queue.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
class PutJobCommand implements Command<Job>
{
    public Job execute(InputStream in, OutputStream out) throws BeanstalkcException
    {
        return null;
    }
}
