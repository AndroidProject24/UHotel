package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.annotation.TabMoviesDef;
import com.acuteksolutions.uhotel.mvp.model.data.Category;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.MoviesView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.MoviesAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.utils.Preconditions;
import java.util.List;
import javax.inject.Inject;

public class ListMoviesFragment extends BaseFragment implements MoviesView {
  @Inject
  MoviesPresenter
  mPresenter;
  private Context mContext;
  @BindView(R.id.recycle_movies)
  RecyclerView mRecyclerMovies;

  public static ListMoviesFragment newInstance(@TabMoviesDef.TabMovies int index) {
    ListMoviesFragment fragment=new ListMoviesFragment();
    Bundle bundle=new Bundle();
    bundle.putInt(BundleDef.TAB_INDEX,index);
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
  protected void initViews() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    mPresenter.attachView(this);
    mRecyclerMovies.setLayoutManager(new GridLayoutManager(mContext,4));
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
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
  }

  @Override
  public void listCategory(List<Category> categoryList) {
    mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(Preconditions.checkNotNull(getArguments().getInt(BundleDef.TAB_INDEX)))).getId());
  }

  @Override
  public void listMovies(List<VODInfo> moviesList) {
    MoviesAdapter moviesAdapter = new MoviesAdapter(glide,moviesList);
    moviesAdapter.openLoadAnimation();
    mRecyclerMovies.setAdapter(moviesAdapter);
  }

}

