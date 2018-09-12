package net.shemand.anull.fragments.object;

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
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.adapters.object.RVObjectListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.tableInterfaces.TableObjects;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.fragments.base.FragmentList;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 03.06.2018.
 */

public class FragmentObjectList extends FragmentList {

    public final static int CONTEXT_ITEM_DELETE = 1;
    public final static int CONTEXT_ITEM_ADD_TASK = 2;
    public final static int CONTEXT_ITEM_ADD_BUSINESS = 3;
    public final static int CONTEXT_ITEM_LOOK = 4;
    public final static int CONTEXT_ITEM_EDIT_OBJECT = 5;


    public FragmentObjectList(RVListAdapter adapter) {
        super(adapter);
        setHasFloatingButton(true);
        setHasOptionsMenu(false);
    }

    @Override
    public void onClickFloatingButton() {
        final AlertDialog dialog = Dialogs.create(getActivity(), Dialogs.ID_OBJECT);
        dialog.show();

        dialog.setTitle("Создание объекта");

        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        final EditText name = dialog.findViewById(R.id.object_name);
        final RadioGroup group = dialog.findViewById(R.id.object_color_group);

        name.setText("");
        name.setSelection(0);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectDataModel newModel = new ObjectDataModel(0, name.getText().toString(), 4512, new ArrayList<ContactDataModel>());
                switch(group.getCheckedRadioButtonId()){
                    case R.id.object_color_1:
                        newModel.set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_1));
                        break;
                    case R.id.object_color_2:
                        newModel.set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_2));
                        break;
                    case R.id.object_color_3:
                        newModel.set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_3));
                        break;
                    case R.id.object_color_4:
                        newModel.set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_4));
                        break;
                    default:
                        newModel.set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_1));
                        Log.e("bez", "cveta");
                }
                long id = DB.useObjects().add(newModel);
                newModel.set(ObjectDataModel.ID, id == -1 ? 0 : id);
                getRv_adapter().addItem(newModel);
                Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_object_add), Toast.LENGTH_SHORT).show();
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
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_object_list).toString();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        return createRootView(rootView);
    }

    @Override
    public void onCardClick(View v, BaseDataModel model) {
        super.onCardClick(v, model);
        FrameManager.getInstance().changeFrame().toList().withContactsByObject((int) model.get(ObjectDataModel.ID));
    }


    public void onCreateCardContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
        menu.setHeaderTitle(FrameManager.getMainContext().getResources().getText(R.string.context_object_title).toString() + " " + getRv_adapter().items.get(getContextItemPosition()).get(ObjectDataModel.NAME));
        menu.add(Menu.NONE, CONTEXT_ITEM_LOOK, Menu.NONE, "Просмотр закрпленных контактов");
        menu.add(Menu.NONE, CONTEXT_ITEM_EDIT_OBJECT, Menu.NONE, "Редактировать объект");
        menu.add(Menu.NONE, CONTEXT_ITEM_ADD_TASK, Menu.NONE, "Создать задачу");
        menu.add(Menu.NONE, CONTEXT_ITEM_ADD_BUSINESS, Menu.NONE, "Создать дело");
        menu.add(Menu.NONE, CONTEXT_ITEM_DELETE, Menu.NONE, "Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        super.onContextItemSelected(item);
        switch(item.getItemId()){
            case CONTEXT_ITEM_DELETE:
                onDeleteItem(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_ADD_BUSINESS:
                onAddBusiness(getContextItemPosition());
                return true;
            case CONTEXT_ITEM_LOOK:
                FrameManager.getInstance().changeFrame().toList().withContactsByObject((int) getRv_adapter().items.get(getContextItemPosition()).get(ObjectDataModel.ID));
                return true;
            case CONTEXT_ITEM_EDIT_OBJECT:
                onEditObject(getContextItemPosition());
                return true;
                case CONTEXT_ITEM_ADD_TASK:
            onAddTask(getContextItemPosition());
                return true;
        }
        return false;
    }

    public void onAddTask(int position) {
        FrameManager.getInstance().changeFrame().toAdd().newTaskByObject((RVObjectListAdapter) getRv_adapter(), (ObjectDataModel) getRv_adapter().items.get(position));
    }

    public void onDeleteItem(int position) {
        DB.useObjects().delete((int) getRv_adapter().items.get(position).get(ObjectDataModel.ID));
        getRv_adapter().items.remove(position);
        getRv_adapter().notifyDataSetChanged();
        Toast.makeText(getActivity(), FrameManager.getMainContext().getResources().getText(R.string.toast_object_del), Toast.LENGTH_SHORT).show();
    }

    private void onAddBusiness(int position){
        FrameManager.getInstance().changeFrame().toAdd().newBusinessByModel(getRv_adapter(), getRv_adapter().items.get(position));
    }

    private void onEditObject(final int position) {
        final AlertDialog dialog = Dialogs.create(getActivity(), Dialogs.ID_OBJECT);
        dialog.show();
        dialog.setTitle("Редактрование объекта: " + getRv_adapter().items.get(position).get(ObjectDataModel.NAME));

        Button okButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        Button cancelButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        final EditText name = dialog.findViewById(R.id.object_name);
        final RadioGroup group = dialog.findViewById(R.id.object_color_group);

        name.setText((String) getRv_adapter().items.get(position).get(ObjectDataModel.NAME));
        name.setSelection(((String) getRv_adapter().items.get(position).get(ObjectDataModel.NAME)).length());

        if((int) getRv_adapter().items.get(position).get(ObjectDataModel.COLOR) == getResources().getColor(R.color.object_1))
            group.check(R.id.object_color_1);
        else if((int) getRv_adapter().items.get(position).get(ObjectDataModel.COLOR) == getResources().getColor(R.color.object_2))
            group.check(R.id.object_color_2);
        else if((int) getRv_adapter().items.get(position).get(ObjectDataModel.COLOR) == getResources().getColor(R.color.object_3))
            group.check(R.id.object_color_3);
        else if((int) getRv_adapter().items.get(position).get(ObjectDataModel.COLOR) == getResources().getColor(R.color.object_4))
            group.check(R.id.object_color_4);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getRv_adapter().items.get(position).set(ObjectDataModel.NAME, name.getText().toString());
                switch(group.getCheckedRadioButtonId()){
                    case R.id.object_color_1:
                        getRv_adapter().items.get(position).set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_1));
                        break;
                    case R.id.object_color_2:
                        getRv_adapter().items.get(position).set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_2));
                        break;
                    case R.id.object_color_3:
                        getRv_adapter().items.get(position).set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_3));
                        break;
                    case R.id.object_color_4:
                        getRv_adapter().items.get(position).set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_4));
                        break;
                    default:
                        getRv_adapter().items.get(position).set(ObjectDataModel.COLOR, getResources().getColor(R.color.object_1));
                        Log.e("bez", "cveta");
                }
                getRv_adapter().notifyDataSetChanged();
                Toast.makeText(getContext(), FrameManager.getMainContext().getResources().getText(R.string.toast_object_edited), Toast.LENGTH_SHORT).show();
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
