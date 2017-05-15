package com.acuteksolutions.uhotel.ui.fragment.roomService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
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
import com.acuteksolutions.uhotel.mvp.view.RoomServiceView;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import java.util.Random;

public class RoomServiceFragment extends BaseFragment implements RoomServiceView {
  @BindView(R.id.room_control_preset_home) Button roomControlPresetHome;
  @BindView(R.id.room_control_preset_away) Button roomControlPresetAway;
  @BindView(R.id.room_control_preset_read) Button roomControlPresetRead;
  @BindView(R.id.room_control_preset_sleep) Button roomControlPresetSleep;
  @BindView(R.id.room_control_temp_name) TextView roomControlTempName;
  @BindView(R.id.room_control_temp_btnUp) ImageView roomControlTempBtnUp;
  @BindView(R.id.room_control_temp_btnDown) ImageView roomControlTempBtnDown;
  @BindView(R.id.room_control_temp_temptxt) TextView roomControlTempTemptxt;
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

  @Override protected void injectDependencies() {

  }

  @Override protected void initViews() {
    seekBarMain.setOnProgressChangedListener(progressChanged(mainStatusOff,mainStatusOn));
    seekBarSlider.setOnProgressChangedListener(progressChanged(sliderStatusOff,sliderStatusOn));
    seekBarSheers.setOnProgressChangedListener(progressChanged(sheersStatusOff,sheersStatusOn));
    seekBarWall.setOnProgressChangedListener(progressChanged(wallStatusOff,wallStatusOn));
    seekBarOverhead.setOnProgressChangedListener(progressChanged(overheadStatusOff,overheadStatusOn));
    seekBarBlackouts.setOnProgressChangedListener(progressChanged(blackoutsStatusOff,blackoutsStatusOn));
  }

  @Override protected int setLayoutResourceID() {
    return R.layout.room_control_fragment;
  }

  @Override protected void initData() {
    getAllSeekRandomValue();
    roomControlPresetHome.requestFocus();
    roomControlPresetHome.requestFocusFromTouch();
  }

  private BubbleSeekBar.OnProgressChangedListener progressChanged(TextView txtViewLeft,TextView txtViewRight) {
    return new BubbleSeekBar.OnProgressChangedListener() {
      @Override public void onProgressChanged(int progress, float progressFloat) {
        Logger.e("onProgressChanged:"+progress);
        setColorOnOff(txtViewLeft,txtViewRight,progressFloat);
        viewpagerListener.disableSwipe(false);
      }

      @Override public void getProgressOnActionUp(int progress, float progressFloat) {

      }

      @Override public void getProgressOnFinally(int progress, float progressFloat) {
        viewpagerListener.disableSwipe(true);
        Logger.e("disableSwipe:true");
      }
    };
  }

  @OnClick({R.id.room_control_preset_home,R.id.room_control_preset_away,R.id.room_control_preset_read,R.id.room_control_preset_sleep})
  void randomPreset(View view) {
    getAllSeekRandomValue();
    view.requestFocusFromTouch();
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
    roomControlTempTemptxt.setText(progressValue-7+"°");
    seekBarMain.setProgress(getRandomMinMax(min, max));
    seekBarOverhead.setProgress(getRandomMinMax(min, max));
    seekBarWall.setProgress(getRandomMinMax(min, max));
    seekBarSheers.setProgress(getRandomMinMax(min, max));
    seekBarSheers.setProgress(getRandomMinMax(min, max));
    seekBarSlider.setProgress(getRandomMinMax(min, max));
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

