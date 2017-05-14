package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.mvp.model.conciege.ParentalItem;
import com.acuteksolutions.uhotel.ui.adapter.concierge.ParentalAdapter;
import com.acuteksolutions.uhotel.ui.dialog.PinVerifyDialog;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.utils.PageLockState;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */

public class ParentalFragment extends BaseFragment {
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  private Context mContext;
  public static ParentalFragment newInstance() {
    return new ParentalFragment();
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
    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 3);
    recyclerView.setLayoutManager(gridLayoutManager);
  }

  @Override protected int setLayoutResourceID() {
    return R.layout.concierge_parental_fragment;
  }

  @Override protected void initData() {
    List<ParentalItem> arrayList = new ArrayList<>();
    TabMainDef tabMainDef = new TabMainDef();
    for (int i = 0; i < tabMainDef.tabSize(); i++) {
      boolean isLocked = getValueSetting(i);
      arrayList.add(new ParentalItem(isLocked, getString(tabMainDef.getTab(i)), isLocked ? R.drawable.locked : R.drawable.opened));
    }
    ParentalAdapter parentalAdapter=new ParentalAdapter(glide,arrayList);
    parentalAdapter.openLoadAnimation();
    recyclerView.setAdapter(parentalAdapter);
    //parentalAdapter.setOnItemChildClickListener((baseQuickAdapter, view, position) -> showScreen(position));
  }

  @OnClick(R.id.btnSave)
  void editClick() {
    PinVerifyDialog parentalDialogFragment = PinVerifyDialog.init();
    parentalDialogFragment.setTargetFragment(this, 1);
    parentalDialogFragment.show(getFragmentManager(), PinVerifyDialog.class.getName());

  }

  @OnClick(R.id.btnChangePin)
  void changePinClick() {
    PinVerifyDialog parentalDialogFragment = PinVerifyDialog.init();
    parentalDialogFragment.setTargetFragment(this, 2);
    parentalDialogFragment.show(getFragmentManager(), PinVerifyDialog.class.getName());
  }

  @OnClick(R.id.btnEnableAll)
  void enableAllClick() {
   /* isEnableAll = !isEnableAll;
    setLockButton();
    MyAdapter myAdapter = (MyAdapter) recyclerView.getAdapter();
    myAdapter.setEnable(isEnableAll);*/
  }

  private void setLockButton() {
   /* if (isEnableAll)
      btnEnableAll.setText("Disable All");
    else btnEnableAll.setText("Enable All");*/
  }
  private boolean getValueSetting(int pos) {
    JSONObject jsonObject = PageLockState.getInstance().getPageLock();
    try {
      switch (pos) {
        case 0:
          return jsonObject.getBoolean("watchTvState");
        case 1:
          return jsonObject.getBoolean("moviesState");
        case 2:
          return jsonObject.getBoolean("conciergeState");
        case 3:
          return jsonObject.getBoolean("fnaState");
        case 4:
          return jsonObject.getBoolean("roomControlState");
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return false;
  }
}
