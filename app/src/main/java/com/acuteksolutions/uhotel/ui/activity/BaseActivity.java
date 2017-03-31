package com.acuteksolutions.uhotel.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.acuteksolutions.uhotel.BaseApplication;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.injector.component.ActivityComponent;
import com.acuteksolutions.uhotel.injector.component.DaggerActivityComponent;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.utils.StatusBarUtil;
import com.github.pwittchen.reactivenetwork.library.ReactiveNetwork;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Toan.IT
 * Date: 25/05/2016
 */
public abstract class BaseActivity extends AppCompatActivity {
    private CompositeSubscription mCompositeSubscription;
    private Subscription subscription;
    private ActivityComponent mActivityComponent;
    private AlertDialog alertDialog;
    private AlertDialog.Builder dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayoutResourceID());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarUtil.setColor(this,getResources().getColor(R.color.colorPrimary));
        }
        setButterKnife();
        initbase();
        injectDependencies();
        initViews();
        initData();
        //RXThreadConnect();
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

    protected void startActivityWithoutExtras(Class<?> clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }

    protected void startActivityWithExtras(Class<?> clazz, Bundle extras) {
        Intent intent = new Intent(this, clazz);
        intent.putExtras(extras);
        startActivity(intent);

    }
    void addFagment(@NonNull FragmentManager fragmentManager,@NonNull int frameId, @NonNull Fragment fragment){
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment,fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    void replaceFagment(@NonNull FragmentManager fragmentManager,@NonNull int frameId, @NonNull Fragment fragment){
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment,fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }
    public CompositeSubscription getCompositeSubscription() {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        return this.mCompositeSubscription;
    }

    public void addSubscription(Subscription s) {
        if (s == null) {
            return;
        }
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }

        this.mCompositeSubscription.add(s);
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
        if (this.mCompositeSubscription != null) {
            this.mCompositeSubscription.unsubscribe();
        }
       /* RefWatcher refWatcher = BaseApplication.getRefWatcher();
        refWatcher.watch(this);*/
    }
    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    private void RXThreadConnect(){
        addSubscription(ReactiveNetwork.observeInternetConnectivity(10000, "http://n3t.top/test/api/", 80,30000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isConnectedToInternet -> {
                    try {
                      if(!isConnectedToInternet) {
                        View dialogView = this.getLayoutInflater().inflate(R.layout.layout_no_connect, null);
                        dialog = new AlertDialog.Builder(BaseActivity.this,R.style.AppCompatAlertDialogStyle);
                        dialog.setCancelable(false);
                        dialog.setView(dialogView);
                        alertDialog = dialog.create();
                        alertDialog.show();
                      }else if(alertDialog!=null){
                        alertDialog.dismiss();
                        alertDialog.cancel();
                      }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }));
    }
}
