package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.movies.Category;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.MoviesView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.MoviesAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;

import java.util.List;

import butterknife.BindView;

public class MoreMoviesFragment extends BaseFragment<MoviesPresenter> implements MoviesView {
  private Context mContext;
  @BindView(R.id.recycle_movies)
  RecyclerView mRecyclerview;
  public static MoreMoviesFragment newInstance() {
    return new MoreMoviesFragment();
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
  protected void injectDependencies() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
  }

  @Override
  protected void initViews() {
    initRecyclerview();
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.more_movies_fragment;
  }

  @Override
  protected void initData() {
    //mPresenter.getData(TheloaiDef.HOA_MANG_TRA_TRUOC);
  }

  private void initRecyclerview(){
    mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    mRecyclerview.setHasFixedSize(true);
  }

  @Override
  public void listCategory(List<Category> categoryList) {

  }

  @Override
  public void listMovies(List<VODInfo> moviesList) {
    MoviesAdapter moviesAdapter =new MoviesAdapter(glide,moviesList);
    moviesAdapter.openLoadAnimation();
    mRecyclerview.setAdapter(moviesAdapter);
  }

  @Override
  public void playStream(String title, String channel, String linkStream) {

  }


  @Override public void showEmty() {

  }

  @Override
  public void linkStreamError() {

  }
}

