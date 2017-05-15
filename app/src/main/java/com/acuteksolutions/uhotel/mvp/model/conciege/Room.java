package com.acuteksolutions.uhotel.mvp.model.conciege;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Toan.IT on 5/14/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class Room extends RealmObject {
  @PrimaryKey
  private String name;

  @Override public String toString() {
    return "Room{" + "name='" + name + '\'' + ", amount=" + amount + '}';
  }

  private int amount;

  public Room(){}

  public Room(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

}
