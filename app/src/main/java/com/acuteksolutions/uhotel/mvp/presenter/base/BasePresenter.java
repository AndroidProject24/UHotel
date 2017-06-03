package com.acuteksolutions.uhotel.mvp.presenter.base;


import android.support.annotation.NonNull;

import com.acuteksolutions.uhotel.mvp.view.base.BaseView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<T extends BaseView> implements Presenter<T> {
    private T mMvpView;
    @NonNull
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public void attachView(T mvpView) {
        mMvpView = mvpView;
    }

    @Override
    public void detachView() {
        mMvpView = null;
        if (!compositeDisposable.isDisposed())
            compositeDisposable.dispose();
    }

    private boolean isViewAttached() {
        return mMvpView != null;
    }

    public T getMvpView() {
        checkViewAttached();
        return mMvpView;
    }

    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    private static class MvpViewNotAttachedException extends RuntimeException {
        private MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(BaseView) before" + "requesting data to the Presenter");
        }
    }

    protected final void addSubscribe(@NonNull Disposable disposable, @NonNull Disposable... disposables) {
        this.compositeDisposable.add(disposable);
        for (Disposable s : disposables) {
            this.compositeDisposable.add(s);
        }
    }
}

