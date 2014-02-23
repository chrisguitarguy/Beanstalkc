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

public class UseTubeCommandTest
{
    @Test(expected=BeanstalkcException.class)
    public void testWithNonUsingResponse() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("NOT_USING tuber\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        UseTubeCommand cmd = new UseTubeCommand("tuber");
        cmd.execute(in, out);
    }

    @Test
    public void testWithValidResponse() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("USING tuber\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        UseTubeCommand cmd = new UseTubeCommand("tuber");
        Assert.assertTrue(cmd.execute(in, out));
        Assert.assertArrayEquals(
            "use tuber\r\n".getBytes(),
            out.toByteArray()
        );
    }
}
