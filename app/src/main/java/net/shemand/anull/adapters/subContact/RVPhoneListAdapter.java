package net.shemand.anull.adapters.subContact;

import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.PhoneDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 01.06.2018.
 */

public class RVPhoneListAdapter extends RVListAdapter {

    public RVPhoneListAdapter(ArrayList<PhoneDataModel> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(items.get(position) instanceof PhoneDataModel) {
            if(items.get(position).get(PhoneDataModel.NUMBER) != "" && items.get(position).get(PhoneDataModel.NUMBER) != null)
                holder.title.setText(items.get(position).get(PhoneDataModel.NUMBER).toString());
            else
                holder.title.setText("-");
            holder.subTitle.setText(items.get(position).get(PhoneDataModel.TYPE).toString());
        }
    }
}
