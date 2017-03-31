package com.acuteksolutions.uhotel.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.interfaces.KeyListener;
import com.acuteksolutions.uhotel.interfaces.OnBackListener;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.ui.fragment.MainFragment;
import com.acuteksolutions.uhotel.ui.fragment.food.FoodFragment;
import com.acuteksolutions.uhotel.ui.fragment.liveTV.LiveTVFragment;
import com.acuteksolutions.uhotel.ui.fragment.login.LoginFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.roomService.RoomServiceFragment;
import com.acuteksolutions.uhotel.ui.fragment.setting.SettingFragment;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ToolbarTitleListener {
    private Drawer result = null;
    private boolean doubleBackToExitPressedOnce;
    @Inject
    PreferencesHelper mPreferencesHelper;
    @BindView(R.id.drawer_container)
    FrameLayout mLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbarTranparent();
        initDrawer(savedInstanceState);
    }

    private void initToolbarTranparent(){
      setSupportActionBar(mToolbar);
      mToolbar.setBackgroundColor(Color.TRANSPARENT);
      mToolbar.getBackground().setAlpha(100);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    private void initDrawer(Bundle savedInstanceState){
        result = new DrawerBuilder(this)
                .withActivity(this)
                .withHeader(R.layout.header)
                .withToolbar(mToolbar)
                .withHasStableIds(true)
               // .withItemAnimator(new AlphaCrossFadeAnimator())
                .addDrawerItems(
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.menu_concierge)).withIcon(R.drawable.menu_concierge).withIdentifier(1).withSetSelected(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.menu_live_tv)).withIcon(R.drawable.menu_livetv).withIdentifier(2).withSetSelected(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.menu_movies)).withIcon(R.drawable.menu_movies).withIdentifier(3).withSetSelected(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.menu_food_activity)).withIcon(R.drawable.menu_food_activities).withIdentifier(4).withSetSelected(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.menu_room_control)).withIcon(R.drawable.menu_room_control).withIdentifier(5).withSetSelected(true).withEnabled(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName(getString(R.string.menu_setting)).withIcon(R.drawable.menu_settings).withIdentifier(6).withSetSelected(true)
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem != null) {
                        if (mPreferencesHelper.getJsonLogin()!=null) {
                            if (drawerItem.getIdentifier() == 1) {
                                replaceFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance());
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
                                replaceFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance());
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
    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void initViews() {
        if(mPreferencesHelper.getJsonLogin()==null) {
            addFagment(getSupportFragmentManager(), R.id.fragment, LoginFragment.newInstance());
        }else{
            addFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance());
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

    @Override
    public void onBackPressed() {
        //handle the back press :D close the drawer first and if the drawer is closed close the activity
        if (result != null && result.isDrawerOpen()) {
            result.closeDrawer();
        } else {
            String currentTag = getSupportFragmentManager().findFragmentById(R.id.fragment).getTag();
            if (currentTag.equals(MainFragment.class.getName()) || currentTag.equals(LoginFragment.class.getName())) {
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
    public void hideToolBar(boolean isShow) {
        if(mToolbar != null) {
            if (isShow)
                getSupportActionBar().hide();
            else
                getSupportActionBar().show();
        }
    }
}
