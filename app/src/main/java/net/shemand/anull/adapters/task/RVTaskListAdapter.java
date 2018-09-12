package net.shemand.anull.adapters.task;

import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 01.06.2018.
 */

public class RVTaskListAdapter extends RVListAdapter {
    public RVTaskListAdapter(ArrayList<? extends BaseDataModel> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.title.setText(items.get(position).get(TaskDataModel.TITLE).toString());
        holder.subTitle.setText(items.get(position).get(TaskDataModel.IMPORTANT).toString());
    }
}
