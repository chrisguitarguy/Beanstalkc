// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import org.chrisguitarguy.beanstalkc.Command;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.ServerErrorException;
import org.chrisguitarguy.beanstalkc.exception.InvalidValueException;

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
    @Override
    public T execute(InputStream in, OutputStream out) throws BeanstalkcException, IOException
    {
        String first_line;
        String[] first_line_arr;

        sendRequest(out);
        out.flush();

        first_line = new String(readLine(in));
        if (first_line.length() < 1) {
            throw new ServerErrorException("Received an empty response");
        }
        first_line_arr = first_line.split(" ");

        if (first_line_arr[0].equals(OUT_OF_MEMORY)) {
            throw new ServerErrorException("The beanstalkd server is out of memory");
        } else if (first_line_arr[0].equals(INTERNAL_ERROR)) {
            throw new ServerErrorException("There was an internal error in the beanstalkd server");
        } else if (first_line_arr[0].equals(BAD_FORMAT)) {
            throw new ServerErrorException("The last sent command was inproperly formatted");
        } else if (first_line_arr[0].equals(UNKNOWN_COMMAND)) {
            throw new ServerErrorException("Unknown command");
        }

        return readResponse(first_line_arr, in);
    }

    /**
     * Send the command's request to the server.
     *
     * @since   0.1
     * @param   OutputStream out
     * @return  void
     */
    abstract protected void sendRequest(OutputStream out) throws BeanstalkcException, IOException;

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
    abstract protected T readResponse(String[] first_line, InputStream in) throws BeanstalkcException, IOException;

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
        while ((byt = s.read()) > -1) {
            if (has_cr) {
                if (LF == byt) {
                    break;
                } else {
                    // we didn't get a linefeed, put the carriage return on the buffer, try again
                    buf.write('\r');
                    has_cr = false;
                }
            }

            if (CR == byt) {
                has_cr = true;
                continue;
            }

            buf.write(byt);
        }

        return buf.toByteArray();
    }

    protected byte[] readLength(InputStream in, int length) throws BeanstalkcException, IOException
    {
        int byt = 0;
        byte[] bytes = new byte[length];
        int offset = 0;
        int read = 0;

        while (offset < bytes.length && (read = in.read(bytes, offset, bytes.length-offset)) >= 0) {
          offset += read;
        }

        if (read < 0) {
            throw new IOException("No bytes available to read");
        }

        // before we validate the length, lets make sure that we get
        // a CRLF as expected
        if (CR != (byt = in.read())) {
            throw new InvalidValueException(String.format(
                "Expected a Carriage return, got \"%c\"",
                (char)byt // this won't really work all the time...
            ));
        }

        if (LF != (byt = in.read())) {
            throw new InvalidValueException(String.format(
                "Expected a line feed, got \"%c\"",
                (char)byt
            ));
        }

        // make sure we actually go the length we expected
        if (offset != length) {
            throw new InvalidValueException(String.format(
                "Expected %d bytes to be read, got %d bytes long",
                length,
                read
            ));
        }

        return bytes;
    }

    protected int parseInt(String to_parse, String what) throws BeanstalkcException
    {
        try {
            return Integer.parseInt(to_parse);
        } catch (NumberFormatException e) {
            throw new InvalidValueException(String.format(
                "Could not parse %s integer: %s",
                what,
                e.getMessage()
            ), e);
        }
    }
}
