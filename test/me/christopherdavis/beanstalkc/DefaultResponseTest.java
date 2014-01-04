// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import org.junit.Test;
import org.junit.Assert;

public class DefaultResponseTest
{
    @Test
    public void testGetCommandName()
    {
        final String cmd = "A_COMMAND";
        DefaultResponse r = new DefaultResponse(cmd, null, null);
        Assert.assertEquals(cmd, r.getCommandName());
    }

    @Test
    public void testGetCommandLine()
    {
        final byte[] cmd_line = "bytes here".getBytes();
        DefaultResponse r = new DefaultResponse("A_COMMAND", cmd_line, null);
        Assert.assertEquals(cmd_line, r.getCommandLine());
    }

    @Test
    public void testGetData()
    {
        final byte[] data = "bytes here".getBytes();
        DefaultResponse r = new DefaultResponse("A_COMMAND", null, data);
        Assert.assertEquals(data, r.getData());
    }
}
