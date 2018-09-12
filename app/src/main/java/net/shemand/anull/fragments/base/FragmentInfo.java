package net.shemand.anull.fragments.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import net.shemand.anull.FrameManager;
import net.shemand.anull.MainActivity;
import net.shemand.anull.R;
import net.shemand.anull.adapters.business.RVBusinessInfoAdapter;
import net.shemand.anull.adapters.contact.RVContactInfoAdapter;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.task.RVTaskInfoAdapter;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shema on 06.05.2018.
 */

public abstract class FragmentInfo extends FragmentBase implements RVInfoAdapter.OnCardActionListener {

    private RecyclerView rv;
    private RVInfoAdapter rv_adapter;
    private RecyclerView.LayoutManager rv_manager;

    private OnFragmentInteractionListener mListener;

    public FragmentInfo(RVInfoAdapter adapter) {
        rv_adapter = adapter;
        setTypeFloatingButton(FragmentBase.FLOATING_TYPE_DONE);
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
        setRetainInstance(true);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

    public View createRootView(View rootView) {
        rv = rootView.findViewById(R.id.business_rv);
        rv_manager = new LinearLayoutManager(getActivity());
        rv_adapter.setOnCardActionListener(this);
        rv.setAdapter(rv_adapter);
        rv.setLayoutManager(rv_manager);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void onCardClick(View v, final BaseDataModel model, final int fieldId) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
        Button okButton;
        Button cancelButton;
        final AlertDialog dialog;
        switch(model.getTypeById(fieldId)) {
            case Dialogs.ID_DATE:
                Button clearButton;
                dialog = Dialogs.create(getActivity(), Dialogs.ID_DATE);
                dialog.show();

                dialog.setTitle(model.getFieldName(fieldId));
                okButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                clearButton = dialog.getButton(Dialog.BUTTON_NEUTRAL);
                cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                DatePicker picker = dialog.findViewById(R.id.date_picker);

                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(MainActivity.timeZone);

                picker.init(picker.getYear(), picker.getMonth(), picker.getDayOfMonth(), new DatePicker.OnDateChangedListener (){
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar.set(year,monthOfYear,dayOfMonth);
                    }
                });
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        onSaveDate(model,fieldId, calendar.getTimeInMillis());
                        // todo someactions
                    }
                });
                clearButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        onSaveDate(model,fieldId,0);
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                break;
            case Dialogs.ID_NUMBER:
                dialog = Dialogs.create(getActivity(), Dialogs.ID_NUMBER);
                dialog.show();
                dialog.setTitle(model.getFieldName(fieldId));
                EditText numberField = dialog.findViewById(R.id.number_field);

                Log.e("Number", numberField.getText().toString());
                okButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);
                okButton.setTextColor(getResources().getColor(R.color.caldroid_light_red));
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        Toast.makeText(getActivity(),"Число изменено",Toast.LENGTH_LONG).show();
                        // todo add some action
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                break;
            case Dialogs.ID_STRING:
                dialog = Dialogs.create(getActivity(), Dialogs.ID_STRING);
                dialog.show();
                dialog.setTitle(model.getFieldName(fieldId));
                final EditText stringField = dialog.findViewById(R.id.text_area);
                okButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

                stringField.setText(model.get(fieldId).toString());
                stringField.setSelection(model.get(fieldId).toString().length());
                Log.d("String", stringField.getText().toString());
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        onSaveString(model, fieldId, stringField.getText().toString());
                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                break;
            case Dialogs.ID_LIST:
                dialog = Dialogs.create(getActivity(), Dialogs.ID_LIST);
                dialog.setTitle(model.getFieldName(fieldId));
                dialog.show();

                final Spinner spinnerView = dialog.findViewById(R.id.spinner);
                okButton = dialog.getButton(Dialog.BUTTON_POSITIVE);
                cancelButton = dialog.getButton(Dialog.BUTTON_NEGATIVE);

                if(model.get(fieldId) instanceof BusinessDataModel.Important)
                    spinnerView.setSelection(((BusinessDataModel.Important) model.get(fieldId)).ordinal());
                else
                    dialog.cancel();

                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                        onSaveList(model, fieldId, spinnerView.getSelectedItemPosition());

                    }
                });
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.cancel();
                    }
                });
                break;
            case Dialogs.ID_REFERENCE:
                if(getRv_adapter() instanceof RVContactInfoAdapter)
                    if(model.get(fieldId) instanceof ArrayList) {
                        if (fieldId == ContactDataModel.INTERNET_ADDRS) {
                            FrameManager.getInstance().changeFrame().toList().withInternets((ContactDataModel) getRv_adapter().getModel());
                        } else if (fieldId == ContactDataModel.PLACES) {
                            FrameManager.getInstance().changeFrame().toList().withPlaces((ContactDataModel) getRv_adapter().getModel());
                        } else if (fieldId == ContactDataModel.PHONES) {
                            FrameManager.getInstance().changeFrame().toList().withPhones((ContactDataModel) getRv_adapter().getModel());
                        } else
                            Log.e("FragmentInfo", "ID_REFERENCE, wrong model type of instance");
                    } else
                        Log.e("FragmentInfo", "ID_REFERENCE, model.get not an ArrayList");
                else if(getRv_adapter() instanceof RVTaskInfoAdapter) {
                    if (fieldId == TaskDataModel.CONTACTS) {
                        FrameManager.getInstance().changeFrame().toList().withContactsByTask((int) model.get(TaskDataModel.ID));
                    } else if (fieldId == TaskDataModel.CREATED_BY_MODEL) {
                        if (getRv_adapter().getModel().get(fieldId) instanceof ContactDataModel)
                            FrameManager.getInstance().changeFrame().toInfo().aboutContact(null, (ContactDataModel) getRv_adapter().getModel().get(TaskDataModel.CREATED_BY_MODEL));
//                        else if(getRv_adapter().getModel().get(fieldId) instanceof ObjectDataModel)
//                            FrameManager.getInstance().changeFrame().toList().withContacts((int) ((ObjectDataModel) getRv_adapter().getModel().get(TaskDataModel.PARENT_MODEL)).get(ObjectDataModel.ID));
                    } else if (fieldId == TaskDataModel.PARENT_MODEL)
                        if (getRv_adapter().getModel().get(TaskDataModel.PARENT_MODEL) != null)
                            FrameManager.getInstance().changeFrame().toInfo().aboutTask(null, (TaskDataModel) getRv_adapter().getModel().get(TaskDataModel.PARENT_MODEL));
                        else
                            Log.e("FragmentInfo", "onCardClick, wrong created by model type");
                    else
                        Log.e("FragmentInfo", "ID_REFERENCE, wrong model type of instance");
                }else if(getRv_adapter() instanceof RVBusinessInfoAdapter){
                    if(fieldId == BusinessDataModel.CREATED_BY_MODEL){
                        if (getRv_adapter().getModel().get(fieldId) instanceof ObjectDataModel){

                        } else if(getRv_adapter().getModel().get(fieldId) instanceof ContactDataModel) {
                            FrameManager.getInstance().changeFrame().toInfo().aboutContact(null, (ContactDataModel) getRv_adapter().getModel().get(BusinessDataModel.CREATED_BY_MODEL));
                        } else if(getRv_adapter().getModel().get(fieldId) instanceof TaskDataModel) {
                            FrameManager.getInstance().changeFrame().toInfo().aboutTask(null, (TaskDataModel) getRv_adapter().getModel().get(BusinessDataModel.CREATED_BY_MODEL));
                        } else
                            Log.e("FragmentInfo", "ID_REFERENCE, wrong model type on instance");
                    }
                } else
                    Log.e("FragmentInfo", "Adapter, not a instance of RVContactInfoAdapter");
                break;
            default:
                Log.e("Wrong switch argument","yeah");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int id);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.actions_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public RVInfoAdapter getRv_adapter() {
        return rv_adapter;
    }

    public void onSaveNumber(BaseDataModel model, int fieldId, int number) {

    }

    public void onSaveDate(BaseDataModel model, int fieldId, long date) {
        if(model.set(fieldId, date)){
            getRv_adapter().notifyDataSetChanged();
            Toast.makeText(getActivity(), "Date Changed", Toast.LENGTH_LONG).show();
        }
    }

    public void onSaveString(BaseDataModel model, int fieldId, String str) {
        if (model.set(fieldId, str)) {
            getRv_adapter().notifyDataSetChanged();
            Toast.makeText(getActivity(), "String changed", Toast.LENGTH_LONG).show();
        }
    }

    public void onSaveList(BaseDataModel model, int fieldId, int value){
        boolean flag = false;
        if(model.get(fieldId) instanceof BusinessDataModel.Important){
            if(model.set(fieldId, BusinessDataModel.Important.getByNumber(value))){
                getRv_adapter().notifyDataSetChanged();
                Toast.makeText(getActivity(), "List updated", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
