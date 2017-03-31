package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.data.Category;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.MoviesView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.movies.MoviesAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MoviesFragment extends BaseFragment implements MoviesView {
  @Inject
  MoviesPresenter
  mPresenter;
  private Context mContext;
  @BindView(R.id.recycle_movies)
  RecyclerView mRecyclerMovies;
  public static MoviesFragment newInstance() {
    return new MoviesFragment();
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
    mPresenter.attachView(this);
    initRecyclerview();
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.movies_fragment;
  }

  @Override
  protected void initData() {
    mPresenter.getCategory();
  }

  private void initRecyclerview(){
    mRecyclerMovies.setLayoutManager(new LinearLayoutManager(mContext));
    mRecyclerMovies.setHasFixedSize(true);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
  }

  @Override
  public void listCategory(List<Category> categoryList) {
    //Logger.e("categoryList="+categoryList.toString()+"\n GetID="+categoryList.get(0).getId());
    mPresenter.getMoviesDetails(categoryList.get(0).getId());
  }

  @Override
  public void listMovies(List<VODInfo> moviesList) {
    Logger.e("moviesList="+moviesList.toString());
    MoviesAdapter moviesAdapter =new MoviesAdapter(moviesList);
    moviesAdapter.openLoadAnimation();
    mRecyclerMovies.setAdapter(moviesAdapter);
  }
  @OnClick(R.id.btn_all_movies)
  public void ClickAllMovies(){

  }
}

