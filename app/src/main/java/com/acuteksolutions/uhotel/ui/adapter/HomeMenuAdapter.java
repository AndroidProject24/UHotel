package com.acuteksolutions.uhotel.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.login.HomeMenu;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class HomeMenuAdapter extends BaseQuickAdapter<HomeMenu, BaseViewHolder> {
    private Context context;

    public HomeMenuAdapter(Context context, List<HomeMenu> datas) {
        super(R.layout.menu_item, datas);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeMenu data) {
        try {
            helper.setText(R.id.txtTitle, data.getName())
                    .addOnClickListener(R.id.root);
            ImageUtils.loadImage(Glide.with(context), data.getIcon(), (ImageView) helper.getView(R.id.imageView));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}