package com.acuteksolutions.uhotel.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.mvp.presenter.PinPresenter;
import com.acuteksolutions.uhotel.mvp.view.PinView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.utils.Preconditions;
import javax.inject.Inject;

/**
 * Created by Toan.IT on 5/5/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class PinChangeDialog extends DialogFragment implements PinView {
  @Inject PinPresenter mPresenter;
  private Context context;
  private Unbinder unbinder;
  public static PinChangeDialog newInstance(String currentPIN) {
    PinChangeDialog pinDialogFragment = new PinChangeDialog();
    Bundle bundle = new Bundle();
    bundle.putString(BundleDef.BUNDLE_KEY,currentPIN);
    pinDialogFragment.setArguments(bundle);
    return pinDialogFragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    mPresenter.attachView(this);
    Preconditions.checkNotNull(dialog.getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
    dialog.setCanceledOnTouchOutside(false);
    return dialog;
  }


  @Override
  public void onStart() {
    super.onStart();
    Dialog dialog = getDialog();
    if (dialog != null) {
      Preconditions.checkNotNull(dialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
      dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
    }
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.pin_verify_dialog, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

  @OnClick(R.id.btnClose)
  void closeClick() {
    this.dismiss();
  }

  @OnClick(R.id.btnOk)
  void okClick() {
    mPresenter.changePin("1234","1234");
  }


  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    mPresenter.detachView();
  }

  @Override public void verifyPin(boolean checkVerify) {

  }

  @Override public void changePin(boolean checkChangePin) {

  }

  @Override public void saveSetting(boolean checkSave) {

  }

  @Override public void showLoading() {

  }

  @Override public void hideLoading() {

  }

  @Override public void showError(String message) {

  }

  @Override public void showEmptyView(String message) {

  }

  @Override public void showEmptyViewAction(String message, View.OnClickListener onClickListener) {

  }
}