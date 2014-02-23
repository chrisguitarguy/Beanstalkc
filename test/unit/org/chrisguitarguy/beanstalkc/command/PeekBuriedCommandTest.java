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
import org.junit.Before;
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.Job;

public class PeekBuriedCommandTest
{
    private static byte[] expected = "peek-buried\r\n".getBytes();
    PeekBuriedCommand cmd = new PeekBuriedCommand();

    @Test
    public void testWithFoundJob() throws Exception
    {
        InputStream in = new ByteArrayInputStream("FOUND 1 4\r\ntest\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Job j = cmd.execute(in, out);

        Assert.assertNotNull(j);
        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    @Test
    public void testWithNotFound() throws Exception
    {
        InputStream in = new ByteArrayInputStream("NOT_FOUND\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Job j = cmd.execute(in, out);

        Assert.assertNull(j);
        Assert.assertArrayEquals(expected, out.toByteArray());
    }
}
