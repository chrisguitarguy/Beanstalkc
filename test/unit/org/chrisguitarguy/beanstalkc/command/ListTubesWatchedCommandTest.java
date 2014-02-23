// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.junit.Test;
import org.junit.Assert;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;

public class ListTubesWatchedCommandTest
{
    final private byte[] expected = "list-tubes-watched\r\n".getBytes();
    private ListTubesWatchedCommand cmd = new ListTubesWatchedCommand();

    @Test
    public void testWithOkResponse() throws Exception
    {
        final String yaml = " - one_watched\n - another_watched";
        final byte[] yaml_b = yaml.getBytes();

        InputStream in = new ByteArrayInputStream(String.format(
            "OK %d\r\n%s\r\n",
            yaml_b.length,
            yaml
        ).getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        List<String> res = cmd.execute(in, out);

        Assert.assertArrayEquals(expected, out.toByteArray());
        Assert.assertNotNull(res);
        Assert.assertTrue(res.contains("one_watched"));
        Assert.assertTrue(res.contains("another_watched"));
    }
}
