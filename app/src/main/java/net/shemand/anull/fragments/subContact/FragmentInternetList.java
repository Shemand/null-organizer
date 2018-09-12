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
import net.shemand.anull.datebase.tableInterfaces.TableContactsInternet;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.fragments.base.FragmentList;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;

/**
 * Created by Shema on 01.06.2018.
 */

public class FragmentInternetList extends FragmentList {

    private ContactDataModel model;

    private final static int CONTEXT_ITEM_DELETE = 1;
    private final static int CONTEXT_ITEM_EDIT = 2;

    public FragmentInternetList(RVListAdapter adapter, ContactDataModel model) {
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
        final AlertDialog dialog = Dialogs.create(getActivity(), Dialogs.ID_STRING);
        dialog.show();
        dialog.setTitle(R.string.internet_common_name);

        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        final EditText email = dialog.findViewById(R.id.text_area);

        email.setText("");

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InternetDataModel newModel = new InternetDataModel(0, (int) model.get(ContactDataModel.ID), email.getText().toString());
                long id = model.addInternet(newModel);
                newModel.set(InternetDataModel.ID, id == -1 ? 0 : id);
                getRv_adapter().addItem(newModel);
                Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_internets_add), Toast.LENGTH_SHORT).show();
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
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_internet_list).toString();
    }

    @Override
    public void onCardClick(View v, final BaseDataModel model) {
        onEditInternet((InternetDataModel) model);
    }

    public void onCreateCardContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
        menu.setHeaderTitle(FrameManager.getMainContext().getResources().getText(R.string.context_internets_title).toString() + " " + getRv_adapter().items.get(getContextItemPosition()).get(InternetDataModel.EMAIL));
        menu.add(Menu.NONE, CONTEXT_ITEM_EDIT, Menu.NONE, "Редактировать");
        menu.add(Menu.NONE, CONTEXT_ITEM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch(item.getItemId()){
            case CONTEXT_ITEM_DELETE:
                onDeleteItem(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_EDIT:
                onEditInternet((InternetDataModel) getRv_adapter().items.get(getContextItemPosition()));
                return true;
        }
        return false;
    }

    private void onEditInternet(final InternetDataModel model) {
        final AlertDialog dialog = Dialogs.create(getActivity(), Dialogs.ID_STRING);
        dialog.show();
        dialog.setTitle(R.string.internet_common_name);
        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        final EditText field = dialog.findViewById(R.id.text_area);
        field.setText(model.get(InternetDataModel.EMAIL).toString());
        field.setSelection(model.get(InternetDataModel.EMAIL).toString().length());
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                model.set(InternetDataModel.EMAIL, field.getText().toString());
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

    public void onDeleteItem(int position) {
        DB.useInternets().delete((int) getRv_adapter().items.get(position).get(InternetDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_internets_del), Toast.LENGTH_SHORT).show();
    }
}
