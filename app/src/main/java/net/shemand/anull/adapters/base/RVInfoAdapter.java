package net.shemand.anull.adapters.base;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.shemand.anull.FrameManager;
import net.shemand.anull.MainActivity;
import net.shemand.anull.R;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.models.DataModels.BaseDataModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shema on 30.04.2018.
 */

public abstract class RVInfoAdapter extends RecyclerView.Adapter<RVInfoAdapter.ViewHolder> {

    RVListAdapter rootAdapter;
    BaseDataModel model;
    public ArrayList<Integer> fieldsList = new ArrayList<>();

    public RVInfoAdapter.OnCardActionListener cardListener;

    public RVInfoAdapter(RVListAdapter rootAdapter, BaseDataModel model) {
        this.rootAdapter = rootAdapter;
        this.model = model;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_info_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.image.setImageAlpha(50);

        if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_DATE)
            holder.image.setImageResource(R.drawable.ic_calendar_24dp);
        else if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_NUMBER)
            holder.image.setImageResource(R.drawable.ic_string_24dp);
        else if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_STRING)
            holder.image.setImageResource(R.drawable.ic_string_24dp);
        else if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_LIST)
            holder.image.setImageResource(R.drawable.ic_list_24dp);
        else if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_REFERENCE)
            holder.image.setImageResource(R.drawable.ic_reference_2_24dp);
        else if(getModel().getTypeById(fieldsList.get(position)) == Dialogs.ID_BOOLEAN)
            holder.image.setImageResource(R.drawable.ic_done_black_24dp);
    }

    @Override
    public int getItemCount() {
        return fieldsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public CardView cv;
        public ImageView image;
        public TextView clauseName;
        public TextView clauseText;

        public ViewHolder(View item) {
            super(item);
            cv = item.findViewById(R.id.info_cv);
            image = item.findViewById(R.id.clause_image);
            clauseName = item.findViewById(R.id.clause_name);
            clauseText = item.findViewById(R.id.clause_text);
        }

    }

    public interface OnCardActionListener {
        public void onCardClick(View v, BaseDataModel model, int fieldId);
    }

    public void setOnCardActionListener(RVInfoAdapter.OnCardActionListener listener){
        cardListener = listener;
    }

    public BaseDataModel getModel() {
        return model;
    }

    public RVListAdapter getRootAdapter() {
        return rootAdapter;
    }

    public String mapDate(long mSeconds){
        Calendar date = Calendar.getInstance();
        date.setTimeZone(MainActivity.timeZone);
        date.setTimeInMillis(mSeconds);
        String[] weekDays = FrameManager.getMainContext().getResources().getStringArray(R.array.weeks_day);
        String[] monthNames = FrameManager.getMainContext().getResources().getStringArray(R.array.month_names);
        StringBuilder str = new StringBuilder();
        str.append(weekDays[date.get(Calendar.DAY_OF_WEEK)-1]).append(", ");
        str.append(date.get(Calendar.DAY_OF_MONTH)).append(" ");
        str.append(monthNames[date.get(Calendar.MONTH)]).append(" ");
        str.append(date.get(Calendar.YEAR));
        return str.toString();
    }
}
