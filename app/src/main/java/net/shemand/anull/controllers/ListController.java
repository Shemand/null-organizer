package net.shemand.anull.controllers;

import net.shemand.anull.FrameManager;
import net.shemand.anull.MainActivity;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.adapters.business.RVBusinessListAdapter;
import net.shemand.anull.adapters.contact.RVContactListAdapter;
import net.shemand.anull.adapters.object.RVObjectListAdapter;
import net.shemand.anull.adapters.subContact.RVAddrListAdapter;
import net.shemand.anull.adapters.subContact.RVInternetListAdapter;
import net.shemand.anull.adapters.subContact.RVPhoneListAdapter;
import net.shemand.anull.adapters.task.RVTaskListAdapter;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.fragments.business.FragmentBusinessList;
import net.shemand.anull.fragments.contact.FragmentContactList;
import net.shemand.anull.fragments.object.FragmentObjectContactList;
import net.shemand.anull.fragments.object.FragmentObjectList;
import net.shemand.anull.fragments.subContact.FragmentAddrList;
import net.shemand.anull.fragments.subContact.FragmentInternetList;
import net.shemand.anull.fragments.subContact.FragmentPhoneList;
import net.shemand.anull.fragments.task.FragmentTaskContactsList;
import net.shemand.anull.fragments.task.FragmentTaskList;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shema on 10.06.2018.
 */

public class ListController {

    boolean replace;

    public ListController(boolean replace) {
        this.replace = replace;
    }

    public void withBusiness() {
        Calendar date = Calendar.getInstance();
        date.setTimeZone(MainActivity.timeZone);
        RVListAdapter adapter = new RVBusinessListAdapter(DB.useBusiness().getList(0));
        FrameManager.getInstance().changeState(new FragmentBusinessList(adapter, date), replace);
    }

    public void withBusiness(Calendar date) {
        RVListAdapter adapter = new RVBusinessListAdapter(DB.useBusiness().getList(date.getTimeInMillis()));
        FrameManager.getInstance().changeState(new FragmentBusinessList(adapter, date), replace);
    }

    public void withContacts(){
        RVListAdapter adapter = new RVContactListAdapter(DB.useContacts().getList());
        FrameManager.getInstance().changeState(new FragmentContactList(adapter), replace);
    };

    public void withContacts(ArrayList<ContactDataModel> models){
        RVListAdapter adapter = new RVContactListAdapter(models);
        FrameManager.getInstance().changeState(new FragmentContactList(adapter), replace);
    };

    public void withContactsByObject(int objectId) {
        RVListAdapter adapter = new RVContactListAdapter(DB.useContacts().getListByObject(objectId));
        FrameManager.getInstance().changeState(new FragmentObjectContactList(adapter, objectId), replace);
    }

    public void withContactsByTask(int taskId) {
        RVListAdapter adapter = new RVContactListAdapter(DB.useContacts().getListByTask(taskId));
        FrameManager.getInstance(). changeState(new FragmentTaskContactsList(adapter, taskId), replace);
    }

    public void withPlaces(ContactDataModel model) {
        RVListAdapter adapter = new RVAddrListAdapter((ArrayList<AddrDataModel>) model.get(ContactDataModel.PLACES));
        FrameManager.getInstance().changeState( new FragmentAddrList(adapter, model), replace);
    }

    public void withInternets(ContactDataModel model) {
        RVListAdapter adapter = new RVInternetListAdapter((ArrayList<InternetDataModel>) model.get(ContactDataModel.INTERNET_ADDRS));
        FrameManager.getInstance().changeState(new FragmentInternetList(adapter, model), replace);
    }

    public void withPhones(ContactDataModel model) {
        RVListAdapter adapter = new RVPhoneListAdapter((ArrayList<PhoneDataModel>) model.get(ContactDataModel.PHONES));
        FrameManager.getInstance().changeState(new FragmentPhoneList(adapter, model), replace);
    }

    public void withTasks(){
        RVListAdapter adapter = new RVTaskListAdapter(DB.useTasks().getList());
        FrameManager.getInstance().changeState(new FragmentTaskList(adapter), replace);
    }

    public void withObjects(){
        RVListAdapter adapter = new RVObjectListAdapter(DB.useObjects().getList());
        FrameManager.getInstance().changeState(new FragmentObjectList(adapter), replace);
    }

}
