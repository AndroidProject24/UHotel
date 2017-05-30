package com.acuteksolutions.uhotel.ui.adapter;

import android.widget.ImageView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.movies.VODInfo;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MoreMoviesAdapter extends BaseQuickAdapter<VODInfo, BaseViewHolder> {
  private RequestManager glide;
    public MoreMoviesAdapter(RequestManager glide,List<VODInfo> datas) {
        super(R.layout.more_movies_item,datas);
        this.glide=glide;
    }

    @Override
    protected void convert(BaseViewHolder helper, VODInfo data) {
      try {
        helper.setText(R.id.txt_movies_name, data.getDetail().getTitle())
                .addOnClickListener(R.id.layout_item);
        ImageUtils.loadImage(glide,data.getDetail().getPoster(),(ImageView) helper.getView(R.id.img_movies));
      }catch (Exception e){
        e.printStackTrace();
      }
    }
}