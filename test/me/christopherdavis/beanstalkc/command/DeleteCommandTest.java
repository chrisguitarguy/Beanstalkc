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
import me.christopherdavis.beanstalkc.BeanstalkcException;

public class DeleteCommandTest
{
    @Test
    public void testWithNotFound() throws Exception
    {
        final byte[] expected = "delete 1\r\n".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream("NOT_FOUND\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeleteCommand cmd = new DeleteCommand(1);

        Assert.assertFalse(
            "NOT_FOUND response on delete should return false",
            cmd.execute(in, out)
        );

        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    @Test
    public void testWithDeleted() throws Exception
    {
        final byte[] expected = "delete 1\r\n".getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream("DELETED\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeleteCommand cmd = new DeleteCommand(1);

        Assert.assertTrue(
            "DELETED response on delete should return true",
            cmd.execute(in, out)
        );

        Assert.assertArrayEquals(expected, out.toByteArray());
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithUnknownResponse() throws Exception
    {
        ByteArrayInputStream in = new ByteArrayInputStream("NOPE\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeleteCommand cmd = new DeleteCommand(1);
        cmd.execute(in, out);
    }
}
