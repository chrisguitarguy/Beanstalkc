// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.Mock;
import me.christopherdavis.beanstalkc.command.PutJobCommand;
import me.christopherdavis.beanstalkc.command.UseTubeCommand;
import me.christopherdavis.beanstalkc.command.ReserveCommand;
import me.christopherdavis.beanstalkc.command.ReserveTimeoutCommand;
import me.christopherdavis.beanstalkc.command.DeleteCommand;
import me.christopherdavis.beanstalkc.command.ReleaseCommand;
import me.christopherdavis.beanstalkc.command.BuryCommand;
import me.christopherdavis.beanstalkc.command.KickJobCommand;
import me.christopherdavis.beanstalkc.command.TouchCommand;
import me.christopherdavis.beanstalkc.command.PeekCommand;
import me.christopherdavis.beanstalkc.command.WatchCommand;
import me.christopherdavis.beanstalkc.command.IgnoreCommand;
import me.christopherdavis.beanstalkc.command.PeekReadyCommand;
import me.christopherdavis.beanstalkc.command.PeekDelayedCommand;
import me.christopherdavis.beanstalkc.command.PeekBuriedCommand;

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

    @Before
    public void setUp() throws Exception
    {
        adapter = Mockito.mock(Adapter.class);
        client = new DefaultClient(adapter);
    }
}
