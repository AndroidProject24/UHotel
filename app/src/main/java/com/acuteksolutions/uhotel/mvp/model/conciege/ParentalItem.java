package com.acuteksolutions.uhotel.mvp.model.conciege;

/**
 * Created by Toan.IT on 4/30/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class ParentalItem {
  private int resId;
  private String name;
  private boolean isLocked;

  public ParentalItem(boolean isActive, String name, int resId) {
    this.isLocked = isActive;
    this.name = name;
    this.resId = resId;
  }

  public int getResId() {
    return resId;
  }

  public String getName() {
    return name;
  }

  public void setLocked(boolean locked) {
    isLocked = locked;
  }

  public boolean isLocked() {
    return isLocked;
  }

}
