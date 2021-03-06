// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc;

import org.junit.Test;
import org.junit.Assert;

public class DefaultJobTest
{
    @Test
    public void testGetId()
    {
        final int job_id = 123;

        Job j = new DefaultJob(job_id, null);
        Assert.assertEquals(
            String.format("Job ID should be equal to \"%d\"", job_id),
            job_id,
            j.getId()
        );
    }

    @Test
    public void testGetBody()
    {
        final byte[] job_body = "job body".getBytes();

        Job j = new DefaultJob(1, job_body);
        Assert.assertEquals(
            String.format("Job body should be equal to \"%s\"", new String(job_body)),
            job_body,
            j.getBody()
        );
    }

    @Test
    public void testIsBuried()
    {
        Job j = new DefaultJob(1, "body".getBytes(), true);
        Assert.assertTrue(j.isBuried());
    }
}
