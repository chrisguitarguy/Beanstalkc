// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;
import org.junit.Assert;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.JobNotFoundException;

public class KickJobCommandTest
{
    @Test(expected=JobNotFoundException.class)
    public void testWithNotFound() throws Exception
    {
        ByteArrayInputStream in = new ByteArrayInputStream("NOT_FOUND\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        KickJobCommand cmd = new KickJobCommand(1);
        cmd.execute(in, out);
    }

    @Test
    public void testWithReleased() throws Exception
    {
        final byte[] expected = "kick-job 1\r\n".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream("KICKED\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        KickJobCommand cmd = new KickJobCommand(1);

        Assert.assertTrue(
            "KICKED response on kick-job should return true",
            cmd.execute(in, out)
        );

        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    @Test
    public void testWithUnknownResponse() throws Exception
    {
        ByteArrayInputStream in = new ByteArrayInputStream("NOPE\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        KickJobCommand cmd = new KickJobCommand(1);
        Assert.assertFalse(cmd.execute(in, out));
    }
}
