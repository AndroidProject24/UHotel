package com.acuteksolutions.uhotel.ui.adapter.setting;

import android.widget.ImageView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.setting.Device;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Toan.IT on 5/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class DeviceAdapter extends BaseQuickAdapter<Device, BaseViewHolder> {
    private RequestManager glide;
    public DeviceAdapter(RequestManager glide,List<Device> datas) {
        super(R.layout.device_item,datas);
        this.glide=glide;
    }

    @Override
    protected void convert(BaseViewHolder helper, Device data) {
        try {
            helper.setText(R.id.txtName, data.getName())
                    .addOnLongClickListener(R.id.layout_item);
            ImageView img_device=(ImageView) helper.getView(R.id.img_device);
            switch (data.getType()) {
                case 1:
                    if (data.isActive())
                        ImageUtils.loadImage(glide,R.drawable.phone,img_device);
                    else
                        ImageUtils.loadImage(glide,R.drawable.phone_inactive,img_device);
                    break;
                case 2:
                    if (data.isActive())
                        ImageUtils.loadImage(glide,R.drawable.tablet,img_device);
                    else
                        ImageUtils.loadImage(glide,R.drawable.tablet_inactive,img_device);
                    break;
                case 3:
                    if (data.isActive())
                        ImageUtils.loadImage(glide,R.drawable.laptop,img_device);
                    else
                        ImageUtils.loadImage(glide,R.drawable.laptop_inactive,img_device);
                    break;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}