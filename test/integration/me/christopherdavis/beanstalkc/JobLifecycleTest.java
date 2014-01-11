// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import java.util.Random;
import org.junit.Test;
import org.junit.Assert;
import org.junit.Before;

public class JobLifecycleTest
{
    private Client client;

    @Test
    public void testUseTube() throws Exception
    {
        Assert.assertTrue(client.use("a_tube"));
    }

    @Test
    public void testWatchIgnore() throws Exception
    {
        Assert.assertTrue(client.watch("a_tube") > 0);
        Assert.assertTrue(client.ignore("a_tube") > 0);
        // seems that beanstalkd doesn't actually return "NOT_IGNORED" ...
        //Assert.assertTrue(client.ignore("a_tube") < 0);
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
    public void testLifecycle() throws Exception
    {
        Job inserted;
        Job fetched;
        Random r = new Random();

        final byte[] body = "test body".getBytes();
        final String tube = String.format("lifecycle_tube_%d", r.nextInt(10000));

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

        // reserve the job then bury it
        fetched = client.reserve();
        Assert.assertEquals(inserted.getId(), fetched.getId());
        Assert.assertTrue(client.bury(fetched));
    }

    @Before
    public void setUp() throws Exception
    {
        client = ConnectionHelper.create();
    }
}
