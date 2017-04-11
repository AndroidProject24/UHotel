package com.acuteksolutions.uhotel.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.interfaces.KeyListener;
import com.acuteksolutions.uhotel.interfaces.OnBackListener;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.libs.CustomCenteredPrimaryDrawerItem;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerMainAdapter;
import com.acuteksolutions.uhotel.ui.fragment.food.FoodFragment;
import com.acuteksolutions.uhotel.ui.fragment.landing.LandingFragment;
import com.acuteksolutions.uhotel.ui.fragment.liveTV.LiveTVFragment;
import com.acuteksolutions.uhotel.ui.fragment.login.LoginFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.roomService.RoomServiceFragment;
import com.acuteksolutions.uhotel.ui.fragment.setting.SettingFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements ToolbarTitleListener {
  private Drawer result = null;
  private boolean doubleBackToExitPressedOnce;
  @BindView(R.id.fragment) FrameLayout mLayout;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.tab_main) TabLayout tabMain;
  @BindView(R.id.viewPager_main) ViewPager viewPagerMain;
  private TabPagerMainAdapter tabPagerAdapter;
  @Inject
  PreferencesHelper mPreferencesHelper;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initToolbarTranparent();
    initDrawer(savedInstanceState);
    initTabLayout();
  }
  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    if(mPreferencesHelper.getJsonLogin()==null) {
      addFagment(getSupportFragmentManager(), R.id.fragment, LoginFragment.newInstance());
    }
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.activity_main;
  }

  @Override
  protected void initData() {

  }

  @Override
  protected void injectDependencies() {
    getActivityComponent().inject(this);
  }
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState = result.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  private void initToolbarTranparent(){
    try {
      setSupportActionBar(mToolbar);
      mToolbar.setBackgroundColor(Color.TRANSPARENT);
      mToolbar.getBackground().setAlpha(100);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private void initDrawer(Bundle savedInstanceState){
    result = new DrawerBuilder(this)
        .withActivity(this)
        .withHeader(R.layout.header)
        .withToolbar(mToolbar)
        .withHasStableIds(true)
        .withDrawerWidthDp(400)
        .withSliderBackgroundColorRes(R.color.black)
        .withItemAnimator(new DefaultItemAnimator())
        .addDrawerItems(
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.CONCIERGE)).withIcon(R.drawable.menu_concierge).withIdentifier(1).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.LIVETV)).withIcon(R.drawable.menu_livetv).withIdentifier(2).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.MOVIES)).withIcon(R.drawable.menu_movies).withIdentifier(3).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.FOOD)).withIcon(R.drawable.menu_food_activities).withIdentifier(4).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.ROOMCONTROL)).withIcon(R.drawable.menu_room_control).withIdentifier(5).withSetSelected(true).withEnabled(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(R.string.home_menu_setting)).withIcon(R.drawable.menu_settings).withIdentifier(6).withSetSelected(true),
            new DividerDrawerItem()
        )
        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
          if (drawerItem != null) {
            if (mPreferencesHelper.getJsonLogin()!=null) {
              if (drawerItem.getIdentifier() == 1) {
                replaceFagment(getSupportFragmentManager(), R.id.fragment, LandingFragment.newInstance());
              } else if (drawerItem.getIdentifier() == 2) {
                replaceFagment(getSupportFragmentManager(), R.id.fragment, LiveTVFragment.newInstance());
              } else if (drawerItem.getIdentifier() == 3) {
                replaceFagment(getSupportFragmentManager(), R.id.fragment, MoviesFragment.newInstance());
              } else if (drawerItem.getIdentifier() == 4) {
                replaceFagment(getSupportFragmentManager(), R.id.fragment, FoodFragment.newInstance());
              } else if (drawerItem.getIdentifier() == 5) {
                replaceFagment(getSupportFragmentManager(), R.id.fragment, RoomServiceFragment.newInstance());
              }else if (drawerItem.getIdentifier() == 6) {
                replaceFagment(getSupportFragmentManager(), R.id.fragment, SettingFragment.newInstance());
              }else{
                replaceFagment(getSupportFragmentManager(), R.id.fragment, LandingFragment.newInstance());
              }
            }else{
              addFagment(getSupportFragmentManager(), R.id.fragment, LoginFragment.newInstance());
              Snackbar.make(mLayout, "Please login!", Snackbar.LENGTH_LONG).show();
            }
          }
          return false;
        })
        .withSavedInstance(savedInstanceState)
        .build();
    if (Build.VERSION.SDK_INT >= 19) {
      result.getDrawerLayout().setFitsSystemWindows(false);
    }
  }

  private void initTabLayout() {
    try {
      TabMainDef tabMainDef = new TabMainDef();
      tabPagerAdapter = new TabPagerMainAdapter(this,tabMainDef,getSupportFragmentManager());
      viewPagerMain.setAdapter(tabPagerAdapter);
      tabMain.setupWithViewPager(viewPagerMain);
      viewPagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
      tabMain.addOnTabSelectedListener(onTabSelectedListener(viewPagerMain));
      ImageView tabOne = (ImageView) LayoutInflater.from(this).inflate(R.layout.icon_tab, null);
      tabMain.addTab(tabMain.newTab().setCustomView(tabOne),0);
      for (int i = 1; i < tabMainDef.tabSize(); i++) {
        tabMain.addTab(tabMain.newTab().setText(getString(tabMainDef.getTab(i))),i);
      }
    }catch (Exception e){
      e.printStackTrace();
    }
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

  @Override
  public void onBackPressed() {
    if (result != null && result.isDrawerOpen()) {
      result.closeDrawer();
    } else {
      String currentTag = getSupportFragmentManager().findFragmentById(R.id.fragment).getTag();
      if (currentTag.equals(LandingFragment.class.getName()) || currentTag.equals(LoginFragment.class.getName())) {
        if (doubleBackToExitPressedOnce) {
          finish();
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(mLayout,R.string.msg_exit,Snackbar.LENGTH_SHORT).show();
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
      } else {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if (fragment instanceof OnBackListener) {
          ((OnBackListener) fragment).onBackPress();
        }
      }
    }
  }
  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(event.getKeyCode()==KeyEvent.KEYCODE_BACK ) {
      onBackPressed();
    }
    else if(getSupportFragmentManager().findFragmentById(R.id.fragment) instanceof KeyListener) {
      KeyListener keyListener = (KeyListener) getSupportFragmentManager().findFragmentById(R.id.fragment);
      keyListener.onKeyDown(keyCode, event);
    }
    return false;
  }

  @Override
  public void changeTitle(String name) {
    mToolbar.setTitle(name);
  }

  @Override
  public void hideShowToolBar(boolean isShow) {
    if(mToolbar != null) {
      if (isShow) {
        if(getSupportActionBar()!=null)
          getSupportActionBar().show();
        tabMain.setVisibility(View.GONE);
      }else {
        result.closeDrawer();
        tabMain.setVisibility(View.VISIBLE);
        if(getSupportActionBar()!=null) {
          getSupportActionBar().setDisplayHomeAsUpEnabled(false);
          getSupportActionBar().hide();
        }
      }
    }
  }
}
