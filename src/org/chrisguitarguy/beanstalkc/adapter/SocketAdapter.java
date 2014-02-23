// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.adapter;

import java.net.Socket;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import org.chrisguitarguy.beanstalkc.Adapter;
import org.chrisguitarguy.beanstalkc.Command;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.ConnectionException;

/**
 * Wraps up a native Socket with our adapter.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopehrdavis.me>
 */
public class SocketAdapter implements Adapter
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

    public <T> T perform(Command<T> cmd) throws BeanstalkcException
    {
        try {
            return cmd.execute(sock.getInputStream(), sock.getOutputStream());
        } catch (IOException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }

    public void close() throws IOException
    {
        if (!sock.isClosed()) {
            sock.close();
        }
    }

    public boolean isClosed()
    {
        return sock.isClosed();
    }
}
