// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc;

import java.util.List;
import org.junit.Test;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.AfterClass;

public class TubesTest
{
    private static Client client;

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
    public void testUseTube() throws Exception
    {
        Assert.assertTrue(client.use("a_tube"));
        Assert.assertEquals("a_tube", client.listTubeUsed());
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
    public void testListTubes() throws Exception
    {
        List<String> res = client.listTubes();
        // we should have at least one tube by this point...
        Assert.assertTrue(res.size() > 0);
    }

    @Test
    public void testListTubesWatched() throws Exception
    {
        List<String> res = client.listTubesWatched();
        Assert.assertTrue(res.size() >= 1);
    }
}
