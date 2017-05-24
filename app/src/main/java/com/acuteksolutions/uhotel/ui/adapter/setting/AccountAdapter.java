package com.acuteksolutions.uhotel.ui.adapter.setting;

import android.view.View;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.setting.Account;
import com.bumptech.glide.RequestManager;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Toan.IT on 5/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class AccountAdapter extends BaseMultiItemQuickAdapter<Account, BaseViewHolder> {
    private RequestManager glide;
    private boolean isEdit;
    public AccountAdapter(RequestManager glide,boolean isEdit,List datas) {
        super(datas);
        this.glide=glide;
        this.isEdit=isEdit;
        addItemType(Account.ACCOUNT, R.layout.account_item);
        addItemType(Account.ACCOUNT_NOTIFY, R.layout.account_notify_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Account account) {
        switch (helper.getItemViewType()) {
            case Account.ACCOUNT:
                helper.setText(R.id.txtName, account.getName())
                      .setText(R.id.txtValue, account.getValue());
                if (helper.getAdapterPosition() == 0 || helper.getAdapterPosition() == 1)
                    helper.getView(R.id.txtValue).setEnabled(false);
                else
                    helper.getView(R.id.txtValue).setEnabled(isEdit);
                if (!isEdit) {
                    helper.itemView.setBackgroundResource(R.drawable.transparent);
                    helper.getView(R.id.imaLock).setVisibility(View.GONE);
                } else {
                    helper.itemView.setBackgroundResource(R.drawable.setting_row_selector);
                    helper.getView(R.id.imaLock).setVisibility(View.VISIBLE);
                }
                if (account.getImaResId() != 0)
                    ((ImageView)helper.getView(R.id.imaLock)).setImageResource(account.getImaResId());
                else
                    ((ImageView)helper.getView(R.id.imaLock)).setImageResource(R.drawable.transparent);
                break;
            case Account.ACCOUNT_NOTIFY:
                 helper.setText(R.id.txtName, account.getName());
                ToggleButton toggleButton=((ToggleButton)helper.getView(R.id.toggleButton));
                toggleButton.setChecked(Boolean.valueOf(account.getValue()));
                toggleButton.setEnabled(isEdit);
                if (!isEdit)
                    helper.itemView.setBackgroundResource(R.drawable.transparent);
                else
                    helper.itemView.setBackgroundResource(R.drawable.setting_row_selector);
                break;
        }
    }
}