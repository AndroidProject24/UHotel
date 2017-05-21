package com.acuteksolutions.uhotel.mvp.model.conciege;

import com.acuteksolutions.uhotel.ui.adapter.concierge.RoomAdapter;
import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class RoomExpand extends AbstractExpandableItem<Room> implements MultiItemEntity {
  private String title;
  private int total;

  public RoomExpand( String title, int total) {
    this.total = total;
    this.title = title;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getTotal() {
    return total;
  }

  public void setTotal(int total) {
    this.total = total;
  }

  @Override
  public int getItemType() {
    return RoomAdapter.TYPE_EXPANDABLE;
  }

  @Override
  public int getLevel() {
    return 0;
  }
}
