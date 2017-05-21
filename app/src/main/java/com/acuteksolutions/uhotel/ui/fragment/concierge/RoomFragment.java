package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.RealmManager;
import com.acuteksolutions.uhotel.interfaces.SaveDataRoomListener;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.model.conciege.ListRoom;
import com.acuteksolutions.uhotel.mvp.model.conciege.Room;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;
import com.acuteksolutions.uhotel.mvp.presenter.RoomPresenter;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.concierge.RoomAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class RoomFragment extends BaseFragment<RoomPresenter> implements SaveDataRoomListener{
    @BindView(R.id.txt_title) TextView txtTitle;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    @BindView(R.id.txt_request_send) TextView txtRequestSend;
    @BindView(R.id.txt_item_total) TextView txtItemTotal;
    public RoomAdapter roomAdapter;
    private Context mContext;
    private Realm realm;
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
        realm=realmManager.getRealmDB();
    }

    @Override protected int setLayoutResourceID() {
        return R.layout.concierge_room_framgent;
    }

    @Override
    protected void initData() {
        ArrayList<MultiItemEntity> list = new ArrayList<>();
        realm.executeTransactionAsync(realm -> {
            RealmResults<ListRoom> listRooms= realm.where(ListRoom.class).findAll();
            for(ListRoom listRoom:listRooms){
                Logger.e("getTotal="+listRoom.getTotal());
                RoomExpand roomExpand=new RoomExpand(listRoom.getName(),listRoom.getTotal());
                for(int i=0;i<listRoom.getDetailList().size();i++) {
                    Room room = listRoom.getDetailList().get(i);
                    roomExpand.addSubItem(new Room(room.getName(),room.getValue(),i));
                }
                list.add(roomExpand);
            }

        }, () -> {
            roomAdapter = new RoomAdapter(list,this,viewpagerListener);
            recyclerView.setAdapter(roomAdapter);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        realm.close();
    }

    int total=0;
    @SuppressLint("DefaultLocale")
    @Override
    public void saveData(int positionExPand,int position,int progress) {
        realm.executeTransactionAsync(realm -> {
            total = 0;
            RealmResults<ListRoom> listRooms = realm.where(ListRoom.class).findAll();
            if (listRooms.size() >= positionExPand) {
                ListRoom listRoom = listRooms.get(positionExPand);
                RealmList<Room> realmList = listRoom.getDetailList();
                if (realmList.size() > position)
                    realmList.get(position).setValue(progress);
                for (Room room : realmList) {
                    total = total + room.getValue();
                }
                listRoom.setTotal(total);
            }
        }, () -> ((AppCompatTextView)roomAdapter.getViewByPosition(recyclerView,positionExPand,R.id.txt_total)).setText(String.format(" (%d)", total)));
    }

    @Override
    public void refreshList() {
        initData();
    }

}
