package com.acuteksolutions.uhotel.mvp.model.login;

/**
 * Created by Toan.IT on 4/28/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class HomeMenu {
  private int icon;
  private String name;

  public HomeMenu(int icon, String name) {
    this.icon = icon;
    this.name = name;
  }

  public int getIcon() {
    return icon;
  }

  public String getName() {
    return name;
  }

}
