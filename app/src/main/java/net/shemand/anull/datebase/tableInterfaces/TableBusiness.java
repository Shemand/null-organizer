package net.shemand.anull.datebase.tableInterfaces;

/**
 * Created by Shema on 15.05.2018.
 */

public interface TableBusiness {

    String TABLE_BUSINESS = "Business";

    String KEY_BUSINESS_ID = "_id";
    String KEY_BUSINESS_TITLE = "business_title";
    String KEY_BUSINESS_NOTE = "business_note";
    String KEY_BUSINESS_IMPORTANT = "business_important";
    String KEY_BUSINESS_CREATED = "business_created";
    String KEY_BUSINESS_STARTED = "business_started";
    String KEY_BUSINESS_DUE = "business_due";
    String KEY_BUSINESS_ENDED = "business_ended";
    String KEY_BUSINESS_FK_TASK_ID = "task_id";
    String KEY_BUSINESS_FK_OBJECT_ID = "object_id";
    String KEY_BUSINESS_FK_CONTACT_ID = "contact_id";
}
