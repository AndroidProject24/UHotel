package com.acuteksolutions.uhotel.ui.fragment.setting;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.ui.activity.MainActivity;
import com.acuteksolutions.uhotel.ui.adapter.setting.AccountAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;
import com.acuteksolutions.uhotel.utils.FakeDataUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toan.IT on 5/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class AccountFragment extends BaseFragment {
    @BindView(R.id.btnleft)
    TextView btnLeft;
    @BindView(R.id.btnRight)
    TextView btnRight;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private boolean isEdit;
    private Context mContext;
    private AlertDialog alertDialog;
    public static AccountFragment newInstance() {
        return new AccountFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override
    protected String getTAG() {
        return null;
    }

    @Override
    protected void injectDependencies() {

    }

    @Override
    protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.account_fragment;
    }

    @Override
    protected void initData() {
        AccountAdapter hotelAdapter = new AccountAdapter(glide,isEdit,FakeDataUtils.fakeDataAccount());
        recyclerView.setAdapter(hotelAdapter);
    }

    @SuppressLint("SetTextI18n")
    @OnClick(R.id.btnleft)
    void leftClick() {
        if (!isEdit) {
            isEdit = true;
            btnLeft.setText("Cancel");
            btnRight.setText("Save Changes");
        } else {
            isEdit = false;
            btnLeft.setText("Edit Settings");
            btnRight.setText("Sign Out");
        }
        recyclerView.getAdapter().notifyDataSetChanged();
    }

    @OnClick(R.id.btnRight)
    void rightClick() {
        if (!isEdit) {
            Snackbar.make(recyclerView, R.string.sign_out, Snackbar.LENGTH_SHORT).show();
            PreferencesHelper preferencesHelper=new PreferencesHelper(mContext);
            preferencesHelper.putJsonLogin(null);
            getActivity().finish();
            MainActivity.start(mContext);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            View view = LayoutInflater.from(mContext).inflate(R.layout.account_save_dialog, null);
            view.findViewById(R.id.btnClose).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });
            builder.setView(view);
            alertDialog = builder.create();
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();

        }
    }

}
