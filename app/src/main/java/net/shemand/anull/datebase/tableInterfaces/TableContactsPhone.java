package net.shemand.anull.datebase.tableInterfaces;

/**
 * Created by Shema on 15.05.2018.
 */

public interface TableContactsPhone {

    String TABLE_CONTACTS_PHONE = "phone_contacts";

    String KEY_CONTACTS_PHONE_ID = "_id";
    String KEY_CONTACTS_PHONE_NUMBER = "phone_number";
    String KEY_CONTACTS_PHONE_TYPE   = "phone_type";
    String KEY_CONTACTS_PHONE_FK_CONTACT_ID = "contact_id";
}
