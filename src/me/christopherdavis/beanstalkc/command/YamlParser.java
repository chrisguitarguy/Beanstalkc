// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.IOException;
import me.christopherdavis.beanstalkc.BeanstalkcException;

/**
 * A simple yaml parser for the various beanstalkd yaml responses.
 *
 * Since beanstalkd's YAML response are extremely limited, we're not going
 * to try to hard to parse them: just a few simple parsers.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
interface YamlParser<T>
{
    /**
     * Parse the response body into type T -- probably a List or Map.
     *
     * @since   0.1
     * @param   input the bytes comprising the response body
     * @return  T, something that makes sense for the parser
     */
    public T parse(byte[] input) throws BeanstalkcException;

    /**
     * Parse the response body into type T -- probably a List or Map.
     *
     * @since   0.1
     * @param   input the bytes comprising the response body
     * @return  T, something that makes sense for the parser
     */
    public T parse(InputStream input) throws BeanstalkcException;
}
