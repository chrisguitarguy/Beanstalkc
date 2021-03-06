// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import org.junit.Test;
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

public class StatsCommandTest
{
    final private byte[] expected = "stats\r\n".getBytes();
    private StatsCommand cmd = new StatsCommand();

    @Test
    public void testWithOkResponse() throws Exception
    {
        final String yaml = "one: value\ntwo: value2";
        final byte[] yaml_b = yaml.getBytes();

        InputStream in = new ByteArrayInputStream(String.format(
            "OK %d\r\n%s\r\n",
            yaml_b.length,
            yaml
        ).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        Map<String, String> res = cmd.execute(in, out);

        Assert.assertArrayEquals(expected, out.toByteArray());
        Assert.assertNotNull(res);
        Assert.assertTrue(res.containsKey("one"));
        Assert.assertEquals("value", res.get("one"));
        Assert.assertTrue(res.containsKey("two"));
        Assert.assertEquals("value2", res.get("two"));
    }
}
