package com.acuteksolutions.uhotel.mvp.view.base;

import android.view.View;

/**
 * Base interface that any class that wants to act as a BaseView in the MVP (Data BaseView Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or LoginFragment.
 */
public interface BaseView {
    void showLoading();
    void hideLoading();
    void showError(String message);
    void showEmptyView(String message);
    void showEmptyViewAction(String message, View.OnClickListener onClickListener);
}