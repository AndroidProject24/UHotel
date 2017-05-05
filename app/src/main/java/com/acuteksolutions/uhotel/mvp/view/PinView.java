package com.acuteksolutions.uhotel.mvp.view;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */
public interface PinView {

  void showLoading();

  void hideLoading();

  void showError(String message);

  void verifyPin(boolean checkVerify);

  void changePin(boolean checkChangePin);

  void saveSetting(boolean checkSave);

}
