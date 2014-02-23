// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package org.chrisguitarguy.beanstalkc;

import java.util.Map;

class ConnectionHelper
{
    private static Map<String, String> env = System.getenv();
    private static String host = null;
    private static Integer port = null;

    public static Client create() throws Exception
    {
        return new DefaultClient(getHost(), getPort());
    }

    private static int getPort() throws Exception
    {
        if (port != null) {
            return port;
        }

        if (!env.containsKey("BEANSTALKD_PORT")) {
            throw new Exception("BEANSTALKD_PORT not set");
        }

        port = Integer.parseInt(env.get("BEANSTALKD_PORT"));

        return port;
    }

    private static String getHost() throws Exception
    {
        if (host != null) {
            return host;
        }

        if (!env.containsKey("BEANSTALKD_HOST")) {
            throw new Exception("BEANSTALKD_HOST not set");
        }

        host = env.get("BEANSTALKD_HOST");

        return host;
    }
}
