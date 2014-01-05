// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

/**
 * Adapters wrap up the write/read response lifecycle of talking with the
 * beanstalkd server.
 *
 * The idea is that the write/read cycle would be hidden behind a single method
 * so we can use things like connection pools transparently within Client
 * implementations. Adapters should also understand global error response as
 * defined in the beanstalkd spec and throw exceptions accordingly. Adapters
 * also understand how to deal with with the various responses and when to
 * fetch the line of data, etc.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public interface Adapter
{
    /**
     * Perform a command.
     *
     * @since   0.1
     * @param   req The request to send
     * @return  The response.
     */
    public <T> T perform(Command<T> cmd) throws BeanstalkcException;
}
