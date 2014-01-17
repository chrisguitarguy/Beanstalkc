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

public class AbstractStatsCommandTest
{
    private AbstractStatsCommand<String> cmd;
    final private YamlParser<String> parser = new YamlParser<String>() {
        @Override
        public String parse(byte[] bytes) throws BeanstalkcException
        {
            return new String(bytes);
        }

        @Override
        public String parse(InputStream in) throws BeanstalkcException
        {
            return "never gets here";
        }
    };

    @Test
    public void testWithInvalidResponse() throws Exception
    {
        InputStream in = new ByteArrayInputStream("NOPE\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        Assert.assertNull(cmd.execute(in, out));
    }

    @Test
    public void testWithOkResponse() throws Exception
    {
        final String expected = "test";
        InputStream in = new ByteArrayInputStream("OK 4\r\ntest\r\n".getBytes());
        OutputStream out = new ByteArrayOutputStream();
        Assert.assertEquals(expected, cmd.execute(in, out));
    }

    @Before
    public void setUp()
    {
        cmd = new AbstractStatsCommand<String>() {
            @Override
            protected void sendRequest(OutputStream out) throws BeanstalkcException
            {

            }

            @Override
            protected YamlParser<String> getParser()
            {
                return parser;
            }
        };
    }
}
