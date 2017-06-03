package com.acuteksolutions.uhotel.ui.fragment.concierge;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import butterknife.BindView;
import com.acuteksolutions.uhotel.R;
import com.acuteksolutions.uhotel.mvp.model.conciege.CarRentalItem;
import com.acuteksolutions.uhotel.ui.adapter.concierge.CarRentalAdapter;
import com.acuteksolutions.uhotel.ui.fragment.base.BaseFragment;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Toan.IT
 * Date:22/04/2017
 */
public class CarRentalFragment extends BaseFragment {
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private Context mContext;

    public static CarRentalFragment newInstance() {
        return new CarRentalFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext=context;
    }

    @Override protected void injectDependencies() {

    }

    @Override protected String getTAG() {
        return null;
    }

    @Override protected void initViews() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override protected int setLayoutResourceID() {
        return R.layout.concierge_car_rental_fragment;
    }

    @Override protected void initData() {
        List<CarRentalItem> list = new ArrayList<>();
        list.add(new CarRentalItem("40 Bellevue Way NE\nBellevue, WA 98004\n(425) 324-2353", "Avis", R.drawable.car_item1));
        list.add(new CarRentalItem("40 Bellevue Way NE\nBellevue, WA 98004\n(425) 434-4634", "Hertz", R.drawable.car_item2));
        list.add(new CarRentalItem("40 Bellevue Way NE\nBellevue, WA 98004\n(425) 434-4634", "Budget", R.drawable.car_item3));
        list.add(new CarRentalItem("40 Bellevue Way NE\nBellevue, WA 98004\n(425) 434-4634", "Enterprise", R.drawable.car_item4));
        CarRentalAdapter carRentalAdapter=new CarRentalAdapter(glide,list);
        carRentalAdapter.openLoadAnimation();
        recyclerView.setAdapter(carRentalAdapter);
    }
}
