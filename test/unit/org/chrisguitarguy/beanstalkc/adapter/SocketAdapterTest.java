// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.adapter;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;
import org.mockito.Mockito;
import org.mockito.Mock;
import org.chrisguitarguy.beanstalkc.Command;
import org.chrisguitarguy.beanstalkc.Adapter;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.adapter.SocketAdapter;

public class SocketAdapterTest
{
    private Socket sock;
    private InputStream in;
    private OutputStream out;
    private Adapter apt;

    @Test
    public void testSuccessfulPerform() throws BeanstalkcException, IOException
    {
        Mockito.when(this.sock.getInputStream()).thenReturn(this.in);
        Mockito.when(this.sock.getOutputStream()).thenReturn(this.out);

        Command<Boolean> cmd = new Command<Boolean>() {
            public Boolean execute(InputStream in, OutputStream out)
            {
                return true;
            }
        };

        Assert.assertTrue(
            "The command should return true, which should then be passed to the adapter",
            this.apt.perform(cmd)
        );
    }

    @Test(expected=BeanstalkcException.class)
    public void testPerformWithException() throws BeanstalkcException, IOException
    {
        Mockito.doThrow(new IOException("Broken")).when(this.sock).getInputStream();

        Command<Boolean> cmd = new Command<Boolean>() {
            public Boolean execute(InputStream in, OutputStream out)
            {
                return true;
            }
        };

        this.apt.perform(cmd);
    }

    @Test(expected=BeanstalkcException.class)
    public void testPerformWithBeanstalkcException() throws BeanstalkcException, IOException
    {
        Mockito.when(this.sock.getInputStream()).thenReturn(this.in);
        Mockito.when(this.sock.getOutputStream()).thenReturn(this.out);

        Command<Boolean> cmd = new Command<Boolean>() {
            public Boolean execute(InputStream in, OutputStream out) throws BeanstalkcException
            {
                throw new BeanstalkcException("Broken");
            }
        };

        this.apt.perform(cmd);
    }

    @Before
    public void setUp()
    {
        this.sock = Mockito.mock(Socket.class);
        this.in = Mockito.mock(InputStream.class);
        this.out = Mockito.mock(OutputStream.class);
        this.apt = new SocketAdapter(this.sock);
    }
}
