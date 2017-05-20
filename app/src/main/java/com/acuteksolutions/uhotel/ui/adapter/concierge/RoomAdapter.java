package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.expandablerecyclerView.ExpandableRecyclerViewAdapter;
import com.acuteksolutions.uhotel.libs.expandablerecyclerView.models.ExpandableGroup;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomItem;
import java.util.List;

public class RoomAdapter extends ExpandableRecyclerViewAdapter<RoomExpandViewHolder,RoomSliderViewHolder> {

  public RoomAdapter(List<? extends ExpandableGroup> groups) {
    super(groups);
  }

  @Override public RoomExpandViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.room_list_item_expand, parent, false);
    return new RoomExpandViewHolder(view);
  }

  @Override public RoomSliderViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.room_list_item_slider, parent, false);
    return new RoomSliderViewHolder(view);
  }

  @Override public void onBindChildViewHolder(RoomSliderViewHolder holder, int flatPosition,
      ExpandableGroup group, int childIndex) {
    final RoomItem roomItem = ((RoomExpand) group).getItems().get(childIndex);
    holder.setProgress(roomItem.getProgress());
    holder.setTitle(roomItem.getName());
  }

  @Override public void onBindGroupViewHolder(RoomExpandViewHolder holder, int flatPosition,
      ExpandableGroup group) {
    holder.setGenreTitle(group);
  }

}
