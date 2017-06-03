package com.acuteksolutions.uhotel.ui.fragment.parentalControl;

import android.content.Context;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.view.ParentalControlView;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;

public class ParentalControlFragment extends BaseFragment implements ParentalControlView {
  private Context mContext;
  public static ParentalControlFragment newInstance() {
    return new ParentalControlFragment();
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

  @Override protected void injectDependencies() {

  }

  @Override
  protected void initViews() {

  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.list_food_fragment;
  }

  @Override
  protected void initData() {
    //mPresenter.getData(TheloaiDef.HOA_MANG_TRA_TRUOC);
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    //mPresenter.detachView();
  }

}

