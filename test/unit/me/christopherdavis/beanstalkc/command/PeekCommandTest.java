// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.Assert;
import me.christopherdavis.beanstalkc.Job;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.JobNotFoundException;

public class PeekCommandTest
{
    @Test(expected=JobNotFoundException.class)
    public void testWithNotFound() throws Exception
    {
        ByteArrayInputStream in = new ByteArrayInputStream("NOT_FOUND\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PeekCommand cmd = new PeekCommand(1);
        cmd.execute(in, out);
    }

    @Test
    public void testWithFound() throws Exception
    {
        final byte[] expected = "peek 1\r\n".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream("FOUND 1 4\r\ntest\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PeekCommand cmd = new PeekCommand(1);

        Job j = cmd.execute(in, out);
        Assert.assertEquals(1, j.getId());
        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithUnknownResponse() throws Exception
    {
        ByteArrayInputStream in = new ByteArrayInputStream("NOPE\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PeekCommand cmd = new PeekCommand(1);
        cmd.execute(in, out);
    }
}
