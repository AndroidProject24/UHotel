package com.acuteksolutions.uhotel.ui.fragment.food;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.BundleDef;
import com.acuteksolutions.uhotel.annotation.TabFoodDef;
import com.acuteksolutions.uhotel.libs.ItemClickSupport;
import com.acuteksolutions.uhotel.mvp.model.food.Food;
import com.acuteksolutions.uhotel.mvp.model.food.ListFood;
import com.acuteksolutions.uhotel.mvp.presenter.FoodPresenter;
import com.acuteksolutions.uhotel.mvp.view.FoodView;
import com.acuteksolutions.uhotel.ui.activity.BaseActivity;
import com.acuteksolutions.uhotel.ui.adapter.FoodAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseLazyFragment;
import com.acuteksolutions.uhotel.utils.ImageUtils;
import com.acuteksolutions.uhotel.utils.Preconditions;
import com.bumptech.glide.Glide;

import butterknife.BindView;

public class ListFoodFragment extends BaseLazyFragment<FoodPresenter> implements FoodView {
  @BindView(R.id.recycle_food)
  RecyclerView mRecycleFood;
  @BindView(R.id.txt_food_category)
  TextView mTxtFoodCategory;
  @BindView(R.id.txt_food_name)
  TextView mTxtFoodName;
  @BindView(R.id.rating) RatingBar mRating;
  @BindView(R.id.txt_food_rating)
  TextView mTxtFoodRating;
  @BindView(R.id.txt_food_des)
  TextView mTxtFoodDes;
  @BindView(R.id.txt_food_address)
  TextView mTxtFoodAddress;
  @BindView(R.id.img_bg)
  ImageView mImg_bg;
  private Context mContext;

  public static ListFoodFragment newInstance(@TabFoodDef.TabFood int index) {
    ListFoodFragment fragment=new ListFoodFragment();
    Bundle bundle=new Bundle();
    bundle.putInt(BundleDef.BUNDLE_KEY,index);
    fragment.setArguments(bundle);
    return fragment;
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override
  protected void injectDependencies() {
    ((BaseActivity) getActivity()).getActivityComponent().inject(this);
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    mRecycleFood.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    mRecycleFood.setHasFixedSize(true);
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.list_food_fragment;
  }

  @Override
  protected void initData() {
    mPresenter.getListFood(mContext,Preconditions.checkNotNull(getArguments().getInt(BundleDef.BUNDLE_KEY)));
  }

  @Override
  public void getListFood(ListFood listFood) {
    FoodAdapter foodAdapter = new FoodAdapter(Glide.with(this),listFood.getFoodList());
    foodAdapter.openLoadAnimation();
    mRecycleFood.setAdapter(foodAdapter);
    showInfo(listFood.getFoodList().get(0),listFood.getTitle());
    ItemClickSupport.addTo(mRecycleFood).setOnItemClickListener((recyclerView, position, v) ->
        showInfo(listFood.getFoodList().get(position),listFood.getTitle()));
  }

  @Override
  public void showInfo(Food info,String title) {
    try {
      Preconditions.checkNotNull(info);
      mTxtFoodCategory.setText(title);
      mTxtFoodName.setText(info.getName());
      mRating.setRating(info.getRating());
      mTxtFoodRating.setText(info.getType());
      mTxtFoodDes.setText(info.getDes());
      mTxtFoodAddress.setText(info.getAddress());
      ImageUtils.loadImage(glide,info.getUrl(),mImg_bg);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}

