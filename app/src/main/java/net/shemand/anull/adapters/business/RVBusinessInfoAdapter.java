package net.shemand.anull.adapters.business;

import android.util.Log;
import android.view.View;

import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

/**
 * Created by Shema on 24.05.2018.
 */

public class RVBusinessInfoAdapter extends RVInfoAdapter {

    public RVBusinessInfoAdapter(RVListAdapter rootAdapter, BaseDataModel model) {
        super(rootAdapter, model);

        fieldsList.add(BusinessDataModel.TITLE);
        if(model.get(BusinessDataModel.CREATED_BY_MODEL) != null)
            fieldsList.add(BusinessDataModel.CREATED_BY_MODEL);
        fieldsList.add(BusinessDataModel.NOTE);
        fieldsList.add(BusinessDataModel.IMPORTANT);
        fieldsList.add(BusinessDataModel.CREATED);
        fieldsList.add(BusinessDataModel.STARTED);
        fieldsList.add(BusinessDataModel.DUE);
        fieldsList.add(BusinessDataModel.ENDED);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);

        holder.clauseName.setText(getModel().getFieldName(fieldsList.get(position)));
        if(getModel().get(fieldsList.get(position)) != null && getModel().get(fieldsList.get(position)) != "")
            if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_DATE) {
                if (((long) getModel().get(fieldsList.get(position))) != 0)
                    holder.clauseText.setText(mapDate((long) getModel().get(fieldsList.get(position))));
                else
                    holder.clauseText.setText("-");
            } else if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_REFERENCE) {
                if (getModel().get(fieldsList.get(position)) != null)
                    if(getModel().get(fieldsList.get(position)) instanceof ObjectDataModel)
                        holder.clauseText.setText(((ObjectDataModel)getModel().get(BusinessDataModel.CREATED_BY_MODEL)).get(ObjectDataModel.NAME).toString());
                    else if(getModel().get(fieldsList.get(position)) instanceof ContactDataModel)
                        holder.clauseText.setText(((ContactDataModel)getModel().get(BusinessDataModel.CREATED_BY_MODEL)).get(ContactDataModel.FIRST_NAME).toString());
                    else if(getModel().get(fieldsList.get(position)) instanceof TaskDataModel)
                        holder.clauseText.setText(((TaskDataModel)getModel().get(BusinessDataModel.CREATED_BY_MODEL)).get(TaskDataModel.TITLE).toString());
                    else
                        Log.e("onBindViewHolder","BusinessInfo. Undefined type");
                else
                    Log.e("onBindViewHolder", "BusinessInfo. null reference");
            } else
                holder.clauseText.setText(getModel().get(fieldsList.get(position)).toString()); // todo check
        else
            holder.clauseText.setText("-");

        holder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cardListener.onCardClick(v, getModel(), fieldsList.get(position));
            }
        });
    }
}
