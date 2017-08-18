package com.acuteksolutions.uhotel.ui.exoplayer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.SystemClock;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.util.Util;

import java.util.Formatter;
import java.util.Locale;


/**
 * Created by Toan.IT on 5/3/17.
 * Email: huynhvantoan.itc@gmail.com
 */
public class CustomPlaybackControlView extends FrameLayout {

  private static final String TAG = "CustomVideoPlaybackCont";

  /**
   * Listener to be notified about changes of the visibility of the UI control.
   */
  public interface VisibilityListener {
    /**
     * Called when the visibility changes.
     *
     * @param visibility The new visibility. Either {@link View#VISIBLE} or {@link View#GONE}.
     */
    void onVisibilityChange(int visibility);
  }

  public static final int DEFAULT_FAST_FORWARD_MS = 15000;
  public static final int DEFAULT_REWIND_MS = 15000;
  public static final int DEFAULT_SHOW_TIMEOUT_MS = 5000;

  private static final int PROGRESS_BAR_MAX = 1000;
  private static final long MAX_POSITION_FOR_SEEK_TO_PREVIOUS = 3000;

  private final CustomPlaybackControlView.ComponentListener componentListener;
  private final ImageView playButton;
  private final TextView timeCurrent,timeDuration,txtTitle,txtChannel;
  public final SeekBar progressBar;
  private final StringBuilder formatBuilder;
  private final Formatter formatter;
  private final Timeline.Window currentWindow;

  private ExoPlayer player;
  private CustomPlaybackControlView.VisibilityListener visibilityListener;

  private boolean isAttachedToWindow;
  private boolean dragging;
  public int rewindMs;
  public int fastForwardMs;
  private int showTimeoutMs;
  private long hideAtMs;
  long curPosition;

  private final Runnable updateProgressAction = new Runnable() {
    @Override
    public void run() {
      updateProgress();
    }
  };

  private final Runnable hideAction = new Runnable() {
    @Override
    public void run() {
      hide();
    }
  };

  public CustomPlaybackControlView(@NonNull Context context) {
    this(context, null);
  }

