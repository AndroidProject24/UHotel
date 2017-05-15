package com.acuteksolutions.uhotel.mvp.model.conciege;

import android.os.Parcel;
import android.os.Parcelable;

public class RoomItem implements Parcelable {

  private String name;
  private float progress;

  public RoomItem(String name, float progress) {
    this.name = name;
    this.progress = progress;
  }

  protected RoomItem(Parcel in) {
    name = in.readString();
  }

  public String getName() {
    return name;
  }

  public float getProgress() {
    return progress;
  }

  @Override public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    RoomItem roomItem = (RoomItem) o;

    return Float.compare(roomItem.progress, progress) == 0 && (name != null ? name.equals(
        roomItem.name) : roomItem.name == null);
  }

  @Override public int hashCode() {
    int result = name != null ? name.hashCode() : 0;
    result = 31 * result + (progress != +0.0f ? Float.floatToIntBits(progress) : 0);
    return result;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(name);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<RoomItem> CREATOR = new Creator<RoomItem>() {
    @Override
    public RoomItem createFromParcel(Parcel in) {
      return new RoomItem(in);
    }

    @Override
    public RoomItem[] newArray(int size) {
      return new RoomItem[size];
    }
  };


  @Override public String toString() {
    return "RoomItem{" + "name='" + name + '\'' + ", progress=" + progress + '}';
  }

}

