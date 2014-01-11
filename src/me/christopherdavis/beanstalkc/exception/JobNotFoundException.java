// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.exception;

import me.christopherdavis.beanstalkc.BeanstalkcException;

/**
 * Throw when a consomer command returns a NOT_FOUND response.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class JobNotFoundException extends BeanstalkcException
{
    public JobNotFoundException(Throwable cause)
    {
        super(cause);
    }

    public JobNotFoundException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public JobNotFoundException(String msg)
    {
        super(msg);
    }
}
