package com.acuteksolutions.uhotel.ui.fragment.roomService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.bubbleseekbar.BubbleSeekBar;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.libs.swagpoints.SwagPoints;
import com.acuteksolutions.uhotel.mvp.view.RomServiceView;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import java.util.Random;

public class RoomServiceFragment extends BaseFragment implements RomServiceView, BubbleSeekBar.OnProgressChangedListener{
  @BindView(R.id.room_control_preset_home) Button roomControlPresetHome;
  @BindView(R.id.room_control_preset_away) Button roomControlPresetAway;
  @BindView(R.id.room_control_preset_read) Button roomControlPresetRead;
  @BindView(R.id.room_control_preset_sleep) Button roomControlPresetSleep;
  @BindView(R.id.room_control_temp_name) TextView roomControlTempName;
  @BindView(R.id.room_control_temp_btnUp) ImageView roomControlTempBtnUp;
  @BindView(R.id.room_control_temp_btnDown) ImageView roomControlTempBtnDown;
  @BindView(R.id.room_control_temp_temptxt) TextView roomControlTempTemptxt;
  @BindView(R.id.room_control_temp_temptxt1) TextView roomControlTempTemptxt1;
  @BindView(R.id.room_control_temp_seek) SwagPoints roomControlTempSeek;
  @BindView(R.id.seekBar_main) BubbleSeekBar seekBarMain;
  @BindView(R.id.seekBar_sheers) BubbleSeekBar seekBarSheers;
  @BindView(R.id.main_status_off) TextView mainStatusOff;
  @BindView(R.id.main_status_on) TextView mainStatusOn;
  @BindView(R.id.sheers_status_off) TextView sheersStatusOff;
  @BindView(R.id.sheers_status_on) TextView sheersStatusOn;
  @BindView(R.id.seekBar_overhead) BubbleSeekBar seekBarOverhead;
  @BindView(R.id.seekBar_blackouts) BubbleSeekBar seekBarBlackouts;
  @BindView(R.id.overhead_status_off) TextView overheadStatusOff;
  @BindView(R.id.overhead_status_on) TextView overheadStatusOn;
  @BindView(R.id.blackouts_status_off) TextView blackoutsStatusOff;
  @BindView(R.id.blackouts_status_on) TextView blackoutsStatusOn;
  @BindView(R.id.seekBar_wall) BubbleSeekBar seekBarWall;
  @BindView(R.id.seekBar_slider) BubbleSeekBar seekBarSlider;
  @BindView(R.id.wall_status_off) TextView wallStatusOff;
  @BindView(R.id.wall_status_on) TextView wallStatusOn;
  @BindView(R.id.slider_status_off) TextView sliderStatusOff;
  @BindView(R.id.slider_status_on) TextView sliderStatusOn;
  @BindColor(R.color.status_item_on) int colorOn;
  @BindColor(R.color.status_item_off) int colorOff;
  /* @Inject
    MoviesPresenter
    mPresenter;*/
  private Context mContext;
  private int progressValue=0;
  public static RoomServiceFragment newInstance() {
    return new RoomServiceFragment();
  }

  @Override public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override protected void initViews() {
    seekBarMain.setOnProgressChangedListener(this);
    seekBarSlider.setOnProgressChangedListener(this);
    seekBarSheers.setOnProgressChangedListener(this);
    seekBarWall.setOnProgressChangedListener(this);
    seekBarOverhead.setOnProgressChangedListener(this);
    seekBarBlackouts.setOnProgressChangedListener(this);
  }

  @Override protected int setLayoutResourceID() {
    return R.layout.room_control_fragment;
  }

  @Override protected void initData() {
    //mPresenter.getData(TheloaiDef.HOA_MANG_TRA_TRUOC);
  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    //mPresenter.detachView();
  }

  @OnClick({R.id.room_control_preset_home,R.id.room_control_preset_away,R.id.room_control_preset_read,R.id.room_control_preset_sleep})
  void randomPreset() {
    getAllSeekRandomValue();
  }

  @OnClick(R.id.room_control_temp_btnUp)
  void clickTempUp() {
    if (progressValue < 100) {
      progressValue = progressValue + 1;
      roomControlTempSeek.setPoints(progressValue);
    }
  }

  @OnClick(R.id.room_control_temp_btnDown)
  void clickTempDown() {
    if (progressValue > 0) {
      progressValue = progressValue - 1;
      roomControlTempSeek.setPoints(progressValue);
    }
  }

  public int getRandomMinMax(int min, int max) {
    Random r = new Random();
    return (r.nextInt(max - min + 1) + min);
  }

  @SuppressLint("SetTextI18n")
  public void getAllSeekRandomValue() {
    int min = (int)seekBarMain.getMin();
    int max = (int)seekBarMain.getMax();
    progressValue = getRandomMinMax(20, 75);
    roomControlTempSeek.setPoints(progressValue);
    roomControlTempTemptxt.setText(progressValue + "°");
    seekBarMain.setProgress(getRandomMinMax(min, max));
    seekBarOverhead.setProgress(getRandomMinMax(min, max));
    seekBarWall.setProgress(getRandomMinMax(min, max));
    seekBarSheers.setProgress(getRandomMinMax(min, max));
    seekBarSheers.setProgress(getRandomMinMax(min, max));
    seekBarSlider.setProgress(getRandomMinMax(min, max));
  }

  @Override public void onProgressChanged(int viewID, int progress, float progressFloat) {
    switch (viewID){
      case R.id.seekBar_main:
        Logger.e("seekBar_main:"+progressFloat);
        setColorOnOff(mainStatusOff,mainStatusOn,progressFloat);
        break;
      case R.id.seekBar_blackouts:
        Logger.e("seekBar_blackouts:"+progressFloat);
        setColorOnOff(blackoutsStatusOff,blackoutsStatusOn,progressFloat);
        break;
      case R.id.seekBar_overhead:
        Logger.e("seekBar_overhead:"+progressFloat);
        setColorOnOff(overheadStatusOff,overheadStatusOn,progressFloat);
        break;
      case R.id.seekBar_sheers:
        Logger.e("seekBar_sheers:"+progressFloat);
        setColorOnOff(sheersStatusOff,sheersStatusOn,progressFloat);
        break;
      case R.id.seekBar_slider:
        Logger.e("seekBar_slider:"+progressFloat);
        setColorOnOff(sliderStatusOff,sliderStatusOn,progressFloat);
        break;
      case R.id.seekBar_wall:
        Logger.e("seekBar_wall:"+progressFloat);
        setColorOnOff(wallStatusOff,wallStatusOn,progressFloat);
        break;
    }
  }

  @Override public void getProgressOnActionUp(int progress, float progressFloat) {

  }

  @Override public void getProgressOnFinally(int progress, float progressFloat) {

  }

  private void setColorOnOff(TextView txtViewLeft,TextView txtViewRight,float progress){
    if (progress == 0) {
      txtViewLeft.setTextColor(colorOn);
      txtViewRight.setTextColor(colorOff);
    } else {
      txtViewLeft.setTextColor(colorOff);
      txtViewRight.setTextColor(colorOn);
    }
  }
}

