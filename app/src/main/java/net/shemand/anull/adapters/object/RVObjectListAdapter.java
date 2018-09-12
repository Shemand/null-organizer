package net.shemand.anull.adapters.object;

import android.graphics.Color;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 03.06.2018.
 */

public class RVObjectListAdapter extends RVListAdapter {
    public RVObjectListAdapter(ArrayList<? extends BaseDataModel> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.cv.setCardBackgroundColor((int) items.get(position).get(ObjectDataModel.COLOR));
        holder.title.setText(items.get(position).get(ObjectDataModel.NAME).toString());
        if(((ArrayList) items.get(position).get(ObjectDataModel.CONTACTS)).size() == 1) {
            holder.subTitle.setText("1 закрепленный контакт");
        }
        else {
            holder.subTitle.setText(((ArrayList) items.get(position).get(ObjectDataModel.CONTACTS)).size() + " закрепленных контактов");
        }
    }
}
