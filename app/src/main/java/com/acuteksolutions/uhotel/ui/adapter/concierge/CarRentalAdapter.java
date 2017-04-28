package com.acuteksolutions.uhotel.ui.adapter.concierge;

import android.widget.ImageView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.conciege.CarRentalItem;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class CarRentalAdapter extends BaseQuickAdapter<CarRentalItem, BaseViewHolder> {
  private RequestManager glide;
    public CarRentalAdapter(RequestManager glide,List<CarRentalItem> datas) {
        super(R.layout.concierge_car_rental_item,datas);
        this.glide=glide;
    }

  @Override
  protected void convert(BaseViewHolder helper, CarRentalItem data) {
    try {
      helper.setText(R.id.txtName, data.getName())
          .setText(R.id.txtAddress,data.getAddress());
      ImageUtils.loadImage(glide,data.getUrl(),(ImageView) helper.getView(R.id.img_card));
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}