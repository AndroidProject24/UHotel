package com.acuteksolutions.uhotel.ui.fragment.login;

import android.content.Context;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.interfaces.ToolbarTitleListener;
import com.acuteksolutions.uhotel.mvp.presenter.LoginPresenter;
import com.acuteksolutions.uhotel.mvp.view.LoginView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.ui.fragment.MainFragment;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

import static com.acuteksolutions.uhotel.utils.Utils.isPasswordValid;

public class LoginFragment extends BaseFragment implements LoginView {
  @Inject
  LoginPresenter
          mLoginPresenter;
  @BindView(R.id.img_logo)
  ImageView mImgLogo;
  @BindView(R.id.lock)
  TextClock mLock;
  @BindView(R.id.txt_date)
  TextView mTxtDate;
  @BindView(R.id.img_weather)
  ImageView mImgWeather;
  @BindView(R.id.txt_temp)
  TextView mTxtTemp;
  @BindView(R.id.etPass)
  TextInputEditText mEtPass;
  @BindView(R.id.layout_pass)
  TextInputLayout mLayoutPass;
  @BindView(R.id.btn_login)
  AppCompatButton mBtnLogin;
  private Context mContext;
  protected ToolbarTitleListener toolbarTitleListener;
  public static Fragment newInstance() {
    return new LoginFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
    try {
      toolbarTitleListener = (ToolbarTitleListener) getActivity();
    } catch (ClassCastException e) {
      throw new ClassCastException(context.toString() + " must implement OnHeadlineSelectedListener");
    }
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    mLoginPresenter.attachView(this);
    mEtPass.addTextChangedListener(textWatcher);
    toolbarTitleListener.hideToolBar(true);
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.login_fragment;
  }

  @Override
  protected void initData() {

  }

  private TextWatcher textWatcher = new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
      enableLoginBtn();
    }
  };

  private void enableLoginBtn() {
    mBtnLogin.setEnabled(mEtPass.getText().length() != 0);
  }

  @OnClick(R.id.btn_login)
  void clickLogin() {
    checkLogin();
  }

  private void checkLogin() {
    mEtPass.setError(null);
    String password = mEtPass.getText().toString();
    boolean cancel = false;
    View focusView = null;
    if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
      mLayoutPass.setErrorEnabled(true);
      mLayoutPass.setError(getString(R.string.error_invalid_password));
      focusView = mLayoutPass;
      cancel = true;
    }
    if (cancel) {
      focusView.requestFocus();
    } else {
      mLayoutPass.setErrorEnabled(false);
      mLoginPresenter.login(mEtPass.getText().toString());
    }
  }

  @Override
  public void loginSucess() {
    replaceFagment(getFragmentManager(), R.id.fragment, MainFragment.newInstance());
  }

  @Override
  public void loginError() {
    showError("");
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    try {
      toolbarTitleListener.hideToolBar(false);
    }catch (Exception e){
      e.printStackTrace();
    }
  }
}

