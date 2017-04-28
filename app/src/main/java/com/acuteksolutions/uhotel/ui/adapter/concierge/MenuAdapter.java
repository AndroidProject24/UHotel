package com.acuteksolutions.uhotel.ui.adapter.concierge;

import com.acuteksolutions.uhotel.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

/**
 * Created by Toan.IT on 4/27/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class MenuAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
  public MenuAdapter(List<String> datas) {
    super(R.layout.concierge_menu_item,datas);
  }

  @Override
  protected void convert(BaseViewHolder helper, String data) {
    helper.setText(R.id.btn_menu, data)
        .addOnClickListener(R.id.btn_menu);
  }
}