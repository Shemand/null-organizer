package net.shemand.anull.adapters.contact;

import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 30.05.2018.
 */

public class RVContactListAdapter extends RVListAdapter {

    public RVContactListAdapter(ArrayList<? extends BaseDataModel> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.title.setText(items.get(position).get(ContactDataModel.FIRST_NAME).toString());
        holder.subTitle.setText(items.get(position).get(ContactDataModel.SECOND_NAME).toString());
    }
}
