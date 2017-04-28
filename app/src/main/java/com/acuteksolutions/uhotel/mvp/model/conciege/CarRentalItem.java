package com.acuteksolutions.uhotel.mvp.model.conciege;

/**
 * Created by Toan.IT on 4/28/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class CarRentalItem {
  private int url;
  private String name;
  private String address;

  public CarRentalItem(String address, String name) {
    this.address = address;
    this.name = name;
  }

  public CarRentalItem(String address, String name, int url) {
    this.address = address;
    this.name = name;
    this.url = url;
  }

  public int getUrl() {
    return url;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }
}
