package com.acuteksolutions.uhotel.ui.adapter.movies;

import android.widget.ImageView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import static com.acuteksolutions.uhotel.utils.ImageUtils.loadImage;

public class MoviesAdapter extends BaseQuickAdapter<VODInfo, BaseViewHolder> {
    public MoviesAdapter(List<VODInfo> datas) {
        super(R.layout.movies_item,datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, VODInfo data) {
      try {
        helper.setText(R.id.txt_movies_name, data.getDetail().getTitle())
                .addOnClickListener(R.id.layout_item);
        loadImage(mContext,data.getDetail().getPoster(),(ImageView) helper.getView(R.id.img_movies));
      }catch (Exception e){
        e.printStackTrace();
      }
    }
}