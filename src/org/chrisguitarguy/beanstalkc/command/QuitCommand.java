// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.Command;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Tell the server you're done.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class QuitCommand implements Command<Boolean>
{
    public Boolean execute(InputStream in, OutputStream out) throws BeanstalkcException, IOException
    {
        out.write("quit\r\n".getBytes());
        return true;
    }
}
