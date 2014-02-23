// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.io.InputStream;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import org.chrisguitarguy.beanstalkc.BeanstalkcException;
import org.chrisguitarguy.beanstalkc.exception.YamlException;

/**
 * Abstract base class for YAML parsers.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
abstract class AbstractYamlParser<T> implements YamlParser<T>
{
    final private static String YAML_START = "---";

    /**
     * @see     YamlParser#parse
     */
    public T parse(byte[] input) throws BeanstalkcException
    {
        return parse(new ByteArrayInputStream(input));
    }

    /**
     * @see     YamlParser#parse
     */
    public T parse(InputStream input) throws BeanstalkcException
    {
        Scanner scan = new Scanner(input);
        T out = createOutput();

        try {
            parse(scan, out);
        } catch (BeanstalkcException e) {
            throw e;
        } catch (Exception e) {
            throw new YamlException(String.format("YAML Parse Error: %s", e.getMessage()), e);
        } finally {
            scan.close(); // note that this will call close on the input stream
        }

        return out;
    }

    protected void parse(Scanner scan, T output) throws Exception
    {
        boolean first = true;
        String line;

        // every yaml response starts with a line of three dashes
        while (scan.hasNextLine()) {
            line = scan.nextLine().trim();
            if (first) {
                first = false;
                if (line.equals(YAML_START)) {
                    continue;
                }
            }

            handleLine(line, output);
        }
    }

    abstract protected T createOutput();

    abstract protected void handleLine(String line, T output) throws Exception;
}
