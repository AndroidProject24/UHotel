package com.acuteksolutions.uhotel.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.presenter.PinPresenter;
import com.acuteksolutions.uhotel.mvp.view.PinView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Toan.IT on 5/5/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class LockDialog extends DialogFragment implements PinView {
  @Inject PinPresenter mPresenter;
  public static final int LOCK_REQUEST_CODE = 2;
  public static final int RESULT_TO_HOME = 1;
  public static final int RESULT_UNBLOCK = 2;

  @BindView(R.id.btnUnlock) Button btnUnlock;
  private Context context;
  private Unbinder unbinder;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    try {
      setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light_NoActionBar_Fullscreen);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    this.context = context;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    mPresenter.attachView(this);
    Dialog dialog = super.onCreateDialog(savedInstanceState);
    try {
      dialog.setCanceledOnTouchOutside(false);
      dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
          if ((keyCode == android.view.KeyEvent.KEYCODE_BACK)) {
            //This is the filter
            if (event.getAction() != KeyEvent.ACTION_DOWN)
              return true;
            else {
              //Hide your keyboard here!!!!!!
              return true; // pretend we've processed it
            }
          } else
            return false; // pass on to be processed as normal
        }
      });
    } catch (Exception e) {
      e.printStackTrace();
    }
    return dialog;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Preconditions.checkNotNull(getDialog().getWindow()).setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    View view = inflater.inflate(R.layout.lock_dialog, container, false);
    unbinder = ButterKnife.bind(this, view);
    return view;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    try {
     /* txtUnlockPin.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
          if (s.length() == 4) {
            btnUnlock.requestFocus();
          }
        }
      });*/
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    unbinder.unbind();
    mPresenter.detachView();
  }

  @OnClick(R.id.btnHome)
  void btnHome(){
    getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_TO_HOME, null);
    dismiss();
  }

  @OnClick(R.id.btnUnlock)
  void btnUnlock(){
   // mPresenter.verifyPin(txtUnlockPin.getText().toString());
  }

  @Override public void verifyPin(boolean checkVerify) {
    try {
      if (checkVerify) {
        getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_UNBLOCK, null);
        dismiss();
      } else {
        Toast.makeText(context, "Failed to verify your pin code!", Toast.LENGTH_SHORT).show();
        //txtUnlockPin.clearText();
        //txtUnlockPin.requestFocus();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
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
