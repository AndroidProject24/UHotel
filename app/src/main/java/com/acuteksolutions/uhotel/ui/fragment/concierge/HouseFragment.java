package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.utils.ImageUtils;

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
      ImageUtils.loadImage(glide,R.drawable.housekeeping_bold,img_house);
      txtBottom.setText("Housekeeping coming!");
      txtBottom.setTextColor(getResources().getColor(R.color.white));
      isClicked = true;
    }
  }
}
