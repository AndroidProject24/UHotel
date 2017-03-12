package com.acuteksolutions.uhotel.ui.adapter.movies;

import android.widget.ImageView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.data.VODInfo;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class MoviesAdapter extends BaseQuickAdapter<VODInfo, BaseViewHolder> {
    public MoviesAdapter(List<VODInfo> datas) {
        super(R.layout.movies_item,datas);
    }

    @Override
    protected void convert(BaseViewHolder helper, VODInfo data) {
        helper.setText(R.id.txt_thutuc, data.getContentInfoUid())
                .addOnClickListener(R.id.txt_thutuc)
                .addOnClickListener(R.id.img_theloai);
        Glide.with(mContext).load(data.getContentInfoUid()).crossFade().into((ImageView) helper.getView(R.id.img_theloai));
    }
}