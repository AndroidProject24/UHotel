package com.acuteksolutions.uhotel.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.interfaces.TextWatcher;
import com.acuteksolutions.uhotel.libs.AsteriskPassword;
import com.acuteksolutions.uhotel.mvp.presenter.PinPresenter;
import com.acuteksolutions.uhotel.mvp.view.PinView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.utils.Preconditions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.acuteksolutions.uhotel.utils.Utils.getStringFromEditText;

/**
 * Created by Toan.IT on 5/5/17.
 * Email: huynhvantoan.itc@gmail.com
 */

public class PinVerifyDialog extends DialogFragment implements PinView {
    @Inject
    PinPresenter mPresenter;
    @BindView(R.id.ediPin1)
    EditText ediPin1;
    @BindView(R.id.ediPin2)
    EditText ediPin2;
    @BindView(R.id.ediPin3)
    EditText ediPin3;
    @BindView(R.id.ediPin4)
    EditText ediPin4;
    private Context context;
    private Unbinder unbinder;

    public static PinVerifyDialog newInstance() {
        return new PinVerifyDialog();
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
        ediPin1.setTransformationMethod(new AsteriskPassword());
        ediPin2.setTransformationMethod(new AsteriskPassword());
        ediPin3.setTransformationMethod(new AsteriskPassword());
        ediPin4.setTransformationMethod(new AsteriskPassword());

        ediPin1.addTextChangedListener(textWatcher(ediPin1));
        ediPin2.addTextChangedListener(textWatcher(ediPin2));
        ediPin3.addTextChangedListener(textWatcher(ediPin3));
        ediPin4.addTextChangedListener(textWatcher(ediPin4));
    }

    private TextWatcher textWatcher(EditText editText) {
        return new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (editText.getText().toString().length() == 1) {
                    switch (editText.getId()) {
                        case R.id.ediPin1:
                            ediPin2.requestFocus();
                            break;
                        case R.id.ediPin2:
                            ediPin3.requestFocus();
                            break;
                        case R.id.ediPin3:
                            ediPin4.requestFocus();
                            break;
                        case R.id.ediPin4:
                            mPresenter.verifyPin(getPin());
                            break;
                        default:
                            break;
                    }
                }
            }
        };
    }

    @OnClick(R.id.txtCancel)
    void closeClick() {
        this.dismiss();
    }
/*

    @OnClick(R.id.btnOk)
    void okClick() {
        mPresenter.verifyPin(getPin());
    }
*/

    private String getPin() {
        return getStringFromEditText(ediPin1) + getStringFromEditText(ediPin2)
                + getStringFromEditText(ediPin3) + getStringFromEditText(ediPin4);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        mPresenter.detachView();
    }

    @Override
    public void verifyPin(boolean checkVerify) {
        if (!checkVerify)
            showError("Error");
        else
            this.dismiss();
        Intent intent = new Intent();
        intent.putExtra(BundleDef.BUNDLE_KEY, getPin());
        intent.putExtra(BundleDef.IS_CORRECT, checkVerify);
        getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
    }

    @Override
    public void changePin(boolean checkChangePin) {

    }

    @Override
    public void saveSetting(boolean checkSave) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showEmptyView(String message) {

    }

    @Override
    public void showEmptyViewAction(String message, View.OnClickListener onClickListener) {

    }
}