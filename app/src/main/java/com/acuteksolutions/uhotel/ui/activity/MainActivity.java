package com.acuteksolutions.uhotel.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.interfaces.KeyListener;
import com.acuteksolutions.uhotel.interfaces.OnBackListener;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.ui.fragment.MainFragment;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import javax.inject.Inject;

import butterknife.BindView;

import static com.acuteksolutions.uhotel.R.id.toolbar;

public class MainActivity extends BaseActivity implements ToolbarTitleListener {
    @BindView(toolbar)
    Toolbar mToolbar;
    private AccountHeader headerResult = null;
    private Drawer result = null;
    private boolean doubleBackToExitPressedOnce;
    @Inject
    PreferencesHelper mPreferencesHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(mToolbar);
        initDrawer(savedInstanceState);
    }
    private void initDrawer(Bundle savedInstanceState){
        //final IProfile profile = new ProfileDrawerItem().withName(mPreferencesHelper.getFistName()+" "+mPreferencesHelper.getLastName()).withEmail(mPreferencesHelper.getEmail()).withIcon(mPreferencesHelper.getAvatar()).withIdentifier(100);
        final IProfile profile = new ProfileDrawerItem().withName("Huỳnh văn toàn").withEmail("huynhvantoan.itc@gmail.com").withIcon("https://scontent.fsgn5-2.fna.fbcdn.net/v/t31.0-8/11722301_794373917343882_7256246783339720174_o.jpg?oh=f93a997e513c128aa626de61c205d91c&oe=593ED314").withIdentifier(100);
        headerResult = new AccountHeaderBuilder()
                .withActivity(this)
                .withTranslucentStatusBar(true)
                .withHeaderBackground(R.drawable.material_bg_account)
                .addProfiles(profile)
                .withSavedInstance(savedInstanceState)
                .build();
        result = new DrawerBuilder(this)
                .withActivity(this)
                .withToolbar(mToolbar)
                .withHasStableIds(true)
                .withAccountHeader(headerResult)
                .withRootView(R.id.drawer_container)
                .withDisplayBelowStatusBar(false)
                .withActionBarDrawerToggleAnimated(true)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Home").withIcon(R.mipmap.ic_profile).withIdentifier(1).withSetSelected(true),
                        new PrimaryDrawerItem().withName("Kho số").withIcon(R.mipmap.ic_sim).withIdentifier(2).withSetSelected(true),
                        new PrimaryDrawerItem().withName("khuyến mãi").withIcon(R.mipmap.ic_khuyenmai2).withIdentifier(3).withSetSelected(true),
                        new PrimaryDrawerItem().withName("Công nợ").withIcon(R.mipmap.ic_congno).withIdentifier(4).withSetSelected(true),
                        new PrimaryDrawerItem().withName("Thủ tục").withIcon(R.mipmap.ic_thutuc2).withIdentifier(5).withSetSelected(true).withEnabled(true),
                        new PrimaryDrawerItem().withName("Úp ảnh").withIcon(R.mipmap.ic_upload).withIdentifier(6).withSetSelected(true),
                        new PrimaryDrawerItem().withName("Profile").withIcon(R.mipmap.ic_profile).withIdentifier(7).withSetSelected(true),
                        new SectionDrawerItem().withName("Help"),
                        new PrimaryDrawerItem().withName("Contact").withIcon(R.mipmap.ic_call).withIdentifier(8).withSetSelected(true)
                )
                .withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    if (drawerItem != null) {
                        if (drawerItem.getIdentifier() == 1) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance());
                        } /*else if (drawerItem.getIdentifier() == 2) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, UIKhosoFragment.newInstance());
                        } else if (drawerItem.getIdentifier() == 3) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, KhuyenmaiFragment.newInstance());
                        } else if (drawerItem.getIdentifier() == 4) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, CongnoFragment.newInstance());
                        } else if (drawerItem.getIdentifier() == 5) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, LandingFragment.newInstance());
                        }else if (drawerItem.getIdentifier() == 6) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, UpanhFragment.newInstance());
                        }else if (drawerItem.getIdentifier() == 7) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, UpanhFragment.newInstance());
                        }else if (drawerItem.getIdentifier() == 8) {
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, LienHeFragment.newInstance());
                        }*/else{
                            replaceFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance());//TODO: LoginFragment
                            //Snackbar.make(mToolbar, "Please login!", Snackbar.LENGTH_LONG).show();
                        }
                       /* if (!mPreferencesHelper.getUserId().equalsIgnoreCase("")) {
                            if (drawerItem.getIdentifier() == 1) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance());
                            } else if (drawerItem.getIdentifier() == 2) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, KhosoFragment.newInstance());
                            } else if (drawerItem.getIdentifier() == 3) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, KhuyenmaiFragment.newInstance());
                            } else if (drawerItem.getIdentifier() == 4) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, CongnoFragment.newInstance());
                            } else if (drawerItem.getIdentifier() == 5) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, LandingFragment.newInstance());
                            }else if (drawerItem.getIdentifier() == 6) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, UpanhFragment.newInstance());
                            }else if (drawerItem.getIdentifier() == 7) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, UpanhFragment.newInstance());
                            }else if (drawerItem.getIdentifier() == 8) {
                                addFagment(getSupportFragmentManager(), R.id.fragment, LienHeFragment.newInstance());
                            }
                        }else{
                            addFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance());//TODO: LoginFragment
                            //Snackbar.make(mToolbar, "Please login!", Snackbar.LENGTH_LONG).show();
                        }*/
                    }
                    return false;
                })
                .withSavedInstance(savedInstanceState)
                .build();
    }
    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void initViews() {
        if(mPreferencesHelper.getUserId().equalsIgnoreCase("")) {
            addFagment(getSupportFragmentManager(), R.id.fragment, MainFragment.newInstance()); //TODO: LoginFragment
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
        outState = headerResult.saveInstanceState(outState);
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
                Snackbar.make(mToolbar,R.string.msg_exit,Snackbar.LENGTH_SHORT).show();
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
    public void hideToolBar(boolean isHide) {
        if(mToolbar != null) {
            if (isHide)
                getSupportActionBar().hide();
            else
                getSupportActionBar().show();
        }
    }
}
