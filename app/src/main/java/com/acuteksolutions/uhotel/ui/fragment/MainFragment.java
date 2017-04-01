package com.acuteksolutions.uhotel.ui.fragment;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.mvp.model.login.Login;
import com.acuteksolutions.uhotel.ui.activity.MainActivity;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.parentalControl.ParentalControlFragment;
import com.acuteksolutions.uhotel.ui.fragment.roomService.RoomServiceFragment;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.acuteksolutions.uhotel.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toan.IT
 * Date: 11/06/2016
 */

public class MainFragment extends BaseFragment {
  @Inject
  PreferencesHelper mPreferencesHelper;
  @BindView(R.id.txt_welcome)
  TextView mTxtWelcome;
  @BindView(R.id.txt_marquee_bottom)
  TextView mTxtMarqueeBottom;
  @BindView(R.id.txt_datetime)
  TextView mTxtDateTime;
  @BindView(R.id.txt_temp)
  TextView mTxtTemp;
  public static MainFragment newInstance() {
    return new MainFragment();
  }
  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.main_fragment;
  }

  @Override
  protected void initData() {
    mTxtWelcome.setText(getResources().getString(R.string.home_welcome)+" "+ getName());
    mTxtDateTime.setText(Utils.getTime());
    mTxtTemp.setText("70");
    mTxtMarqueeBottom.setText("");
    mTxtMarqueeBottom.setSelected(true);
  }

  @Override
  protected void initViews() {
    ((MainActivity)getActivity()).getActivityComponent().inject(this);
  }

  @OnClick(R.id.layout_message)
  public void click_message(){
    replaceFagment(getFragmentManager(),R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.layout_bar)
  public void click_bar(){
    replaceFagment(getFragmentManager(),R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.layout_sabrora)
  public void click_sabrora(){
    replaceFagment(getFragmentManager(),R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.img_movies)
  public void click_movies(){
    replaceFagment(getFragmentManager(),R.id.fragment, MoviesFragment.newInstance());
  }

  @OnClick(R.id.img_room_service)
  public void click_roomService(){
    replaceFagment(getFragmentManager(),R.id.fragment, RoomServiceFragment.newInstance());
  }

  @OnClick(R.id.img_parental_control)
  public void click_parentalControl(){
    replaceFagment(getFragmentManager(),R.id.fragment, ParentalControlFragment.newInstance());
  }

  @Nullable
  private String getName() {
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
    }catch (Exception e){
      e.printStackTrace();
    }
    return "Mr.";
  }

}
