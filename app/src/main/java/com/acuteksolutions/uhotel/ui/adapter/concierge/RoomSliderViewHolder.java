package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.view.View;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.bubbleseekbar.BubbleSeekBar;
import com.acuteksolutions.uhotel.libs.expandablerecyclerView.viewholders.ChildViewHolder;

public class RoomSliderViewHolder extends ChildViewHolder {

  private BubbleSeekBar seekBar;

  public RoomSliderViewHolder(View itemView) {
    super(itemView);
    seekBar = (BubbleSeekBar) itemView.findViewById(R.id.list_item_artist_name);
  }

  public void setProgress(int progress) {
    seekBar.setProgress(progress);
  }
}