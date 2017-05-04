package com.acuteksolutions.uhotel.mvp.model.conciege;

import com.acuteksolutions.uhotel.libs.expandablerecyclerView.models.ExpandableGroup;
import java.util.List;

public class RoomExpand extends ExpandableGroup<RoomItem> {

  private int iconResId;

  public RoomExpand(String title, List<RoomItem> items, int iconResId) {
    super(title, items);
    this.iconResId = iconResId;
  }

  public int getIconResId() {
    return iconResId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof RoomExpand)) return false;

    RoomExpand roomExpand = (RoomExpand) o;

    return getIconResId() == roomExpand.getIconResId();

  }

  @Override
  public int hashCode() {
    return getIconResId();
  }
}

