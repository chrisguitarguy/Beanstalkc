Beanstalkc
==========

A java [beanstalkd](https://github.com/kr/beanstalkd) client.

#### Example:

```java
import java.io.IOException;
import me.christopherdavis.beanstalkc.Client;
import me.christopherdavis.beanstalkc.DefaultClient;
import me.christopherdavis.beanstalkc.Job;
import me.christopherdavis.beanstalkc.BeanstalkcException;

public class TestingBeanstalkc
{
    public static void main(String[] args)
    {
        Client client;
        Job j;
        Job found;

        try {
            // same as: client = new DefaultClient("localhost", 11300);
            client = new DefaultClient();
        } catch(BeanstalkcException e) {
            System.err.println("couldn't connect!");
            return;
        }

        try {
            j = client.put("some job body".getBytes());
        } catch(BeanstalkcException e) {
            System.err.println("couldn't put a job in");
            return;
        }

        System.out.println(String.format(
            "Put job with ID %d",
            j.getId()
        ));

        try {
            // wait 10 seconds for a job... or you can block forever
            // found = client.reserve();
            found = client.reserve(10);
        } catch (BeanstalkcException e) {
            System.err.println("Could not reserve a job");
            return;
        }

        if (null == found) {
            System.out.println("Found no job");
        } else {
            System.out.println(String.format(
                "Found a job with ID %d",
                found.getId()
            ));
        }

        try {
            client.close();
        } catch (IOException e) {
            // ...
        }
    }
}
```

For other examples see the `test/integration` directory.
