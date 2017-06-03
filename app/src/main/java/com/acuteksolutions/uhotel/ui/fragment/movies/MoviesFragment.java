package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMoviesDef;
import com.acuteksolutions.uhotel.interfaces.OnTabSelectedListener;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerMoviesAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;

import butterknife.BindView;

public class MoviesFragment extends BaseFragment<BasePresenter>{
  @BindView(R.id.tabLayout)
  TabLayout mTabLayout;
  @BindView(R.id.view_pager)
  ViewPager mViewPager;
  private Context mContext;

  public static MoviesFragment newInstance() {
    return new MoviesFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override protected void injectDependencies() {

  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }


  @Override
  protected int setLayoutResourceID() {
    return R.layout.movies_fragment;
  }

  @Override
  protected void initViews() {
    TabMoviesDef tabMoviesDef = new TabMoviesDef();
    for (int i = 0; i < tabMoviesDef.tabSize(); i++) {
      mTabLayout.addTab(mTabLayout.newTab().setText(getString(tabMoviesDef.getTab(i))));
    }
    TabPagerMoviesAdapter tabPagerMoviesAdapter=new TabPagerMoviesAdapter(mContext,tabMoviesDef,getChildFragmentManager());
    mViewPager.setAdapter(tabPagerMoviesAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override
  protected void initData() {
    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener().onTabSelectedListener(mViewPager));
  }

}

