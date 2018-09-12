package net.shemand.anull.adapters.subContact;

import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.InternetDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 01.06.2018.
 */

public class RVInternetListAdapter extends RVListAdapter {

    public RVInternetListAdapter(ArrayList<InternetDataModel> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(items.get(position) instanceof InternetDataModel) {
            if (items.get(position).get(InternetDataModel.EMAIL) == null ||
                    items.get(position).get(InternetDataModel.EMAIL) == "") {
                holder.title.setText("-");
                holder.subTitle.setText("");
            } else {
                holder.title.setText(items.get(position).get(InternetDataModel.EMAIL).toString());
                holder.subTitle.setText("");
            }
        }
    }
}
