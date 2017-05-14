package com.acuteksolutions.uhotel.ui.fragment.liveTV;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.Program;
import com.acuteksolutions.uhotel.mvp.presenter.LiveTVPresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import java.util.Calendar;
import java.util.List;

public class LiveTVFragment extends BaseFragment<LiveTVPresenter> implements LiveTvView {
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


  @Override
  protected void injectDependencies() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
  }

  @Override protected void initViews() {
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
    mPresenter.getAllChannel();
  }

  @Override public void listAllChannel(List<Channel> channelList) {
    Logger.e(channelList.toString());
    mPresenter.getProgram(channelList.get(0).getChannelId(), Calendar.getInstance().getTime());
  }

  @Override public void getProgram(List<Program> programList) {
    Logger.e(programList.toString());
  }

  @Override public void showEmty() {

  }

}

