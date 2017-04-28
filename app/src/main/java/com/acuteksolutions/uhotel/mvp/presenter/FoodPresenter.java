package com.acuteksolutions.uhotel.mvp.presenter;

import android.content.Context;
import com.acuteksolutions.uhotel.annotation.TabFoodDef;
import com.acuteksolutions.uhotel.data.local.PreferencesHelper;
import com.acuteksolutions.uhotel.data.rxjava.DefaultObserver;
import com.acuteksolutions.uhotel.data.service.RestData;
import com.acuteksolutions.uhotel.mvp.model.food.Food;
import com.acuteksolutions.uhotel.mvp.model.food.ListFood;
import com.acuteksolutions.uhotel.mvp.presenter.base.BasePresenter;
import com.acuteksolutions.uhotel.mvp.view.FoodView;
import com.acuteksolutions.uhotel.utils.FakeDataUtils;
import com.acuteksolutions.uhotel.utils.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.inject.Inject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Toan.IT
 * Date: 06/06/2016
 */
public class FoodPresenter extends BasePresenter<FoodView> {
  private RestData mRestData;
  private PreferencesHelper mPreferencesHelper;
  @Inject FoodPresenter(RestData restData, PreferencesHelper preferencesHelper){
    this.mRestData=restData;
    this.mPreferencesHelper=preferencesHelper;
  }

  public JSONObject getFakeData() {
   return Preconditions.checkNotNull(FakeDataUtils.initFakeData());
  }

  public void getListFood(Context mContext,int foodIndex) {
    addSubscribe(Observable.just(new ListFood())
        .subscribeOn(Schedulers.newThread())
        .map(listFood -> {
          JSONObject jsonObject=null;
          try {
            JSONArray jsonArray = getFakeData().optJSONArray("FoodObject");
            jsonObject = jsonArray.getJSONObject(foodIndex);
          }catch (JSONException e) {
            e.printStackTrace();
          }
          switch (foodIndex) {
            case TabFoodDef.TabFood.COFFEE:
              listFood.setTitle(mContext.getString(TabFoodDef.COFFEE));
              break;
            case TabFoodDef.TabFood.BEST:
              listFood.setTitle(mContext.getString(TabFoodDef.BEST));
              break;
            case TabFoodDef.TabFood.TOP:
              listFood.setTitle(mContext.getString(TabFoodDef.TOP));
              break;
            case TabFoodDef.TabFood.HOTEL:
              listFood.setTitle(mContext.getString(TabFoodDef.HOTEL));
              break;
          }
          List<Food> foodList = new ArrayList<>();
          for (int i = 1; i <= 5; i++) {
            Food food = new Food();
            if (!Objects.equals(jsonObject.optString("img" + (i) + "Rating"), "")) {
              food.setRating(Integer.parseInt(jsonObject.optString("img" + (i) + "Rating")));
            }
            food.setName(jsonObject.optString("img" + (i) + "Name"));
            food.setType(jsonObject.optString("img" + (i) + "Type"));
            food.setDes(jsonObject.optString("img" + (i) + "Desc"));
            food.setAddress(jsonObject.optString("img" + (i) + "Address"));
            food.setUrl(jsonObject.optInt("img" + i + "Path"));
            foodList.add(food);
          }
          listFood.setFoodList(foodList);
          return listFood;
        })
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSubscribe(() -> getMvpView().showLoading())
        .doOnCompleted(() -> getMvpView().hideLoading())
        .subscribe(new DefaultObserver<ListFood>() {
          @Override public void onError(Throwable e) {
            e.printStackTrace();
            getMvpView().showError(e.getMessage());
          }

          @Override public void onNext(ListFood listFood) {
            if (listFood!=null) {
              getMvpView().getListFood(listFood);
            }
          }
        }));
  }

  public PreferencesHelper getPreferencesHelper(){
    return mPreferencesHelper;
  }
}
