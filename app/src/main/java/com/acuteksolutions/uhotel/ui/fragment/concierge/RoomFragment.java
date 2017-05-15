package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.data.local.RealmManager;
import com.acuteksolutions.uhotel.libs.bubbleseekbar.BubbleSeekBar;
import com.acuteksolutions.uhotel.mvp.model.conciege.ListRoom;
import com.acuteksolutions.uhotel.mvp.model.conciege.Room;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomItem;
import com.acuteksolutions.uhotel.mvp.presenter.RoomPresenter;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.concierge.RoomAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import io.realm.Realm;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class RoomFragment extends BaseFragment<RoomPresenter> implements BubbleSeekBar.OnProgressChangedListener{
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

  @Override protected void initData() {
    List<RoomExpand> roomExpandList=new ArrayList<>();
    realm.executeTransactionAsync(realm -> {
       List<ListRoom> listRooms= realm.where(ListRoom.class).findAll();
       for(ListRoom listRoom:listRooms){
         List<RoomItem> itemList=new ArrayList<>();
         for(int i=0;i<listRoom.getDetailList().size();i++) {
           Room room = listRoom.getDetailList().get(i);
           itemList.add(new RoomItem(room.getName(),room.getAmount()));
         }
         roomExpandList.add(new RoomExpand(listRoom.getName(),itemList,1));
       }

    }, () -> {
      roomAdapter = new RoomAdapter(roomExpandList);
      recyclerView.setAdapter(roomAdapter);
    });
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    if(roomAdapter!=null)
      roomAdapter.onSaveInstanceState(outState);
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    if(roomAdapter!=null)
      roomAdapter.onRestoreInstanceState(savedInstanceState);
  }

  @Override public void onProgressChanged(int progress, float progressFloat) {
    viewpagerListener.disableSwipe(false);
  }

  @Override public void getProgressOnActionUp(int progress, float progressFloat) {
    viewpagerListener.disableSwipe(true);
  }

  @Override public void getProgressOnFinally(int progress, float progressFloat) {

  }

  @Override public void onDestroyView() {
    super.onDestroyView();
    realm.close();
  }
}
