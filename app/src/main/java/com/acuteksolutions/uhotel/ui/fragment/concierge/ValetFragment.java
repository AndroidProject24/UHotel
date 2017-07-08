package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
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
public class ValetFragment extends BaseFragment {

  @BindView(R.id.img_valet) ImageView imgValet;
  @BindView(R.id.txt_bottom) TextView txtBottom;
  private boolean isClicked;

  public static ValetFragment newInstance() {
    return new ValetFragment();
  }

  @Override protected String getTAG() {
    return null;
  }

  @Override protected void injectDependencies() {

  }

  @Override protected void initViews() {

  }

  @Override protected int setLayoutResourceID() {
    return R.layout.concierge_vallet_fragment;
  }

  @Override protected void initData() {

  }

  @OnClick(R.id.img_valet)
  void imageClick() {
    if (!isClicked) {
      imgValet.setSelected(true);
      String pre = "Your car will be right up! Please come to valet counter with ";
      String mes = pre + " your name and room number to get your vehicle.";
      SpannableString spannableString = new SpannableString(mes);
      spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), pre.length(),
          pre.length() + 10,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      spannableString.setSpan(new ForegroundColorSpan(Color.WHITE), pre.length() + 15,
          pre.length() + 26,
          Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
      txtBottom.setText(spannableString);
      txtBottom.setTextColor(getResources().getColor(R.color.white));
      isClicked = true;
    }else
      imgValet.setSelected(false);
  }
}
