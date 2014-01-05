// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import me.christopherdavis.beanstalkc.Command;
import me.christopherdavis.beanstalkc.Job;
import me.christopherdavis.beanstalkc.BeanstalkcException;

/**
 * Put a new job into the queue.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class PutJobCommand implements Command<Job>
{
    private int priority;
    private int delay;
    private int ttr;
    private byte[] data;

    public PutJobCommand(int priority, int delay, int ttr, byte[] data) throws BeanstalkcException
    {
        if (Validator.isValidPriority(priority)) {
            this.priority = priority;
        }
        this.delay = delay;
        this.ttr = ttr;
        this.data = data;
    }

    public Job execute(InputStream in, OutputStream out) throws BeanstalkcException
    {
        return null;
    }
}
