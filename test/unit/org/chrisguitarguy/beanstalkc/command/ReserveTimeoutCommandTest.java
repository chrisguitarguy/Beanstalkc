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

public class ReserveTimeoutCommandTest
{
    private ByteArrayInputStream input;
    private ByteArrayOutputStream output;

    @Test
    public void testReserveTimeoutInput() throws Exception
    {
        ReserveTimeoutCommand cmd = new ReserveTimeoutCommand(10);

        cmd.execute(input, output);

        Assert.assertArrayEquals(
            "reserve-with-timeout 10\r\n".getBytes(),
            output.toByteArray()
        );
    }

    @Before
    public void setUp()
    {
        input = new ByteArrayInputStream("RESERVED 1 4\r\ntest\r\n".getBytes());
        output = new ByteArrayOutputStream();
    }
}
