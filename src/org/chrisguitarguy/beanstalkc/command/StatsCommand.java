// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.OutputStream;
import java.io.IOException;
import java.util.Map;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

/**
 * Send a stats-tube command to the server.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class StatsCommand extends AbstractStatsCommand<Map<String, String>>
{
    /**
     * @see     AbstractCommand#sendRequest
     */
    @Override
    protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
    {
        out.write("stats\r\n".getBytes());
    }

    /**
     * @see     AbstractStatsCommand#getParser
     */
    @Override
    protected YamlParser<Map<String, String>> getParser()
    {
        return new YamlDictParser();
    }
}
