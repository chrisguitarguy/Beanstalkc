// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.AdditionalAnswers;
import org.mockito.AdditionalMatchers;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.Command;
import me.christopherdavis.beanstalkc.Job;

public class PutJobCommandTest
{
    private InputStream in;
    private OutputStream out;
    private PutJobCommand cmd;
    final private byte[] expected_first = "put 1 1 1 4\r\n".getBytes();
    final private byte[] expected_second = "test\r\n".getBytes();

    @Test(expected=BeanstalkcException.class)
    public void testErrorExpectedCrlf() throws BeanstalkcException, IOException
    {
        Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(TestHelper.byteCollection("EXPECTED_CRLF")))
               .when(in)
               .read();

        cmd.execute(in, out);
    }

    @Before
    public void setUp()
    {
        in = Mockito.mock(InputStream.class);
        out = Mockito.mock(OutputStream.class);
        cmd = new PutJobCommand(1, 1, 1, "test".getBytes());
    }

    @After
    public void tearDown() throws IOException
    {
        // we write on every test, so make sure it happens
        Mockito.verify(out).write(AdditionalMatchers.aryEq(expected_first));
        Mockito.verify(out).write(AdditionalMatchers.aryEq(expected_second));
        Mockito.reset(in);
        Mockito.reset(out);
    }
}
