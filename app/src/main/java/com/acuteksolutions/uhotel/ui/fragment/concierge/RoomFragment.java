package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.RealmManager;
import com.acuteksolutions.uhotel.interfaces.SaveDataRoomListener;
import com.acuteksolutions.uhotel.mvp.model.conciege.ListRoom;
import com.acuteksolutions.uhotel.mvp.model.conciege.Room;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;
import com.acuteksolutions.uhotel.mvp.presenter.RoomPresenter;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.concierge.RoomAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class RoomFragment extends BaseFragment<RoomPresenter> implements SaveDataRoomListener{
    @BindView(R.id.txt_title) TextView txtTitle;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.btnRequest) Button btnRequestSend;
    public RoomAdapter roomAdapter;
    private AlertDialog alertDialog;
    private Context mContext;
    private int totalRow=0,total=0;
    @Inject RealmManager realmManager;
    public static RoomFragment newInstance() {
        return new RoomFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override protected void injectDependencies() {
        ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    }

    @Override protected String getTAG() {
        return null;
    }

    @Override protected void initViews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override protected int setLayoutResourceID() {
        return R.layout.concierge_room_fragment;
    }

    @Override
    protected void initData() {
        total=0;
        ArrayList<MultiItemEntity> list = new ArrayList<>();
        realmManager.getRealmDB().executeTransactionAsync(realm -> {
            RealmResults<ListRoom> listRooms= realm.where(ListRoom.class).findAll();
            for(ListRoom listRoom:listRooms){
               // Logger.e("getTotal="+listRoom.getTotal());
                RoomExpand roomExpand=new RoomExpand(listRoom.getName(),listRoom.getTotal());
                for(int i=0;i<listRoom.getDetailList().size();i++) {
                    Room room = listRoom.getDetailList().get(i);
                    roomExpand.addSubItem(new Room(room.getName(),room.getValue(),i));
                }
                list.add(roomExpand);
            }
            for (int i = 0; i < listRooms.size(); i++) {
                for (Room room : listRooms.get(i).getDetailList())
                    total = total + room.getValue();
            }
        }, () -> {
            try {
                btnRequestSend.setText(String.format(getString(R.string.room_request_count), total));
                roomAdapter = new RoomAdapter(list, this, viewpagerListener);
                recyclerView.setAdapter(roomAdapter);
            }catch (Exception ignored){}
        });
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void saveData(int positionExPand,int position,int progress) {
        realmManager.getRealmDB().executeTransactionAsync(realm -> {
            totalRow = 0;
            total=0;
            RealmResults<ListRoom> listRooms = realm.where(ListRoom.class).findAll();
            try {
                if (listRooms.size() >= positionExPand) {
                    ListRoom listRoom = listRooms.get(positionExPand);
                    RealmList<Room> realmList = listRoom.getDetailList();
                    if (realmList.size() > position)
                        realmList.get(position).setValue(progress);
                    for (Room room : realmList) {
                        totalRow = totalRow + room.getValue();
                    }
                    listRoom.setTotal(totalRow);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            //Get total
            for (int i = 0; i < listRooms.size(); i++) {
                for (Room room : listRooms.get(i).getDetailList())
                    total = total + room.getValue();
            }
        }, () -> {
            try {
                btnRequestSend.setText(String.format(getString(R.string.room_request_count), total));
                ((AppCompatTextView) roomAdapter.getViewByPosition(recyclerView, positionExPand, R.id.txt_total)).setText(String.format(" (%d)", totalRow));
            }catch (Exception ignored){}
        });
    }

    @Override
    public void refreshList() {
        initData();
    }

    @OnClick(R.id.btnRequest)
    void btnRequest(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.room_service_dialog, null);
        view.findViewById(R.id.btnClose).setOnClickListener(v -> alertDialog.dismiss());
        builder.setView(view);
        alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

}
