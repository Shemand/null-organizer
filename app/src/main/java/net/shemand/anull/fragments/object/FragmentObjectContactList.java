package net.shemand.anull.fragments.object;

import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.adapters.object.RVObjectListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.contact.FragmentContactList;
import net.shemand.anull.models.DataModels.ContactDataModel;

/**
 * Created by Shema on 04.06.2018.
 */

public class FragmentObjectContactList extends FragmentContactList {

    int objectId;

    public FragmentObjectContactList(RVListAdapter adapter, int objectId) {
        super(adapter);
        this.objectId = objectId;
    }

    @Override
    public void onClickFloatingButton() {
        FrameManager.getInstance().changeFrame().toAdd().newContactByObject(getRv_adapter(), objectId);
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_contact_list).toString();
    }

    @Override
    public void onDeleteItem(int position) {
        DB.useObjects().deleteContact((int) getRv_adapter().items.get(position).get(ContactDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_contact_del), Toast.LENGTH_SHORT).show();
    }
}
