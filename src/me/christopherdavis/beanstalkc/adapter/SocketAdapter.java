// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.adapter;

import java.net.Socket;
import java.util.Scanner;
import me.christopherdavis.beanstalkc.Adapter;
import me.christopherdavis.beanstalkc.Response;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.ConnectionException;

/**
 * Wraps up a native Socket with our adapter.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopehrdavis.me>
 */
class SocketAdapter implements Adapter
{
    private Socket sock;

    public SocketAdapter(Socket sock)
    {
        this.sock = sock;
    }

    public SocketAdapter(String hostname, int port) throws BeanstalkcException
    {
        try {
            this.sock = new Socket(hostname, port);
        } catch (Exception e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    public Response execute(byte[] req) throws BeanstalkcException
    {
        return null;
    }
}
