// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.JobNotFoundException;

public class BuryCommandTest
{
    @Test(expected=JobNotFoundException.class)
    public void testWithNotFound() throws Exception
    {
        ByteArrayInputStream in = new ByteArrayInputStream("NOT_FOUND\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BuryCommand cmd = new BuryCommand(1, 1);
        cmd.execute(in, out);
    }

    @Test
    public void testWithBuried() throws Exception
    {
        final byte[] expected = "bury 1 10\r\n".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream("BURIED\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BuryCommand cmd = new BuryCommand(1, 10);

        Assert.assertTrue(
            "BURIED response on bury should return true",
            cmd.execute(in, out)
        );

        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    @Test
    public void testWithUnknownResponse() throws Exception
    {
        ByteArrayInputStream in = new ByteArrayInputStream("NOPE\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BuryCommand cmd = new BuryCommand(1, 10);
        Assert.assertFalse(cmd.execute(in, out));
    }
}
