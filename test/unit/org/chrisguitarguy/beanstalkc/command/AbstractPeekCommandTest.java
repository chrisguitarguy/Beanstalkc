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
import org.chrisguitarguy.beanstalkc.Command;
import org.chrisguitarguy.beanstalkc.Job;

public class AbstractPeekCommandTest
{
    private Command<Job> cmd;

    @Test
    public void testWithInvalidResponse() throws Exception
    {
        OutputStream out = new ByteArrayOutputStream();
        InputStream in = new ByteArrayInputStream("NOT_FOUND\r\n".getBytes());

        Assert.assertNull(cmd.execute(in, out));
    }

    @Test
    public void testWithFoundResponse() throws Exception
    {
        OutputStream out = new ByteArrayOutputStream();
        InputStream in = new ByteArrayInputStream("FOUND 1 4\r\ntest\r\n".getBytes());

        Job j = cmd.execute(in, out);
        Assert.assertEquals(1, j.getId());
        Assert.assertArrayEquals("test".getBytes(), j.getBody());
    }

    @Before
    public void setUp()
    {
        cmd = new AbstractPeekCommand() {
            @Override
            public void sendRequest(OutputStream out) throws BeanstalkcException, IOException
            {

            }
        };
    }
}
