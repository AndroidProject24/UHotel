package com.acuteksolutions.uhotel.ui.adapter.livetv;

import android.widget.ImageView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class RightNowAdapter extends BaseQuickAdapter<TVInfo, BaseViewHolder> {
  private RequestManager glide;
  public RightNowAdapter(RequestManager glide, List<TVInfo> datas) {
    super(R.layout.right_now_item,datas);
    this.glide=glide;
  }

  @Override
  protected void convert(BaseViewHolder helper, TVInfo data) {
    try {
      helper.setText(R.id.txtChannelName, data.getChannelName())
              .setText(R.id.txtTitle,data.getTitle())
              .setText(R.id.txtDesc,data.getDescription())
              .addOnClickListener(R.id.layout_play_now)
              .addOnClickListener(R.id.layout_play_begin);
      if(data.getPictureLink()!=null)
        ImageUtils.loadImage(glide,data.getPictureLink(),(ImageView) helper.getView(R.id.imageView));
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}