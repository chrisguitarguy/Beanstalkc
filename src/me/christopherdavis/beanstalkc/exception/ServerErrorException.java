// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.exception;

import me.christopherdavis.beanstalkc.BeanstalkcException;

/**
 * Thrown when an error response is received from the beanstalkd server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class ServerErrorException extends BeanstalkcException
{
    public ServerErrorException(Throwable cause)
    {
        super(cause);
    }

    public ServerErrorException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public ServerErrorException(String msg)
    {
        super(msg);
    }
}
