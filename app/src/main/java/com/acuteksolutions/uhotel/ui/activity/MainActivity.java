package com.acuteksolutions.uhotel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.interfaces.KeyListener;
import com.acuteksolutions.uhotel.interfaces.OnTabSelectedListener;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.interfaces.ViewpagerListener;
import com.acuteksolutions.uhotel.libs.CustomSwipeableViewPager;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.ui.adapter.HomeMenuAdapter;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerMainAdapter;
import com.acuteksolutions.uhotel.ui.fragment.setting.SettingFragment;
import com.acuteksolutions.uhotel.utils.FakeDataUtils;

import butterknife.BindView;
import qiu.niorgai.StatusBarCompat;

public class MainActivity extends BaseActivity implements ToolbarTitleListener,ViewpagerListener {
  private boolean doubleBackToExitPressedOnce;
  private ActionBarDrawerToggle toggle;
  @BindView(R.id.drawer) NavigationView mDrawer;
  @BindView(R.id.drawerLayout) DrawerLayout drawerLayout;
  @BindView(R.id.recycler_menu) RecyclerView recyclerViewMenu;
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
      toggle = new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
      drawerLayout.addDrawerListener(toggle);
      toggle.syncState();
      HomeMenuAdapter menuAdapter=new HomeMenuAdapter(this, FakeDataUtils.homeMenus(this));
      menuAdapter.openLoadAnimation();
      recyclerViewMenu.setAdapter(menuAdapter);
      recyclerViewMenu.setLayoutManager(new LinearLayoutManager(this));
      recyclerViewMenu.setHasFixedSize(true);
      menuAdapter.setOnItemChildClickListener((baseQuickAdapter, view, position) -> {
          position++;
          removeSettingFragment();
          drawerLayout.closeDrawer(GravityCompat.START);
          if (mPreferencesHelper.getJsonLogin()==null) {
              LoginActivity.start(MainActivity.this);
              Snackbar.make(mDrawer, "Please login!", Snackbar.LENGTH_LONG).show();
              return;
          }
          if(position==6)
              replaceFagment(getSupportFragmentManager(), R.id.layout_root, SettingFragment.newInstance());
          else
              viewPagerMain.setCurrentItem(position);
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
      tabPagerAdapter.getRegisteredFragment(viewPagerMain.getCurrentItem());
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private void initToolbar(){
    setSupportActionBar(mToolbar);
  }

  private void removeSettingFragment(){
    if(getSupportFragmentManager().findFragmentByTag(SettingFragment.class.getName())!=null)
      getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(SettingFragment.class.getName())).commit();
  }

  @Override
  public void onBackPressedSupport() {
      if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
          drawerLayout.closeDrawer(GravityCompat.START);
      } else {
          if(viewPagerMain.getCurrentItem()>0)
            viewPagerMain.setCurrentItem(0);
          else{
              if (doubleBackToExitPressedOnce) {
                  finish();
              }
              this.doubleBackToExitPressedOnce = true;
              Snackbar.make(mDrawer, R.string.msg_exit, Snackbar.LENGTH_SHORT).show();
              new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
          }
      }
  }

  @Override
  public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(event.getKeyCode()==KeyEvent.KEYCODE_BACK ) {
        onBackPressedSupport();
    }
    else if(getSupportFragmentManager().findFragmentById(R.id.fragment) instanceof KeyListener) {
      KeyListener keyListener = (KeyListener) getSupportFragmentManager().findFragmentById(R.id.fragment);
      keyListener.onKeyDown(keyCode, event);
    }
    return false;
  }

  @Override
  public void changeTitle(String name) {
    //mToolbar.setTitle(name);
  }

  @Override
  public void hideShowToolBar(boolean isShow) {
    if (isShow) {
      toggle.setDrawerIndicatorEnabled(false);
      tabMain.setVisibility(View.VISIBLE);
      custom_tab_icon.setVisibility(View.VISIBLE);
      layout_tab.setBackgroundColor(getResources().getColor(R.color.tab_background));
      RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_root.getLayoutParams();
      params.addRule(RelativeLayout.BELOW,layout_tab.getId());
      StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.tab_background));
    }else {
      toggle.setDrawerIndicatorEnabled(true);
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
    public void showScreen(int index) {
        Logger.e("showScreen="+index);
        if(viewPagerMain!=null)
            viewPagerMain.setCurrentItem(index);
    }

    @Override
  public void disableSwipe(boolean enable) {
    viewPagerMain.setPagingEnabled(enable);
  }

}
