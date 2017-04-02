package com.vikashyap.rxbus;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by vikas on 02/04/17.
 */
public class Application {

    public static void main(String[] args) {
        Application application = new Application();
        application.start();
    }

    public void start() {
        String event = "Sticky Event";
        RxBus.getInstance().postSticky(event);
        Disposable stringDisposable = RxBus.getInstance().observe(String.class)
                .subscribe(new Consumer<String>() {
                    public void accept(@NonNull String s) throws Exception {
                        System.out.println("Subscriber 1: On String event :" + s);
                    }
                });
        RxBus.getInstance().post("Hello");
        RxBus.getInstance().post("World");
        stringDisposable.dispose();
        RxBus.getInstance().post("No takers");
        RxBus.getInstance().removeStickyEvent(event);

        Disposable stringDisposable2 = RxBus.getInstance().observe(String.class)
                .subscribe(new Consumer<String>() {
                    public void accept(@NonNull String s) throws Exception {
                        System.out.println("Subscriber 2: On String event :" + s);
                    }
                });

        RxBus.getInstance().post("Second");
        RxBus.getInstance().post("Sequence");
        stringDisposable2.dispose();

        RxBus.getInstance().postSticky(22);
        RxBus.getInstance().post(33);
        Disposable intDisposable = RxBus.getInstance().observe(Integer.class)
                .subscribe(new Consumer<Integer>() {
                    public void accept(@NonNull Integer integer) throws Exception {
                        System.out.println("On Integer event :" + integer);
                    }
                });
        RxBus.getInstance().post(44);
        intDisposable.dispose();


    }
}
