// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc;

import java.util.Random;
import java.util.Arrays;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.chrisguitarguy.beanstalkc.exception.JobNotFoundException;

public class JobLifecycleTest
{
    private static Client client;
    private Random rand;

    public JobLifecycleTest()
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
    public void testPut() throws Exception
    {
        final byte[] body = "a job body".getBytes();

        Job j = client.put(body);

        Assert.assertTrue(j.getId() > 0);
        Assert.assertArrayEquals(body, j.getBody());
    }

    @Test
    public void testPutWithHugeJobBody() throws Exception
    {
        String tube = generateTubeName();
        byte[] body = new byte[10000];
        Arrays.fill(body, (byte)'a');

        Job put;
        Job fetched;

        Assert.assertTrue(client.use(tube));
        put = client.put(body);
        Assert.assertTrue(put.getId() > 0);

        Assert.assertTrue(client.watch(tube) > 0);
        Assert.assertTrue(client.ignore("default") > 0);

        fetched = client.reserve(1);
        Assert.assertNotNull(fetched);
        Assert.assertEquals(put.getId(), fetched.getId());
    }


    @Test
    public void testLifecycle() throws Exception
    {
        Job inserted;
        Job fetched;

        final byte[] body = "test body".getBytes();
        final String tube = generateTubeName();

        // create a new job
        Assert.assertTrue(client.use(tube));
        inserted = client.put(body);
        Assert.assertArrayEquals(body, inserted.getBody());

        Assert.assertTrue(client.watch(tube) > 0);
        Assert.assertTrue(client.ignore("default") > 0);

        // try reserving the job, touching it, and then releasing it
        fetched = client.reserve();
        Assert.assertEquals(inserted.getId(), fetched.getId());
        Assert.assertTrue(client.touch(fetched));
        Assert.assertTrue(client.release(fetched));

        // reserve the job, bury it, then kick it
        fetched = client.reserve();
        Assert.assertEquals(inserted.getId(), fetched.getId());
        Assert.assertTrue(client.bury(fetched));
        Assert.assertTrue(client.kickJob(fetched));

        // peek at a the job.
        fetched = client.peek(inserted);
        Assert.assertEquals(inserted.getId(), fetched.getId());

        // reserve the job, bury it, the kick it out with the general kick command
        fetched = client.reserve();
        Assert.assertEquals(inserted.getId(), fetched.getId());
        Assert.assertTrue(client.bury(fetched));
        Assert.assertTrue(client.kick(10) == 1);

        client.ignore(tube);
    }

    @Test(expected=JobNotFoundException.class)
    public void testPeekWithInvalidJob() throws Exception
    {
        client.peek(10000);
    }

    @Test(expected=JobNotFoundException.class)
    public void testBuryWithInvalidJob() throws Exception
    {
        client.bury(10000);
    }

    @Test(expected=JobNotFoundException.class)
    public void testKickJobWithInvalidJob() throws Exception
    {
        client.kickJob(10000);
    }

    @Test(expected=JobNotFoundException.class)
    public void testTouchWithInvalidJob() throws Exception
    {
        client.touch(10000);
    }

    @Test(expected=JobNotFoundException.class)
    public void testReleaseWithInvalidJob() throws Exception
    {
        client.release(10000);
    }

    @Test
    public void testReserveWithoutJob() throws Exception
    {
        final String tube = generateTubeName();
        client.watch(tube);
        client.ignore("default");

        Assert.assertNull(client.reserve(1));

        client.ignore(tube);
    }

    private String generateTubeName()
    {
        return String.format("peektest_tube_%d", rand.nextInt(10000));
    }
}
