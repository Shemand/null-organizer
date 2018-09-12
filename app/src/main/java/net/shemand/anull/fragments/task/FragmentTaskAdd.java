package net.shemand.anull.fragments.task;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.task.RVTaskListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.base.FragmentInfo;
import net.shemand.anull.models.DataModels.TaskDataModel;

/**
 * Created by Shema on 02.06.2018.
 */

public class FragmentTaskAdd extends FragmentInfo {

    public FragmentTaskAdd(RVInfoAdapter adapter) {
        super(adapter);
        setHasFloatingButton(true);
    }

    @Override
    public void onClickFloatingButton() {
        long id = DB.useTasks().add((TaskDataModel) getRv_adapter().getModel());
        getRv_adapter().getModel().set(TaskDataModel.ID, id == -1 ? 0 : id);
        Log.e("check", getRv_adapter().getModel().get(TaskDataModel.ID) + " = " + id);
        if(getRv_adapter().getRootAdapter() instanceof RVTaskListAdapter)
            getRv_adapter().getRootAdapter().addItem(getRv_adapter().getModel());
        Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_task_add), Toast.LENGTH_SHORT).show();
        FrameManager.getInstance().changeFrame(true).toInfo().aboutTask(getRv_adapter().getRootAdapter(), (TaskDataModel) getRv_adapter().getModel());
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_task_add).toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return createRootView(rootView);
    }
}
