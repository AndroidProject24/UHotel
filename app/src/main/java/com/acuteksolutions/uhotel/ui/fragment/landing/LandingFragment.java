package com.acuteksolutions.uhotel.ui.fragment.landing;

import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.ui.activity.MainActivity;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.parentalControl.ParentalControlFragment;
import com.acuteksolutions.uhotel.ui.fragment.roomService.RoomServiceFragment;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.acuteksolutions.uhotel.utils.Utils;
import javax.inject.Inject;

import static com.acuteksolutions.uhotel.utils.ImageUtils.loadImage;

/**
 * Created by Toan.IT
 * Date: 11/06/2016
 */

public class LandingFragment extends BaseFragment {
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

  @Override protected void initData() {
    mTxtWelcome.setText(getResources().getString(R.string.home_welcome) + " " + getName());
    mTxtDateTime.setText(Utils.getTime());
    mTxtTemp.setText("70");
    mTxtMarqueeBottom.setText("");
    mTxtMarqueeBottom.setSelected(true);
  }

  @Override protected void initViews() {
    toolbarTitleListener.hideShowToolBar(true);
    ((MainActivity) getActivity()).getActivityComponent().inject(this);
    loadImage(getmContext(),R.drawable.home_background,imgLanding);
    loadImage(getmContext(),R.drawable.logo_uhotel,imgLogo);
    loadImage(getmContext(),R.drawable.home_message,imgMessage);
    loadImage(getmContext(),R.drawable.home_barluxe,imgBar);
    loadImage(getmContext(),R.drawable.home_sabrosa,imgSabrora);
    loadImage(getmContext(),R.drawable.home_movies,imgMovies);
    loadImage(getmContext(),R.drawable.home_parental_control,imgParentalControl);
    loadImage(getmContext(),R.drawable.home_room_service,imgRoomService);
  }

  @OnClick(R.id.layout_message) public void click_message() {
    replaceFagment(getFragmentManager(), R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.layout_bar) public void click_bar() {
    replaceFagment(getFragmentManager(), R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.layout_sabrora) public void click_sabrora() {
    replaceFagment(getFragmentManager(), R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.img_movies) public void click_movies() {
    replaceFagment(getFragmentManager(), R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.img_room_service) public void click_roomService() {
    replaceFagment(getFragmentManager(), R.id.fragment, RoomServiceFragment.newInstance());
  }

  @OnClick(R.id.img_parental_control) public void click_parentalControl() {
    replaceFagment(getFragmentManager(), R.id.fragment, ParentalControlFragment.newInstance());
  }

  @Nullable private String getName() {
    try {
      final StringBuilder builder = new StringBuilder();
      Login login = Preconditions.checkNotNull(mPreferencesHelper.getJsonLogin());
      if ("M".equals(login.getGender())) {
        builder.append("Mr.");
      } else {
        builder.append("Ms.");
      }
      builder.append(login.getName());
      return builder.toString();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return "Mr.";
  }

}
