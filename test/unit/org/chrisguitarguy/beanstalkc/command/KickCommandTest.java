// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;

public class KickCommandTest
{
    @Test(expected=ServerErrorException.class)
    public void testWithShortLine() throws Exception
    {
        InputStream in = new ByteArrayInputStream("NOPE\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        KickCommand cmd = new KickCommand(1);
        cmd.execute(in, out);
    }

    @Test
    public void testWithKickedResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("KICKED 10\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        KickCommand cmd = new KickCommand(20);

        Assert.assertEquals(10, (int)cmd.execute(in, out));
        Assert.assertArrayEquals("kick 20\r\n".getBytes(), out.toByteArray());
    }

    @Test
    public void testWithoutKickedResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("NOT_KICKED 10\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        KickCommand cmd = new KickCommand(20);

        Assert.assertEquals(-1, (int)cmd.execute(in, out));
        Assert.assertArrayEquals("kick 20\r\n".getBytes(), out.toByteArray());
    }
}
