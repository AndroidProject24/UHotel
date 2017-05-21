package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.annotation.SuppressLint;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.interfaces.SaveDataRoomListener;
import com.acuteksolutions.uhotel.interfaces.ViewpagerListener;
import com.acuteksolutions.uhotel.libs.bubbleseekbar.BubbleSeekBar;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.conciege.Room;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class RoomAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private SaveDataRoomListener saveDataRoomListener;
    private ViewpagerListener viewpagerListener;
    private int posExpand=0;
    public static final int TYPE_EXPANDABLE = 0;
    public static final int TYPE_ROOM = 1;
    public RoomAdapter(List<MultiItemEntity> data,SaveDataRoomListener saveDataRoomListener,ViewpagerListener viewpagerListener) {
        super(data);
        addItemType(TYPE_EXPANDABLE, R.layout.room_list_item_expand);
        addItemType(TYPE_ROOM, R.layout.room_list_item_slider);
        this.saveDataRoomListener=saveDataRoomListener;
        this.viewpagerListener=viewpagerListener;
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void convert(BaseViewHolder holder, MultiItemEntity item) {
        switch (holder.getItemViewType()) {
            case TYPE_EXPANDABLE:
                RoomExpand roomExpand = (RoomExpand)item;
                holder.setText(R.id.txt_name_expandable, roomExpand.getTitle())
                        .setText(R.id.txt_total, roomExpand.getTotal() > 0 ? String.format(" (%d)", roomExpand.getTotal()) : "0")
                        .setImageResource(R.id.img_arrow, roomExpand.isExpanded() ? R.drawable.room_arrow_up : R.drawable.room_arrow_down);
                holder.itemView.setOnClickListener(v -> {
                    posExpand = holder.getAdapterPosition();
                    if (roomExpand.isExpanded()) {
                        collapse(posExpand);
                        saveDataRoomListener.refreshList();
                    } else {
                        expand(posExpand);
                    }
                });
                break;
            case TYPE_ROOM:
                Room room = (Room)item;
                holder.setText(R.id.txt_name, room.getName())
                      .setText(R.id.txt_progress, String.valueOf(room.getValue()));
                BubbleSeekBar bubbleSeekBar=holder.getView(R.id.seekBar);
                bubbleSeekBar.setProgress(room.getValue());
                bubbleSeekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListenerAdapter() {
                    @Override
                    public void onProgressChanged(int progress, float progressFloat) {
                        viewpagerListener.disableSwipe(false);
                        Logger.e("posExpand="+posExpand+"getAdapterPosition="+room.getPosition());
                        saveDataRoomListener.saveData(posExpand,room.getPosition(),progress);
                        holder.setText(R.id.txt_progress,String.valueOf(progress));
                    }

                    @Override
                    public void getProgressOnFinally(int progress, float progressFloat) {
                        viewpagerListener.disableSwipe(true);
                    }
                });
                break;
        }
    }
}
