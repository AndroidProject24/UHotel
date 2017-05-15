package com.acuteksolutions.uhotel.mvp.model.conciege;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Toan.IT on 5/14/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class ListRoom extends RealmObject {
  @Override public String toString() {
    return "ListRoom{" + "name='" + name + '\'' + ", detailList=" + detailList + '}';
  }
  @PrimaryKey
  private String name;
  private RealmList<Room> detailList;

  public ListRoom(){}

  public ListRoom(String name, RealmList<Room> detailList) {
    this.name = name;
    this.detailList = detailList;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public RealmList<Room> getDetailList() {
    return detailList;
  }

  public void setDetailList(RealmList<Room> detailList) {
    this.detailList = detailList;
  }

}
