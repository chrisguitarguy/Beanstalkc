// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.OutputStream;
import java.io.IOException;
import java.util.List;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Send a list-tubes-watched command to the server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class ListTubesWatchedCommand extends AbstractStatsCommand<List<String>>
{
    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write("list-tubes-watched\r\n".getBytes());
    }

    /**
     * @see     AbstractStatsCommand#getParser
     */
    @Override
    protected YamlParser<List<String>> getParser()
    {
        return new YamlListParser();
    }
}
