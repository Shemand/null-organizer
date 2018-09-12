package net.shemand.anull.controllers;

import net.shemand.anull.FrameManager;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.adapters.business.RVBusinessInfoAdapter;
import net.shemand.anull.adapters.contact.RVContactInfoAdapter;
import net.shemand.anull.adapters.task.RVTaskInfoAdapter;
import net.shemand.anull.fragments.business.FragmentBusinessInfo;
import net.shemand.anull.fragments.contact.FragmentContactInfo;
import net.shemand.anull.fragments.task.FragmentTaskInfo;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

/**
 * Created by Shema on 10.06.2018.
 */

public class InfoController {

    boolean replace;

    public InfoController(boolean replace) {
        this.replace = replace;
    }

    public void aboutContact(RVListAdapter rootAdapter, ContactDataModel model){
        RVInfoAdapter adapter = new RVContactInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentContactInfo(adapter), replace);
    }

    public void aboutBusiness(RVListAdapter rootAdapter, BusinessDataModel model) {
        RVInfoAdapter adapter = new RVBusinessInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentBusinessInfo(adapter), replace);
    }

    public void aboutTask(RVListAdapter rootAdapter, TaskDataModel model){
        RVInfoAdapter adapter = new RVTaskInfoAdapter(rootAdapter, model);
        FrameManager.getInstance().changeState(new FragmentTaskInfo(adapter), replace);
    }

}
