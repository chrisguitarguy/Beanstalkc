// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import me.christopherdavis.beanstalkc.Command;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.ServerErrorException;

/**
 * An abstract base class for commands.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
abstract class AbstractCommand<T> implements Command<T>
{
    final private char CR = '\r';
    final private char LF = '\n';

    // is this the right way to do this?
    final private String OUT_OF_MEMORY = "OUT_OF_MEMORY";
    final private String INTERNAL_ERROR = "INTERNAL_ERROR";
    final private String BAD_FORMAT = "BAD_FORMAT";
    final private String UNKNOWN_COMMAND = "UNKNOWN_COMMAND";

    /**
     * @see     Command#execute
     */
    public T execute(InputStream in, OutputStream out) throws BeanstalkcException
    {
        String first_line;
        String[] first_line_arr;

        try {
            sendRequest(out);
        } catch (Exception e) {
            throw new BeanstalkcException(e.getMessage(), e);
        }

        try {
            first_line = Arrays.toString(readLine(in));
        } catch (IOException e) {
            throw new BeanstalkcException(e.getMessage(), e);
        }

        first_line_arr = first_line.split(" ");

        if (OUT_OF_MEMORY == first_line_arr[0]) {
            throw new ServerErrorException("The beanstalkd server is out of memory");
        } else if (INTERNAL_ERROR == first_line_arr[0]) {
            throw new ServerErrorException("There was an internal error in the beanstalkd server");
        } else if (BAD_FORMAT ==  first_line_arr[0]) {
            throw new ServerErrorException("The last sent command was inproperly formatted");
        } else if (UNKNOWN_COMMAND ==  first_line_arr[0]) {
            throw new ServerErrorException("Unknown command");
        }

        try {
            return readResponse(first_line_arr, in);
        } catch (Exception e) {
            throw new BeanstalkcException(e.getMessage(), e);
        }
    }

    /**
     * Send the command's request to the server.
     *
     * @since   0.1
     * @param   OutputStream out
     * @return  void
     */
    abstract protected void sendRequest(OutputStream out) throws Exception;

    /**
     * Read the commands response from the server -- the first line has already
     * been read, but it's up to the command to read the second (if applicable).
     *
     * @since   0.1
     * @param   first_line The first line of the command, it's up to command to read
     *          the second line if it needs it.
     * @param   in The input stream
     * @return  T
     */
    abstract protected T readResponse(String[] first_line, InputStream in) throws Exception;

    protected byte[] readLine(InputStream s) throws IOException
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
