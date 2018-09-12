package net.shemand.anull.fragments.business;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.fragments.base.FragmentInfo;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;

/**
 * Created by Shema on 06.05.2018.
 */

public class FragmentBusinessInfo extends FragmentInfo {

    public FragmentBusinessInfo(RVInfoAdapter adapter) {
        super(adapter);
        setHasFloatingButton(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView = createRootView(rootView);
        return rootView;
    }

    @Override
    public void onClickFloatingButton() {

    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_business_info).toString();
    }

    @Override
    public void onCardClick(View v, BaseDataModel model, int fieldId) {
        if(fieldId != BusinessDataModel.CREATED)
            super.onCardClick(v, model, fieldId);
    }
}
