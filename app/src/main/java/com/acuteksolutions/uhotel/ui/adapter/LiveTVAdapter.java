package com.acuteksolutions.uhotel.ui.adapter;

import android.widget.ImageView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.livetv.Program;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class LiveTVAdapter extends BaseQuickAdapter<Program, BaseViewHolder> {
  private RequestManager glide;
    public LiveTVAdapter(RequestManager glide,List<Program> datas) {
        super(R.layout.livetv_item,datas);
        this.glide=glide;
    }

    @Override
    protected void convert(BaseViewHolder helper, Program data) {
      try {
        helper.setText(R.id.txt_liveTV_name, data.getTitle())
                .addOnClickListener(R.id.layout_item);
        if(data.getPicture()!=null)
          ImageUtils.loadImage(glide,data.getPicture(),(ImageView) helper.getView(R.id.img_liveTV));
      }catch (Exception e){
        e.printStackTrace();
      }
    }
}