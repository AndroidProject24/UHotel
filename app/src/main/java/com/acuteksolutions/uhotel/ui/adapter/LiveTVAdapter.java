package com.acuteksolutions.uhotel.ui.adapter;

import android.widget.ImageView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class LiveTVAdapter extends BaseQuickAdapter<VODInfo, BaseViewHolder> {
  private RequestManager glide;
    public LiveTVAdapter(RequestManager glide,List<VODInfo> datas) {
        super(R.layout.livetv_item,datas);
        this.glide=glide;
    }

    @Override
    protected void convert(BaseViewHolder helper, VODInfo data) {
      try {
        helper.setText(R.id.txt_liveTV_name, data.getDetail().getTitle())
                .addOnClickListener(R.id.layout_item);
        ImageUtils.loadImage(glide,data.getDetail().getPoster(),(ImageView) helper.getView(R.id.img_liveTV));
      }catch (Exception e){
        e.printStackTrace();
      }
    }
}