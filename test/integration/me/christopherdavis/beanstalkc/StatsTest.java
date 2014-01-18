// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import java.util.Map;
import java.util.Random;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class StatsTest
{
    private static Client client;
    private Random rand;

    public StatsTest() throws Exception
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
    public void testStatsJob() throws Exception
    {
        Map<String, String> res;
        final String tube = generateTubeName();

        // Job 10000 should exist yet, null response
        res = client.statsJob(10000);
        Assert.assertNull(res);

        Assert.assertTrue(client.use(tube));
        Job j = client.put("test".getBytes());

        res = client.statsJob(j);
        Assert.assertNotNull(res);
        Assert.assertTrue(res.containsKey("id"));
        Assert.assertTrue(res.containsKey("tube"));
        Assert.assertEquals(tube, res.get("tube"));
    }

    @Test
    public void testStatsTube() throws Exception
    {
        Map<String, String> res;
        final String tube = generateTubeName();

        // the tube shouldn't exist yet, null response
        res = client.statsTube(tube);
        Assert.assertNull(res);

        // let's make the tube exist by inserted a job.
        Assert.assertTrue(client.use(tube));
        Job j = client.put("test".getBytes());

        res = client.statsTube(tube);
        Assert.assertNotNull(res);
        Assert.assertTrue(res.containsKey("current-jobs-ready"));
        Assert.assertEquals("1", res.get("current-jobs-ready"));
    }

    @Test
    public void testStats() throws Exception
    {
        Map<String, String> res = client.stats();

        Assert.assertNotNull(res);
        Assert.assertTrue(res.containsKey("version"));
    }

    private String generateTubeName()
    {
        return String.format("peektest_tube_%d", rand.nextInt(10000));
    }
}
