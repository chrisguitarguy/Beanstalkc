// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.util.Map;
import java.util.HashMap;
import org.junit.Test;
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.exception.YamlException;

public class YamlDictParserTest
{
    private YamlDictParser parser = new YamlDictParser();

    @Test(expected=YamlException.class)
    public void testWithLeadingColon() throws Exception
    {
        parser.parse("---\n:oneline".getBytes());
    }

    @Test(expected=YamlException.class)
    public void testWithManyColons() throws Exception
    {
        parser.parse("---\noneline: with: many: colons:".getBytes());
    }

    @Test(expected=YamlException.class)
    public void testWithNoColons() throws Exception
    {
        parser.parse("---\noneline without any colons\n".getBytes());
    }

    @Test
    public void testWithValidYaml() throws Exception
    {
        Map<String, String> dict = parser.parse("---\none: value\n two: value2".getBytes());
        Assert.assertEquals(2, dict.size());
        Assert.assertTrue(dict.containsKey("one"));
        Assert.assertEquals("value", dict.get("one"));
        Assert.assertTrue(dict.containsKey("two"));
        Assert.assertEquals("value2", dict.get("two"));
    }
}
