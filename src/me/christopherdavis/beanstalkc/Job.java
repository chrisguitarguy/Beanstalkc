// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

/**
 * Represents a single job in the beanstalkd queue.
 *
 * Each job has information about its ID (used to kick, delete, bury a job) and
 * the job body itself.
 *
 * @author  Christopher Davis <http://christopherdavis.me>
 * @since   0.1
 */
public interface Job
{
    /**
     * Get the job ID.
     *
     * This is returned from the beanstalkd server and gets used to kick, delete,
     * or burry the job.
     *
     * @since   0.1
     * @return  The job ID.
     */
    public int getId();

    /**
     * Get the body (data) of the job.
     *
     * Data is an arbitrary string of bytes. Beanstalkd doesn't care what it is
     * and neither does this library.
     *
     * @since   0.1
     * @return  The byte array of data
     */
    public byte[] getBody();

    /**
     * Whether or not the job is buried -- this may not be meaningful in all
     * contexts.
     *
     * @since   0.1
     * @return  True if the job was buried rather than inserted
     */
    public boolean isBuried();
}
