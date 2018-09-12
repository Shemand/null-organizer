package net.shemand.anull.fragments.business;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.business.RVBusinessListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.base.FragmentInfo;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;

/**
 * Created by Shema on 16.05.2018.
 */

public class FragmentBusinessAdd extends FragmentInfo {

    public FragmentBusinessAdd(RVInfoAdapter adapter) {
        super(adapter);
        setHasFloatingButton(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView = createRootView(rootView);
        return rootView;
    }

    @Override
    public void onClickFloatingButton() {
        if((long) getRv_adapter().getModel().get(BusinessDataModel.STARTED) != 0 &&
                getRv_adapter().getModel().get(BusinessDataModel.STARTED) != null&&
                getRv_adapter().getModel().get(BusinessDataModel.TITLE) != ""){

            long id = DB.useBusiness().add((BusinessDataModel) getRv_adapter().getModel());
            getRv_adapter().getModel().set(BusinessDataModel.ID, id == -1 ? 0 : id);
            if(getRv_adapter().getRootAdapter() instanceof RVBusinessListAdapter)
                getRv_adapter().getRootAdapter().addItem(getRv_adapter().getModel());
            Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_business_add), Toast.LENGTH_SHORT).show();
            FrameManager.getInstance().changeFrame(true).toInfo().aboutBusiness(getRv_adapter().getRootAdapter(), (BusinessDataModel) getRv_adapter().getModel());
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Ошибка");
            if(getRv_adapter().getModel().get(BusinessDataModel.TITLE) != ""){
                builder.setMessage("Поле начала дела не может быть пустым");
            } else {
                builder.setMessage("Поле-заголовок должно быть заполненным");
            }
            builder.setPositiveButton("Понял", null);
            builder.create().show();
        }
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_business_add).toString();
    }

    @Override
    public void onCardClick(View v, BaseDataModel model, int fieldId) {
        if(fieldId != BusinessDataModel.CREATED)
            super.onCardClick(v, model, fieldId);
    }
}
