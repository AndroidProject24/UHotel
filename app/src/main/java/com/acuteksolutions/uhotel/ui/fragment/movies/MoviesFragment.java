package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.ui.adapter.TabPagerAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

public class MoviesFragment extends BaseFragment {
  @BindView(R.id.tabLayout)
  TabLayout mTabLayout;
  @BindView(R.id.view_pager)
  ViewPager mViewPager;
  Unbinder unbinder;
  private Context mContext;

  public static MoviesFragment newInstance() {
    return new MoviesFragment();
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
    TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getFragmentManager());
    tabPagerAdapter.addFragment(ListMoviesFragment.newInstance(), "1");
    tabPagerAdapter.addFragment(ListMoviesFragment.newInstance(), "2");
    mViewPager.setAdapter(tabPagerAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.movies_fragment;
  }

  @Override
  protected void initData() {

  }
}

