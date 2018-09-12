package net.shemand.anull.fragments.contact;

import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.adapters.contact.RVContactListAdapter;
import net.shemand.anull.adapters.object.RVObjectListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.base.FragmentList;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;

/**
 * Created by Shema on 30.05.2018.
 */

public class FragmentContactList extends FragmentList {

    private final static int CONTEXT_ITEM_DELETE = 1;
    private final static int CONTEXT_ITEM_ADD_TASK = 2;
    private final static int CONTEXT_ITEM_ADD_BUSINESS = 3;
    private final static int CONTEXT_ITEM_LOOK = 4;

    public FragmentContactList(RVListAdapter adapter) {
        super(adapter);
        setHasFloatingButton(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return createRootView(rootView);
    }

    @Override
    public void onClickFloatingButton() {
        FrameManager.getInstance().changeFrame().toAdd().newContact(getRv_adapter());
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_contact_list).toString();
    }

    @Override
    public void onCardClick(View v, BaseDataModel model) {
        super.onCardClick(v, model);
        FrameManager.getInstance().changeFrame().toInfo().aboutContact(getRv_adapter(),(ContactDataModel) model);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onCreateCardContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
        menu.setHeaderTitle(FrameManager.getMainContext().getResources().getText(R.string.context_contact_title).toString() + " "+ getRv_adapter().items.get(getContextItemPosition()).get(ContactDataModel.FIRST_NAME));
        menu.add(Menu.NONE, CONTEXT_ITEM_LOOK, Menu.NONE, "Просмотр информации");
        menu.add(Menu.NONE, CONTEXT_ITEM_ADD_TASK, Menu.NONE, "Создать задачу");
        menu.add(Menu.NONE, CONTEXT_ITEM_ADD_BUSINESS, Menu.NONE, "Создать дело");
        menu.add(Menu.NONE, CONTEXT_ITEM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch(item.getItemId()){
            case CONTEXT_ITEM_DELETE:
                onDeleteItem(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_ADD_BUSINESS:
                onAddBusiness(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_ADD_TASK:
                onAddTask(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_LOOK:
                return true;
        }
        return false;
    }

    public void onAddTask(int position) {
        FrameManager.getInstance().changeFrame().toAdd().newTaskByContact((RVContactListAdapter) getRv_adapter(), (ContactDataModel) getRv_adapter().items.get(position));
    }

    public void onDeleteItem(int position) {
        DB.useContacts().delete((int) getRv_adapter().items.get(position).get(ContactDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_contact_del), Toast.LENGTH_SHORT).show();
    }

    private void onAddBusiness(int position){
        FrameManager.getInstance().changeFrame().toAdd().newBusinessByModel(getRv_adapter(), getRv_adapter().items.get(position));
    }
}
