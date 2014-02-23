// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc;

import java.util.Map;
import java.util.List;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.adapter.SocketAdapter;
import org.chrisguitarguy.beanstalkc.command.PutJobCommand;
import org.chrisguitarguy.beanstalkc.command.UseTubeCommand;
import org.chrisguitarguy.beanstalkc.command.ReserveCommand;
import org.chrisguitarguy.beanstalkc.command.ReserveTimeoutCommand;
import org.chrisguitarguy.beanstalkc.command.DeleteCommand;
import org.chrisguitarguy.beanstalkc.command.ReleaseCommand;
import org.chrisguitarguy.beanstalkc.command.BuryCommand;
import org.chrisguitarguy.beanstalkc.command.KickJobCommand;
import org.chrisguitarguy.beanstalkc.command.TouchCommand;
import org.chrisguitarguy.beanstalkc.command.PeekCommand;
import org.chrisguitarguy.beanstalkc.command.WatchCommand;
import org.chrisguitarguy.beanstalkc.command.IgnoreCommand;
import org.chrisguitarguy.beanstalkc.command.PeekReadyCommand;
import org.chrisguitarguy.beanstalkc.command.PeekDelayedCommand;
import org.chrisguitarguy.beanstalkc.command.PeekBuriedCommand;
import org.chrisguitarguy.beanstalkc.command.KickCommand;
import org.chrisguitarguy.beanstalkc.command.StatsJobCommand;
import org.chrisguitarguy.beanstalkc.command.StatsTubeCommand;
import org.chrisguitarguy.beanstalkc.command.StatsCommand;
import org.chrisguitarguy.beanstalkc.command.ListTubesCommand;
import org.chrisguitarguy.beanstalkc.command.ListTubesWatchedCommand;
import org.chrisguitarguy.beanstalkc.command.ListTubeUsedCommand;
import org.chrisguitarguy.beanstalkc.command.QuitCommand;

