package com.acuteksolutions.uhotel.libs;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.Scroller;
import java.lang.reflect.Field;

/**
 * Created by Toan.IT on 5/9/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class CustomSwipeableViewPager extends ViewPager {
  private boolean pagingEnabled = true;
  public CustomSwipeableViewPager(Context context) {
    super(context);
    setMyScroller();
  }

  public CustomSwipeableViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
    setMyScroller();
  }

  public void setPagingEnabled(boolean enabled) {
    pagingEnabled = enabled;
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent event) {
    // do not intercept
    return pagingEnabled && super.onInterceptTouchEvent(event);
  }

  @Override
  public boolean onTouchEvent(MotionEvent event) {
    // do not consume
    return pagingEnabled && super.onTouchEvent(event);
  }

  //down one is added for smooth scrolling

  private void setMyScroller() {
    try {
      Class<?> viewpager = ViewPager.class;
      Field scroller = viewpager.getDeclaredField("mScroller");
      scroller.setAccessible(true);
      scroller.set(this, new MyScroller(getContext()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public class MyScroller extends Scroller {
    public MyScroller(Context context) {
      super(context, new DecelerateInterpolator());
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
      super.startScroll(startX, startY, dx, dy, 350 /*1 secs*/);
    }
  }
}