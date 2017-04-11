package com.acuteksolutions.uhotel.ui.fragment.movies;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMoviesDef;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerMoviesAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;

public class MoviesFragment extends BaseFragment{
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
    mViewPager.setAdapter(new TabPagerMoviesAdapter(mContext,tabMoviesDef,getFragmentManager()));
    mTabLayout.setupWithViewPager(mViewPager);
    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    mTabLayout.addOnTabSelectedListener(onTabSelectedListener(mViewPager));
    for (int i = 0; i < tabMoviesDef.tabSize(); i++) {
      mTabLayout.getTabAt(i).setText(getString(tabMoviesDef.getTab(i)));
    }
  }

  @Override
  protected void initData() {

  }

  private TabLayout.OnTabSelectedListener onTabSelectedListener(final ViewPager pager) {
    return new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    };
  }

}

