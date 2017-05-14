package com.acuteksolutions.uhotel.mvp.view;

import com.acuteksolutions.uhotel.mvp.view.base.BaseView;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */
public interface PinView extends BaseView{

  void verifyPin(boolean checkVerify);

  void changePin(boolean checkChangePin);

  void saveSetting(boolean checkSave);

}
