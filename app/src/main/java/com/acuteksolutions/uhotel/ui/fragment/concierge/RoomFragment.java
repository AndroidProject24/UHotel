package com.acuteksolutions.uhotel.ui.fragment.concierge;

import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.ui.fragment.BaseFragment;


/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class RoomFragment extends BaseFragment {

    public static RoomFragment newInstance() {
        return new RoomFragment();
    }

    @Override protected String getTAG() {
        return null;
    }

    @Override protected void initViews() {

    }

    @Override protected int setLayoutResourceID() {
        return R.layout.concierge_room_framgent;
    }

    @Override protected void initData() {

    }
}
