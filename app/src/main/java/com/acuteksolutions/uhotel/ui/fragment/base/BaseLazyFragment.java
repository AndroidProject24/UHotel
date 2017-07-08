package com.acuteksolutions.uhotel.ui.fragment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.interfaces.OnBackListener;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.interfaces.ViewpagerListener;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.base.BaseView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

import static dagger.internal.Preconditions.checkNotNull;

/**
 * Created by Toan.IT
 * Date:22/5/2016
 */
public abstract class BaseLazyFragment<T extends BasePresenter> extends SupportFragment implements OnBackListener,BaseView {
    private View mContentView;
    private Context mContext;
    private Unbinder unbinder;
    private String TAG = getTAG();
    protected abstract String getTAG();
    protected ToolbarTitleListener toolbarTitleListener;
    protected ViewpagerListener viewpagerListener;
    protected RequestManager glide;
    private boolean isPrepared=false;
    protected boolean isVisible;
    @Inject
    protected T mPresenter;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            toolbarTitleListener = (ToolbarTitleListener) getActivity();
            viewpagerListener = (ViewpagerListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        injectDependencies();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (null == mContentView) {
            mContentView = inflater.inflate(setLayoutResourceID(),container, false);
        }
        return mContentView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            isPrepared = true;
            if(mPresenter!=null)
                mPresenter.attachView(this);
            unbinder = ButterKnife.bind(this, mContentView);
            mContext = getContext();
            glide= Glide.with(this);
            lazyLoad();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    private void lazyLoad(){
        if (!isPrepared || !isVisible) {
            return;
        }
        Logger.wtf(TAG);
        initViews();
        initData();
        isPrepared = false;
    }
    protected abstract void injectDependencies();

    protected abstract int setLayoutResourceID();

    protected abstract void initViews();

    protected abstract void initData();

    protected View getContentView() {
        return mContentView;
    }

    protected Context getmContext() {
        return mContext;
    }

    public void addFagment(@NonNull FragmentManager fragmentManager, int frameId, @NonNull Fragment fragment){
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment,fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void replaceFagment(@NonNull FragmentManager fragmentManager, int frameId, @NonNull Fragment fragment){
        checkNotNull(fragmentManager);
        checkNotNull(fragment);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, fragment.getClass().getName());
        transaction.addToBackStack(null);
        transaction.commit();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Logger.wtf(TAG);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null)
            mPresenter.detachView();
        /*if(BaseApplication.getRefWatcher(mContext)!=null) {
            RefWatcher refWatcher = BaseApplication.getRefWatcher(mContext);
            refWatcher.watch(this);
        }*/
        Glide.get(getmContext()).clearMemory();
        glide.onDestroy();
    }
    protected void toggleShowLoading(boolean toggle) {

    }

    protected void toggleShowEmpty(String msg, View.OnClickListener onClickListener) {

    }

    protected void toggleShowError(String msg, View.OnClickListener onClickListener) {

    }

    @Override
    public void showEmptyView(String message) {
        toggleShowEmpty(message, null);
    }

    @Override
    public void showEmptyViewAction(String message, View.OnClickListener onClickListener) {
        toggleShowEmpty(message, onClickListener);
    }

    @Override
    public void showError(String message) {
        toggleShowError(message, null);
    }

    @Override
    public void showLoading() {
        toggleShowLoading(true);
    }

    @Override
    public void hideLoading() {
        toggleShowLoading(false);
    }

    protected void checkException(Throwable e) {
        //NetUtils.checkHttpException(getContext(), e, mRootView);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName();
    }

    @Override
    public void onBackPress() {
        _removeWorkerFragments();
    }
    private void _removeWorkerFragments() {
        String fragmentTAG=getFragmentManager().findFragmentById(R.id.fragment).getTag();
        if(!fragmentTAG.equalsIgnoreCase("")) {
            Fragment fragment=getFragmentManager().findFragmentByTag(fragmentTAG);
            if(fragment!=null)
                getFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
}
