package net.shemand.anull.fragments.business;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.base.FragmentList;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentBusinessList extends FragmentList{

    private final static int CONTEXT_ITEM_DELETE = 1;
    private final static int CONTEXT_ITEM_LOOK = 2;

    private Calendar date;

    public FragmentBusinessList(RVListAdapter adapter, Calendar date) {
        super(adapter);
        setHasFloatingButton(true);
        this.date = date;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return createRootView(rootView);
    }

    @Override
    public void onClickFloatingButton() {
        FrameManager.getInstance().changeFrame().toAdd().newBusiness(getRv_adapter());
    }

    @Override
    public String getFragmentTitle() {
        return "Дела: " + getRv_adapter().mapDate(date.getTimeInMillis());
    }

    @Override
    public void onCardClick(View v, BaseDataModel model) {
        super.onCardClick(v, model);
        FrameManager.getInstance().changeFrame().toInfo().aboutBusiness(getRv_adapter(),(BusinessDataModel) model);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.drawer_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void onCreateCardContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
        menu.setHeaderTitle(FrameManager.getMainContext().getResources().getText(R.string.context_business_title).toString() + " " + getRv_adapter().items.get(getContextItemPosition()).get(BusinessDataModel.TITLE));
        menu.add(Menu.NONE, CONTEXT_ITEM_LOOK, Menu.NONE, "Просмотр дела");
        menu.add(Menu.NONE, CONTEXT_ITEM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch(item.getItemId()){
            case CONTEXT_ITEM_LOOK:
                FrameManager.getInstance().changeFrame().toInfo().aboutBusiness(getRv_adapter(),(BusinessDataModel) getRv_adapter().items.get(getContextItemPosition()));
            case CONTEXT_ITEM_DELETE:
                onDeleteItem(getContextItemPosition());
                return true;
        }
        return false;
    }

    public void onDeleteItem(int position) {
        DB.useBusiness().delete((int) getRv_adapter().items.get(position).get(BusinessDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_business_del), Toast.LENGTH_SHORT).show();
    }
}
