// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.exception;

import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Thrown when there's trouble parsing a YAML response.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class YamlException extends BeanstalkcException
{
    public YamlException(Throwable cause)
    {
        super(cause);
    }

    public YamlException(String msg, Throwable cause)
    {
        super(msg, cause);
    }

    public YamlException(String msg)
    {
        super(msg);
    }
}
