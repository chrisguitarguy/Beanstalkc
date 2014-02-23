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
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.Command;
import org.chrisguitarguy.beanstalkc.Job;

public class PutJobCommandTest
{
    final private byte[] expected_first = "put 1 1 1 4\r\n".getBytes();
    final private byte[] expected_second = "test\r\n".getBytes();

    @Test(expected=BeanstalkcException.class)
    public void testErrorExpectedCrlf() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("EXPECTED_CRLF".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        PutJobCommand cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testErrorJobTooBig() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("JOB_TOO_BIG".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        PutJobCommand cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testErrorDraining() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("DRAINING".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        PutJobCommand cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testErrorUnknown() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("THIS_IS_AN_UNKNOWN_ERROR".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        PutJobCommand cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
        cmd.execute(in, out);
    }

    @Test
    public void testSuccessWithInserted() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("INSERTED 12".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PutJobCommand cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
        Job j = cmd.execute(in, out);

        Assert.assertArrayEquals(
            "put 1 1 1 4\r\ntest\r\n".getBytes(),
            out.toByteArray()
        );
        Assert.assertEquals(12, j.getId());
    }

    @Test
    public void testSuccessWithBuried() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("BURIED 12".getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PutJobCommand cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
        Job j = cmd.execute(in, out);

        Assert.assertArrayEquals(
            "put 1 1 1 4\r\ntest\r\n".getBytes(),
            out.toByteArray()
        );
        Assert.assertEquals(12, j.getId());
        Assert.assertTrue(j.isBuried());
    }

    @Test(expected=BeanstalkcException.class)
    public void testSuccessWithBadParse() throws BeanstalkcException, IOException
    {
        InputStream in = new ByteArrayInputStream("BURIED nopenopenope".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        PutJobCommand cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
        cmd.execute(in, out);
    }
}
