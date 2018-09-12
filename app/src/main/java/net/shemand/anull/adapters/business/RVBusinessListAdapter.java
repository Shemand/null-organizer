package net.shemand.anull.adapters.business;

import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 24.05.2018.
 */

public class RVBusinessListAdapter extends RVListAdapter {
    public RVBusinessListAdapter(ArrayList<? extends BaseDataModel> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.title.setText(items.get(position).get(BusinessDataModel.TITLE).toString());
        holder.subTitle.setText(items.get(position).get(BusinessDataModel.IMPORTANT).toString());
    }
}
