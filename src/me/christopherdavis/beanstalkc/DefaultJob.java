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
public class DefaultJob implements Job
{
    private int id;
    private byte[] data;
    private boolean buried;

    public DefaultJob(int id, byte[] data, boolean buried)
    {
        this.id = id;
        this.data = data;
        this.buried = buried;
    }

    public DefaultJob(int id, byte[] data)
    {
        this(id, data, false);
    }

    /**
     * @see     Job#getId
     */
    @Override
    public int getId()
    {
        return id;
    }

    /**
     * @see     Job#getBody
     */
    @Override
    public byte[] getBody()
    {
        return data;
    }

    /**
     * @see     Job#isBuried
     */
    @Override
    public boolean isBuried()
    {
        return buried;
    }
}
