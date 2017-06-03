package com.acuteksolutions.uhotel.data.rxjava;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class RxUtils {
    private static CompositeDisposable compositeDisposable;

    public static void addCompositeSubscription(Disposable s) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(s);
    }

    public void unCompositeSubscription(){
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable=null;
        }
    }

    public static void unSubscribe(Disposable subscription) {
        if (subscription != null) {
            subscription.dispose();
        }
    }

    public static void unSubscribe(Disposable ... subscriptions)
    {
        for (Disposable subscription : subscriptions)
        {
            if (subscription != null && !subscription.isDisposed())
            {
                subscription.dispose();
            }
        }
    }


}
