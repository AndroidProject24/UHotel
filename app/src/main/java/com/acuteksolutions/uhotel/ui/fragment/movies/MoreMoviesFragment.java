package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.data.Category;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.MoviesView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.MoviesAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import java.util.List;
import javax.inject.Inject;

public class MoreMoviesFragment extends BaseFragment implements MoviesView {
  @Inject
  MoviesPresenter
  mPresenter;
  private Context mContext;
  @BindView(R.id.recyclerview)
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
  protected void initViews() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    mPresenter.attachView(this);
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
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
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

  @Override public void showInfo(VODInfo info) {

  }
}

