package net.shemand.anull.adapters.contact;

import android.util.Log;
import android.view.View;

import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 30.05.2018.
 */

public class RVContactInfoAdapter extends RVInfoAdapter {
    public RVContactInfoAdapter(RVListAdapter rootAdapter, BaseDataModel model) {
        super(rootAdapter, model);

        fieldsList.add(ContactDataModel.FIRST_NAME);
        fieldsList.add(ContactDataModel.SECOND_NAME);
        fieldsList.add(ContactDataModel.PLACES);
        fieldsList.add(ContactDataModel.INTERNET_ADDRS);
        fieldsList.add(ContactDataModel.PHONES);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        holder.clauseName.setText(getModel().getFieldName(fieldsList.get(position)));
        if(getModel().get(fieldsList.get(position)) != null & getModel().get(fieldsList.get(position)) != "")
            if(getModel().getTypeById(fieldsList.get(position)) != Dialogs.ID_REFERENCE)
                holder.clauseText.setText(getModel().get(fieldsList.get(position)).toString());
            else
                if(getModel().get(fieldsList.get(position)) instanceof ArrayList)
                    if(((ArrayList) getModel().get(fieldsList.get(position))).size() > 0)
                        holder.clauseText.setText(((ArrayList) getModel().get(fieldsList.get(position))).size() + " элементов");
                    else
                        holder.clauseText.setText("0 элементов");
        else
            if(fieldsList.get(position) == ContactDataModel.SECOND_NAME)
                holder.clauseText.setText("any");

        holder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardListener.onCardClick(v,getModel(),fieldsList.get(position));
            }
        });
    }


}
