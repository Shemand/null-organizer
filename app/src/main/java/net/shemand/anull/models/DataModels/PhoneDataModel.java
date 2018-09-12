package net.shemand.anull.models.DataModels;

import android.content.ContentValues;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhone;
import net.shemand.anull.dialogs.Dialogs;

/**
 * Created by Shema on 30.05.2018.
 */

public class PhoneDataModel extends BaseDataModel {

    private int contactId;
    private String phoneNumber;
    private PhoneType type;

    public enum PhoneType{HOME, WORK;

        public static PhoneDataModel.PhoneType getByNumber(int num){
            switch(num){
                case 0: return PhoneType.HOME;
                case 1: return PhoneType.WORK;
                default:
                    Log.e("Important get", "Wrong number. returned value by 0");
                    return PhoneType.HOME;
            }
        }

        @Override
        public String toString() {
            switch(this){
                case HOME: return FrameManager.getMainContext().getResources().getStringArray(R.array.phone_groups)[0];
                case WORK: return FrameManager.getMainContext().getResources().getStringArray(R.array.phone_groups)[1];
                default:   return "Неизвестная телефонная группа";
            }
        }
    }

    public final static int ID         = 1;
    public final static int CONTACT_ID = 2;
    public final static int NUMBER     = 3;
    public final static int TYPE       = 4;

    public PhoneDataModel(int id,
                          int contactId,
                          String phoneNumber,
                          PhoneType type) {
        super(id);
        this.contactId = contactId;
        this.phoneNumber = phoneNumber;
        this.type = type;
    }

    @Override
    public Object get(int id) {
        switch(id){
            case ID:
                return this.getId();
            case CONTACT_ID:
                return this.contactId;
            case NUMBER:
                return this.phoneNumber;
            case TYPE:
                return this.type;
            default:
                Log.e("PhoneDataModel.get", "Wrong ID parametr");
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
            case NUMBER:
                if(value instanceof String)
                    return setNumber((String) value);
                break;
            case TYPE:
                if(value instanceof PhoneType)
                    return setType((PhoneType) value);
                break;
            default:
                Log.e("PhoneDataModel.set", "Wrong ID parametr");
                break;
        }
        return false;
    }

    @Override
    public String getFieldName(int id) {
        switch(id){
            case ID:
                return FrameManager.getMainContext().getResources().getText(R.string.phone_id).toString();
            case NUMBER:
                return FrameManager.getMainContext().getResources().getText(R.string.phone_number).toString();
            case TYPE:
                return FrameManager.getMainContext().getResources().getText(R.string.phone_type).toString();
            default:
                Log.e("PhoneData.getfieldName", "Wrong id parametr or parametr haven't name");
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
            case NUMBER:
                return Dialogs.ID_PHONE;
            case TYPE:
                return Dialogs.ID_LIST;
            default:
                Log.e("AddrData.getTypeById", "Wrong id parametr");
        }
        Log.e("PhoneDataModel", "getTypeId. Wrong parametr");
        return 0;
    }

    public boolean setContactId(int contactId) {
        this.contactId = contactId;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhone.KEY_CONTACTS_PHONE_FK_CONTACT_ID, contactId);
        DB.usePhones().update(getId(), content);
        return true;
    }

    public boolean setNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhone.KEY_CONTACTS_PHONE_NUMBER, phoneNumber);
        DB.usePhones().update(getId(), content);
        return true;
    }

    public boolean setType(PhoneType type) {
        this.type = type;
        ContentValues content = new ContentValues();
        content.put(TableContactsPhone.KEY_CONTACTS_PHONE_TYPE, type.ordinal());
        DB.usePhones().update(getId(), content);
        return true;
    }
}
