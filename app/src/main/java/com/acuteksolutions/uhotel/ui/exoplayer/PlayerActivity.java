package com.acuteksolutions.uhotel.ui.exoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.SeekBar;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.libs.preview.PreviewSeekBar;
import com.acuteksolutions.uhotel.libs.preview.PreviewSeekBarLayout;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

/**
 * Created by Toan.IT on 7/8/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class PlayerActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener{
    private ExoPlayerManager exoPlayerManager;
    private PreviewSeekBarLayout seekBarLayout;
    private PreviewSeekBar seekBar;
    private String title = "", channel = "", link = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestFullScreenIfLandscape();
        setContentView(R.layout.activity_player);
        SimpleExoPlayerView playerView = (SimpleExoPlayerView) findViewById(R.id.player_view);
        SimpleExoPlayerView previewPlayerView = (SimpleExoPlayerView) findViewById(R.id.previewPlayerView);
        seekBar = (PreviewSeekBar) playerView.findViewById(R.id.exo_progress);
        seekBarLayout = (PreviewSeekBarLayout) findViewById(R.id.previewSeekBarLayout);
        getData();
        //seekBarLayout.setTintColorResource(R.color.colorPrimary);

        seekBar.addOnSeekBarChangeListener(this);
        exoPlayerManager = new ExoPlayerManager(playerView, previewPlayerView, seekBarLayout);
        exoPlayerManager.play(Uri.parse(link));
        seekBarLayout.setup(exoPlayerManager);

        View view = previewPlayerView.getVideoSurfaceView();

        if (view instanceof SurfaceView) {
            SurfaceView surfaceView = (SurfaceView) view;
            surfaceView.setZOrderMediaOverlay(true);
            surfaceView.setZOrderOnTop(true);
            surfaceView.setVisibility(View.INVISIBLE);
        }
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras().getBundle(BundleDef.BUNDLE);
        if (bundle != null) {
            link = Preconditions.checkNotNull(bundle.getString(BundleDef.BUNDLE_KEY));
            title = Preconditions.checkNotNull(bundle.getString(BundleDef.BUNDLE_MOVIES_TITLE));
            channel = Preconditions.checkNotNull(bundle.getString(BundleDef.BUNDLE_MOVIES_CHANNEL));
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        exoPlayerManager.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        exoPlayerManager.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        exoPlayerManager.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        exoPlayerManager.onStop();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        exoPlayerManager.stopPreview();
    }


    private void requestFullScreenIfLandscape() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
}
