package com.acuteksolutions.uhotel.data.rxjava;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class RxUtils {
    private static CompositeSubscription mCompositeSubscription;

    public static void addCompositeSubscription(Subscription s) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(s);
    }

    public void unCompositeSubscription(){
        if (mCompositeSubscription != null) {
            mCompositeSubscription.clear();
            mCompositeSubscription=null;
        }
    }

    public static void unSubscribe(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static void unSubscribe(Subscription... subscriptions)
    {
        for (Subscription subscription : subscriptions)
        {
            if (subscription != null && !subscription.isUnsubscribed())
            {
                subscription.unsubscribe();
            }
        }
    }


}
