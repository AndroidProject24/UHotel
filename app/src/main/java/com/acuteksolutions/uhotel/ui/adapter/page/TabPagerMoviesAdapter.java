package com.acuteksolutions.uhotel.ui.adapter.page;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import com.acuteksolutions.uhotel.annotation.TabMoviesDef;
import com.acuteksolutions.uhotel.libs.SmartFragmentStatePagerAdapter;
import com.acuteksolutions.uhotel.ui.fragment.movies.ListMoviesFragment;

/**
 * Toan.IT
 * Created by toan.it on 1/12/16.
 * Email: huynhvantoan.itc@gmail.com
 */
public class TabPagerMoviesAdapter extends SmartFragmentStatePagerAdapter<ListMoviesFragment> {
  private TabMoviesDef tab;
  private Context context;
  public TabPagerMoviesAdapter(Context context,TabMoviesDef tabMoviesDef,FragmentManager fm) {
    super(fm);
    this.context=context;
    this.tab=tabMoviesDef;
  }

  @Override
  public Fragment getItem(int position) {
    return ListMoviesFragment.newInstance(position);
  }

  @Override public CharSequence getPageTitle(int position) {
    return context.getString(tab.getTab(position));
  }

  @Override
  public int getCount() {
    return tab.tabSize();
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return super.isViewFromObject(view, object);
  }
}