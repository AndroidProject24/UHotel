package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.widget.ImageView;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class HouseFragment extends BaseFragment {

  @BindView(R.id.img_house) ImageView img_house;
  @BindView(R.id.txt_bottom) TextView txtBottom;
  private boolean isClicked;
  public static HouseFragment newInstance() {
    return new HouseFragment();
  }

  @Override protected String getTAG() {
    return null;
  }

  @Override protected void injectDependencies() {

  }

  @Override protected void initViews() {

  }

  @Override protected int setLayoutResourceID() {
    return R.layout.concierge_house_fragment;
  }

  @Override protected void initData() {

  }

  @OnClick(R.id.img_house)
  void imageClick() {
    if (!isClicked) {
      img_house.setSelected(true);
      txtBottom.setText("Housekeeping coming!");
      txtBottom.setTextColor(getResources().getColor(R.color.white));
      isClicked = true;
    }else
      img_house.setSelected(false);
  }
}
