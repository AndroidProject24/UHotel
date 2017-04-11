package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.annotation.TabMoviesDef;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.data.Category;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.MoviesView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.movies.MoviesAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.acuteksolutions.uhotel.utils.Preconditions;
import java.util.List;
import javax.inject.Inject;

public class ListMoviesFragment extends BaseFragment implements MoviesView {
  @Inject
  MoviesPresenter
  mPresenter;
  @BindView(R.id.txt_movies_name)
  TextView mTxtMoviesName;
  @BindView(R.id.txt_movies_info)
  TextView mTxtMoviesInfo;
  private Context mContext;
  @BindView(R.id.recycle_movies)
  RecyclerView mRecyclerMovies;
  @BindView(R.id.img_main)
  ImageView mImageMain;

  public static ListMoviesFragment newInstance(@TabMoviesDef.TabMovies int index) {
    ListMoviesFragment fragment=new ListMoviesFragment();
    Bundle bundle=new Bundle();
    bundle.putInt(BundleDef.TAB_MOVIES,index);
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
    initRecyclerview();
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.list_movies_fragment;
  }

  @Override
  protected void initData() {
    mPresenter.getCategory();
  }

  private void initRecyclerview() {
    mRecyclerMovies.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    mRecyclerMovies.setHasFixedSize(true);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
  }

  public void displayContent(VODInfo info) {
    try {
      Preconditions.checkNotNull(info);
      ImageUtils.loadImage(mContext, Preconditions.checkNotNull(info.getDetail().getPoster()), mImageMain);
      mTxtMoviesName.setText(Preconditions.checkNotNull(info.getDetail().getTitle()));
      mTxtMoviesInfo.setText(Preconditions.checkNotNull(info.getDetail().getDescription()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void listCategory(List<Category> categoryList) {
    mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(Preconditions.checkNotNull(getArguments().getInt(BundleDef.TAB_MOVIES))).getId()));
    switch (getArguments().getInt(BundleDef.TAB_MOVIES)){
      case TabMoviesDef.TabMovies.LATEST:
        break;
      case TabMoviesDef.TabMovies.ACTION:

        break;
      case TabMoviesDef.TabMovies.ADULTS:

        break;
      case TabMoviesDef.TabMovies.COMEDY:

        break;
      case TabMoviesDef.TabMovies.DRAMA:

        break;
      case TabMoviesDef.TabMovies.EVENTS:

        break;
      case TabMoviesDef.TabMovies.FAMILY:

        break;
    }
  }

  @Override
  public void listMovies(List<VODInfo> moviesList) {
    Logger.e("moviesList=" + moviesList.toString());
    displayContent(moviesList.get(0));
    MoviesAdapter moviesAdapter = new MoviesAdapter(moviesList);
    moviesAdapter.openLoadAnimation();
    mRecyclerMovies.setAdapter(moviesAdapter);
  }

  @OnClick(R.id.btn_all_movies)
  public void ClickAllMovies() {
    replaceFagment(getFragmentManager(), R.id.fragment, MoreMoviesFragment.newInstance());
  }

}

