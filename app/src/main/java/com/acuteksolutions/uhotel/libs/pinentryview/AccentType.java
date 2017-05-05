package com.acuteksolutions.uhotel.libs.pinentryview;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by huynhvantoan on 11/3/16.
 * Email huynhvantoan.itc@gmail.com
 */

@IntDef(flag = true, value = {PinEntryView.ACCENT_NONE, PinEntryView.ACCENT_ALL, PinEntryView.ACCENT_CHARACTER})
@Retention(RetentionPolicy.SOURCE)
public @interface AccentType {
}