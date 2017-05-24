package com.acuteksolutions.uhotel.ui.adapter.page;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.acuteksolutions.uhotel.annotation.TabSettingDef;
import com.acuteksolutions.uhotel.libs.SmartFragmentStatePagerAdapter;
import com.acuteksolutions.uhotel.ui.fragment.food.ListFoodFragment;
import com.acuteksolutions.uhotel.ui.fragment.setting.AccountFragment;
import com.acuteksolutions.uhotel.ui.fragment.setting.DeviceFragment;

/**
 * Toan.IT
 * Created by toan.it on 1/12/16.
 * Email: huynhvantoan.itc@gmail.com
 */
public class TabPagerSettingAdapter extends SmartFragmentStatePagerAdapter<ListFoodFragment> {
  private TabSettingDef tab;
  private Context context;
  public TabPagerSettingAdapter(Context context, TabSettingDef tabSettingDef, FragmentManager fm) {
    super(fm);
    this.context=context;
    this.tab=tabSettingDef;
  }

  @Override
  public Fragment getItem(int position) {
    switch (position){
      case TabSettingDef.ACCOUNT:
        return AccountFragment.newInstance();
      case TabSettingDef.DEVICES:
        return DeviceFragment.newInstance();
      default:
        return AccountFragment.newInstance();
    }
  }

  @Override public CharSequence getPageTitle(int position) {
    return context.getString(tab.getTab(position));
  }

  @Override
  public int getCount() {
    return tab.tabSize();
  }
}