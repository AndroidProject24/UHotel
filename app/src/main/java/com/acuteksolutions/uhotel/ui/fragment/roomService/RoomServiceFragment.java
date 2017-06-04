package com.acuteksolutions.uhotel.ui.fragment.roomService;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.swagpoints.SwagPoints;
import com.acuteksolutions.uhotel.mvp.view.RoomServiceView;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.Random;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.OnClick;

public class RoomServiceFragment extends BaseFragment implements RoomServiceView {
    @BindView(R.id.room_control_preset_home)
    Button roomControlPresetHome;
    @BindView(R.id.room_control_preset_away)
    Button roomControlPresetAway;
    @BindView(R.id.room_control_preset_read)
    Button roomControlPresetRead;
    @BindView(R.id.room_control_preset_sleep)
    Button roomControlPresetSleep;
    @BindView(R.id.room_control_temp_name)
    TextView roomControlTempName;
    @BindView(R.id.room_control_temp_btnUp)
    ImageView roomControlTempBtnUp;
    @BindView(R.id.room_control_temp_btnDown)
    ImageView roomControlTempBtnDown;
    @BindView(R.id.room_control_temp_temptxt)
    TextView roomControlTempTemptxt;
    @BindView(R.id.room_control_temp_seek)
    SwagPoints roomControlTempSeek;
    @BindView(R.id.seekBar_main)
    DiscreteSeekBar seekBarMain;
    @BindView(R.id.seekBar_sheers)
    DiscreteSeekBar seekBarSheers;
    @BindView(R.id.main_status_off)
    TextView mainStatusOff;
    @BindView(R.id.main_status_on)
    TextView mainStatusOn;
    @BindView(R.id.sheers_status_off)
    TextView sheersStatusOff;
    @BindView(R.id.sheers_status_on)
    TextView sheersStatusOn;
    @BindView(R.id.seekBar_overhead)
    DiscreteSeekBar seekBarOverhead;
    @BindView(R.id.seekBar_blackouts)
    DiscreteSeekBar seekBarBlackouts;
    @BindView(R.id.overhead_status_off)
    TextView overheadStatusOff;
    @BindView(R.id.overhead_status_on)
    TextView overheadStatusOn;
    @BindView(R.id.blackouts_status_off)
    TextView blackoutsStatusOff;
    @BindView(R.id.blackouts_status_on)
    TextView blackoutsStatusOn;
    @BindView(R.id.seekBar_wall)
    DiscreteSeekBar seekBarWall;
    @BindView(R.id.seekBar_slider)
    DiscreteSeekBar seekBarSlider;
    @BindView(R.id.wall_status_off)
    TextView wallStatusOff;
    @BindView(R.id.wall_status_on)
    TextView wallStatusOn;
    @BindView(R.id.slider_status_off)
    TextView sliderStatusOff;
    @BindView(R.id.slider_status_on)
    TextView sliderStatusOn;
    @BindColor(R.color.status_item_on)
    int colorOn;
    @BindColor(R.color.status_item_off)
    int colorOff;
    private Context mContext;
    private int progressValue = 0;

    public static RoomServiceFragment newInstance() {
        return new RoomServiceFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void injectDependencies() {

    }

    @Override
    protected void initViews() {
        seekBarMain.setOnProgressChangeListener(progressChanged(mainStatusOff, mainStatusOn));
        seekBarSlider.setOnProgressChangeListener(progressChanged(sliderStatusOff, sliderStatusOn));
        seekBarSheers.setOnProgressChangeListener(progressChanged(sheersStatusOff, sheersStatusOn));
        seekBarWall.setOnProgressChangeListener(progressChanged(wallStatusOff, wallStatusOn));
        seekBarOverhead.setOnProgressChangeListener(progressChanged(overheadStatusOff, overheadStatusOn));
        seekBarBlackouts.setOnProgressChangeListener(progressChanged(blackoutsStatusOff, blackoutsStatusOn));
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.room_control_fragment;
    }

    @Override
    protected void initData() {
        getAllSeekRandomValue();
        setPresentFocus(roomControlPresetHome.getId());
    }

    private DiscreteSeekBar.OnProgressChangeListener progressChanged(TextView txtViewLeft, TextView txtViewRight) {
        return new DiscreteSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int progress, boolean b) {
                setColorOnOff(txtViewLeft, txtViewRight, progress);
            }

            @Override
            public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
                viewpagerListener.disableSwipe(false);
            }

            @Override
            public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
                viewpagerListener.disableSwipe(true);
            }
        };
    }

    @OnClick({R.id.room_control_preset_home, R.id.room_control_preset_away, R.id.room_control_preset_read, R.id.room_control_preset_sleep})
    void randomPreset(View view) {
        getAllSeekRandomValue();
        resetBackground();
        setPresentFocus(view.getId());
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
        int min = (int) seekBarMain.getMin();
        int max = (int) seekBarMain.getMax();
        progressValue = getRandomMinMax(20, 75);
        roomControlTempSeek.setPoints(progressValue);
        roomControlTempTemptxt.setText(progressValue - 7 + "°");
        seekBarMain.setProgress(getRandomMinMax(min, max));
        seekBarOverhead.setProgress(getRandomMinMax(min, max));
        seekBarWall.setProgress(getRandomMinMax(min, max));
        seekBarSheers.setProgress(getRandomMinMax(min, max));
        seekBarSheers.setProgress(getRandomMinMax(min, max));
        seekBarSlider.setProgress(getRandomMinMax(min, max));
    }

    private void setColorOnOff(TextView txtViewLeft, TextView txtViewRight, float progress) {
        if (progress == 0) {
            txtViewLeft.setTextColor(colorOn);
            txtViewRight.setTextColor(colorOff);
        } else {
            txtViewLeft.setTextColor(colorOff);
            txtViewRight.setTextColor(colorOn);
        }
    }

    private void resetBackground(){
        roomControlPresetHome.setBackgroundColor(getResources().getColor(R.color.room_bg_tab));
        roomControlPresetHome.setTextColor(getResources().getColor(R.color.white));
        roomControlPresetAway.setBackgroundColor(getResources().getColor(R.color.room_bg_tab));
        roomControlPresetAway.setTextColor(getResources().getColor(R.color.white));
        roomControlPresetRead.setBackgroundColor(getResources().getColor(R.color.room_bg_tab));
        roomControlPresetRead.setTextColor(getResources().getColor(R.color.white));
        roomControlPresetSleep.setBackgroundColor(getResources().getColor(R.color.room_bg_tab));
        roomControlPresetSleep.setTextColor(getResources().getColor(R.color.white));
    }
    private void setPresentFocus(int viewID) {
        switch (viewID){
            case R.id.room_control_preset_home:
                roomControlPresetHome.setBackgroundColor(getResources().getColor(R.color.room_bg_tab_focus));
                roomControlPresetHome.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.room_control_preset_away:
                roomControlPresetAway.setBackgroundColor(getResources().getColor(R.color.room_bg_tab_focus));
                roomControlPresetAway.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.room_control_preset_read:
                roomControlPresetRead.setBackgroundColor(getResources().getColor(R.color.room_bg_tab_focus));
                roomControlPresetRead.setTextColor(getResources().getColor(R.color.black));
                break;
            case R.id.room_control_preset_sleep:
                roomControlPresetSleep.setBackgroundColor(getResources().getColor(R.color.room_bg_tab_focus));
                roomControlPresetSleep.setTextColor(getResources().getColor(R.color.black));
                break;
        }

    }

}

