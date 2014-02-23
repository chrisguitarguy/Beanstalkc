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
import org.chrisguitarguy.beanstalkc.Command;
import org.chrisguitarguy.beanstalkc.Job;

public class AbstractReserveCommandTest
{
    private Command<Job> cmd;

    @Test(expected=BeanstalkcException.class)
    public void testErrorDeadlineSoon() throws Exception
    {
        InputStream in = new ByteArrayInputStream("DEADLINE_SOON\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testInvalidRespondVerb() throws Exception
    {
        InputStream in = new ByteArrayInputStream("NOPE 12\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testInvalidResponseLineLength() throws Exception
    {
        InputStream in = new ByteArrayInputStream("RESERVED 12\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testInvalidJobIdResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("RESERVED as 123\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testInvalidBodyLengthResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("RESERVED 12 asdf\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithMismatchedBodyLength() throws Exception
    {
        InputStream in = new ByteArrayInputStream("RESERVED 12 1\r\nMore than\rone byte\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        cmd.execute(in, out);
    }

    @Test
    public void testErrorTimedOut() throws Exception
    {
        InputStream in = new ByteArrayInputStream("TIMED_OUT\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        Assert.assertNull("Timeout response should return null", cmd.execute(in, out));
    }

    @Test
    public void testWithSuccessfulResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("RESERVED 12 4\r\ntest\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        Job j = cmd.execute(in, out);

        Assert.assertEquals(12, j.getId());
        Assert.assertArrayEquals("test".getBytes(), j.getBody());
    }

    @Before
    public void setUp()
    {
        cmd = new AbstractReserveCommand() {
            @Override
            protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
            {

            }
        };
    }
}
