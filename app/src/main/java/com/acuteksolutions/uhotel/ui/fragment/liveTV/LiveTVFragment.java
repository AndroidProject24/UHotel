package com.acuteksolutions.uhotel.ui.fragment.liveTV;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.presenter.LiveTVPresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import javax.inject.Inject;

public class LiveTVFragment extends BaseFragment implements LiveTvView {
  @Inject LiveTVPresenter mPresenter;
  @BindView(R.id.recycler_main) RecyclerView recyclerMain;
  @BindView(R.id.recycler_right_now) RecyclerView recyclerRightNow;
  @BindView(R.id.recycler_coming_up) RecyclerView recyclerComingUp;
  private Context mContext;

  public static LiveTVFragment newInstance() {
    return new LiveTVFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override protected void initViews() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    mPresenter.attachView(this);
    recyclerMain.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    recyclerMain.setHasFixedSize(true);
    recyclerRightNow.setLayoutManager(new LinearLayoutManager(mContext));
    recyclerRightNow.setHasFixedSize(true);
    recyclerComingUp.setLayoutManager(new LinearLayoutManager(mContext));
    recyclerComingUp.setHasFixedSize(true);
  }

  @Override protected int setLayoutResourceID() {
    return R.layout.livetv_fragment;
  }

  @Override protected void initData() {
    mPresenter.verifyPin("1234");
    mPresenter.changePin("1234","1234");
    mPresenter.saveSetting("kakakaa","1234");
  }

  @Override public void listLiveTv() {

  }

  @Override public void showEmty() {

  }

  @Override public void onDestroy() {
    super.onDestroy();
    mPresenter.detachView();
  }
}

