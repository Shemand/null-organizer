package net.shemand.anull.controllers;

import net.shemand.anull.FrameManager;
import net.shemand.anull.MainActivity;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.adapters.business.RVBusinessInfoAdapter;
import net.shemand.anull.adapters.contact.RVContactInfoAdapter;
import net.shemand.anull.adapters.contact.RVContactListAdapter;
import net.shemand.anull.adapters.object.RVObjectListAdapter;
import net.shemand.anull.adapters.task.RVTaskInfoAdapter;
import net.shemand.anull.fragments.business.FragmentBusinessAdd;
import net.shemand.anull.fragments.contact.FragmentContactAdd;
import net.shemand.anull.fragments.object.FragmentObjectContactsAdd;
import net.shemand.anull.fragments.task.FragmentTaskAdd;
import net.shemand.anull.fragments.task.FragmentTaskContactsAdd;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shema on 10.06.2018.
 */

public class AddController {

    boolean replace;

    public AddController(boolean replace) {
        this.replace = replace;
    }

    public void newBusiness(RVListAdapter rootAdapter) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeZone(MainActivity.timeZone);
        BusinessDataModel model = new BusinessDataModel(
                0,
                "Заголовок",
                BusinessDataModel.Important.IMPORTANT_URGENT,
                null,
                "",
                currentTime.getTimeInMillis(),
                currentTime.getTimeInMillis(),
                0,
                0);
        RVInfoAdapter adapter = new RVBusinessInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentBusinessAdd(adapter), replace);
    }

    public void newBusinessByModel(RVListAdapter rootAdapter, BaseDataModel createdByModel) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeZone(MainActivity.timeZone);
        BusinessDataModel model = new BusinessDataModel(
                0,
                "Заголовок",
                BusinessDataModel.Important.IMPORTANT_URGENT,
                createdByModel,
                "",
                currentTime.getTimeInMillis(),
                0,
                0,
                0);
        RVInfoAdapter adapter = new RVBusinessInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentBusinessAdd(adapter), replace);
    }

    public void newBusinessByTask(RVListAdapter rootAdapter, int taskId) {
        ContactDataModel model = new ContactDataModel(0, 0, "Имя", "", new ArrayList<AddrDataModel>(),
                new ArrayList<InternetDataModel>(),
                new ArrayList<PhoneDataModel>());
        RVInfoAdapter adapter = new RVContactInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentTaskContactsAdd(adapter, taskId), replace);
    }

    public void newContact(RVListAdapter rootAdapter) {
        ContactDataModel model = new ContactDataModel(0, 0, "Имя", "", new ArrayList<AddrDataModel>(),
                new ArrayList<InternetDataModel>(),
                new ArrayList<PhoneDataModel>());
        RVInfoAdapter adapter = new RVContactInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentContactAdd(adapter), replace);
    }

    public void newContactByObject(RVListAdapter rootAdapter, int objectId) {
        ContactDataModel model = new ContactDataModel(0, 0, "Имя", "", new ArrayList<AddrDataModel>(),
                new ArrayList<InternetDataModel>(),
                new ArrayList<PhoneDataModel>());
        RVInfoAdapter adapter = new RVContactInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentObjectContactsAdd(adapter, objectId), replace);
    }

    public void newContactByTask(RVListAdapter rootAdapter, int taskId) {
        ContactDataModel model = new ContactDataModel(0, 0, "Имя", "", new ArrayList<AddrDataModel>(),
                new ArrayList<InternetDataModel>(),
                new ArrayList<PhoneDataModel>());
        RVInfoAdapter adapter = new RVContactInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentTaskContactsAdd(adapter, taskId), replace);
    }

    public void newTask(RVListAdapter rootAdapter, TaskDataModel parentModel) {
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeZone(MainActivity.timeZone);
        TaskDataModel model = new TaskDataModel(0, "Заголовок", TaskDataModel.Important.IMPORTANT_URGENT,
                "", parentModel,null, new ArrayList<ContactDataModel>(),
                currentTime.getTimeInMillis(), 0, 0, 0);
        RVInfoAdapter adapter = new RVTaskInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentTaskAdd(adapter), replace);
    }

    public void newTaskByObject(RVObjectListAdapter rootAdapter, ObjectDataModel object){
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeZone(MainActivity.timeZone);
        TaskDataModel model = new TaskDataModel(0, "Заголовок", TaskDataModel.Important.IMPORTANT_URGENT,
                "", null, object, new ArrayList<ContactDataModel>(),
                currentTime.getTimeInMillis(), 0, 0, 0);
        RVInfoAdapter adapter = new RVTaskInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentTaskAdd(adapter), replace);
    }

    public void newTaskByContact(RVContactListAdapter rootAdapter, ContactDataModel contact){
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeZone(MainActivity.timeZone);
        TaskDataModel model = new TaskDataModel(0, "Заголовок", TaskDataModel.Important.IMPORTANT_URGENT,
                "", null, contact, new ArrayList<ContactDataModel>(),
                currentTime.getTimeInMillis(), 0, 0, 0);
        RVInfoAdapter adapter = new RVTaskInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentTaskAdd(adapter), replace);
    }

}
