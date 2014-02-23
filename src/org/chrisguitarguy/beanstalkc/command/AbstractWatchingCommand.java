// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * An abstract base class for Watch and Ignore commands -- anything that returns
 * a WATCHING response.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
abstract class AbstractWatchingCommand extends AbstractCommand<Integer>
{
    /**
     * @see     AbstractCommand#readResponse
     * @return  < 0 if an error occured otherwise the number of tubes
     *          being watch
     */
    @Override
    protected Integer readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (first_line[0].equals("WATCHING") && first_line.length > 1) {
            return parseInt(first_line[1], "watching number");
        }

        return -1;
    }
}
