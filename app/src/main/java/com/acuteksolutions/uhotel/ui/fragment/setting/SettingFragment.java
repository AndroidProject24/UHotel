package com.acuteksolutions.uhotel.ui.fragment.setting;

import android.content.Context;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.SettingView;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import javax.inject.Inject;

public class SettingFragment extends BaseFragment implements SettingView {
  @Inject
  MoviesPresenter
  mPresenter;
  private Context mContext;
  public static SettingFragment newInstance() {
    return new SettingFragment();
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

  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.tab_viewpager_fragment;
  }

  @Override
  protected void initData() {
    //mPresenter.getData(TheloaiDef.HOA_MANG_TRA_TRUOC);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
  }

}

