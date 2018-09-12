package net.shemand.anull.fragments.subContact;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.dataBaseInterfaces.PlaceOperations;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.fragments.base.FragmentList;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;

/**
 * Created by Shema on 01.06.2018.
 */

public class FragmentAddrList extends FragmentList {

    private ContactDataModel model;

    private final static int CONTEXT_ITEM_DELETE = 1;
    private final static int CONTEXT_ITEM_EDIT = 2;

    public FragmentAddrList(RVListAdapter adapter, ContactDataModel model) {
        super(adapter);
        this.model = model;
        setHasFloatingButton(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return createRootView(rootView);
    }

    @Override
    public void onClickFloatingButton() {
        final AlertDialog dialog = Dialogs.create(getActivity(), Dialogs.ID_PLACE);
        dialog.show();
        dialog.setTitle(R.string.addr_common_name);

        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        final EditText name = dialog.findViewById(R.id.name_field);
        final EditText city = dialog.findViewById(R.id.city_field);
        final EditText street = dialog.findViewById(R.id.street_field);
        final EditText houseNumber = dialog.findViewById(R.id.house_field);

        name.setText("");
        city.setText("");
        street.setText("");
        houseNumber.setText("");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddrDataModel newModel = new AddrDataModel(0, (int) getModel().get(ContactDataModel.ID), name.getText().toString(),
                                                                                                             city.getText().toString(),
                                                                                                             street.getText().toString(),
                                                                                                             houseNumber.getText().toString());
                long id = model.addPlace(newModel);
                newModel.set(AddrDataModel.ID, id == -1 ? 0 : id);
                getRv_adapter().addItem(newModel);
                Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_place_add), Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_place_list).toString();
    }

    @Override
    public void onCardClick(View v, final BaseDataModel model) {
        onEditPlace((AddrDataModel) model);
    }

    public ContactDataModel getModel() {
        return model;
    }

    public void onCreateCardContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
        menu.setHeaderTitle(FrameManager.getMainContext().getResources().getText(R.string.context_place_title).toString() + " " + getRv_adapter().items.get(getContextItemPosition()).get(AddrDataModel.NAME));
        menu.add(Menu.NONE, CONTEXT_ITEM_EDIT, Menu.NONE, "Редактировать");
        menu.add(Menu.NONE, CONTEXT_ITEM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch(item.getItemId()){
            case CONTEXT_ITEM_EDIT:
                onEditPlace((AddrDataModel) getRv_adapter().items.get(getContextItemPosition()));
                return true;
            case CONTEXT_ITEM_DELETE:
                onDeleteItem(getContextItemPosition());
                return true;
        }
        return false;
    }

    public void onDeleteItem(int position) {
        DB.usePlaces().delete((int) getRv_adapter().items.get(position).get(AddrDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_place_del), Toast.LENGTH_SHORT).show();
    }

    public void onEditPlace(final AddrDataModel model) {
        final AlertDialog dialog = Dialogs.create(getActivity(), Dialogs.ID_PLACE);
        dialog.show();
        dialog.setTitle(R.string.addr_common_name);
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        final EditText name = dialog.findViewById(R.id.name_field);
        final EditText city = dialog.findViewById(R.id.city_field);
        final EditText street = dialog.findViewById(R.id.street_field);
        final EditText houseNumber = dialog.findViewById(R.id.house_field);

        name.setText(model.get(AddrDataModel.NAME).toString());
        city.setText(model.get(AddrDataModel.CITY).toString());
        street.setText(model.get(AddrDataModel.STREET).toString());
        houseNumber.setText(model.get(AddrDataModel.HOUSE_NUMBER).toString());

        name.setSelection(model.get(AddrDataModel.NAME).toString().length());
        city.setSelection(model.get(AddrDataModel.CITY).toString().length());
        street.setSelection(model.get(AddrDataModel.STREET).toString().length());
        houseNumber.setSelection(model.get(AddrDataModel.HOUSE_NUMBER).toString().length());

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.set(AddrDataModel.NAME, name.getText().toString());
                model.set(AddrDataModel.CITY, city.getText().toString());
                model.set(AddrDataModel.STREET, street.getText().toString());
                model.set(AddrDataModel.HOUSE_NUMBER, houseNumber.getText().toString());

                getRv_adapter().notifyDataSetChanged();
                dialog.cancel();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.cancel();
                }
            });
    }
}
