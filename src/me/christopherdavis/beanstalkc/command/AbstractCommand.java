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
    final private byte[] OUT_OF_MEMORY = "OUT_OF_MEMORY".getBytes();
    final private byte[] INTERNAL_ERROR = "INTERNAL_ERROR".getBytes();
    final private byte[] BAD_FORMAT = "BAD_FORMAT".getBytes();
    final private byte[] UNKNOWN_COMMAND = "UNKNOWN_COMMAND".getBytes();

    /**
     * @see     Command#execute
     */
    public T execute(InputStream in, OutputStream out) throws BeanstalkcException
    {
        int space_pos;
        byte[] first_line;

        try {
            first_line = readLine(in);
        } catch (IOException e) {
            throw new BeanstalkcException(e.getMessage(), e);
        }

        byte[] first_word = first_line;
        if ((space_pos = Arrays.asList(first_line).indexOf(' ')) > -1) {
            first_word = Arrays.copyOfRange(first_line, 0, space_pos);
        }

        if (Arrays.equals(OUT_OF_MEMORY, first_word)) {

        } else if (Arrays.equals(INTERNAL_ERROR, first_word)) {

        } else if (Arrays.equals(BAD_FORMAT, first_word)) {

        } else if (Arrays.equals(UNKNOWN_COMMAND, first_word)) {

        }

        return doCommand(first_line, in, out);
    }

    /**
     * Actually run the command.
     *
     * This is called from execute after the responses are check for global
     * error codes.
     *
     * @since   0.1
     * @param   first The first line of the command, it's up to command to read
     *          the second line if it needs it.
     * @param   in The input stream
     * @param   out The output stream
     * @return  T
     */
    abstract protected T doCommand(byte[] first_line, InputStream in, OutputStream out) throws BeanstalkcException;

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
