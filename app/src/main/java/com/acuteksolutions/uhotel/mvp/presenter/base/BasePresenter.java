package com.acuteksolutions.uhotel.mvp.presenter.base;


import android.support.annotation.NonNull;
import com.acuteksolutions.uhotel.mvp.view.base.BaseView;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 */
public class BasePresenter<T extends BaseView> implements Presenter<T> {
  private T mMvpView;
  @NonNull
  private CompositeSubscription subscriptionsToUnsubscribeOnUnbindView = new CompositeSubscription();
  @Override
  public void attachView(T mvpView) {
    mMvpView = mvpView;
  }
  @Override
  public void detachView() {
    mMvpView = null;
    this.subscriptionsToUnsubscribeOnUnbindView.clear();
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
  protected final void addSubscribe(@NonNull Subscription subscription, @NonNull Subscription... subscriptions) {
    this.subscriptionsToUnsubscribeOnUnbindView.add(subscription);

    for (Subscription s : subscriptions) {
      this.subscriptionsToUnsubscribeOnUnbindView.add(s);
    }
  }
}

