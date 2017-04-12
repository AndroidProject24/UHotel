package com.acuteksolutions.uhotel.ui.fragment.food;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabFoodDef;
import com.acuteksolutions.uhotel.mvp.view.FoodView;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerFoodAdapter;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;
import com.acuteksolutions.uhotel.ui.fragment.OnTabSelectedListener;

public class FoodFragment extends BaseFragment implements FoodView {
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
    return R.layout.tab_viewpager_fragment;
  }

  @Override
  protected void initViews() {
    TabFoodDef tabFoodDef = new TabFoodDef();
    TabPagerFoodAdapter tabPagerFoodAdapter=new TabPagerFoodAdapter(mContext,tabFoodDef,getFragmentManager());
    mViewPager.setAdapter(tabPagerFoodAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
    for (int i = 0; i < tabFoodDef.tabSize(); i++) {
      mTabLayout.addTab(mTabLayout.newTab().setText(getString(tabFoodDef.getTab(i))));
    }
  }

  @Override
  protected void initData() {
    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener().onTabSelectedListener(mViewPager));
  }
}

