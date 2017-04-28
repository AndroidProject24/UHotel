package com.acuteksolutions.uhotel.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.interfaces.KeyListener;
import com.acuteksolutions.uhotel.interfaces.OnBackListener;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.libs.CustomCenteredPrimaryDrawerItem;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerMainAdapter;
import com.acuteksolutions.uhotel.ui.fragment.OnTabSelectedListener;
import com.acuteksolutions.uhotel.ui.fragment.landing.LandingFragment;
import com.acuteksolutions.uhotel.ui.fragment.login.LoginFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import javax.inject.Inject;

public class MainActivity extends BaseActivity implements ToolbarTitleListener {
  private Drawer result = null;
  private boolean doubleBackToExitPressedOnce;
  @BindView(R.id.drawer_container) ViewGroup mLayout;
  @BindView(R.id.toolbar) Toolbar mToolbar;
  @BindView(R.id.tab_main) TabLayout tabMain;
  @BindView(R.id.viewPager_main) ViewPager viewPagerMain;
  @BindView(R.id.custom_tab_icon) AppCompatImageView custom_tab_icon;
  @BindView(R.id.appBar) AppBarLayout layout_tab;
  @BindView(R.id.layout_root) RelativeLayout layout_root;
  @Inject
  PreferencesHelper mPreferencesHelper;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initDrawer(savedInstanceState);
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    if(mPreferencesHelper.getJsonLogin()==null)
      addFagment(getSupportFragmentManager(), R.id.drawer_container, LoginFragment.newInstance());
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
    getActivityComponent().inject(this);
  }
  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState = result.saveInstanceState(outState);
    super.onSaveInstanceState(outState);
  }

  private void initDrawer(Bundle savedInstanceState){
    result = new DrawerBuilder(this)
        .withActivity(this)
        .withHeader(R.layout.layout_logo)
        .withDisplayBelowStatusBar(true)
        .withTranslucentStatusBar(true)
        .withToolbar(mToolbar)
        .withActionBarDrawerToggleAnimated(true)
        .withDrawerWidthDp(400)
        .withSliderBackgroundDrawableRes(R.color.black)
        .withItemAnimator(new DefaultItemAnimator())
        .addDrawerItems(
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.CONCIERGE)).withIcon(R.drawable.menu_concierge).withIdentifier(TabMainDef.TabMain.CONCIERGE).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.LIVETV)).withIcon(R.drawable.menu_livetv).withIdentifier(TabMainDef.TabMain.LIVETV).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.MOVIES)).withIcon(R.drawable.menu_movies).withIdentifier(TabMainDef.TabMain.MOVIES).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.FOOD)).withIcon(R.drawable.menu_food_activities).withIdentifier(TabMainDef.TabMain.FOOD).withSetSelected(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(TabMainDef.ROOMCONTROL)).withIcon(R.drawable.menu_room_control).withIdentifier(TabMainDef.TabMain.ROOMCONTROL).withSetSelected(true).withEnabled(true),
            new DividerDrawerItem(),
            new CustomCenteredPrimaryDrawerItem().withName(getString(R.string.home_menu_setting)).withIcon(R.drawable.menu_settings).withIdentifier(TabMainDef.TabMain.HOME).withSetSelected(true),
            new DividerDrawerItem()
        )
        .withOnDrawerItemClickListener((view, position, drawerItem) -> {
          if (drawerItem != null) {
            if (mPreferencesHelper.getJsonLogin()!=null) {
              viewPagerMain.setCurrentItem((int)drawerItem.getIdentifier());
            }else{
              replaceFagment(getSupportFragmentManager(), R.id.drawer_container, LoginFragment.newInstance());
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
    result.getActionBarDrawerToggle().setDrawerIndicatorEnabled(true);
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
    //mToolbar.setTitle(name);
  }

  @Override
  public void hideShowToolBar(boolean isShow) {
    if(tabMain != null && mToolbar!=null) {
      if (isShow) {
        tabMain.setVisibility(View.VISIBLE);
        custom_tab_icon.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_root.getLayoutParams();
        params.addRule(RelativeLayout.BELOW,layout_tab.getId());
      }else {
        tabMain.setVisibility(View.GONE);
        custom_tab_icon.setVisibility(View.GONE);
        layout_tab.setBackgroundColor(Color.TRANSPARENT);
        layout_tab.getBackground().setAlpha(0);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout_root.getLayoutParams();
        params.addRule(RelativeLayout.BELOW,0);
      }
    }
  }
}
