package com.acuteksolutions.uhotel.ui.fragment.liveTV;

import android.content.Context;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import javax.inject.Inject;

public class LiveTVFragment extends BaseFragment implements LiveTvView {
  @Inject
  MoviesPresenter
  mPresenter;
  private Context mContext;
  public static LiveTVFragment newInstance() {
    return new LiveTVFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext=context;
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    //mPresenter.attachView(this);
    initRecyclerview();
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.livetv_fragment;
  }

  @Override
  protected void initData() {
    //mPresenter.getData(TheloaiDef.HOA_MANG_TRA_TRUOC);
  }

  private void initRecyclerview(){

  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
  }

}

