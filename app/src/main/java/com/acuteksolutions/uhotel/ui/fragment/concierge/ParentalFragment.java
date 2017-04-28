package com.acuteksolutions.uhotel.ui.fragment.concierge;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */

public class ParentalFragment extends BaseFragment {

    public static ParentalFragment newInstance() {
        return new ParentalFragment();
    }

    @Override protected String getTAG() {
        return null;
    }

    @Override protected void initViews() {

    }

    @Override protected int setLayoutResourceID() {
        return R.layout.concierge_parental_fragment;
    }

    @Override protected void initData() {

    }
}
