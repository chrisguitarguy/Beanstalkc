// This file is part of me.christopherdavis.beanstalkc
// Copyright: (c) 2014 Christopher Davis <http://christopherdavis.me>
// License: MIT http://opensource.org/licenses/MIT

package me.christopherdavis.beanstalkc.command;

import java.util.List;
import java.util.ArrayList;

class TestHelper
{
    public static List<Byte> byteCollection(String msg)
    {
        byte[] bytes = msg.getBytes();
        List<Byte> lst = new ArrayList<Byte>();
        for (byte b : bytes) {
            lst.add(b);
        }

        return lst;
    }
}
