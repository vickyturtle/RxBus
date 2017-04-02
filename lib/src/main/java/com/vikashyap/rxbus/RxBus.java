package com.vikashyap.rxbus;

import io.reactivex.Flowable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by vikas on 02/04/17.
 */
public class RxBus {
    private static RxBus ourInstance = new RxBus();

    private Map<Class<?>, Source<?>> sourceMap = new HashMap<Class<?>, Source<?>>();

    public static RxBus getInstance() {
        return ourInstance;
    }

    private RxBus() {
    }

    public <T> void post(T event) {
        Source<T> source = getSource((Class<T>) event.getClass());
        source.post(event);
    }

    public <T> void postSticky(T event) {
        getSource((Class<T>) event.getClass()).postSticky(event);
    }

    private <T> Source<T> getSource(Class<T> eventClass) {
        Source<?> rawSource = sourceMap.get(eventClass);
        Source<T> source;
        if (rawSource == null) {
            source = new Source<T>();
            sourceMap.put(eventClass, source);
        } else {
            source = (Source<T>) rawSource;
        }
        return source;
    }

    public <T> void removeStickyEvent(T event) {
        getSource((Class<T>) event.getClass()).removeStickyEvent(event);
    }

    public <T> Flowable<T> observe(Class<T> eventClass) {
        return getSource(eventClass).observe();
    }
}
