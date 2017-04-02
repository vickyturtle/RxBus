package com.vikashyap.rxbus;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vikas on 02/04/17.
 */
public class Source<T> {
    private PublishProcessor<T> processor = PublishProcessor.create();
    private List<T> stickyEvents = new ArrayList<T>();

    public void post(T event) {
        if (!processor.hasSubscribers()) {
            //Dead Event
            System.out.println("No subscribers for event :" + event);
        }
        processor.onNext(event);
    }

    public void postSticky(T stickyEvent) {
        stickyEvents.add(stickyEvent);
        post(stickyEvent);
    }

    public void removeStickyEvent(T event) {
        stickyEvents.remove(event);
    }

    public Flowable<T> observe() {
        Flowable<T> stickyEventsFlowable = Flowable.fromIterable(stickyEvents);
        return processor.mergeWith(stickyEventsFlowable);
    }
}
