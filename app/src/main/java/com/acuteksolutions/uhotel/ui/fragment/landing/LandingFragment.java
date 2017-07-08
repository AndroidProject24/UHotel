package com.acuteksolutions.uhotel.ui.fragment.landing;

import android.annotation.SuppressLint;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.mvp.presenter.LandingPresenter;
import com.acuteksolutions.uhotel.ui.activity.MainActivity;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.acuteksolutions.uhotel.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toan.IT
 * Date: 11/06/2016
 */

public class LandingFragment extends BaseFragment<LandingPresenter> {
  @Inject PreferencesHelper mPreferencesHelper;
  @BindView(R.id.txt_welcome) TextView mTxtWelcome;
  @BindView(R.id.txt_marquee_bottom) TextView mTxtMarqueeBottom;
  @BindView(R.id.txt_datetime) TextView mTxtDateTime;
  @BindView(R.id.txt_temp) TextView mTxtTemp;
  @BindView(R.id.img_landing) AppCompatImageView imgLanding;
  @BindView(R.id.img_logo) AppCompatImageView imgLogo;
  @BindView(R.id.img_message) AppCompatImageView imgMessage;
  @BindView(R.id.img_bar) AppCompatImageView imgBar;
  @BindView(R.id.img_sabrora) AppCompatImageView imgSabrora;
  @BindView(R.id.img_movies) AppCompatImageView imgMovies;
  @BindView(R.id.img_room_service) AppCompatImageView imgRoomService;
  @BindView(R.id.img_parental_control) AppCompatImageView imgParentalControl;

  public static LandingFragment newInstance() {
    return new LandingFragment();
  }

  @Override protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override protected int setLayoutResourceID() {
    return R.layout.landing_fragment;
  }

  @SuppressLint("SetTextI18n")
  @Override protected void initData() {
    mTxtWelcome.setText(getResources().getString(R.string.home_welcome) + " " + getName());
    mTxtDateTime.setText(Utils.getTime());
    mTxtTemp.setText("70");
    mTxtMarqueeBottom.setText("");
    mTxtMarqueeBottom.setSelected(true);
    //StatusBarUtil.setTranslucentForImageView(getActivity(),imgLanding);
  }

  @Override protected void injectDependencies() {
    ((MainActivity) getActivity()).getActivityComponent().inject(this);
  }

  @Override protected void initViews() {
    toolbarTitleListener.hideShowToolBar(false);
    ImageUtils.loadImage(glide,R.drawable.home_background,imgLanding);
    ImageUtils.loadImage(glide,R.drawable.ic_logo_uhotel,imgLogo);
    ImageUtils.loadImage(glide,R.drawable.home_message,imgMessage);
    ImageUtils.loadImage(glide,R.drawable.home_barluxe,imgBar);
    ImageUtils.loadImage(glide,R.drawable.home_sabrosa,imgSabrora);
    ImageUtils.loadImage(glide,R.drawable.home_movies,imgMovies);
    ImageUtils.loadImage(glide,R.drawable.home_parental_control,imgParentalControl);
    ImageUtils.loadImage(glide,R.drawable.home_room_service,imgRoomService);
  }

  @OnClick(R.id.layout_message) public void click_message() {
    toolbarTitleListener.showScreen(TabMainDef.TabMain.MOVIES);
  }

  @OnClick(R.id.layout_bar) public void click_bar() {
    toolbarTitleListener.showScreen(TabMainDef.TabMain.MOVIES);
  }

  @OnClick(R.id.layout_sabrora) public void click_sabrora() {
    toolbarTitleListener.showScreen(TabMainDef.TabMain.MOVIES);
  }

  @OnClick(R.id.img_movies) public void click_movies() {
    toolbarTitleListener.showScreen(TabMainDef.TabMain.MOVIES);
  }

  @OnClick(R.id.img_room_service) public void click_roomService() {
    toolbarTitleListener.showScreen(TabMainDef.TabMain.ROOMCONTROL);
  }

  @OnClick(R.id.img_parental_control) public void click_parentalControl() {
    toolbarTitleListener.showScreen(TabMainDef.TabMain.CONCIERGE);
  }

  private String getName() {
    try {
      final StringBuilder builder = new StringBuilder();
      if(mPreferencesHelper.getJsonLogin()!=null) {
        Login login = mPreferencesHelper.getJsonLogin();
        if ("M".equals(login.getGender())) {
          builder.append("Mr.");
        } else {
          builder.append("Ms.");
        }
        builder.append(login.getName());
        return builder.toString();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "Mr.";
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    try {
        toolbarTitleListener.hideShowToolBar(true);
    }catch (Exception e){
        e.printStackTrace();
    }
  }
}
