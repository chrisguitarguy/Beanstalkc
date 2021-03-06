// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.exception;

import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Thrown when a bad value is passed to a job.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class InvalidValueException extends BeanstalkcException
{
    public InvalidValueException(Throwable cause)
    {
        super(cause);
    }

    public InvalidValueException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public InvalidValueException(String msg)
    {
        super(msg);
    }
}
