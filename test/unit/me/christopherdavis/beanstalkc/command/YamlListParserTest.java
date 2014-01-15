// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.util.ArrayList;
import org.junit.Test;
import org.junit.Assert;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.YamlException;

public class YamlListParserTest
{
    public YamlListParser parser = new YamlListParser();

    @Test(expected=YamlException.class)
    public void testWithoutValidList() throws Exception
    {
        parser.parse("---\n not a valid list line".getBytes());
    }

    @Test(expected=YamlException.class)
    public void testWithoutSpaceAfterList() throws Exception
    {
        parser.parse("---\n -also not a valid list line".getBytes());
    }

    @Test
    public void testWithValidYaml() throws Exception
    {
        ArrayList<String> list = parser.parse("---\n - one\n - two".getBytes());
        Assert.assertEquals(2, list.size());
        Assert.assertEquals("one", list.get(0));
        Assert.assertEquals("two", list.get(1));
    }
}
