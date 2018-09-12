package net.shemand.anull.fragments.task;

import android.util.Log;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.contact.FragmentContactAdd;
import net.shemand.anull.models.DataModels.ContactDataModel;

/**
 * Created by Shema on 05.06.2018.
 */

public class FragmentTaskContactsAdd extends FragmentContactAdd {

    int taskId;

    public FragmentTaskContactsAdd(RVInfoAdapter adapter, int taskId) {
        super(adapter);
        this.taskId = taskId;
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_task_contacts_add).toString();
    }

    @Override
    public void onClickFloatingButton() {
        Log.e("onClickFloatingButton", taskId + "");
        getRv_adapter().getRootAdapter().addItem(getRv_adapter().getModel());
        DB.useTasks().bindingContact(taskId, (int) DB.useContacts().add((ContactDataModel) getRv_adapter().getModel()));
        FrameManager.getInstance().changeFrame(true).toInfo().aboutContact(getRv_adapter().getRootAdapter(), (ContactDataModel) getRv_adapter().getModel());
        Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_contact_add), Toast.LENGTH_SHORT).show();
    }
}
