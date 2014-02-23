// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;

/**
 * All "stats" style reponses share the same type of OK <bytes>\r\n<data>
 * response -- this takes care of parsing it.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
abstract class AbstractStatsCommand<T> extends AbstractCommand<T>
{
    private YamlParser<T> parser = null;

    /**
     * @see     AbstractCommand#readResponse
     * @return  null if an error response is returned by the server.
     */
    @Override
    protected T readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
    {
        if (!first_line[0].equals("OK") || first_line.length < 2) {
            return null;
        }

        int len = parseInt(first_line[1], "stats body length");
        return getParser().parse(readLength(in, len));
    }

    /**
     * create an appropriate parser for this command.
     *
     * XXX any way we can reuse parsers across commands without make it super
     * awkward?
     *
     * @since   0.1
     * @return  A YamlParser implementation appropriate for the command
     */
    abstract protected YamlParser<T> getParser();
}
