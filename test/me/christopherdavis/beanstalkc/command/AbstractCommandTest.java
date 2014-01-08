// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.Before;
import org.junit.Assert;
import org.mockito.Mockito;
import org.mockito.AdditionalAnswers;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.Command;
import me.christopherdavis.beanstalkc.command.AbstractCommand;

public class AbstractCommandTest
{
    private InputStream in;
    private OutputStream out;

    @Test(expected=BeanstalkcException.class)
    public void testWithEmptyResponse() throws BeanstalkcException, IOException
    {
        Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(byteCollection("\r\n")))
               .when(in)
               .read();
        Command<Boolean> cmd = new Cmd();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithOutOfMememoryError() throws BeanstalkcException, IOException
    {
        Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(byteCollection("OUT_OF_MEMORY\r\n")))
               .when(in)
               .read();
        Command<Boolean> cmd = new Cmd();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithInternalError() throws BeanstalkcException, IOException
    {
        Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(byteCollection("INTERNAL_ERROR\r\n")))
               .when(in)
               .read();
        Command<Boolean> cmd = new Cmd();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithBadFormat() throws BeanstalkcException, IOException
    {
        Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(byteCollection("BAD_FORMAT\r\n")))
               .when(in)
               .read();
        Command<Boolean> cmd = new Cmd();
        cmd.execute(in, out);
    }

    @Test(expected=BeanstalkcException.class)
    public void testWithUnknownCommand() throws BeanstalkcException, IOException
    {
        Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(byteCollection("UNKNOWN_COMMAND\r\n")))
               .when(in)
               .read();
        Command<Boolean> cmd = new Cmd();
        cmd.execute(in, out);
    }

    @Test
    public void testWithOkayResponse() throws BeanstalkcException, IOException
    {
        Mockito.doAnswer(AdditionalAnswers.returnsElementsOf(byteCollection("INSERTED 12\r\n")))
               .when(in)
               .read();
        Command<Boolean> cmd = new Cmd();
        Assert.assertTrue(cmd.execute(in, out));
    }

    @Before
    public void setUp()
    {
        in = Mockito.mock(InputStream.class);
        out = Mockito.mock(OutputStream.class);
    }

    private List<Byte> byteCollection(String msg)
    {
        byte[] bytes = msg.getBytes();
        List<Byte> lst = new ArrayList<Byte>();
        for (byte b : bytes) {
            lst.add(b);
        }

        return lst;
    }

    // stub class for tests here.
    class Cmd extends AbstractCommand<Boolean>
    {
        protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException
        {

        }

        protected Boolean readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException
        {
            return true;
        }
    }
}
