package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.annotation.ParentalItemDef;
import com.acuteksolutions.uhotel.annotation.ParentalPinDef;
import com.acuteksolutions.uhotel.annotation.TabMainDef;
import com.acuteksolutions.uhotel.mvp.model.conciege.ParentalItem;
import com.acuteksolutions.uhotel.mvp.presenter.PinPresenter;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.concierge.ParentalAdapter;
import com.acuteksolutions.uhotel.ui.dialog.PinChangeDialog;
import com.acuteksolutions.uhotel.ui.dialog.PinVerifyDialog;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.utils.PageLockState;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */

public class ParentalFragment extends BaseFragment<PinPresenter> {
  @BindView(R.id.recyclerView) RecyclerView recyclerView;
  private Context mContext;
  private ParentalAdapter parentalAdapter;
  private boolean isEnableAll;
  @BindView(R.id.btnEnableAll) TextView btnEnableAll;

  public static ParentalFragment newInstance() {
    return new ParentalFragment();
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
    parentalAdapter=new ParentalAdapter(glide,arrayList);
    parentalAdapter.openLoadAnimation();
    recyclerView.setAdapter(parentalAdapter);
    //parentalAdapter.setOnItemChildClickListener((baseQuickAdapter, view, position) -> showScreen(position));
  }

  @OnClick(R.id.btnSave)
  void editClick() {
    PinVerifyDialog parentalDialogFragment = PinVerifyDialog.newInstance();
    parentalDialogFragment.setTargetFragment(this, ParentalPinDef.ONLY_VERIFY);
    parentalDialogFragment.show(getFragmentManager(), PinVerifyDialog.class.getName());

  }

  @OnClick(R.id.btnChangePin)
  void changePinClick() {
    PinVerifyDialog parentalDialogFragment = PinVerifyDialog.newInstance();
    parentalDialogFragment.setTargetFragment(this, ParentalPinDef.CHANGE_PIN);
    parentalDialogFragment.show(getFragmentManager(), PinVerifyDialog.class.getName());
  }

  @OnClick(R.id.btnEnableAll)
  void enableAllClick() {
    isEnableAll = !isEnableAll;
    setLockButton();
    parentalAdapter.setEnable(isEnableAll);
  }

  @SuppressLint("SetTextI18n")
  private void setLockButton() {
    if (isEnableAll)
      btnEnableAll.setText("Disable All");
    else btnEnableAll.setText("Enable All");
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

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ParentalPinDef.ONLY_VERIFY) {
      if (resultCode == Activity.RESULT_OK) {
        if (data.getBooleanExtra(BundleDef.IS_CORRECT, false)) {
          JsonObject jsonObject = new JsonObject();
          List<ParentalItem> list = parentalAdapter.getList();
          for (int i = 0; i < list.size(); i++) {
            switch (i) {
              case 0:
                jsonObject.addProperty(ParentalItemDef.WATCH_TV, list.get(i).isLocked());
                break;
              case 1:
                jsonObject.addProperty(ParentalItemDef.MOVIES, list.get(i).isLocked());
                break;
              case 2:
                jsonObject.addProperty(ParentalItemDef.CONCIERGE, list.get(i).isLocked());
                break;
              case 3:
                jsonObject.addProperty(ParentalItemDef.FNA, list.get(i).isLocked());
                break;
              case 4:
                jsonObject.addProperty(ParentalItemDef.ROOM_CONTROL, list.get(i).isLocked());
                break;
              default:
                break;
            }
          }
          mPresenter.saveSetting(data.getStringExtra(BundleDef.BUNDLE_KEY),jsonObject.toString());
        }
      }
    } else if (requestCode == ParentalPinDef.VERIFY_CHANGE_PIN) {
      if (resultCode == Activity.RESULT_OK) {
        if (data.getBooleanExtra(BundleDef.IS_CORRECT, false)) {
          PinChangeDialog dialog = PinChangeDialog.newInstance(data.getStringExtra(BundleDef.BUNDLE_KEY));
          dialog.setTargetFragment(this, ParentalPinDef.VERIFY_CHANGE_PIN);
          dialog.show(getFragmentManager(), PinChangeDialog.class.getName());
        }
      }
    } else if (requestCode == ParentalPinDef.CHANGE_PIN) {
      if (resultCode == Activity.RESULT_OK) {
        if (data.getBooleanExtra(BundleDef.IS_CORRECT, false)) {

        }
      }
    }
  }

}
