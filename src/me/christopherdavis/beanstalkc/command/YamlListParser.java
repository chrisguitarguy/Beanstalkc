// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.io.InputStream;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.util.Scanner;
import java.util.ArrayList;
import me.christopherdavis.beanstalkc.BeanstalkcException;
import me.christopherdavis.beanstalkc.exception.YamlException;

/**
 * Reads the yaml into an ArrayList
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
class YamlListParser extends AbstractYamlParser<ArrayList<String>>
{
    protected ArrayList<String> createOutput() 
    {
        return new ArrayList<String>();
    }

    protected void handleLine(String line, ArrayList<String> output) throws Exception
    {
        Character c;

        if (line.length() < 1) {
            return;
        }

        // each list line should begin with a dash
        c = line.charAt(0);
        if ('-' != c) {
            throw new YamlException(String.format(
                "Expected line to begin with \"-\" got \"%c\"",
                c
            ));
        }

        // each dash should be followed by a space
        c = line.charAt(1);
        if (!Character.isWhitespace(c)) {
            throw new YamlException(String.format(
                "Expected line to have a space following its dash, got \"%c\"",
                c
            ));
        }

        output.add(line.substring(2));
    }
}
