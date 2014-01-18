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

public class ListTubeUsedCommandTest
{
    private final byte[] expected = "list-tube-used\r\n".getBytes();

    public void testWithNonUsingResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("NOT_USING tuber\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ListTubeUsedCommand cmd = new ListTubeUsedCommand();
        Assert.assertNull(cmd.execute(in, out));
        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    public void testWithInvalidResponseLine() throws Exception
    {
        InputStream in = new ByteArrayInputStream("USING\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ListTubeUsedCommand cmd = new ListTubeUsedCommand();
        Assert.assertNull(cmd.execute(in, out));
        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    @Test
    public void testWithUsingResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("USING tuber\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ListTubeUsedCommand cmd = new ListTubeUsedCommand();
        Assert.assertEquals("tuber", cmd.execute(in, out));
        Assert.assertArrayEquals(expected, out.toByteArray());
    }
}
