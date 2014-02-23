// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc.command;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import org.chrisguitarguy.beanstalkc.exception.YamlException;

/**
 * Reads the yaml into an Map.
 *
 * Doesn't do much validation to test for "valid" yaml -- we pretty much assume
 * whatever we get back from the beanstalkd server will worker well enough.
 *
 * @since   0.1
 * @author  Christopher Davis <http://christopherdavis.me>
 */
class YamlDictParser extends AbstractYamlParser<Map<String, String>>
{
    @Override
    protected Map<String, String> createOutput()
    {
        return new HashMap<String, String>();
    }

    @Override
    protected void handleLine(String line, Map<String, String> output) throws Exception
    {
        if (line.length() < 1) {
            return;
        }

        if (line.startsWith(":")) {
            throw new YamlException(String.format(
                "Lines cannot start with a colon -- %s",
                line
            ));
        }

        if (substringCount(":", line) != 1) {
            throw new YamlException(String.format(
                "Each line should have exactly one colon -- %s",
                line
            ));
        }

        String[] parts = line.split(":");

        output.put(parts[0].trim(), parts[1].trim());
    }

    private int substringCount(String to_find, String source)
    {
        int rv = -1;
        int idx = 0;

        do {
            rv++;
            idx = source.indexOf(to_find, idx > 0 ? idx+1 : idx);
        } while (idx >= 0);

        return rv;
    }
}
