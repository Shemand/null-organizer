package net.shemand.anull.datebase.tableInterfaces;

/**
 * Created by Shema on 15.05.2018.
 */

public interface TableTaskHasContacts {

    String TABLE_TASK_HAS_CONTACTS = "tasks_has_contacts";

    String KEY_THC_ID = "_id";
    String KEY_THC_FK_CONTACT_ID = "contact_id";
    String KEY_THC_FK_TASK_ID = "task_id";
}
