package net.shemand.anull.models.DataModels;

import android.content.ContentValues;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.dialogs.Dialogs;

/**
 * Created by Shema on 30.05.2018.
 */

public class AddrDataModel extends BaseDataModel {

    private int contactId;
    private String name;
    private String city;
    private String street;
    private String house_number;

    public final static int ID           = 1;
    public final static int NAME         = 2;
    public final static int CONTACT_ID   = 3;
    public final static int CITY         = 4;
    public final static int STREET       = 5;
    public final static int HOUSE_NUMBER = 6;

    public AddrDataModel(int id,
                         int contactId,
                         String name,
                         String city,
                         String street,
                         String house_number) {
        super(id);
        this.contactId = contactId;
        this.name = name;
        this.city = city;
        this.street = street;
        this.house_number = house_number;
    }

    @Override
    public Object get(int id) {
        switch(id){
            case ID:
                return this.getId();
            case NAME:
                return this.name;
            case CONTACT_ID:
                return this.contactId;
            case CITY:
                return this.city;
            case STREET:
                return this.street;
            case HOUSE_NUMBER:
                return this.house_number;
            default:
                Log.e("AddrDataModel.get", "Wrong ID parametr");
        }
        return null;
    }

    @Override
    public boolean set(int id, Object value) {
        switch(id){
            case ID:
                if(value instanceof Integer)
                    return setId((int) value);
                else if(value instanceof Long)
                    return setId(((Long) value).intValue());
                break;
            case NAME:
                if(value instanceof String)
                    return setName((String) value);
                break;
            case CONTACT_ID:
                if(value instanceof Integer)
                    return setContactId((int) value);
                else if(value instanceof Long)
                    return setContactId(((Long) value).intValue());
                break;
            case CITY:
                if(value instanceof String)
                    return setCity((String) value);
                break;
            case STREET:
                if(value instanceof String)
                    return setStreet((String) value);
                break;
            case HOUSE_NUMBER:
                if(value instanceof String)
                    return setHouseNumber((String) value);
                break;
            default:
                Log.e("AddrDataModel.set", "Wrong ID parametr");
                break;
        }
        return false;
    }

    @Override
    public String getFieldName(int id) {
        switch(id){
            case ID:
                return FrameManager.getMainContext().getResources().getText(R.string.addr_id).toString();
            case NAME:
                return FrameManager.getMainContext().getResources().getText(R.string.addr_name).toString();
            case CITY:
                return FrameManager.getMainContext().getResources().getText(R.string.addr_city).toString();
            case STREET:
                return FrameManager.getMainContext().getResources().getText(R.string.addr_street).toString();
            case HOUSE_NUMBER:
                return FrameManager.getMainContext().getResources().getText(R.string.addr_house_number).toString();
            default:
                Log.e("AddrData.getfieldName", "Wrong id parametr or parametr haven't name");
        }
        return "";
    }

    @Override
    public int getTypeById(int id){
        switch(id){
            case ID:
                return Dialogs.ID_NUMBER;
            case CONTACT_ID:
                return Dialogs.ID_NUMBER;
            case NAME:
                return Dialogs.ID_STRING;
            case CITY:
                return Dialogs.ID_STRING;
            case STREET:
                return Dialogs.ID_STRING;
            case HOUSE_NUMBER:
                return Dialogs.ID_STRING;
            default:
                Log.e("AddrData.getTypeById", "Wrong id parametr");
        }
        Log.e("AddrDataModel", "getTypeId. Wrong parametr");
        return 0;
    }

    public boolean setName(String name) {
        this.name = name;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_NAME, name);
        DB.usePlaces().update((int) get(AddrDataModel.ID), content);
        return true;
    }

    public boolean setContactId(int contactId) {
        this.contactId = contactId;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_FK_CONTACT_ID, contactId);
        DB.usePlaces().update(getId(), content);
        return true;
    }

    public boolean setCity(String city) {
        this.city = city;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_CITY, city);
        DB.usePlaces().update(getId(), content);
        return true;
    }

    public boolean setStreet(String street) {
        this.street = street;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_STREET, street);
        DB.usePlaces().update(getId(), content);
        return true;
    }

    public boolean setHouseNumber(String house_number) {
        this.house_number = house_number;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_HOUSE_NUMBER, house_number);
        DB.usePlaces().update(getId(), content);
        return true;
    }
}
