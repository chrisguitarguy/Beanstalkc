// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

/**
 * The default implementation of Job.
 *
 * @see     Job
 * @author  Christopher Davis <http://christopherdavis.me>
 * @since   0.1
 */
class DefaultJob implements Job
{
    private int id;
    private byte[] data;

    public DefaultJob(int id, byte[] data)
    {
        this.id = id;
        this.data = data;
    }

    public int getId()
    {
        return id;
    }

    public byte[] getData()
    {
        return data;
    }
}
