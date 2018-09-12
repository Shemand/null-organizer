package net.shemand.anull.adapters.task;

import android.view.View;

import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.adapters.object.RVObjectListAdapter;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 01.06.2018.
 */

public class RVTaskInfoAdapter extends RVInfoAdapter {
    public RVTaskInfoAdapter(RVListAdapter rootAdapter, BaseDataModel model) {
        super(rootAdapter, model);

        fieldsList.add(TaskDataModel.TITLE);
        if(model.get(TaskDataModel.CREATED_BY_MODEL) != null)
            fieldsList.add(TaskDataModel.CREATED_BY_MODEL);
        if(model.get(TaskDataModel.PARENT_MODEL) != null)
            fieldsList.add(TaskDataModel.PARENT_MODEL);
        fieldsList.add(TaskDataModel.IMPORTANT);
        fieldsList.add(TaskDataModel.CONTACTS);
        fieldsList.add(TaskDataModel.NOTE);
        fieldsList.add(TaskDataModel.DUE);
        fieldsList.add(TaskDataModel.STARTED);
        fieldsList.add(TaskDataModel.CREATED);
        fieldsList.add(TaskDataModel.ENDED);
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
                if(fieldsList.get(position) == TaskDataModel.CREATED_BY_MODEL) {
                    if((int) getModel().get(TaskDataModel.CREATED_BY_TYPE) == TaskDataModel.CREATED_BY_OBJECT)
                        holder.clauseText.setText(((ObjectDataModel)getModel().get(fieldsList.get(position))).get(ObjectDataModel.NAME).toString());
                    else if((int) getModel().get(TaskDataModel.CREATED_BY_TYPE) == TaskDataModel.CREATED_BY_CONTACT)
                        holder.clauseText.setText(((ContactDataModel)getModel().get(fieldsList.get(position))).get(ContactDataModel.FIRST_NAME).toString());
                } else if(fieldsList.get(position) == TaskDataModel.CONTACTS) {
                    holder.clauseText.setText("Закрепленных контактов " + ((ArrayList) getModel().get(TaskDataModel.CONTACTS)).size());
                } else if(fieldsList.get(position) == TaskDataModel.PARENT_MODEL){
                    holder.clauseText.setText(((TaskDataModel)getModel().get(fieldsList.get(position))).get(TaskDataModel.TITLE).toString());
                } else {
                    holder.clauseText.setText("Something strange");
                }
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
