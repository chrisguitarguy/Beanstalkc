// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.adapter;

import java.net.Socket;
import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
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
    private static final char CR = '\r';
    private static final char LF = '\n';

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

    private byte[] readLine(InputStream s) throws IOException
    {
        // XXX is any of this depend on endianness? Do I need to worry about that?!
        int byt = 0;
        boolean has_cr = false;
        // would java.nio.ByteBuffer be better?!?!
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        // Read until we encounter a CR, then mark has_cr = true, if the next
        // character is a LF (\n) then we got a CRLF, and we can stop (we read
        // an entire line)
        while (-1 < (byt = s.read())) {
            if (has_cr && LF == byt) {
                break;
            } else {
                // we didn't get a linefeed, put the carriage return on the buffer, try again
                buf.write('\r');
                has_cr = false;
            }

            if (CR == byt) {
                has_cr = true;
                continue;
            }

            buf.write(byt);
        }

        return buf.toByteArray();
    }
}
