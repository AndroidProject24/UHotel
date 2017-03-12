package com.acuteksolutions.uhotel.ui.fragment;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.ui.activity.MainActivity;
import com.acuteksolutions.uhotel.ui.fragment.movies.MoviesFragment;
import com.acuteksolutions.uhotel.ui.fragment.parentalControl.ParentalControlFragment;
import com.acuteksolutions.uhotel.ui.fragment.roomService.RoomServiceFragment;

import javax.inject.Inject;

import butterknife.OnClick;

/**
 * Created by Toan.IT
 * Date: 11/06/2016
 */

public class MainFragment extends BaseFragment {
  @Inject
  PreferencesHelper mPreferencesHelper;
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
   /*@Nullable
   private String getName() {
       final StringBuilder builder = new StringBuilder();
       if ("M".equals(GlobalVariables.currentUser.getGender())) {
           builder.append("Mr.");
       } else {
           builder.append("Ms.");
       }
       builder.append(GlobalVariables.currentUser.getName());
       return builder.toString();
   }*/

}
