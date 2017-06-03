package com.acuteksolutions.uhotel.ui.fragment.food;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabFoodDef;
import com.acuteksolutions.uhotel.interfaces.OnTabSelectedListener;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerFoodAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;

import butterknife.BindView;

public class FoodFragment extends BaseFragment{
  @BindView(R.id.tabLayout)
  TabLayout mTabLayout;
  @BindView(R.id.view_pager)
  ViewPager mViewPager;
  private Context mContext;

  public static FoodFragment newInstance() {
    return new FoodFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext = context;
  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }


  @Override
  protected int setLayoutResourceID() {
    return R.layout.food_fragment;
  }

  @Override protected void injectDependencies() {

  }

  @Override
  protected void initViews() {
    TabFoodDef tabFoodDef = new TabFoodDef();
    for (int i = 0; i < tabFoodDef.tabSize(); i++) {
      mTabLayout.addTab(mTabLayout.newTab().setText(getString(tabFoodDef.getTab(i))));
    }
    TabPagerFoodAdapter tabPagerFoodAdapter=new TabPagerFoodAdapter(mContext,tabFoodDef,getChildFragmentManager());
    mViewPager.setAdapter(tabPagerFoodAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override
  protected void initData() {
    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener().onTabSelectedListener(mViewPager));
  }
}

