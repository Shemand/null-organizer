package net.shemand.anull.fragments.contact;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.contact.RVContactListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.base.FragmentInfo;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 02.06.2018.
 */

public class FragmentContactAdd extends FragmentInfo {

    public FragmentContactAdd(RVInfoAdapter adapter) {
        super(adapter);
        setHasFloatingButton(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return createRootView(rootView);
    }

    @Override
    public void onClickFloatingButton() {
        long id = DB.useContacts().add((ContactDataModel) getRv_adapter().getModel());
        getRv_adapter().getModel().set(ContactDataModel.ID, id == -1 ? 0 : id);

        for(int i = 0; i < ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.PLACES)).size(); i++) {
            ((AddrDataModel) ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.PLACES)).get(i)).set(AddrDataModel.CONTACT_ID, id);
            DB.usePlaces().add((AddrDataModel) ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.PLACES)).get(i));
        }
        for(int i = 0; i < ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.INTERNET_ADDRS)).size(); i++){
            ((InternetDataModel) ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.INTERNET_ADDRS)).get(i)).set(InternetDataModel.CONTACT_ID, id);
            DB.useInternets().add((InternetDataModel) ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.INTERNET_ADDRS)).get(i));
        }

        for(int i = 0; i < ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.PHONES)).size(); i++){
            ((PhoneDataModel) ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.PHONES)).get(i)).set(PhoneDataModel.CONTACT_ID, id);
            DB.usePhones().add((PhoneDataModel) ((ArrayList) getRv_adapter().getModel().get(ContactDataModel.PHONES)).get(i));
        }


            if(getRv_adapter().getRootAdapter() instanceof RVContactListAdapter)
            getRv_adapter().getRootAdapter().addItem(getRv_adapter().getModel());
        Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_contact_add), Toast.LENGTH_SHORT).show();
        FrameManager.getInstance().changeFrame(true).toInfo().aboutContact(getRv_adapter().getRootAdapter(), (ContactDataModel) getRv_adapter().getModel());

    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_contact_add).toString();
    }
}
