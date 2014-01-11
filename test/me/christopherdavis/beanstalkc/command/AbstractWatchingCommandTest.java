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
import org.junit.Before;
import org.junit.Assert;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.Command;

public class AbstractWatchingCommandTest
{
    private Command<Integer> cmd;

    @Test
    public void testWithBadResponseLine() throws Exception
    {
        InputStream in = new ByteArrayInputStream("NOT_IGNORED\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Assert.assertTrue(cmd.execute(in, out) < 0);
    }

    @Test
    public void testWithValidResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("WATCHING 10\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Assert.assertEquals(10, (int)cmd.execute(in, out));
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithBaseWatchingNumber() throws Exception
    {
        InputStream in = new ByteArrayInputStream("WATCHING nope\r\n".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        cmd.execute(in, out);
    }

    @Before
    public void setUp()
    {
        cmd = new AbstractWatchingCommand() {
            @Override
            protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
            {

            }
        };
    }
}
