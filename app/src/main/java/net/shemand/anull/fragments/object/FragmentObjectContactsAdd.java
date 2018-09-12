package net.shemand.anull.fragments.object;

import android.util.Log;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.contact.RVContactListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.contact.FragmentContactAdd;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;

/**
 * Created by Shema on 04.06.2018.
 */

public class FragmentObjectContactsAdd extends FragmentContactAdd {

    int objectId;

    public FragmentObjectContactsAdd(RVInfoAdapter adapter, int objectId) {
        super(adapter);
        this.objectId = objectId;
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_object_contacts_add).toString();
    }

    @Override
    public void onClickFloatingButton() {
        if(getRv_adapter().getRootAdapter() instanceof RVContactListAdapter)
            getRv_adapter().getRootAdapter().addItem(getRv_adapter().getModel());
        DB.useObjects().bindingContact(objectId, (int) DB.useContacts().add((ContactDataModel) getRv_adapter().getModel()));
        FrameManager.getInstance().changeFrame(true).toInfo().aboutContact(getRv_adapter().getRootAdapter(), (ContactDataModel) getRv_adapter().getModel());
        Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_contact_add), Toast.LENGTH_SHORT).show();
    }


}
