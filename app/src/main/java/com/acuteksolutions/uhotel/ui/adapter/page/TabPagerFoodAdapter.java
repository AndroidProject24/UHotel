package com.acuteksolutions.uhotel.ui.adapter.page;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import com.acuteksolutions.uhotel.annotation.TabFoodDef;
import com.acuteksolutions.uhotel.libs.SmartFragmentStatePagerAdapter;
import com.acuteksolutions.uhotel.ui.fragment.food.ListFoodFragment;

/**
 * Toan.IT
 * Created by toan.it on 1/12/16.
 * Email: huynhvantoan.itc@gmail.com
 */
public class TabPagerFoodAdapter extends SmartFragmentStatePagerAdapter<ListFoodFragment> {
  private TabFoodDef tab;
  private Context context;
  public TabPagerFoodAdapter(Context context,TabFoodDef tabMoviesDef,FragmentManager fm) {
    super(fm);
    this.context=context;
    this.tab=tabMoviesDef;
  }

  @Override
  public Fragment getItem(int position) {
    return ListFoodFragment.newInstance(position);
  }

  @Override public CharSequence getPageTitle(int position) {
    return context.getString(tab.getTab(position));
  }

  @Override
  public int getCount() {
    return tab.tabSize();
  }
}