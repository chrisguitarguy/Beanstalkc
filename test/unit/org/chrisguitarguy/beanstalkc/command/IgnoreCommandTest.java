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

public class IgnoreCommandTest
{
    @Test
    public void testCommand() throws Exception
    {
        final byte[] expected = "ignore tube\r\n".getBytes();
        InputStream in = new ByteArrayInputStream("WATCHING 1\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        IgnoreCommand cmd = new IgnoreCommand("tube");
        Assert.assertEquals(1, (int)cmd.execute(in, out));
        Assert.assertArrayEquals(expected, out.toByteArray());
    }
}
