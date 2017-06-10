package com.acuteksolutions.uhotel.ui.adapter.livetv;

import android.widget.ImageView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.livetv.TVInfo;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class LiveTVAdapter extends BaseQuickAdapter<TVInfo, BaseViewHolder> {
  private RequestManager glide;
    public LiveTVAdapter(RequestManager glide, List<TVInfo> datas) {
        super(R.layout.featured_item,datas);
        this.glide=glide;
    }

    @Override
    protected void convert(BaseViewHolder helper, TVInfo data) {
      try {
        helper.setText(R.id.txtChannelName, data.getTitle())
                .setText(R.id.txtDesc,data.getChannelName())
                .addOnClickListener(R.id.root);
        if(data.getPictureLink()!=null)
          ImageUtils.loadImage(glide,data.getPictureLink(),(ImageView) helper.getView(R.id.imageView));
      }catch (Exception e){
        e.printStackTrace();
      }
    }
}