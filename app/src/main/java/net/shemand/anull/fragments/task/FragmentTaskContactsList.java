package net.shemand.anull.fragments.task;

import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.contact.FragmentContactList;
import net.shemand.anull.models.DataModels.ContactDataModel;

/**
 * Created by Shema on 05.06.2018.
 */

public class FragmentTaskContactsList extends FragmentContactList {

    int taskId;

    public FragmentTaskContactsList(RVListAdapter adapter, int taskId) {
        super(adapter);
        this.taskId = taskId;
    }

    @Override
    public void onClickFloatingButton() {
        FrameManager.getInstance().changeFrame().toAdd().newContactByTask(getRv_adapter(), taskId);
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_task_contacts_list).toString();
    }

    @Override
    public void onDeleteItem(int position) {
        DB.useTasks().deleteContact((int) getRv_adapter().items.get(position).get(ContactDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_contact_del), Toast.LENGTH_SHORT).show();
    }
}
