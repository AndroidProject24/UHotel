package com.acuteksolutions.uhotel.ui.fragment.roomService;

import android.content.Context;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.view.RomServiceView;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;

public class RoomServiceFragment extends BaseFragment implements RomServiceView {
 /* @Inject
  MoviesPresenter
  mPresenter;*/
  private Context mContext;
  public static RoomServiceFragment newInstance() {
    return new RoomServiceFragment();
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
    return R.layout.movies_fragment;
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

