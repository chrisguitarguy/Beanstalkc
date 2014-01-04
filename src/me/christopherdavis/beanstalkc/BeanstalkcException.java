// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

/**
 * A marker interface for exceptions in this library.
 *
 * @author  Christopher Davis <http://christopherdavis.me>
 * @since   0.1
 */
public class BeanstalkcException extends Exception
{
    public BeanstalkcException(Throwable cause)
    {
        super(cause);
    }

    public BeanstalkcException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public BeanstalkcException(String msg)
    {
        super(msg);
    }
}
