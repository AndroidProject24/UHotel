package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.annotation.TabMoviesDef;
import com.acuteksolutions.uhotel.libs.ItemClickSupport;
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
  /*@BindView(R.id.txt_movies_name)
  TextView mTxtMoviesName;
  @BindView(R.id.txt_movies_info)
  TextView mTxtMoviesInfo;
  @BindView(R.id.img_main)
  ImageView mImageMain;*/
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
    mRecyclerMovies.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
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
    int categoryIndex=Preconditions.checkNotNull(getArguments().getInt(BundleDef.TAB_INDEX));
    mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(categoryIndex)).getId());
    /*switch (indexFragment){
      case TabMoviesDef.TabMovies.LATEST:
        mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(indexFragment).getId()));
        break;
      case TabMoviesDef.TabMovies.ACTION:
        mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(indexFragment).getId()));
        break;
      case TabMoviesDef.TabMovies.ADULTS:
        mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(indexFragment).getId()));
        break;
      case TabMoviesDef.TabMovies.COMEDY:
        mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(indexFragment).getId()));
        break;
      case TabMoviesDef.TabMovies.DRAMA:
        mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(indexFragment).getId()));
        break;
      case TabMoviesDef.TabMovies.EVENTS:
        mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(indexFragment).getId()));
        break;
      case TabMoviesDef.TabMovies.FAMILY:
        mPresenter.getMoviesDetails(Preconditions.checkNotNull(categoryList.get(indexFragment).getId()));
        break;
    }*/
  }

  @Override
  public void listMovies(List<VODInfo> moviesList) {
    MoviesAdapter moviesAdapter = new MoviesAdapter(glide,moviesList);
    moviesAdapter.openLoadAnimation();
    mRecyclerMovies.setAdapter(moviesAdapter);
    showInfo(moviesList.get(0));
    ItemClickSupport.addTo(mRecyclerMovies).setOnItemClickListener((recyclerView, position, v) ->
        showInfo(moviesList.get(position)));
  }

  @Override
  public void showInfo(VODInfo info) {
    try {
      Preconditions.checkNotNull(info);
     /* ImageUtils.loadImage(glide,info.getDetail().getPoster(), mImageMain);
      mTxtMoviesName.setText(Preconditions.checkNotNull(info.getDetail().getTitle()));
      mTxtMoviesInfo.setText(Preconditions.checkNotNull(info.getDetail().getDescription()));*/
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  /*

  @OnClick(R.id.btn_all_movies)
  public void ClickAllMovies() {
    replaceFagment(getFragmentManager(), R.id.fragment, MoreMoviesFragment.newInstance());
  }
  */

}

