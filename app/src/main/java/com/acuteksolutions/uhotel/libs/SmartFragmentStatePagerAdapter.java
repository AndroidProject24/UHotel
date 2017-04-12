package com.acuteksolutions.uhotel.libs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

/**
 * Created by Toan.IT on 4/12/17.
 * Email: huynhvantoan.itc@gmail.com
 */

/*
   Extension of FragmentStatePagerAdapter which intelligently caches
   all active fragments and manages the fragment lifecycles.
   Usage involves extending from SmartFragmentStatePagerAdapter as you would any other PagerAdapter.
*/
public abstract class SmartFragmentStatePagerAdapter<T extends Fragment> extends FragmentStatePagerAdapter {

  private SparseArray<T> registeredFragments = new SparseArray<>();

  public SmartFragmentStatePagerAdapter(FragmentManager fragmentManager) {
    super(fragmentManager);
  }

  // Register the fragment when the item is instantiated
  @SuppressWarnings("unchecked") @Override
  public Object instantiateItem(ViewGroup container, int position) {
    T fragment = (T) super.instantiateItem(container, position);
    registeredFragments.put(position, fragment);
    return fragment;
  }

  // Unregister when the item is inactive
  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    registeredFragments.remove(position);
    super.destroyItem(container, position, object);
  }

  // Returns the fragment for the position (if instantiated)
  public T getRegisteredFragment(int position) {
    return registeredFragments.get(position);
  }
}