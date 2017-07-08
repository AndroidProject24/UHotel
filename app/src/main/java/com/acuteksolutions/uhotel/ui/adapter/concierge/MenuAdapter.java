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
    private int index=0;
  public MenuAdapter(List<String> datas) {
    super(R.layout.concierge_menu_item,datas);
  }

  @Override
  protected void convert(BaseViewHolder helper, String data) {
    helper.setText(R.id.btn_menu, data)
        .addOnClickListener(R.id.btn_menu);
      if(this.index==helper.getAdapterPosition()) {
          helper.itemView.setSelected(false);
          helper.itemView.clearFocus();
      }
  }
  public void removeFocus(int index){
      this.index=index;
  }
}