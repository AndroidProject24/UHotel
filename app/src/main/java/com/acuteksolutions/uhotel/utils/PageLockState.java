package com.acuteksolutions.uhotel.utils;

import android.util.Log;
import org.json.JSONObject;

public class PageLockState {
    public static final String TAG = PageLockState.class.getName();
    private static PageLockState instance;
    private volatile JSONObject pageLock = new JSONObject();

    public static PageLockState getInstance() {
        if (null == instance) {
            synchronized (PageLockState.class) {
                if (null == instance)
                    instance = new PageLockState();
            }
        }
        return instance;
    }

    public JSONObject getPageLock() {
        return pageLock;
    }

    public void setPageLock(final JSONObject object) {
        this.pageLock = object;
    }

    public boolean getPageLockStatus(String pageName) {
        try {
            return Preconditions.checkNotNull(pageLock.getBoolean(pageName));
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
            return false;
        }
    }


}
