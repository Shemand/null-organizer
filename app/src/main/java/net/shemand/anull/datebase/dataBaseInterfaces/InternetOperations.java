package net.shemand.anull.datebase.dataBaseInterfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.shemand.anull.datebase.DBHelper;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.datebase.tableInterfaces.TableContactsInternet;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhone;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 03.06.2018.
 */

public class InternetOperations {

    private DBHelper helper;

    public InternetOperations(DBHelper helper) {
        this.helper = helper;
    }

    public ArrayList<InternetDataModel> getList(int contactId){
        ArrayList<InternetDataModel> internets = new ArrayList<>();
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }
        Cursor cur = db.query(TableContactsInternet.TABLE_CONTACTS_INTERNET, null, TableContactsInternet.KEY_CONTACTS_INTERNET_FK_CONTACT_ID + " = " + contactId, null, null, null, null, null);
        if(cur.moveToFirst()){
            do{
                internets.add(new InternetDataModel(
                        cur.getInt(cur.getColumnIndex(TableContactsInternet.KEY_CONTACTS_INTERNET_ID)),
                        cur.getInt(cur.getColumnIndex(TableContactsInternet.KEY_CONTACTS_INTERNET_FK_CONTACT_ID)),
                        cur.getString(cur.getColumnIndex(TableContactsInternet.KEY_CONTACTS_INTERNET_EMAIL))
                ));
            } while(cur.moveToNext());
        }
        db.close();
        return internets;
    }

    public long add(InternetDataModel model){

        if((int) model.get(InternetDataModel.CONTACT_ID) == 0)
            return -1;

        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ContentValues content = new ContentValues();
        content.put(TableContactsInternet.KEY_CONTACTS_INTERNET_FK_CONTACT_ID, (int) model.get(InternetDataModel.CONTACT_ID));
        content.put(TableContactsInternet.KEY_CONTACTS_INTERNET_EMAIL, (String) model.get(InternetDataModel.EMAIL));

        long id = db.insert(TableContactsInternet.TABLE_CONTACTS_INTERNET, null, content);

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
        db.update(TableContactsInternet.TABLE_CONTACTS_INTERNET, content, TableContactsInternet.KEY_CONTACTS_INTERNET_ID + " = " + id, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableContactsInternet.TABLE_CONTACTS_INTERNET, TableContactsInternet.KEY_CONTACTS_INTERNET_ID + " = " + id, null);
        db.close();
    }
}
