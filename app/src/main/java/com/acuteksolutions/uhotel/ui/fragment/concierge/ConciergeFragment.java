package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.ConciergeMenuDef;
import com.acuteksolutions.uhotel.ui.adapter.concierge.MenuAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.utils.Preconditions;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class ConciergeFragment extends BaseFragment{
  @BindView(R.id.recycler_menu) RecyclerView recycler_menu;
  private Context mContext;
  public static ConciergeFragment newInstance() {
    return new ConciergeFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext=context;
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    recycler_menu.setLayoutManager(new LinearLayoutManager(mContext));
    recycler_menu.setHasFixedSize(true);
    String[] arrName = getResources().getStringArray(R.array.concierge_menu_array);
    List<String> listMenu= Arrays.asList(Preconditions.checkNotNull(arrName));
    MenuAdapter menuAdapter=new MenuAdapter(listMenu);
    menuAdapter.openLoadAnimation();
    recycler_menu.setAdapter(menuAdapter);
    menuAdapter.setOnItemChildClickListener((baseQuickAdapter, view, position) -> showScreen(position));
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.concierge_fragment;
  }

  @Override
  protected void initData() {
    addFagment(getFragmentManager(), R.id.fragment, RoomFragment.newInstance());
  }

  private void showScreen(int index) {
    switch (index) {
      case ConciergeMenuDef.ROOM:
        replaceFagment(getFragmentManager(), R.id.fragment, RoomFragment.newInstance());
        break;
      case ConciergeMenuDef.CAR_RENTAL:
        replaceFagment(getFragmentManager(), R.id.fragment, CarRentalFragment.newInstance());
        break;
      case ConciergeMenuDef.CHECK_OUT:
        replaceFagment(getFragmentManager(), R.id.fragment, CheckoutFragment.newInstance());
        break;
      case ConciergeMenuDef.BUSINESS:
        replaceFagment(getFragmentManager(), R.id.fragment, BusinessFragment.newInstance());
        break;
      case ConciergeMenuDef.HOUSE:
        replaceFagment(getFragmentManager(), R.id.fragment, HouseFragment.newInstance());
        break;
      case ConciergeMenuDef.VALET:
        replaceFagment(getFragmentManager(), R.id.fragment, ValetFragment.newInstance());
        break;
      case ConciergeMenuDef.MIRROR:
        replaceFagment(getFragmentManager(), R.id.fragment, MirrorFragment.newInstance());
        break;
      case ConciergeMenuDef.PARENTAL:
        replaceFagment(getFragmentManager(), R.id.fragment, ParentalFragment.newInstance());
        break;
    }
  }
}

