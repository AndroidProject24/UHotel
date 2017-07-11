package com.acuteksolutions.uhotel.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatButton;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.libs.loading.AVLoadingIndicatorView;
import com.acuteksolutions.uhotel.libs.logger.Logger;
import com.acuteksolutions.uhotel.mvp.presenter.LoginPresenter;
import com.acuteksolutions.uhotel.mvp.view.LoginView;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.acuteksolutions.uhotel.utils.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

import static com.acuteksolutions.uhotel.utils.Utils.isPasswordValid;

/**
 * Created by Toan.IT on 5/19/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView {
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
    @BindView(R.id.loading_view)
    AVLoadingIndicatorView loading_view;
    @BindView(R.id.txt_error)
    TextView txtError;
    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected String getTAG() {
        return this.getClass().getSimpleName();
    }

    @Override
    protected void initViews() {
        StatusBarUtil.setTranslucent(this);
        initTime();
        mEtPass.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {
                mLayoutPass.setHint("PIN");
            } else {
                mLayoutPass.setHint("PIN");
            }
        });
        mEtPass.addTextChangedListener(textWatcher);
    }

    @Override
    protected int setLayoutResourceID() {
        return R.layout.login_activity;
    }

    @Override
    protected void initData() {
        if(mPresenter.getmPreferencesHelper().getJsonLogin()!=null) {
            finish();
            MainActivity.start(this);
        }
    }

    @Override
    protected void injectDependencies() {
        getActivityComponent().inject(this);
    }

    @Override
    public void loginSucess() {
        finish();
        MainActivity.start(this);
    }

    private void initTime(){
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMMM dd, yyyy");
        String formattedDate = df.format(c.getTime());
        mTxtDate.setText(formattedDate);
        //Constant.DEVICE_MAC= Utils.getMacAddess(); //TODO: MAC
    }
    @Override
    public void loginError() {
        showError("");
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
            mPresenter.login(mEtPass.getText().toString());
        }
    }

    @Override
    public void showError(String message) {
        Logger.e(Preconditions.checkNotNull(message));
        txtError.setText("Error");
    }

    @Override
    public void showLoading() {
        loading_view.smoothToShow();
    }

    @Override
    public void hideLoading() {
        loading_view.smoothToHide();
    }
}
