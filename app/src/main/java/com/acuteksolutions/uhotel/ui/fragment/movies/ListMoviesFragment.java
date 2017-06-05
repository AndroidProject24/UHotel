package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.annotation.TabMoviesDef;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.MoviesView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.MoviesAdapter;
import com.acuteksolutions.uhotel.ui.exoplayer.VideoPlayerActivity;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseLazyFragment;
import com.acuteksolutions.uhotel.utils.Preconditions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListMoviesFragment extends BaseLazyFragment<MoviesPresenter> implements MoviesView {
    private Context mContext;
    @BindView(R.id.recycle_movies)
    RecyclerView mRecyclerMovies;
    private MoviesAdapter moviesAdapter;

    public static ListMoviesFragment newInstance(@TabMoviesDef.TabMovies int index) {
        ListMoviesFragment fragment = new ListMoviesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(BundleDef.BUNDLE_KEY, index);
        fragment.setArguments(bundle);
        return fragment;
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
        mRecyclerMovies.setLayoutManager(new GridLayoutManager(mContext, 4));
        mRecyclerMovies.setHasFixedSize(true);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.list_movies_fragment;
    }

    @Override
    protected void initData() {
        mPresenter.getCategory();
    }

    @Override
    public void listCategory(List<Category> categoryList) {
        mPresenter.getListMovies(Preconditions.checkNotNull(categoryList.get(Preconditions.checkNotNull(getArguments().getInt(BundleDef.BUNDLE_KEY)))).getId());
    }

    @Override
    public void listMovies(List<VODInfo> moviesList) {
        if (moviesAdapter == null) {
            moviesAdapter = new MoviesAdapter(glide, moviesList);
            moviesAdapter.openLoadAnimation();
            mRecyclerMovies.setAdapter(moviesAdapter);
        }
        moviesAdapter.setOnItemChildClickListener((baseQuickAdapter, view, i) -> showDialog(moviesList.get(i)));
    }

    @SuppressLint("SetTextI18n")
    private void showDialog(@NonNull VODInfo vodInfo) {
        try {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
            LayoutInflater layoutInflater = getActivity().getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.dialog_movies, null);
            alertDialogBuilder.setView(view);
            final AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.setCancelable(false);
            alertDialog.show();
            ((TextView) ButterKnife.findById(view, R.id.movies_overlay_main_txtnamemovie)).setText("Watch '" + vodInfo.getDetail().getTitle() + "'");
            ButterKnife.findById(view, R.id.movies_overlay_main_leftdiv).setOnClickListener(v -> {
                alertDialog.dismiss();
                mPresenter.getLinkStream(vodInfo.getDetail().getTitle(), "", vodInfo.getContentInfoUid());
            });
            ButterKnife.findById(view, R.id.movies_overlay_main_rightdiv).setOnClickListener(
                    v -> alertDialog.dismiss());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playStream(String title, String channel, String linkStream) {
        Logger.e(linkStream);
        Intent player = new Intent(mContext, VideoPlayerActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle = new Bundle();
        bundle.putString(BundleDef.BUNDLE_KEY, linkStream);
        bundle.putString(BundleDef.BUNDLE_MOVIES_TITLE, title);
        bundle.putString(BundleDef.BUNDLE_MOVIES_CHANNEL, channel);
        player.putExtra(BundleDef.BUNDLE, bundle);
        startActivity(player);
    }

    @Override
    public void showEmty() {
        showEmptyView(getString(R.string.common_empty_msg));
    }

    @Override
    public void linkStreamError() {
        Snackbar.make(mRecyclerMovies, getString(R.string.link_stream_error), Snackbar.LENGTH_LONG).show();
    }
}

