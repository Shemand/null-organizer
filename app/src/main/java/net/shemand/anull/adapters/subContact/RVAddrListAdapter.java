package net.shemand.anull.adapters.subContact;

import android.util.Log;

import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.AddrDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 01.06.2018.
 */

public class RVAddrListAdapter extends RVListAdapter {


    public RVAddrListAdapter(ArrayList<AddrDataModel> list) {
        super(list);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        if(items.get(position) instanceof AddrDataModel) {
            holder.title.setText(mapAddres(
                    items.get(position).get(AddrDataModel.CITY).toString() ,
                    items.get(position).get(AddrDataModel.STREET).toString(),
                    items.get(position).get(AddrDataModel.HOUSE_NUMBER).toString()));
            if(items.get(position).get(AddrDataModel.NAME) != "" && items.get(position).get(AddrDataModel.NAME) != null)
                holder.subTitle.setText(items.get(position).get(AddrDataModel.NAME).toString());
            else
                holder.subTitle.setText("-");
        } else {
            Log.e("RVAddrListAdapter", "onBindViewHolder. Wrong type of model");
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public String mapAddres(String city, String street, String house){
        StringBuilder str = new StringBuilder();
        if(city != "" && city != null){
            str.append(city);
            if(street != "" && street != null){
                str.append(", ").append(street);
                if(house != "" && house != null){
                    str.append(", ").append(house);
                }
            }
        }
        return str.toString();
    }
}
