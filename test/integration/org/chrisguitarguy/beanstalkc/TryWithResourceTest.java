// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc;

import java.util.Random;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class TryWithResourceTest
{
    private static Client client;
    private Random rand;

    public TryWithResourceTest() throws Exception
    {
        rand = new Random();
    }

    @Test
    public void testTryWithResource() throws Exception
    {
        final String tube = generateTubeName();
        try (Client c = ConnectionHelper.create()) {
            c.use(tube);
            Job inserted = c.put("some job".getBytes());
        } catch (BeanstalkcException e) {
            // noop
        }
    }

    private String generateTubeName()
    {
        return String.format("peektest_tube_%d", rand.nextInt(10000));
    }
}
