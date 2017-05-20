package com.acuteksolutions.uhotel.mvp.model.conciege;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomItem implements Parcelable {

  private String name;

  private int progress;

  public RoomItem(String name, int progress) {
    this.name = name;
    this.progress = progress;
  }

  public String getName() {
    return name;
  }

  public int getProgress() {
    return progress;
  }


  @Override public String toString() {
    return "RoomItem{" + "name='" + name + '\'' + ", progress=" + progress + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoomItem)) return false;

    RoomItem roomItem = (RoomItem) o;

    if (progress != roomItem.progress) return false;
    return name != null ? name.equals(roomItem.name) : roomItem.name == null;
  }

  @Override
  public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + progress;
    return result;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.name);
    dest.writeInt(this.progress);
  }

  protected RoomItem(Parcel in) {
    this.name = in.readString();
    this.progress = in.readInt();
  }

  public static final Creator<RoomItem> CREATOR = new Creator<RoomItem>() {
    @Override
    public RoomItem createFromParcel(Parcel source) {
      return new RoomItem(source);
    }

    @Override
    public RoomItem[] newArray(int size) {
      return new RoomItem[size];
    }
  };
}

