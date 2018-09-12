package net.shemand.anull.models.DataModels;

import android.content.ContentValues;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.tableInterfaces.TableContactsInternet;
import net.shemand.anull.dialogs.Dialogs;

/**
 * Created by Shema on 30.05.2018.
 */

public class InternetDataModel extends BaseDataModel {

    private int contactId;
    private String email;

    public final static int ID         = 1;
    public final static int CONTACT_ID = 2;
    public final static int EMAIL      = 3;

    public InternetDataModel(int id,
                             int contactId,
                             String email) {
        super(id);
        this.contactId = contactId;
        this.email = email;
    }

    @Override
    public Object get(int id) {
        switch(id){
            case ID:
                return this.getId();
            case CONTACT_ID:
                return this.contactId;
            case EMAIL:
                return this.email;
            default:
                Log.e("InternetDataModel.get", "Wrong ID parametr");
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
            case CONTACT_ID:
                if(value instanceof Integer)
                    return setContactId((int) value);
                else if(value instanceof Long)
                    return setContactId(((Long) value).intValue());
                break;
            case EMAIL:
                if(value instanceof String) {
                    return setEmail((String) value);
                }
                break;
            default:
                Log.e("InternetDataModel.set", "Wrong ID parametr");
                break;
        }
        return false;
    }

    @Override
    public String getFieldName(int id) {
        switch(id){
            case ID:
                return FrameManager.getMainContext().getResources().getText(R.string.internet_id).toString();
            case EMAIL:
                return FrameManager.getMainContext().getResources().getText(R.string.internet_email).toString();
            default:
                Log.e("InternetData", "getfieldName. Wrong id parametr or parametr haven't name");
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
            case EMAIL:
                return Dialogs.ID_STRING;
            default:
                Log.e("AddrData.getTypeById", "Wrong id parametr");
        }
        Log.e("InternetDataModel", "getTypeId. Wrong parametr");
        return 0;
    }

    public boolean setContactId(int contactId) {
        this.contactId = contactId;
        ContentValues content = new ContentValues();
        content.put(TableContactsInternet.KEY_CONTACTS_INTERNET_FK_CONTACT_ID, contactId);
        DB.useInternets().update(getId(), content);
        return true;
    }

    public boolean setEmail(String email) {
        this.email = email;
        ContentValues content = new ContentValues();
        content.put(TableContactsInternet.KEY_CONTACTS_INTERNET_EMAIL, email);
        DB.useInternets().update(getId(), content);
        return true;
    }
}
