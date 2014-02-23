// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import org.chrisguitarguy.beanstalkc.exception.YamlException;

/**
 * Reads the yaml into an ArrayList
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
class YamlListParser extends AbstractYamlParser<List<String>>
{
    @Override
    protected List<String> createOutput() 
    {
        return new ArrayList<String>();
    }

    @Override
    protected void handleLine(String line, List<String> output) throws Exception
    {
        Character c;

        if (line.length() < 1) {
            return;
        }

        // each list line should begin with a dash
        c = line.charAt(0);
        if ('-' != c) {
            throw new YamlException(String.format(
                "Expected line to begin with \"-\" got \"%c\" -- %s",
                c,
                line
            ));
        }

        // each dash should be followed by a space
        c = line.charAt(1);
        if (!Character.isWhitespace(c)) {
            throw new YamlException(String.format(
                "Expected line to have a space following its dash, got \"%c\" -- %s",
                c,
                line
            ));
        }

        output.add(line.substring(2));
    }
}
