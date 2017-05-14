package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.bubbleseekbar.BubbleSeekBar;
import com.acuteksolutions.uhotel.libs.expandablerecyclerView.viewholders.ChildViewHolder;

public class RoomSliderViewHolder extends ChildViewHolder {

  private BubbleSeekBar seekBar;
  private AppCompatTextView appCompatTextView;
  public RoomSliderViewHolder(View itemView) {
    super(itemView);
    appCompatTextView=(AppCompatTextView) itemView.findViewById(R.id.txt_name);
    seekBar = (BubbleSeekBar) itemView.findViewById(R.id.seekBar);
  }

  public void setTitle(String title){
    appCompatTextView.setText(title);
  }
  public void setProgress(int progress) {
    seekBar.setProgress(progress);
  }
}