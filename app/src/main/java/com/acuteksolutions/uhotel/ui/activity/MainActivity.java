package com.acuteksolutions.uhotel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.interfaces.KeyListener;
import com.acuteksolutions.uhotel.interfaces.OnBackListener;
import com.acuteksolutions.uhotel.interfaces.OnTabSelectedListener;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.interfaces.ViewpagerListener;
import com.acuteksolutions.uhotel.libs.CustomSwipeableViewPager;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.libs.materialdrawer.DrawerView;
import com.acuteksolutions.uhotel.libs.materialdrawer.structure.DrawerItem;
import com.acuteksolutions.uhotel.libs.materialdrawer.structure.DrawerProfile;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerMainAdapter;
import com.acuteksolutions.uhotel.ui.fragment.landing.LandingFragment;
import com.acuteksolutions.uhotel.ui.fragment.setting.SettingFragment;
import com.acuteksolutions.uhotel.utils.Preconditions;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends BaseActivity implements ToolbarTitleListener,ViewpagerListener {
  private ActionBarDrawerToggle drawerToggle;
  private boolean doubleBackToExitPressedOnce;
  @BindView(R.id.drawer) DrawerView mDrawer;
  @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.tab_main) TabLayout tabMain;
  @BindView(R.id.viewPager_main) CustomSwipeableViewPager viewPagerMain;
  @BindView(R.id.custom_tab_icon) AppCompatImageView custom_tab_icon;
  @BindView(R.id.appBar) AppBarLayout layout_tab;
  @BindView(R.id.layout_root) RelativeLayout layout_root;
  PreferencesHelper mPreferencesHelper;

  public static void start(Context context) {
      Intent starter = new Intent(context, MainActivity.class);
      context.startActivity(starter);
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initDrawer();
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
      mPreferencesHelper=new PreferencesHelper(this);
    if(mPreferencesHelper.getJsonLogin()==null)
      LoginActivity.start(this);
    else {
      initToolbar();
      initTabLayout();
    }
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.activity_main;
  }

  @Override
  protected void initData() {
    viewPagerMain.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabMain));
    tabMain.addOnTabSelectedListener(new OnTabSelectedListener().onTabSelectedListener(viewPagerMain));
  }

  @Override
  protected void injectDependencies() {

  }

  private void initDrawer(){
    drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

      public void onDrawerClosed(View view) {
        invalidateOptionsMenu();
      }

      public void onDrawerOpened(View drawerView) {
        invalidateOptionsMenu();
      }
    };
    //drawerLayout.setStatusBarBackgroundColor(ContextCompat.getColor(this, R.color.transparent));
    drawerLayout.addDrawerListener(drawerToggle);
    drawerLayout.closeDrawer(mDrawer);

    mDrawer.addDivider();
    mDrawer.addItems(new DrawerItem()
        .setImage(ContextCompat.getDrawable(this, R.drawable.menu_concierge))
        .setTextPrimary(getString(TabMainDef.CONCIERGE)));
    mDrawer.addDivider();

    mDrawer.addItems(new DrawerItem()
        .setImage(ContextCompat.getDrawable(this, R.drawable.menu_livetv))
        .setTextPrimary(getString(TabMainDef.LIVETV)));
    mDrawer.addDivider();

    mDrawer.addItems(new DrawerItem()
        .setImage(ContextCompat.getDrawable(this, R.drawable.menu_movies))
        .setTextPrimary(getString(TabMainDef.MOVIES)));
    mDrawer.addDivider();

    mDrawer.addItems(new DrawerItem()
        .setImage(ContextCompat.getDrawable(this, R.drawable.menu_food_activities))
        .setTextPrimary(getString(TabMainDef.FOOD)));
    mDrawer.addDivider();

    mDrawer.addItems(new DrawerItem()
        .setImage(ContextCompat.getDrawable(this, R.drawable.menu_room_control))
        .setTextPrimary(getString(TabMainDef.ROOMCONTROL)));
    mDrawer.addDivider();

    mDrawer.addItems(new DrawerItem()
        .setImage(ContextCompat.getDrawable(this, R.drawable.menu_settings))
        .setTextPrimary(getString(R.string.home_menu_setting)));

    mDrawer.addProfile(new DrawerProfile()
        .setId(1)
        .setRoundedAvatar((BitmapDrawable) ContextCompat.getDrawable(this, R.drawable.ic_exception))
        .setName(getString(R.string.app_name))
    );
    mDrawer.setOnItemClickListener((item, id, position) -> {
      mDrawer.selectItem(position);
      if (mPreferencesHelper.getJsonLogin()!=null) {
          Logger.e("position="+position);
          if(position<2)
              viewPagerMain.setCurrentItem(1);
          else if(position==11)
            replaceFagment(getSupportFragmentManager(),R.id.layout_root,SettingFragment.newInstance());
          else
              viewPagerMain.setCurrentItem(position/2+1);
      }else{
          LoginActivity.start(this);
          Snackbar.make(mDrawer, "Please login!", Snackbar.LENGTH_LONG).show();
      }
      drawerLayout.closeDrawer(mDrawer);
    });
  }

  private void initTabLayout() {
    try {
      TabMainDef tabMainDef = new TabMainDef();
      for (int i = 0; i < tabMainDef.tabSize(); i++) {
        tabMain.addTab(tabMain.newTab().setText(getString(tabMainDef.getTab(i))));
      }
      TabPagerMainAdapter tabPagerAdapter = new TabPagerMainAdapter(this, tabMainDef, getSupportFragmentManager());
      viewPagerMain.setAdapter(tabPagerAdapter);
      tabMain.setupWithViewPager(viewPagerMain);
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private void initToolbar(){
    setSupportActionBar(mToolbar);
  }

  @Override
  public void onBackPressedSupport() {
    if(getSupportFragmentManager().findFragmentById(R.id.fragment)!=null) {
      String currentTag = Preconditions.checkNotNull(
          getSupportFragmentManager().findFragmentById(R.id.fragment).getTag());
      if (currentTag.equals(LandingFragment.class.getName())) {
        if (doubleBackToExitPressedOnce) {
          finish();
        }
        this.doubleBackToExitPressedOnce = true;
        Snackbar.make(mDrawer, R.string.msg_exit, Snackbar.LENGTH_SHORT).show();
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
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    drawerToggle.onConfigurationChanged(newConfig);
  }


  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
    super.onPostCreate(savedInstanceState);
    drawerToggle.syncState();
  }

  @Override
  public void changeTitle(String name) {
    //mToolbar.setTitle(name);
  }

  @Override
  public void hideShowToolBar(boolean isShow) {
    if (isShow) {
      tabMain.setVisibility(View.VISIBLE);
      custom_tab_icon.setVisibility(View.VISIBLE);
      layout_tab.setBackgroundColor(getResources().getColor(R.color.tab_background));
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_root.getLayoutParams();
      params.addRule(RelativeLayout.BELOW,layout_tab.getId());
      StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.tab_background));
    }else {
      tabMain.setVisibility(View.GONE);
      custom_tab_icon.setVisibility(View.GONE);
      layout_tab.setBackgroundColor(Color.TRANSPARENT);
      layout_tab.getBackground().setAlpha(0);
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_root.getLayoutParams();
      params.addRule(RelativeLayout.BELOW,0);
      StatusBarCompat.translucentStatusBar(this, true);
    }
  }

  @Override
  public void disableSwipe(boolean enable) {
    viewPagerMain.setPagingEnabled(enable);
  }

}
