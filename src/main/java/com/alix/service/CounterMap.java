package com.alix.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author shadowyy
 * @version 2017/11/5 13:04
 */
public class CounterMap {
    HashMap<String, Counter> map = new HashMap<>();

    public void increment(String key, long increment) {
        Counter count = map.get(key);
        if (count == null) {
            count = new Counter();
            map.put(key, count);
        }
        count.value += increment;
    }


    public long getValue(String key) {
        Counter count = map.get(key);
        if (count != null) {
            return count.value;
        } else {
            return 0;
        }
    }

    public Set<Map.Entry<String, Counter>> entrySet() {
        return map.entrySet();
    }

    public void clear() {
        map.clear();
    }

    public static class Counter {
        public long value;
    }
}