/**
 * The default implementation of Client.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
public class DefaultClient implements Client
{
    final private static int DEFAULT_DELAY = 0;
    final private static int DEFAULT_TTR = 60; // 1 minute
    final private static int DEFAULT_PRIORITY = 1024;
    final private static String DEFAULT_HOST = "localhost";
    final private static int DEFAULT_PORT = 11300;

    private Adapter adapter;
    private boolean gotClose = false;

    public DefaultClient() throws BeanstalkcException
    {
        this(DEFAULT_HOST, DEFAULT_PORT);
    }

    public DefaultClient(String host, int port) throws BeanstalkcException
    {
        this(new SocketAdapter(host, port));
    }

    public DefaultClient(Adapter adapter) throws BeanstalkcException
    {
        this.adapter = adapter;
    }

    /**
     * @see     Client#put
     */
    public Job put(int priority, int delay, int ttr, byte[] data) throws BeanstalkcException
    {
        return doCommand(new PutJobCommand(priority, delay, ttr, data));
    }

    /**
     * @see     Client#put
     */
    public Job put(byte[] data) throws BeanstalkcException
    {
        return put(DEFAULT_PRIORITY, DEFAULT_DELAY, DEFAULT_TTR, data);
    }

    /**
     * @see     Client#use
     */
    public boolean use(String tube) throws BeanstalkcException
    {
        return doCommand(new UseTubeCommand(tube));
    }

    /**
     * @see     Client#reserve
     */
    public Job reserve() throws BeanstalkcException
    {
        return doCommand(new ReserveCommand());
    }

    /**
     * @see     Client#reserve
     */
    public Job reserve(int timeout) throws BeanstalkcException
    {
        return doCommand(new ReserveTimeoutCommand(timeout));
    }


    /**
     * @see     Client#delete
     */
    public boolean delete(Job job) throws BeanstalkcException
    {
        return delete(job.getId());
    }


    /**
     * @see     Client#delete
     */
    public boolean delete(int job_id) throws BeanstalkcException
    {
        return doCommand(new DeleteCommand(job_id));
    }

    /**
     * @see     Client#release
     */
    public boolean release(Job job, int priority, int delay) throws BeanstalkcException
    {
        return release(job.getId(), priority, delay);
    }

    /**
     * @see     Client#release
     */
    public boolean release(int job_id, int priority, int delay) throws BeanstalkcException
    {
        return doCommand(new ReleaseCommand(job_id, priority, delay));
    }

    /**
     * @see     Client#release
     */
    public boolean release(Job job) throws BeanstalkcException
    {
        return release(job.getId(), DEFAULT_PRIORITY, DEFAULT_DELAY);
    }

    /**
     * @see     Client#release
     */
    public boolean release(int job_id) throws BeanstalkcException
    {
        return release(job_id, DEFAULT_PRIORITY, DEFAULT_DELAY);
    }

    /**
     * @see     Client#bury
     */
    public boolean bury(Job job, int priority) throws BeanstalkcException
    {
        return bury(job.getId(), priority);
    }


    /**
     * @see     Client#bury
     */
    public boolean bury(int job_id, int priority) throws BeanstalkcException
    {
        return doCommand(new BuryCommand(job_id, priority));
    }

    /**
     * @see     Client#bury
     */
    public boolean bury(Job job) throws BeanstalkcException
    {
        return bury(job.getId(), DEFAULT_PRIORITY);
    }

    /**
     * @see     Client#bury
     */
    public boolean bury(int job_id) throws BeanstalkcException
    {
        return bury(job_id, DEFAULT_PRIORITY);
    }

    /**
     * @see     Client#kickJob
     */
    public boolean kickJob(Job job) throws BeanstalkcException
    {
        return kickJob(job.getId());
    }

    /**
     * @see     Client#kickJob
     */
    public boolean kickJob(int job_id) throws BeanstalkcException
    {
        return doCommand(new KickJobCommand(job_id));
    }

    /**
     * @see     Client#touch
     */
    public boolean touch(Job job) throws BeanstalkcException
    {
        return touch(job.getId());
    }

    /**
     * @see     Client#touch
     */
    public boolean touch(int job_id) throws BeanstalkcException
    {
        return doCommand(new TouchCommand(job_id));
    }

    /**
     * @see     Client#peek
     */
    public Job peek(Job job) throws BeanstalkcException
    {
        return peek(job.getId());
    }

    /**
     * @see     Client#peek
     */
    public Job peek(int job_id) throws BeanstalkcException
    {
        return doCommand(new PeekCommand(job_id));
    }

    /**
     * @see     Client#touch
     */
    public int watch(String tube) throws BeanstalkcException
    {
        return doCommand(new WatchCommand(tube));
    }

    /**
     * @see     Client#ignore
     */
    public int ignore(String tube) throws BeanstalkcException
    {
        return doCommand(new IgnoreCommand(tube));
    }

    /**
     * @see     Client#peekReady
     */
    public Job peekReady() throws BeanstalkcException
    {
        return doCommand(new PeekReadyCommand());
    }

    /**
     * @see     Client#peekDelayed
     */
    public Job peekDelayed() throws BeanstalkcException
    {
        return doCommand(new PeekDelayedCommand());
    }

    /**
     * @see     Client#peekBuried
     */
    public Job peekBuried() throws BeanstalkcException
    {
        return doCommand(new PeekBuriedCommand());
    }

    /**
     * @see     Client#kick
     */
    public int kick(int to_kick) throws BeanstalkcException
    {
        return doCommand(new KickCommand(to_kick));
    }

    /**
     * @see     Client#statsJob
     */
    public Map<String, String> statsJob(Job j) throws BeanstalkcException
    {
        return statsJob(j.getId());
    }

    /**
     * @see     Client#statsJob
     */
    public Map<String, String> statsJob(int job_id) throws BeanstalkcException
    {
        return doCommand(new StatsJobCommand(job_id));
    }

    /**
     * @see     Client#statsTube
     */
    public Map<String, String> statsTube(String tube) throws BeanstalkcException
    {
        return doCommand(new StatsTubeCommand(tube));
    }

    /**
     * @see     Client#stats
     */
    public Map<String, String> stats() throws BeanstalkcException
    {
        return doCommand(new StatsCommand());
    }

    /**
     * @see     Client#listTubes
     */
    public List<String> listTubes() throws BeanstalkcException
    {
        return doCommand(new ListTubesCommand());
    }

    /**
     * @see     Client#listTubesWatched
     */
    public List<String> listTubesWatched() throws BeanstalkcException
    {
        return doCommand(new ListTubesWatchedCommand());
    }

    /**
     * @see     Client#listTubeUsed
     */
    public String listTubeUsed() throws BeanstalkcException
    {
        return doCommand(new ListTubeUsedCommand());
    }

    /**
     * @see     Client#close
     */
    public void close() throws IOException
    {
        if (isClosed()) {
            return;
        }

        try {
            doCommand(new QuitCommand());
        } catch (BeanstalkcException e) {
            throw new IOException("Could not close the server", e);
        }

        if (!adapter.isClosed()) {
            adapter.close();
        }

        gotClose = true;
    }

    /**
     * @see     Client#isClosed
     */
    public boolean isClosed()
    {
        return gotClose && adapter.isClosed();
    }

    private <T> T doCommand(Command<T> cmd) throws BeanstalkcException
    {
        return adapter.perform(cmd);
    }
}
