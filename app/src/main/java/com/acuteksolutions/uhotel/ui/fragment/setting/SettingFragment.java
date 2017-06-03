package com.acuteksolutions.uhotel.ui.fragment.setting;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.annotation.TabSettingDef;
import com.acuteksolutions.uhotel.interfaces.OnTabSelectedListener;
import com.acuteksolutions.uhotel.mvp.view.SettingView;
import com.acuteksolutions.uhotel.ui.adapter.page.TabPagerSettingAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;

import butterknife.BindView;

public class SettingFragment extends BaseFragment implements SettingView {
  @BindView(R.id.tabLayout)
  TabLayout mTabLayout;
  @BindView(R.id.view_pager)
  ViewPager mViewPager;
  private Context mContext;
  public static SettingFragment newInstance() {
    return new SettingFragment();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    mContext=context;
  }

  @Override protected void injectDependencies() {

  }

  @Override
  protected String getTAG() {
    return this.getClass().getSimpleName();
  }

  @Override
  protected void initViews() {
    TabSettingDef tabSettingDef = new TabSettingDef();
    for (int i = 0; i < tabSettingDef.tabSize(); i++) {
      mTabLayout.addTab(mTabLayout.newTab().setText(getString(tabSettingDef.getTab(i))));
    }
    TabPagerSettingAdapter tabPagerSettingAdapter=new TabPagerSettingAdapter(mContext,tabSettingDef,getChildFragmentManager());
    mViewPager.setAdapter(tabPagerSettingAdapter);
    mTabLayout.setupWithViewPager(mViewPager);
  }

  @Override
  protected int setLayoutResourceID() {
    return R.layout.setting_fragment;
  }

  @Override
  protected void initData() {
    mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    mTabLayout.addOnTabSelectedListener(new OnTabSelectedListener().onTabSelectedListener(mViewPager));
  }

}

