package net.shemand.anull.datebase.tableInterfaces;

/**
 * Created by Shema on 15.05.2018.
 */

public interface TableContactsPhysical {

    String TABLE_CONTACTS_ADDR = "physical_contacts";

    String KEY_CONTACTS_ADDR_ID = "_id";
    String KEY_CONTACTS_ADDR_NAME = "addr_name";
    String KEY_CONTACTS_ADDR_CITY = "addr_city";
    String KEY_CONTACTS_ADDR_STREET = "addr_street";
    String KEY_CONTACTS_ADDR_HOUSE_NUMBER = "addr_house_number";
    String KEY_CONTACTS_ADDR_FK_CONTACT_ID = "contact_id";

}
