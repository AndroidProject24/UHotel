package com.acuteksolutions.uhotel.ui.fragment.liveTV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.Program;
import com.acuteksolutions.uhotel.mvp.presenter.LiveTVPresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.LiveTVAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.chad.library.adapter.base.BaseQuickAdapter;
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
    LiveTVAdapter liveTVAdapter = new LiveTVAdapter(glide,programList);
    liveTVAdapter.openLoadAnimation();
    recyclerMain.setAdapter(liveTVAdapter);
    recyclerComingUp.setAdapter(liveTVAdapter);
    recyclerRightNow.setAdapter(liveTVAdapter);
    liveTVAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
      @Override public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        showDialog(programList.get(i));
      }
    });

  }

  @SuppressLint("SetTextI18n")
  private void showDialog(@NonNull Program program){
    try {
      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
      LayoutInflater layoutInflater = getActivity().getLayoutInflater();
      View view = layoutInflater.inflate(R.layout.dialog_movies, null);
      alertDialogBuilder.setView(view);
      final AlertDialog alertDialog = alertDialogBuilder.create();
      alertDialog.setCancelable(false);
      alertDialog.show();
      ((TextView) ButterKnife.findById(view, R.id.movies_overlay_main_txtnamemovie)).setText("Watch '" + program.getTitle() + "'");
      ButterKnife.findById(view, R.id.movies_overlay_main_leftdiv).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          alertDialog.dismiss();
         // mPresenter.getLinkStream(program.getChannelId());
        }
      });
      ButterKnife.findById(view, R.id.movies_overlay_main_rightdiv).setOnClickListener(
          v -> alertDialog.dismiss());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  @Override public void showEmty() {

  }

}

