package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.RotateAnimation;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.expandablerecyclerView.models.ExpandableGroup;
import com.acuteksolutions.uhotel.libs.expandablerecyclerView.viewholders.GroupViewHolder;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class RoomExpandViewHolder extends GroupViewHolder {

  private AppCompatTextView genreName;
  private AppCompatImageView arrow;

  public RoomExpandViewHolder(View itemView) {
    super(itemView);
    genreName = (AppCompatTextView) itemView.findViewById(R.id.list_item_genre_name);
    arrow = (AppCompatImageView) itemView.findViewById(R.id.list_item_genre_arrow);
  }

  public void setGenreTitle(ExpandableGroup genre) {
    if (genre instanceof RoomExpand) {
      genreName.setText(genre.getTitle());
    }
  }

  @Override
  public void expand() {
    animateExpand();
  }

  @Override
  public void collapse() {
    animateCollapse();
  }

  private void animateExpand() {
    RotateAnimation rotate =
        new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }

  private void animateCollapse() {
    RotateAnimation rotate =
        new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }
}
