package com.acuteksolutions.uhotel.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.acuteksolutions.uhotel.BaseApplication;
import com.acuteksolutions.uhotel.injector.component.ActivityComponent;
import com.acuteksolutions.uhotel.injector.component.DaggerActivityComponent;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.base.BaseView;
import com.acuteksolutions.uhotel.utils.ActivityManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Toan.IT
 * Date: 25/05/2016
 */
public abstract class BaseActivity <T extends BasePresenter> extends SupportActivity implements BaseView{
  private ActivityComponent mActivityComponent;
  @Inject
  protected T mPresenter;
  @SuppressWarnings("unchecked")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(setLayoutResourceID());
      setButterKnife();
      initbase();
      injectDependencies();
      if(mPresenter!=null)
          mPresenter.attachView(this);
      initViews();
      initData();
      ActivityManager.getInstance().pushActivity(this);
  }
  private void setButterKnife() {
    ButterKnife.bind(this);
  }

  private void initbase() {
    if (mActivityComponent == null) {
      mActivityComponent= DaggerActivityComponent.builder()
          .applicationComponent(BaseApplication.get(this).getApplicationComponent())
          .build();
    }
  }
  public ActivityComponent getActivityComponent(){
    return mActivityComponent;
  }
  private String TAG = getTAG();

  protected abstract String getTAG();

  protected abstract void initViews();

  protected abstract int setLayoutResourceID();

  protected abstract void initData();

  protected abstract void injectDependencies();

  void addFagment(@NonNull FragmentManager fragmentManager, int frameId, @NonNull Fragment fragment){
    checkNotNull(fragmentManager);
    checkNotNull(fragment);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.add(frameId, fragment,fragment.getClass().getName());
    transaction.addToBackStack(null);
    transaction.commit();
  }
  void replaceFagment(@NonNull FragmentManager fragmentManager, int frameId, @NonNull Fragment fragment){
    checkNotNull(fragmentManager);
    checkNotNull(fragment);
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    transaction.replace(frameId, fragment, fragment.getClass().getName());
    transaction.addToBackStack(null);
    transaction.commit();
  }

  @Override
  protected void onStart() {
    super.onStart();
      Logger.d(TAG);
       /* Logger.e(Utils.getDeviceIMEI(this));
        Logger.e(Utils.getDeviceName());
        Logger.e(Utils.getDeviceVersion());*/
  }
  @Override
  protected void onDestroy() {
    super.onDestroy();
    if (mPresenter != null)
      mPresenter.detachView();
    //if(BaseApplication.getRefWatcher(this)!=null) {
//      RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
//      refWatcher.watch(this);
    //}
    ActivityManager.getInstance().popActivity(this);
  }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showEmptyViewAction(String message, View.OnClickListener onClickListener) {

    }

}
