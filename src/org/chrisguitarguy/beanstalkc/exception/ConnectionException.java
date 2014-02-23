// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.exception;

import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Throw when an adapter has trouble connecting or reading/writing a response
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class ConnectionException extends BeanstalkcException
{
    public ConnectionException(Throwable cause)
    {
        super(cause);
    }

    public ConnectionException(String msg, Throwable cause)
    {
        super(msg, cause);
    }
}
