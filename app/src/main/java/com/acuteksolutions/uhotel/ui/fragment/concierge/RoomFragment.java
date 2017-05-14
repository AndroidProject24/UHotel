package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.bubbleseekbar.BubbleSeekBar;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomExpand;
import com.acuteksolutions.uhotel.mvp.model.conciege.RoomItem;
import com.acuteksolutions.uhotel.ui.adapter.concierge.RoomAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class RoomFragment extends BaseFragment implements BubbleSeekBar.OnProgressChangedListener{

  @BindView(R.id.txt_title) TextView txtTitle;
  @BindView(R.id.recycler_view) RecyclerView recyclerView;
  @BindView(R.id.txt_request_send) TextView txtRequestSend;
  @BindView(R.id.txt_item_total) TextView txtItemTotal;
  public RoomAdapter roomAdapter;
  private Context mContext;
  public static RoomFragment newInstance() {
    return new RoomFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext=context;
  }

  @Override protected void injectDependencies() {

  }

  @Override protected String getTAG() {
    return null;
  }

  @Override protected void initViews() {
    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
    recyclerView.setLayoutManager(layoutManager);
  }

  @Override protected int setLayoutResourceID() {
    return R.layout.concierge_room_framgent;
  }

  @Override protected void initData() {
    roomAdapter = new RoomAdapter(fakeData());
    recyclerView.setAdapter(roomAdapter);
  }

  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    roomAdapter.onSaveInstanceState(outState);
  }

  @Override
  public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
    super.onViewStateRestored(savedInstanceState);
    roomAdapter.onRestoreInstanceState(savedInstanceState);
  }

  private List<RoomExpand> fakeData(){
    return Arrays.asList(new RoomExpand("Toan1", makeRockArtists(), R.drawable.ic_logo_uhotel),
        new RoomExpand("Toan2", makeRockArtists(), R.drawable.ic_logo_uhotel),
        new RoomExpand("Toan3", makeRockArtists(), R.drawable.ic_logo_uhotel));
  }
  private static List<RoomItem> makeRockArtists() {
    RoomItem queen = new RoomItem("Queen", 20f);
    RoomItem styx = new RoomItem("Styx", 30f);
    RoomItem reoSpeedwagon = new RoomItem("REO Speedwagon", 40f);
    RoomItem boston = new RoomItem("Boston", 50f);

    return Arrays.asList(queen, styx, reoSpeedwagon, boston);
  }

  @Override public void onProgressChanged(int progress, float progressFloat) {
    viewpagerListener.disableSwipe(true);
  }

  @Override public void getProgressOnActionUp(int progress, float progressFloat) {

  }

  @Override public void getProgressOnFinally(int progress, float progressFloat) {
    viewpagerListener.disableSwipe(false);
  }
}