  public CustomPlaybackControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public CustomPlaybackControlView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes
      int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    rewindMs = DEFAULT_REWIND_MS;
    fastForwardMs = DEFAULT_FAST_FORWARD_MS;
    showTimeoutMs = DEFAULT_SHOW_TIMEOUT_MS;
    if (attrs != null) {
      TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
          R.styleable.PlaybackControlView, 0, 0);
      try {
        rewindMs = a.getInt(R.styleable.PlaybackControlView_rewind_increment, rewindMs);
        fastForwardMs = a.getInt(R.styleable.PlaybackControlView_fastforward_increment,
            fastForwardMs);
        showTimeoutMs = a.getInt(R.styleable.PlaybackControlView_show_timeout, showTimeoutMs);
      } finally {
        a.recycle();
      }
    }
    currentWindow = new Timeline.Window();
    formatBuilder = new StringBuilder();
    formatter = new Formatter(formatBuilder, Locale.getDefault());
    componentListener = new CustomPlaybackControlView.ComponentListener();
    LayoutInflater.from(context).inflate(R.layout.exo_playback_control_view, this);
    timeCurrent = (TextView) findViewById(R.id.time_current);
    timeDuration = (TextView) findViewById(R.id.time_duration);
    txtTitle = (TextView) findViewById(R.id.txt_title);
    txtChannel = (TextView) findViewById(R.id.txt_channel);
    progressBar = (SeekBar) findViewById(R.id.seek_progress);
    progressBar.setOnSeekBarChangeListener(componentListener);
    progressBar.setMax(PROGRESS_BAR_MAX);
    playButton = (ImageView) findViewById(R.id.play);
    playButton.setOnClickListener(componentListener);
  }

  /**
   * Returns the player currently being controlled by this view, or null if no player is set.
   */
  public ExoPlayer getPlayer() {
    return player;
  }

  /**
   * Sets the {@link ExoPlayer} to control.
   *
   * @param player the {@code ExoPlayer} to control.
   */
  public void setPlayer(ExoPlayer player) {
    if (this.player == player) {
      return;
    }
    if (this.player != null) {
      this.player.removeListener(componentListener);
    }
    this.player = player;
    if (player != null) {
      player.addListener(componentListener);
    }
    updateAll();
  }

  /**
   * Sets the {@link CustomPlaybackControlView.VisibilityListener}.
   *
   * @param listener The listener to be notified about visibility changes.
   */
  public void setVisibilityListener(CustomPlaybackControlView.VisibilityListener listener) {
    this.visibilityListener = listener;
  }

  /**
   * Sets the rewind increment in milliseconds.
   *
   * @param rewindMs The rewind increment in milliseconds.
   */
  public void setRewindIncrementMs(int rewindMs) {
    this.rewindMs = rewindMs;
    updateNavigation();
  }

  /**
   * Sets the fast forward increment in milliseconds.
   *
   * @param fastForwardMs The fast forward increment in milliseconds.
   */
  public void setFastForwardIncrementMs(int fastForwardMs) {
    this.fastForwardMs = fastForwardMs;
    updateNavigation();
  }

  /**
   * Returns the playback controls timeout. The playback controls are automatically hidden after
   * this duration of time has elapsed without user input.
   *
   * @return The duration in milliseconds. A non-positive value indicates that the controls will
   * remain visible indefinitely.
   */
  public int getShowTimeoutMs() {
    return showTimeoutMs;
  }

  /**
   * Sets the playback controls timeout. The playback controls are automatically hidden after this
   * duration of time has elapsed without user input.
   *
   * @param showTimeoutMs The duration in milliseconds. A non-positive value will cause the controls
   *                      to remain visible indefinitely.
   */
  public void setShowTimeoutMs(int showTimeoutMs) {
    this.showTimeoutMs = showTimeoutMs;
  }

  /**
   * Shows the playback controls. If {@link #getShowTimeoutMs()} is positive then the controls will
   * be automatically hidden after this duration of time has elapsed without user input.
   */
  public void show() {
    if (!isVisible()) {
      setVisibility(VISIBLE);
      if (visibilityListener != null) {
        visibilityListener.onVisibilityChange(getVisibility());
      }
      updateAll();
    }
    // Call hideAfterTimeout even if already visible to reset the timeout.
    hideAfterTimeout();
  }

  /**
   * Hides the controller.
   */
  public void hide() {
    if (isVisible()) {
      setVisibility(GONE);
      if (visibilityListener != null) {
        visibilityListener.onVisibilityChange(getVisibility());
      }
      removeCallbacks(updateProgressAction);
      removeCallbacks(hideAction);
      hideAtMs = C.TIME_UNSET;
    }
  }

  /**
   * Returns whether the controller is currently visible.
   */
  public boolean isVisible() {
    return getVisibility() == VISIBLE;
  }

  private void hideAfterTimeout() {
    removeCallbacks(hideAction);
    if (showTimeoutMs > 0) {
      hideAtMs = SystemClock.uptimeMillis() + showTimeoutMs;
      if (isAttachedToWindow) {
        postDelayed(hideAction, showTimeoutMs);
      }
    } else {
      hideAtMs = C.TIME_UNSET;
    }
  }

  private void updateAll() {
    updatePlayPauseButton();
    updateNavigation();
    updateProgress();
  }

  private void updatePlayPauseButton() {
    if (!isVisible() || !isAttachedToWindow) {
      return;
    }
    boolean playing = player != null && player.getPlayWhenReady();
    String contentDescription = getResources().getString(
        playing ? com.google.android.exoplayer2.R.string.exo_controls_pause_description : com.google.android.exoplayer2.R.string.exo_controls_play_description);
    playButton.setContentDescription(contentDescription);
    playButton.setImageResource(
        playing ? R.drawable.ic_pause : R.drawable.ic_play);
  }

  private void updateNavigation() {
    //TODO:
        /*if (!isVisible() || !isAttachedToWindow) {
            return;
        }
        Timeline currentTimeline = player != null ? player.getCurrentTimeline() : null;
        boolean haveTimeline = currentTimeline != null;
        boolean isSeekable = false;
        boolean enablePrevious = false;
        boolean enableNext = false;
        if (haveTimeline) {
            int currentWindowIndex = player.getCurrentWindowIndex();
            currentTimeline.getWindow(currentWindowIndex, currentWindow);
            isSeekable = currentWindow.isSeekable;
            enablePrevious = currentWindowIndex > 0 || isSeekable || !currentWindow.isDynamic;
            enableNext = (currentWindowIndex < currentTimeline.getWindowCount() - 1)
                    || currentWindow.isDynamic;
        }
        progressBar.setEnabled(isSeekable);*/
  }

  private void updateProgress() {
    if (!isVisible() || !isAttachedToWindow) {
      return;
    }
    long duration = player == null ? 0 : player.getDuration();
    long position = player == null ? 0 : player.getCurrentPosition();
    timeDuration.setText(stringForTime(duration));
    if (!dragging) {
      timeCurrent.setText(stringForTime(position));
    }
    if (!dragging) {
      progressBar.setProgress(progressBarValue(position));
    }
    long bufferedPosition = player == null ? 0 : player.getBufferedPosition();
    progressBar.setSecondaryProgress(progressBarValue(bufferedPosition));
    // Remove scheduled updates.
    removeCallbacks(updateProgressAction);
    // Schedule an update if necessary.
    int playbackState = player == null ? ExoPlayer.STATE_IDLE : player.getPlaybackState();
    if (playbackState != ExoPlayer.STATE_IDLE && playbackState != ExoPlayer.STATE_ENDED) {
      long delayMs;
      if (player.getPlayWhenReady() && playbackState == ExoPlayer.STATE_READY) {
        delayMs = 1000 - (position % 1000);
        if (delayMs < 200) {
          delayMs += 1000;
        }
      } else {
        delayMs = 1000;
      }
      postDelayed(updateProgressAction, delayMs);
    }
  }

  private void setButtonEnabled(boolean enabled, View view) {
    view.setEnabled(enabled);
    if (Util.SDK_INT >= 11) {
      setViewAlphaV11(view, enabled ? 1f : 0.3f);
      view.setVisibility(VISIBLE);
    } else {
      view.setVisibility(enabled ? VISIBLE : INVISIBLE);
    }
  }

  @TargetApi(11)
  private void setViewAlphaV11(View view, float alpha) {
    view.setAlpha(alpha);
  }

  private String stringForTime(long timeMs) {
    if (timeMs == C.TIME_UNSET) {
      timeMs = 0;
    }
    long totalSeconds = (timeMs + 500) / 1000;
    long seconds = totalSeconds % 60;
    long minutes = (totalSeconds / 60) % 60;
    long hours = totalSeconds / 3600;
    formatBuilder.setLength(0);
    return hours > 0 ? formatter.format("%d:%02d:%02d", hours, minutes, seconds).toString()
        : formatter.format("%02d:%02d", minutes, seconds).toString();
  }

  private int progressBarValue(long position) {
    long duration = player == null ? C.TIME_UNSET : player.getDuration();
    return duration == C.TIME_UNSET || duration == 0 ? 0
        : (int) ((position * PROGRESS_BAR_MAX) / duration);
  }

  private long positionValue(int progress) {
    long duration = player == null ? C.TIME_UNSET : player.getDuration();
    return duration == C.TIME_UNSET ? 0 : ((duration * progress) / PROGRESS_BAR_MAX);
  }

  private void previous() {
    Timeline currentTimeline = player.getCurrentTimeline();
    if (currentTimeline == null) {
      return;
    }
    int currentWindowIndex = player.getCurrentWindowIndex();
    currentTimeline.getWindow(currentWindowIndex, currentWindow);
    if (currentWindowIndex > 0 && (player.getCurrentPosition() <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS
        || (currentWindow.isDynamic && !currentWindow.isSeekable))) {
      player.seekToDefaultPosition(currentWindowIndex - 1);
    } else {
      player.seekTo(0);
    }
  }

  public void next() {
    Timeline currentTimeline = player.getCurrentTimeline();
    if (currentTimeline == null) {
      return;
    }
    int currentWindowIndex = player.getCurrentWindowIndex();
    if (currentWindowIndex < currentTimeline.getWindowCount() - 1) {
      player.seekToDefaultPosition(currentWindowIndex + 1);
    } else if (currentTimeline.getWindow(currentWindowIndex, currentWindow, false).isDynamic) {
      player.seekToDefaultPosition();
    }
  }

  public void rewind() {
    if (rewindMs <= 0) {
      return;
    }
    player.seekTo(Math.max(player.getCurrentPosition() - rewindMs, 0));
  }

  public void fastForward() {
    if (fastForwardMs <= 0) {
      return;
    }
    player.seekTo(Math.min(player.getCurrentPosition() + fastForwardMs, player.getDuration()));
  }

  public void fastFoward(long position) {
    player.seekTo(Math.min(curPosition + position, player.getDuration()));
  }

  public void rewind(long position) {
    player.seekTo(Math.max(curPosition - position, 0));
  }

  @Override
  public void onAttachedToWindow() {
    super.onAttachedToWindow();
    isAttachedToWindow = true;
    if (hideAtMs != C.TIME_UNSET) {
      long delayMs = hideAtMs - SystemClock.uptimeMillis();
      if (delayMs <= 0) {
        hide();
      } else {
        postDelayed(hideAction, delayMs);
      }
    }
    updateAll();
  }

  @Override
  public void onDetachedFromWindow() {
    super.onDetachedFromWindow();
    isAttachedToWindow = false;
    removeCallbacks(updateProgressAction);
    removeCallbacks(hideAction);
  }



  /*  @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (player == null || event.getAction() != KeyEvent.ACTION_DOWN) {
            return super.dispatchKeyEvent(event);
        }
        switch (event.getKeyCode()) {
            case KeyEvent.KEYCODE_MEDIA_FAST_FORWARD:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                fastForward();
                break;
            case KeyEvent.KEYCODE_MEDIA_REWIND:
            case KeyEvent.KEYCODE_DPAD_LEFT:
                rewind();
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                player.setPlayWhenReady(!player.getPlayWhenReady());
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                player.setPlayWhenReady(true);
                break;
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                player.setPlayWhenReady(false);
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                next();
                break;
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                previous();
                break;
            default:
                return false;
        }
        show();
        return true;
    }*/

  private final class ComponentListener implements ExoPlayer.EventListener,
      SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
      removeCallbacks(hideAction);
      dragging = true;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
      if (fromUser) {
        timeCurrent.setText(stringForTime(positionValue(progress)));
      }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      dragging = false;
      player.seekTo(positionValue(seekBar.getProgress()));
      hideAfterTimeout();
    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
      updatePlayPauseButton();
      updateProgress();
    }

    @Override
    public void onPositionDiscontinuity() {
      updateNavigation();
      updateProgress();
    }

    @Override public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {
      updateNavigation();
      updateProgress();
    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
    }

    @Override
    public void onLoadingChanged(boolean isLoading) {
      // Do nothing.
    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {
      // Do nothing.
    }

    @Override
    public void onClick(View view) {
      Timeline currentTimeline = player.getCurrentTimeline();
      if (playButton == view) {
        player.setPlayWhenReady(!player.getPlayWhenReady());
      }
      hideAfterTimeout();
    }
  }

  //Custom

  public void setMoviesTitle(String title) {
    txtTitle.setText(title);
  }

  public void setMoviesChannel(String channel) {
    txtChannel.setText(channel);
  }
}
