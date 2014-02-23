// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.Mock;
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


public class DefaultClientTest
{
    private Adapter adapter;
    private Client client;

    @Test
    public void testPut() throws Exception
    {
        final Job j = new DefaultJob(1, "test".getBytes(), false);
        Mockito.when(adapter.perform(Mockito.isA(PutJobCommand.class))).thenReturn(j);

        Assert.assertSame(j, client.put(1, 1, 1, "test".getBytes()));
        Assert.assertSame(j, client.put("test".getBytes()));
    }

    @Test
    public void testUse() throws Exception
    {
        final boolean expected = true;
        Mockito.when(adapter.perform(Mockito.isA(UseTubeCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.use("tube"));
    }

    @Test
    public void testReserve() throws Exception
    {
        final Job j = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(ReserveCommand.class))).thenReturn(j);

        Assert.assertSame(j, client.reserve());
    }

    @Test
    public void testReserveWithTimeout() throws Exception
    {
        final Job j = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(ReserveTimeoutCommand.class))).thenReturn(j);

        Assert.assertSame(j, client.reserve(10));
    }

    @Test
    public void testDelete() throws Exception
    {
        final boolean expected = true;
        Mockito.when(adapter.perform(Mockito.isA(DeleteCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.delete(new DefaultJob(1, "test".getBytes())));
        Assert.assertEquals(expected, client.delete(1));
    }

    @Test
    public void testRelease() throws Exception
    {
        final boolean expected = true;
        final Job j = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(ReleaseCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.release(j, 1, 1));
        Assert.assertEquals(expected, client.release(1, 1, 1));
        Assert.assertEquals(expected, client.release(j));
        Assert.assertEquals(expected, client.release(1));
    }

    @Test
    public void testBury() throws Exception
    {
        final boolean expected = true;
        final Job j = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(BuryCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.bury(j, 1));
        Assert.assertEquals(expected, client.bury(1, 1));
        Assert.assertEquals(expected, client.bury(j));
        Assert.assertEquals(expected, client.bury(1));
    }

    @Test
    public void testKickJob() throws Exception
    {
        final boolean expected = true;
        final Job j = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(KickJobCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.kickJob(j));
        Assert.assertEquals(expected, client.kickJob(1));
    }

    @Test
    public void testTouch() throws Exception
    {
        final boolean expected = true;
        final Job j = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(TouchCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.touch(j));
        Assert.assertEquals(expected, client.touch(1));
    }

    @Test
    public void testPeek() throws Exception
    {
        final Job expected = new DefaultJob(1, "test".getBytes());
        final Job j = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(PeekCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.peek(j));
        Assert.assertSame(expected, client.peek(1));
    }

    @Test
    public void testWatch() throws Exception
    {
        final int expected = 2;
        Mockito.when(adapter.perform(Mockito.isA(WatchCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.watch("tube"));
    }

    @Test
    public void testIgnore() throws Exception
    {
        final int expected = 2;
        Mockito.when(adapter.perform(Mockito.isA(IgnoreCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.ignore("tube"));
    }

    @Test
    public void testPeekReady() throws Exception
    {
        final Job expected = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(PeekReadyCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.peekReady());
    }

    @Test
    public void testPeekDelayed() throws Exception
    {
        final Job expected = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(PeekDelayedCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.peekDelayed());
    }

    @Test
    public void testPeekBuried() throws Exception
    {
        final Job expected = new DefaultJob(1, "test".getBytes());
        Mockito.when(adapter.perform(Mockito.isA(PeekBuriedCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.peekBuried());
    }

    @Test
    public void testKick() throws Exception
    {
        final int expected = 10;
        Mockito.when(adapter.perform(Mockito.isA(KickCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.kick(20));
    }

    @Test
    public void testStatsJob() throws Exception
    {
        final Job j = new DefaultJob(20, "one".getBytes());
        final Map<String, String> expected = new HashMap<String, String>();
        Mockito.when(adapter.perform(Mockito.isA(StatsJobCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.statsJob(20));
        Assert.assertSame(expected, client.statsJob(j));
    }

    @Test
    public void testStatsTube() throws Exception
    {
        final Map<String, String> expected = new HashMap<String, String>();
        Mockito.when(adapter.perform(Mockito.isA(StatsTubeCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.statsTube("test"));
    }

    @Test
    public void testStats() throws Exception
    {
        final Map<String, String> expected = new HashMap<String, String>();
        Mockito.when(adapter.perform(Mockito.isA(StatsCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.stats());
    }

    @Test
    public void testListTubes() throws Exception
    {
        final List<String> expected = new ArrayList<String>();
        Mockito.when(adapter.perform(Mockito.isA(ListTubesCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.listTubes());
    }

    @Test
    public void testListTubesWatched() throws Exception
    {
        final List<String> expected = new ArrayList<String>();
        Mockito.when(adapter.perform(Mockito.isA(ListTubesWatchedCommand.class))).thenReturn(expected);

        Assert.assertSame(expected, client.listTubesWatched());
    }

    @Test
    public void testListTubeUsed() throws Exception
    {
        final String expected = "a_tube";
        Mockito.when(adapter.perform(Mockito.isA(ListTubeUsedCommand.class))).thenReturn(expected);

        Assert.assertEquals(expected, client.listTubeUsed());
    }

    @Test
    public void testClose() throws Exception
    {
        Mockito.when(adapter.perform(Mockito.isA(QuitCommand.class))).thenReturn(true);
        Mockito.when(adapter.isClosed()).thenReturn(false).thenReturn(true);

        client.close();
        client.close(); // we shouldn't touch close again...

        Mockito.verify(adapter, Mockito.times(1)).close();
    }

    @Test
    public void testIsClosed() throws Exception
    {
        Mockito.when(adapter.isClosed()).thenReturn(false);
        Assert.assertFalse(client.isClosed());
    }

    @Before
    public void setUp() throws Exception
    {
        adapter = Mockito.mock(Adapter.class);
        client = new DefaultClient(adapter);
    }
}
