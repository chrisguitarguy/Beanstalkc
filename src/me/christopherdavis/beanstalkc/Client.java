// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

/**
 * All interaction with the beanstalkd server goes through client
 * implementations.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.em>
 */
public interface Client
{
    /**
     * Put a new job into the queue.
     *
     * Adds a new job into the queue in the currently `used` tube or the tube
     * `default`.
     *
     * @since   0.1
     * @param   priority The job priority, greater than zero and less than 2**32
     * @param   delay The delay, in seconds, for the job.
     * @param   ttr The time, in seconds, the job is allowed to run. If a worker
     *          doesn't delete, release, or bury the job in ttr second, it's put
     *          back into a ready state.
     * @param   data The job body, could be anything.
     * @return  The newly created job.
     */
    public Job put(int priority, int delay, int ttr, byte[] data) throws BeanstalkcException;

    /**
     * Put a new job into the queue, but use this libraries defaults for
     * priority, delay, and ttr.
     *
     * @since   0.1
     * @see     Client#job
     * @param   data The job body
     * @return  Job The newly created job
     */
    public Job put(byte[] data) throws BeanstalkcException;

    /**
     * For job producers: use a tube.
     *
     * Using a tube means that calls to `put` will add jobs to a given tube. If
     * use is not called before `put` the tube `default` will be used.
     *
     * @since   0.1
     * @param   String tube
     * @return  True if the tube was successfully used, false otherwise
     */
    public boolean use(String tube) throws BeanstalkcException;

    /**
     * Reserve a job from the queue.
     *
     * Grabs a job from the queue for consumption. This blocks until a job is
     * ready.
     *
     * @since   0.1
     * @return  The job to that was reserved.
     */
    public Job reserve() throws BeanstalkcException;

    /**
     * Reserve a job from the queue, but do so with a timeout.
     *
     * Internally calls beanstalkd's reserve-with-timeout instead of just
     * reserve. This will block for timeout seconds.
     *
     * @since   0.1
     * @param   int timeout
     * @return  The job that was reserved
     */
    public Job reserve(int timeout) throws BeanstalkcException;

    /**
     * Delete a job from the queue.
     *
     * @since   0.1
     * @param   job The job to delete -- something returned from reserve or put,
     *          for instance.
     * @return  True if the job was deleted, false otherwise.
     */
    public boolean delete(Job job) throws BeanstalkcException;

    /**
     * Delete a job from the queue by its ID.
     *
     * @since   0.1
     * @param   job_id The integery ID of the job.
     * @return  True of the job was deleted, false otherwise.
     */
    public boolean delete(int job_id) throws BeanstalkcException;

    /**
     * Release a job back into a ready state.
     *
     * @since   0.1
     * @param   job The job to release
     * @param   priority The new priority of the job
     * @param   delay The delay before the job goes back into a ready state.
     * @return  True of the job was successfully released
     */
    public boolean release(Job job, int priority, int delay) throws BeanstalkcException;

    /**
     * Release a job back into a ready state using its job ID.
     *
     * @since   0.1
     * @param   job_id The ID of the job to release
     * @param   priority The new priority of the job
     * @param   delay The delay before the job goes back into a ready state.
     * @return  True of the job was successfully released
     */
    public boolean release(int job_id, int priority, int delay) throws BeanstalkcException;

    /**
     * Release a job back into a ready state, but use this libraries defaults
     * for priority and delay.
     *
     * @since   0.1
     * @param   job The job to release
     * @return  True of the job was successfully released
     */
    public boolean release(Job job) throws BeanstalkcException;

    /**
     * Release a job back into a ready state using its job ID, but use this
     * libraries defaults for priority and delay.
     *
     * @since   0.1
     * @param   job_id The ID of the job to release
     * @return  True of the job was successfully released
     */
    public boolean release(int job_id) throws BeanstalkcException;

    /**
     * Bury a job.
     *
     * @since   0.1
     * @param   job The job to bury.
     * @param   priority The new priority to assign the job
     * @return  True of the job was buried.
     */
    public boolean bury(Job job, int priority) throws BeanstalkcException;

    /**
     * Bury a job using its job ID.
     *
     * @since   0.1
     * @param   job_id The ID of the job to bury.
     * @return  True if the job was buried, false otherwise.
     */
    public boolean bury(int job_id, int priority) throws BeanstalkcException;

    /**
     * Bury a job, but use this libraries defaults for the new priority.
     *
     * @since   0.1
     * @param   job The job to bury.
     * @return  True of the job was buried.
     */
    public boolean bury(Job job) throws BeanstalkcException;

    /**
     * Bury a job using its job ID, but use this libraries defaults for the new
     * priority.
     *
     * @since   0.1
     * @param   job_id The ID of the job to bury.
     * @return  True if the job was buried, false otherwise.
     */
    public boolean bury(int job_id) throws BeanstalkcException;

    /**
     * Touch a job (request more time to work on the job).
     *
     * @since   0.1
     * @param   job the job to touch
     * @return  True if the touch was successful.
     */
    public boolean touch(Job job) throws BeanstalkcException;

    /**
     * Touch a job by its job ID.
     *
     * @since   0.1
     * @param   job_id The ID of the job to touch
     * @return  True if the job touch was successfule
     */
    public boolean touch(int job_id) throws BeanstalkcException;

    /**
     * For consumers to "watch" a tube, mark it as something from which jobs
     * can be drawn.
     *
     * @since   0.1
     * @param   String tube
     * @return  The number of tubes the client is currently watching
     */
    public int watch(String tube) throws BeanstalkcException;

    /**
     * For consumers to "ignore" a tube, mark it as something from which jobs
     * should not be drawn.
     *
     * @since   0.1
     * @param   String tube
     * @return  The number of tubes the client is currently watching.
     */
    public int ignore(String tube) throws BeanstalkcException;
}