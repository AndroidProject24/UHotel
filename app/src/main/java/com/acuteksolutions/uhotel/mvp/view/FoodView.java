package com.acuteksolutions.uhotel.mvp.view;

import com.acuteksolutions.uhotel.mvp.model.food.Food;
import com.acuteksolutions.uhotel.mvp.model.food.ListFood;
import com.acuteksolutions.uhotel.mvp.view.base.BaseView;

/**
 * Created by Toan.IT
 * Date: 28/05/2016
 */
public interface FoodView extends BaseView {

  void getListFood(ListFood listFood);

  void showInfo(Food info,String title);
}
