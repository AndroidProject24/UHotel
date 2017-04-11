
package com.acuteksolutions.uhotel.ui.adapter.page;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.ui.fragment.food.FoodFragment;
import com.acuteksolutions.uhotel.ui.fragment.landing.LandingFragment;
import com.acuteksolutions.uhotel.ui.fragment.liveTV.LiveTVFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.parentalControl.ParentalControlFragment;
import com.acuteksolutions.uhotel.ui.fragment.roomService.RoomServiceFragment;

/**
 * Created by Toan.IT on 4/2/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class TabPagerMainAdapter extends FragmentStatePagerAdapter {
  private TabMainDef tab;
  private Context context;
  public TabPagerMainAdapter(Context context,TabMainDef tabMoviesDef,FragmentManager fm) {
    super(fm);
    this.context=context;
    this.tab=tabMoviesDef;
  }


  @Override
  public Fragment getItem(int position) {
    Fragment fragment = null;
    switch (position) {
      case 0:
        fragment= LandingFragment.newInstance();
        break;
      case TabMainDef.TabMain.HOME:
        fragment= LandingFragment.newInstance();
        break;
      case TabMainDef.TabMain.CONCIERGE:
        fragment= ParentalControlFragment.newInstance();
        break;
      case TabMainDef.TabMain.FOOD:
        fragment= FoodFragment.newInstance();
        break;
      case TabMainDef.TabMain.LIVETV:
        fragment= LiveTVFragment.newInstance();
        break;
      case TabMainDef.TabMain.MOVIES:
        fragment= MoviesFragment.newInstance();
        break;
      case TabMainDef.TabMain.ROOMCONTROL:
        fragment= RoomServiceFragment.newInstance();
        break;
    }
    return fragment;
  }

  @Override
  public int getCount() {
    return tab.tabSize()+1;
  }

}
