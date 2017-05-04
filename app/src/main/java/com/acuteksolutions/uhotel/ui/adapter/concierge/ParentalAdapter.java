package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.widget.ImageView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.conciege.ParentalItem;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class ParentalAdapter extends BaseQuickAdapter<ParentalItem, BaseViewHolder> {
  private RequestManager glide;
    public ParentalAdapter(RequestManager glide,List<ParentalItem> datas) {
        super(R.layout.concierge_parental_control_item,datas);
        this.glide=glide;
    }

  @Override
  protected void convert(BaseViewHolder helper, ParentalItem data) {
    try {
      helper.setText(R.id.txtName, data.getName());
      ImageUtils.loadImage(glide,data.getResId(),(ImageView) helper.getView(R.id.imageView));
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}