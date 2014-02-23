// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import org.junit.Test;
import org.junit.Assert;

public class QuitCommandTest
{
    private final byte[] expected = "quit\r\n".getBytes();

    @Test
    public void testQuit() throws Exception
    {
        InputStream in = new ByteArrayInputStream("".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        QuitCommand cmd = new QuitCommand();
        Assert.assertTrue(cmd.execute(in, out));
        Assert.assertArrayEquals(expected, out.toByteArray());
    }
}
