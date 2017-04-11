package com.acuteksolutions.uhotel.ui.fragment.food;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.presenter.MoviesPresenter;
import com.acuteksolutions.uhotel.mvp.view.FoodView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class FoodFragment extends BaseFragment implements FoodView {
  @Inject
  MoviesPresenter
  mPresenter;
  private Context mContext;
  @BindView(R.id.recyclerview)
  RecyclerView mRecyclerview;
  public static FoodFragment newInstance() {
    return new FoodFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext=context;
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
    //mPresenter.attachView(this);
    initRecyclerview();
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.food_fragment;
  }

  @Override
  protected void initData() {
    //mPresenter.getData(TheloaiDef.HOA_MANG_TRA_TRUOC);
  }

  private void initRecyclerview(){
    mRecyclerview.setLayoutManager(new LinearLayoutManager(mContext));
    mRecyclerview.setHasFixedSize(true);
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mPresenter.detachView();
  }

  ///////////////////////////////////
  ///////////  GET FAKE DATA  ///////////
 /* public ListFood getALLInfoItemFromCatJSON(JSONObject jsondata, int catindex) {
    try {
      JSONArray jsonArray = jsondata.optJSONArray("FoodObject");
      JSONObject jsonObject = jsonArray.getJSONObject(catindex);
      if(catindex==0){
        mFoodList.setTitle(mMenuCoffee.getText().toString());
      }else if(catindex==1){
        mFoodList.setTitle(mMenuRestaurant.getText().toString());
      }else if(catindex==2){
        mFoodList.setTitle(mMenuTop.getText().toString());
      }else if(catindex==3){
        mFoodList.setTitle(mMenuHotel.getText().toString());
      }
      List<Food> foodList=new ArrayList<>();
      for (int i = 1; i <= 5; i++) {
        Food food=new Food();
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
      mFoodList.setFoodList(foodList);
      return mFoodList;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }*/
}

