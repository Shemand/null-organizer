package net.shemand.anull.adapters.base;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.shemand.anull.FrameManager;
import net.shemand.anull.MainActivity;
import net.shemand.anull.R;
import net.shemand.anull.models.DataModels.BaseDataModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shema on 30.04.2018.
 */

public abstract class RVListAdapter extends RecyclerView.Adapter<RVListAdapter.ViewHolder> {

    private RVListAdapter rootAdapter;
    public ArrayList<BaseDataModel> items = new ArrayList<>();

    private OnCardActionListener cardListener;

    public RVListAdapter(ArrayList<? extends BaseDataModel> list) {
        this.items.addAll(list);
    }
    public RVListAdapter(RVListAdapter rootAdapter, ArrayList<? extends BaseDataModel> list) {
        this.rootAdapter = rootAdapter;
        this.items.addAll(list);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.business_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.cv.setCardBackgroundColor(FrameManager.getMainContext().getResources().getColor(R.color.colorPrimary));
        holder.cv.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                cardListener.onCardClick(v, items.get(position));
            }
        });

        holder.cv.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View v) {
                cardListener.onCardLongClick(v, position);
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public interface OnCardActionListener {
        public void onCardClick(View v, BaseDataModel model);
        public void onCardLongClick(View v, int position);
        public void onCreateCardContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo  );
    }

    public void setOnCardActionListener(OnCardActionListener listener){
        cardListener = listener;
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public CardView cv;
        public TextView title;
        public TextView subTitle;
        public ViewHolder(View item) {
            super(item);
            cv = (CardView) item.findViewById(R.id.list_cv);
            title = (TextView) item.findViewById(R.id.business_title);
            subTitle = (TextView) item.findViewById(R.id.business_subTitle);
            item.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            cardListener.onCreateCardContextMenu(menu, v, menuInfo);
        }

    }

    public void addItem(BaseDataModel model){
        items.add(model);
        this.notifyDataSetChanged();
    }

    public RVListAdapter getRootAdapter() {
        return rootAdapter;
    }

    public String mapDate(long mSeconds){
        Calendar date = Calendar.getInstance();
        date.setTimeZone(MainActivity.timeZone);
        date.setTimeInMillis(mSeconds);
        String[] monthNames = FrameManager.getMainContext().getResources().getStringArray(R.array.month_names);
        StringBuilder str = new StringBuilder();
        str.append(date.get(Calendar.DAY_OF_MONTH)).append(" ");
        str.append(monthNames[date.get(Calendar.MONTH)]).append(" ");
        str.append(date.get(Calendar.YEAR));
        return str.toString();
    }
}
