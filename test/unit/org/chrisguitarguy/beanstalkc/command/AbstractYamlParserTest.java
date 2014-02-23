// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import org.junit.Test;
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.YamlException;

public class AbstractYamlParserTest
{
    @Test
    public void testWithBeanstalkcThrowingParse()
    {
        final BeanstalkcException e = new BeanstalkcException("Test Exception");

        YamlParser<String> parser = new AbstractYamlParser<String>() {
            @Override
            protected String createOutput()
            {
                return new String();
            }

            @Override
            public void handleLine(String line, String out) throws Exception
            {
                throw e;
            }
        };

        try {
            parser.parse("test".getBytes());
        } catch (Exception caught) {
            Assert.assertSame(e, caught);
        }
    }

    @Test
    public void testWithNonBeanstalkcException()
    {
        final Exception e = new Exception("Test Exception");

        YamlParser<String> parser = new AbstractYamlParser<String>() {
            @Override
            protected String createOutput()
            {
                return new String();
            }

            @Override
            public void handleLine(String line, String out) throws Exception
            {
                throw e;
            }
        };

        try {
            parser.parse("test".getBytes());
        } catch (Exception caught) {
            Assert.assertTrue(caught instanceof YamlException);
            Assert.assertSame(e, caught.getCause());
        }
    }

    @Test
    public void testWithoutException() throws Exception
    {
        final String s = new String("test");

        YamlParser<String> parser = new AbstractYamlParser<String>() {
            @Override
            protected String createOutput()
            {
                return s;
            }

            @Override
            public void handleLine(String line, String out) throws Exception
            {
                // noop
            }
        };

        Assert.assertSame(s, parser.parse("test".getBytes()));
    }
}
