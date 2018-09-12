package net.shemand.anull.datebase.dataBaseInterfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.shemand.anull.datebase.DBHelper;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhone;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 03.06.2018.
 */

public class PhoneOperations {

    private DBHelper helper;

    public PhoneOperations(DBHelper helper) {
        this.helper = helper;
    }

    public ArrayList<PhoneDataModel> getList(int contactId) {
        ArrayList<PhoneDataModel> phones = new ArrayList<>();
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }
        Cursor cur = db.query(TableContactsPhone.TABLE_CONTACTS_PHONE, null, TableContactsPhone.KEY_CONTACTS_PHONE_FK_CONTACT_ID + " = " + contactId, null, null, null, null, null);
        if(cur.moveToFirst()){
            do{
                phones.add(new PhoneDataModel(
                        cur.getInt(cur.getColumnIndex(TableContactsPhone.KEY_CONTACTS_PHONE_ID)),
                        cur.getInt(cur.getColumnIndex(TableContactsPhone.KEY_CONTACTS_PHONE_FK_CONTACT_ID)),
                        cur.getString(cur.getColumnIndex(TableContactsPhone.KEY_CONTACTS_PHONE_NUMBER)),
                        PhoneDataModel.PhoneType.getByNumber(cur.getInt(cur.getColumnIndex(TableContactsPhone.KEY_CONTACTS_PHONE_TYPE)))
                ));
            } while(cur.moveToNext());
        }
        db.close();
        return phones;
    }

    public long add(PhoneDataModel model){

        if((int) model.get(PhoneDataModel.CONTACT_ID) == 0)
            return -1;

        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ContentValues content = new ContentValues();
        content.put(TableContactsPhone.KEY_CONTACTS_PHONE_FK_CONTACT_ID, (int) model.get(PhoneDataModel.CONTACT_ID));
        content.put(TableContactsPhone.KEY_CONTACTS_PHONE_NUMBER, (String) model.get(PhoneDataModel.NUMBER));
        content.put(TableContactsPhone.KEY_CONTACTS_PHONE_TYPE, (int) ((PhoneDataModel.PhoneType) model.get(PhoneDataModel.TYPE)).ordinal());
        long id = db.insert(TableContactsPhone.TABLE_CONTACTS_PHONE, null, content);

        db.close();
        return id;
    }

    public void update(int id, ContentValues content) {
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.update(TableContactsPhone.TABLE_CONTACTS_PHONE, content, TableContactsPhone.KEY_CONTACTS_PHONE_ID + " = " + id, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableContactsPhone.TABLE_CONTACTS_PHONE, TableContactsPhone.KEY_CONTACTS_PHONE_ID + " = " + id, null);
        db.close();
    }
}
