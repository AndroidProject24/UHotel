package com.acuteksolutions.uhotel.ui.fragment.setting;


import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.setting.Device;
import com.acuteksolutions.uhotel.ui.adapter.setting.DeviceAdapter;
import com.acuteksolutions.uhotel.ui.dialog.AddDevice;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;
import com.acuteksolutions.uhotel.utils.FakeDataUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toan.IT on 5/22/17.
 * Email: huynhvantoan.itc@gmail.com
 */
public class DeviceFragment extends BaseFragment {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.btnAddDevice)
    TextView btnAddDevice;
    private Context mContext;

    public static DeviceFragment newInstance() {
        return new DeviceFragment();
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
    protected int setLayoutResourceID() {
        return R.layout.device_fragment;
    }

    @Override
    protected void initViews() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void initData() {
        List<Device> deviceList=FakeDataUtils.fakeDataDevices();
        DeviceAdapter deviceAdapter = new DeviceAdapter(glide,deviceList);
        recyclerView.setAdapter(deviceAdapter);
        deviceAdapter.openLoadAnimation();
        deviceAdapter.setOnItemChildLongClickListener((baseQuickAdapter, view, i) -> {
            deviceList.get(i).setActive(!deviceList.get(i).isActive());
            deviceAdapter.notifyItemChanged(i);
            return false;
        });
    }

    @OnClick(R.id.btnAddDevice)
    void addDeviceClick() {
        AddDevice dialogFragment = new AddDevice();
        dialogFragment.show(getFragmentManager(), AddDevice.class.getName());
    }
}
