package com.acuteksolutions.uhotel.ui.exoplayer;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.acuteksolutions.uhotel.BaseApplication;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by Toan.IT on 5/3/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class VideoPlayerActivity extends AppCompatActivity {
    private Timeline.Window window;
    private CustomExoPlayerView simpleExoPlayerView;
    private SimpleExoPlayer player;
    private Handler mainHandler;
    private boolean shouldAutoPlay;
    private DefaultTrackSelector trackSelector;
    private long playerPosition;
    private String title = "", channel = "", link = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        simpleExoPlayerView = (CustomExoPlayerView) findViewById(R.id.playerView);
        getData();
        shouldAutoPlay = true;
        mainHandler = new Handler();
        window = new Timeline.Window();
    }

    private void getData() {
        Bundle bundle = getIntent().getExtras().getBundle(BundleDef.BUNDLE);
        if (bundle != null) {
            link = Preconditions.checkNotNull(bundle.getString(BundleDef.BUNDLE_KEY));
            title = Preconditions.checkNotNull(bundle.getString(BundleDef.BUNDLE_MOVIES_TITLE));
            channel = Preconditions.checkNotNull(bundle.getString(BundleDef.BUNDLE_MOVIES_CHANNEL));
        }
    }

    private void initializePlayer() {
        simpleExoPlayerView.requestFocus();
        DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
        simpleExoPlayerView.setPlayer(player);
        player.setPlayWhenReady(shouldAutoPlay);
        DataSource.Factory dataSourceFactory = ((BaseApplication) getApplication()).buildHttpDataSourceFactory(bandwidthMeter);
        MediaSource videoSource = new HlsMediaSource(Uri.parse(link), dataSourceFactory, mainHandler, null);
        player.prepare(videoSource);
        simpleExoPlayerView.setMoviesTitle(title);
        simpleExoPlayerView.setMoviesChannel(channel);

        // if you are using different source like mp4 and custom subtitle
        /*
        extractorsFactory = new DefaultExtractorsFactory();
        MediaSource mediaSource = new ExtractorMediaSource(Uri.parse("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"),
                mediaDataSourceFactory, extractorsFactory, null, null);
        Uri uri = Uri.parse("http://www.storiesinflight.com/js_videosub/jellies.srt");
        Format textFormat = Format.createTextSampleFormat(null, MimeTypes.APPLICATION_SUBRIP,
                null, Format.NO_VALUE, Format.NO_VALUE, "en", null);
        MediaSource subtitleSource = new SingleSampleMediaSource(uri, mediaDataSourceFactory, textFormat, C.TIME_UNSET);
        MergingMediaSource mergedSource =
                new MergingMediaSource(mediaSource, subtitleSource);
         */
        // if you want play without subtitle
        /*
                MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);
        player.prepare(mediaSource);
         */
    }

    private void releasePlayer() {
        try {
            if (player != null) {
                shouldAutoPlay = player.getPlayWhenReady();
                int playerWindow = player.getCurrentWindowIndex();
                playerPosition = C.TIME_UNSET;
                Timeline timeline = player.getCurrentTimeline();
                if (timeline != null && timeline.getWindow(playerWindow, window).isSeekable) {
                    playerPosition = player.getCurrentPosition();
                }
                player.release();
                player = null;
                trackSelector = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }
}