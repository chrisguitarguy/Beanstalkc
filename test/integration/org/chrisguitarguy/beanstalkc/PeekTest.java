// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc;

import java.util.Random;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class PeekTest
{
    private static Client client;
    private Random rand;

    public PeekTest() throws Exception
    {
        rand = new Random();
    }

    @BeforeClass
    public static void setUp() throws Exception
    {
        client = ConnectionHelper.create();
    }

    @AfterClass
    public static void tearDown() throws Exception
    {
        client.close();
    }

    @Test
    public void testPeekReady() throws Exception
    {
        final String tube = generateTubeName();
        client.use(tube);

        Assert.assertNull(client.peekReady());

        Job inserted = client.put("some job".getBytes());
        Job peeked = client.peekReady();

        Assert.assertEquals(inserted.getId(), peeked.getId());
    }

    @Test
    public void testPeekDelayed() throws Exception
    {
        final String tube = generateTubeName();
        client.use(tube);

        Assert.assertNull(client.peekDelayed());

        // with 1000 second delay
        Job inserted = client.put(0, 1000, 60, "some delayed job".getBytes());
        Job peeked = client.peekDelayed();

        Assert.assertEquals(inserted.getId(), peeked.getId());
    }

    @Test
    public void testPeekBuried() throws Exception
    {
        final String tube = generateTubeName();
        client.use(tube);

        Assert.assertNull(client.peekBuried());

        // with 1000 second delay
        Job inserted = client.put("some buried job".getBytes());

        // grab the job and bury it...
        client.watch(tube);
        client.ignore("default");
        Job reserved = client.reserve();
        Assert.assertEquals(inserted.getId(), reserved.getId());
        Assert.assertTrue(client.bury(inserted));

        // peek at it
        Job peeked = client.peekBuried();
        Assert.assertEquals(inserted.getId(), peeked.getId());
    }

    private String generateTubeName()
    {
        return String.format("peektest_tube_%d", rand.nextInt(10000));
    }
}
