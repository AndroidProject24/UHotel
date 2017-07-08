package com.acuteksolutions.uhotel.ui.fragment.liveTV;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.libs.SimpleDividerItemDecoration;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.livetv.Channel;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.mvp.presenter.LiveTVPresenter;
import com.acuteksolutions.uhotel.mvp.view.LiveTvView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.livetv.ComingUpAdapter;
import com.acuteksolutions.uhotel.ui.adapter.livetv.LiveTVAdapter;
import com.acuteksolutions.uhotel.ui.adapter.livetv.RightNowAdapter;
import com.acuteksolutions.uhotel.ui.exoplayer.VideoPlayerActivity;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;
import com.acuteksolutions.uhotel.utils.Preconditions;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LiveTVFragment extends BaseFragment<LiveTVPresenter> implements LiveTvView {
    @BindView(R.id.recycler_main)
    RecyclerView recyclerMain;
    @BindView(R.id.recycler_right_now)
    RecyclerView recyclerRightNow;
    @BindView(R.id.recycler_coming_up)
    RecyclerView recyclerComingUp;
    private Context mContext;
    private LiveTVAdapter liveTVAdapter;
    private RightNowAdapter rightNowAdapter;
    private ComingUpAdapter comingUpAdapter;

    public static LiveTVFragment newInstance() {
        return new LiveTVFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }


    @Override
    protected void injectDependencies() {
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override
    protected void initViews() {
        recyclerMain.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        recyclerMain.setHasFixedSize(true);
        recyclerRightNow.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerRightNow.setHasFixedSize(true);
        recyclerComingUp.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerComingUp.setHasFixedSize(true);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.livetv_fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getAllChannel();
    }

    @Override
    public void listAllChannel(List<Channel> channelList) {
        //Logger.e(channelList.toString());
        mPresenter.getProgram(channelList, Calendar.getInstance().getTime());
    }

    @Override
    public void getProgram(List<TVInfo> tvInfoList) {
        Logger.e("getProgram="+tvInfoList.size());
        if (liveTVAdapter == null) {
            mPresenter.getDataComingUp(tvInfoList);
            mPresenter.getDataRightNow(tvInfoList);
            liveTVAdapter = new LiveTVAdapter(glide, tvInfoList);
            liveTVAdapter.openLoadAnimation();
            recyclerMain.setAdapter(liveTVAdapter);
        }
        liveTVAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> showDialog(tvInfoList.get(i)));
    }

    @Override
    public void getDataRightNow(List<TVInfo> tvInfoList) {
        if (rightNowAdapter==null) {
            rightNowAdapter = new RightNowAdapter(glide, tvInfoList);
            rightNowAdapter.openLoadAnimation();
            recyclerRightNow.setAdapter(rightNowAdapter);
            recyclerRightNow.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        }
        rightNowAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> showDialog(tvInfoList.get(i)));
    }

    @Override
    public void getDataComingUp(List<TVInfo> tvInfoList) {
        if (comingUpAdapter==null) {
            comingUpAdapter = new ComingUpAdapter(glide, tvInfoList);
            comingUpAdapter.openLoadAnimation();
            recyclerComingUp.setAdapter(comingUpAdapter);
            recyclerComingUp.addItemDecoration(new SimpleDividerItemDecoration(mContext));
        }
        comingUpAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> showDialog(tvInfoList.get(i)));
    }


    @SuppressLint("SetTextI18n")
    private void showDialog(@NonNull TVInfo tvInfo) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_movies, null);
            alertDialogBuilder.setView(view);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            ((TextView) ButterKnife.findById(view, R.id.movies_overlay_main_txtnamemovie)).setText("Watch '" + tvInfo.getTitle() + "'");
            ButterKnife.findById(view, R.id.movies_overlay_main_leftdiv).setOnClickListener(v -> {
                alertDialog.dismiss();
                if (Preconditions.checkList(tvInfo.getChannelStreams()) && tvInfo.getChannelStreams().get(0) != null) {
                    Intent player = new Intent(mContext, VideoPlayerActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString(BundleDef.BUNDLE_KEY, tvInfo.getChannelStreams().get(0).getSrc());
                    bundle.putString(BundleDef.BUNDLE_MOVIES_TITLE, tvInfo.getTitle());
                    bundle.putString(BundleDef.BUNDLE_MOVIES_CHANNEL, tvInfo.getChannelName());
                    player.putExtra(BundleDef.BUNDLE, bundle);
                    startActivity(player);
                } else {
                    Snackbar.make(recyclerRightNow, getString(R.string.link_stream_error), Snackbar.LENGTH_LONG).show();
                }
            });
            ButterKnife.findById(view, R.id.movies_overlay_main_rightdiv).setOnClickListener(
                    v -> alertDialog.dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showEmty() {

    }

}

