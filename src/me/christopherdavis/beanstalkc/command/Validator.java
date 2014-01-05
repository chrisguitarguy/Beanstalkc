// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import me.christopherdavis.beanstalkc.exception.InvalidValueException;

/**
 * Provides a few static methods to help validate values that go into commands.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
class Validator
{
    private static final int MIN_PRIORITY = 0;
    private static final int MAX_PRIORITY = 2 << 31;

    public static boolean isValidPriority(int prio) throws InvalidValueException
    {
        if (prio >= MIN_PRIORITY && prio <= MAX_PRIORITY) {
            return true;
        }

        throw new InvalidValueException(String.format(
            "Priority must be >= %d and <= %d",
            MIN_PRIORITY,
            MAX_PRIORITY
        ));
    }
}
