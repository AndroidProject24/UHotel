package com.acuteksolutions.uhotel.ui.adapter;

import android.widget.ImageView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.food.Food;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public class FoodAdapter extends BaseQuickAdapter<Food, BaseViewHolder> {
  private RequestManager glide;
    public FoodAdapter(RequestManager glide,List<Food> datas) {
        super(R.layout.food_item,datas);
        this.glide=glide;
    }

    @Override
    protected void convert(BaseViewHolder helper, Food data) {
      try {
        helper.setText(R.id.txt_food_name, data.getName())
                .addOnClickListener(R.id.layout_item);
        ImageUtils.loadImage(glide,data.getUrl(),(ImageView) helper.getView(R.id.img_food));
      }catch (Exception e){
        e.printStackTrace();
      }
    }
}