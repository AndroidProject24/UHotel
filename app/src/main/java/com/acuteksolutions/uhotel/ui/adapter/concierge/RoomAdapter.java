package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.interfaces.SaveDataRoomListener;
import com.acuteksolutions.uhotel.interfaces.ViewpagerListener;
import com.acuteksolutions.uhotel.mvp.model.conciege.Room;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.adw.library.widgets.discreteseekbar.DiscreteSeekBar;

import java.util.List;

public class RoomAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {
    private SaveDataRoomListener saveDataRoomListener;
    private ViewpagerListener viewpagerListener;
    private int posExpand=0;
    private boolean isExpand=false;
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
                TextView total=(TextView)holder.getView(R.id.txt_total);
                total.setText(roomExpand.getTotal() > 0 ? String.format(" (%d)", roomExpand.getTotal()) : "0");
                total.setTextColor(roomExpand.isExpanded() ? mContext.getResources().getColor(R.color.tab_select) : mContext.getResources().getColor(R.color.white));
                holder.setText(R.id.txt_name_expandable, roomExpand.getTitle())
                        .setTextColor(R.id.txt_name_expandable,roomExpand.isExpanded() ? mContext.getResources().getColor(R.color.tab_select) : mContext.getResources().getColor(R.color.white))
                        .setImageResource(R.id.img_arrow, roomExpand.isExpanded() ? R.drawable.room_arrow_up : R.drawable.room_arrow_down);
                if(roomExpand.getTotal()==0)
                    total.setVisibility(View.GONE);
                holder.itemView.setOnClickListener(v -> {
                    if(isExpand && posExpand != holder.getAdapterPosition()){
                        collapse(posExpand);
                        saveDataRoomListener.refreshList();
                    }
                    isExpand = posExpand != holder.getAdapterPosition();
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
                TextView mProgress=holder.getView(R.id.txt_progress);
                mProgress.setText(String.valueOf(room.getValue()));
                holder.setText(R.id.txt_name, room.getName());
                DiscreteSeekBar discreteSeekBar=holder.getView(R.id.seekBar);
                discreteSeekBar.setProgress(room.getValue());
                discreteSeekBar.setOnProgressChangeListener(new DiscreteSeekBar.OnProgressChangeListener() {
                    @Override
                    public void onProgressChanged(DiscreteSeekBar discreteSeekBar, int progress, boolean b) {
                        saveDataRoomListener.saveData(posExpand,room.getPosition(),progress);
                        mProgress.setText(String.valueOf(progress));
                    }

                    @Override
                    public void onStartTrackingTouch(DiscreteSeekBar discreteSeekBar) {
                        viewpagerListener.disableSwipe(false);
                    }

                    @Override
                    public void onStopTrackingTouch(DiscreteSeekBar discreteSeekBar) {
                        viewpagerListener.disableSwipe(true);
                    }
                });
                break;
        }
    }
}
