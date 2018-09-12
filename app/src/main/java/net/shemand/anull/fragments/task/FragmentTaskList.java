package net.shemand.anull.fragments.task;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.base.FragmentList;
import net.shemand.anull.fragments.contact.FragmentContactAdd;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

/**
 * Created by Shema on 01.06.2018.
 */

public class FragmentTaskList extends FragmentList {

    private final static int CONTEXT_ITEM_DELETE = 1;
    private final static int CONTEXT_ITEM_ADD_TASK = 2;
    private final static int CONTEXT_ITEM_LOOK = 3;
    private final static int CONTEXT_ITEM_ADD_BUSINESS = 4;

    public FragmentTaskList(RVListAdapter adapter) {
        super(adapter);
        setHasFloatingButton(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return createRootView(rootView);
    }

    @Override
    public void onClickFloatingButton() {
        FrameManager.getInstance().changeFrame().toAdd().newTask(getRv_adapter(), null);
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_task_list).toString();
    }

    @Override
    public void onCardClick(View v, BaseDataModel model) {
        super.onCardClick(v, model);
        FrameManager.getInstance().changeFrame().toInfo().aboutTask(getRv_adapter(),(TaskDataModel) model);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    public void onCreateCardContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
        menu.setHeaderTitle(FrameManager.getMainContext().getResources().getText(R.string.context_task_title).toString() + " " + getRv_adapter().items.get(getContextItemPosition()).get(TaskDataModel.TITLE));
        menu.add(Menu.NONE, CONTEXT_ITEM_LOOK, Menu.NONE, "Посмотреть информацию");
        menu.add(Menu.NONE, CONTEXT_ITEM_ADD_TASK, Menu.NONE, "Добавить задачу");
        menu.add(Menu.NONE, CONTEXT_ITEM_ADD_BUSINESS, Menu.NONE, "Добавить дело");
        menu.add(Menu.NONE, CONTEXT_ITEM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch(item.getItemId()) {
            case CONTEXT_ITEM_DELETE:
                onDeleteItem(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_ADD_TASK:
                onAddTask(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_LOOK:
                FrameManager.getInstance().changeFrame().toInfo().aboutTask(getRv_adapter(), (TaskDataModel) getRv_adapter().items.get(getContextItemPosition()));
                return true;
            case CONTEXT_ITEM_ADD_BUSINESS:
                onAddBusiness(getContextItemPosition());
                return true;
            default:
                return false;
        }
    }

    private void onDeleteItem(int position) {
        DB.useTasks().delete((int) getRv_adapter().items.get(position).get(TaskDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_task_del), Toast.LENGTH_SHORT).show();
    }

    private void onAddTask(int position) {
        FrameManager.getInstance().changeFrame().toAdd().newTask(getRv_adapter(), (TaskDataModel) getRv_adapter().items.get(position));
    }

    private void onAddBusiness(int position){
        FrameManager.getInstance().changeFrame().toAdd().newBusinessByModel(getRv_adapter(), getRv_adapter().items.get(position));
    }
}
