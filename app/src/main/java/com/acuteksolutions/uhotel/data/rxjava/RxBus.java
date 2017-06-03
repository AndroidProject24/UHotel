package com.acuteksolutions.uhotel.data.rxjava;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Toan.IT on 6/3/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class RxBus {
    public RxBus() {
    }

    private PublishSubject<Object> bus = PublishSubject.create();

    public void send(Object o) {
        bus.onNext(o);
    }

    public Observable<Object> toObservable() {
        return bus;
    }

    public boolean hasObservers() {
        return bus.hasObservers();
    }
}