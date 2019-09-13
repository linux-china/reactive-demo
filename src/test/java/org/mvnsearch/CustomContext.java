package org.mvnsearch;

import reactor.util.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 * custom context
 *
 * @author linux_china
 */
public class CustomContext implements Context {
    private Map<Object, Object> context = new HashMap<>();

    @Override
    public Object get(Object key) {
        return context.get(key);
    }

    @Override
    public boolean hasKey(Object key) {
        return context.containsKey(key);
    }

    @Override
    public Context put(Object key, Object value) {
        context.put(key, value);
        return this;
    }

    @Override
    public Context delete(Object key) {
        context.remove(key);
        return this;
    }

    @Override
    public int size() {
        return context.size();
    }

    @Override
    public Stream<Map.Entry<Object, Object>> stream() {
        return context.entrySet().stream();
    }
}
