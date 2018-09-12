package net.shemand.anull.models.DataModels;

import android.content.ContentValues;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.dataBaseInterfaces.ContactOperations;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.dialogs.Dialogs;

import java.util.ArrayList;

import static net.shemand.anull.models.DataModels.AddrDataModel.CITY;

/**
 * Created by Shema on 08.05.2018.
 */

public class ContactDataModel extends BaseDataModel {

    private String firstName;
    private String secondName;
    private int objectId;
    private ArrayList<AddrDataModel> places;
    private ArrayList<InternetDataModel> internetAddrs;
    private ArrayList<PhoneDataModel> phones;

    public final static int ID             = 1;
    public final static int FIRST_NAME     = 2;
    public final static int SECOND_NAME    = 3;
    public final static int OBJECT_ID      = 4;
    public final static int PLACES         = 5;
    public final static int INTERNET_ADDRS = 6;
    public final static int PHONES         = 7;

    public ContactDataModel(int id,
                            int objectId,
                            String firstName,
                            String secondName,
                            ArrayList<AddrDataModel> places,
                            ArrayList<InternetDataModel> internetAddrs,
                            ArrayList<PhoneDataModel> phones) {
        super(id);
        this.objectId = objectId;
        this.firstName = firstName;
        this.secondName = secondName;
        this.places = places;
        this.internetAddrs = internetAddrs;
        this.phones = phones;
    }

    @Override
    public Object get(int id) {
        switch(id){
            case ID:
                return this.getId();
            case OBJECT_ID:
                return this.objectId;
            case FIRST_NAME:
                return this.firstName;
            case SECOND_NAME:
                return this.secondName;
            case PLACES:
                return this.places;
            case INTERNET_ADDRS:
                return this.internetAddrs;
            case PHONES:
                return this.phones;
            default:
                Log.e("ContactDataModel.get", "Wrong ID parametr");
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
            case OBJECT_ID:
                if(value instanceof Integer)
                    return setObjectId((int) value);
                else if(value instanceof Long)
                    return setObjectId(((Long) value).intValue());
                break;
            case FIRST_NAME:
                if(value instanceof String)
                    return setFirstName((String) value);
                break;
            case SECOND_NAME:
                if(value instanceof String)
                    return setSecondName((String) value);
                break;
            default:
                Log.e("ContactDataModel.set", "Wrong ID parametr");
                break;
        }
        return false;
    }

    @Override
    public String getFieldName(int id) {
        switch(id){
            case ID:
                return FrameManager.getMainContext().getResources().getText(R.string.contact_id).toString();
            case FIRST_NAME:
                return FrameManager.getMainContext().getResources().getText(R.string.contact_first_name).toString();
            case SECOND_NAME:
                return FrameManager.getMainContext().getResources().getText(R.string.contact_second_name).toString();
            case PLACES:
                return FrameManager.getMainContext().getResources().getText(R.string.contact_places_list).toString();
            case INTERNET_ADDRS:
                return FrameManager.getMainContext().getResources().getText(R.string.contact_internet_list).toString();
            case PHONES:
                return FrameManager.getMainContext().getResources().getText(R.string.contact_phones_list).toString();
            default:
                Log.e("ContactData", "getfieldName: Wrong id parametr or parametr haven't name");
        }
        return "";
    }

    @Override
    public int getTypeById(int id){
        switch(id){
            case ID:
                return Dialogs.ID_NUMBER;
            case OBJECT_ID:
                return Dialogs.ID_NUMBER;
            case FIRST_NAME:
                return Dialogs.ID_STRING;
            case SECOND_NAME:
                return Dialogs.ID_STRING;
            case PLACES:
                return Dialogs.ID_REFERENCE;
            case INTERNET_ADDRS:
                return Dialogs.ID_REFERENCE;
            case PHONES:
                return Dialogs.ID_REFERENCE;
        }
        Log.e("ContactDataModel", "getTypeId. Wrong parametr");
        return 0;
    }

    public long addPlace(AddrDataModel model) {
        long id = DB.usePlaces().add(model);
        places.add(model);
        return id;
    }

    public long addInternet(InternetDataModel model) {
        long id = DB.useInternets().add(model);
        internetAddrs.add(model);
        return id;
    }

    public long addPhone(PhoneDataModel model) {
        long id = DB.usePhones().add(model);
        phones.add(model);
        return id;
    }

    public boolean setFirstName(String firstName) {
        this.firstName = firstName;
        ContentValues content = new ContentValues();
        content.put(TableContacts.KEY_CONTACTS_FIRST_NAME, firstName);
        DB.useContacts().update(getId(), content);
        return true;
    }

    public boolean setSecondName(String secondName) {
        this.secondName = secondName;
        ContentValues content = new ContentValues();
        content.put(TableContacts.KEY_CONTACTS_SECOND_NAME, secondName);
        DB.useContacts().update(getId(), content);
        return true;
    }

    public boolean setObjectId(int objectId) {
        this.objectId = objectId;
        return true;
    }

    // todo add places, internets, phones from contact
}
