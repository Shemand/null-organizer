package net.shemand.anull.datebase.dataBaseInterfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.DBHelper;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.datebase.tableInterfaces.TableContactsInternet;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhone;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.datebase.tableInterfaces.TableObjects;
import net.shemand.anull.datebase.tableInterfaces.TableObjectsHasContacts;
import net.shemand.anull.datebase.tableInterfaces.TableTaskHasContacts;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 02.06.2018.
 */

public class ContactOperations {

    private DBHelper helper;

    public ContactOperations(DBHelper helper){
        this.helper = helper;
    }

    public ContactDataModel get(int contactId){
        SQLiteDatabase db;

        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ContactDataModel model = null;

        Cursor cur = db.query(TableContacts.TABLE_CONTACTS, null, TableContacts.KEY_CONTACTS_ID + " = " + contactId, null, null, null, null);
        if(cur.moveToFirst()){
                model = new ContactDataModel(
                        cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID)),
                        0,
                        cur.getString(cur.getColumnIndex(TableContacts.KEY_CONTACTS_FIRST_NAME)),
                        cur.getString(cur.getColumnIndex(TableContacts.KEY_CONTACTS_SECOND_NAME)),
                        DB.usePlaces().getList(cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID))),
                        DB.useInternets().getList(cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID))),
                        DB.usePhones().getList(cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID))));
        }
        db.close();
        return model;
    }

    public ArrayList<ContactDataModel> getList() {
        SQLiteDatabase db;
        Cursor cur;
        ArrayList<ContactDataModel> models = new ArrayList<>();

        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        cur = db.query(TableContacts.TABLE_CONTACTS, null, null, null, null, null, null);
        if(cur.moveToFirst()){
            do {
                models.add(new ContactDataModel(
                        cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID)),
                        0,
                        cur.getString(cur.getColumnIndex(TableContacts.KEY_CONTACTS_FIRST_NAME)),
                        cur.getString(cur.getColumnIndex(TableContacts.KEY_CONTACTS_SECOND_NAME)),
                        DB.usePlaces().getList(cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID))),
                        DB.useInternets().getList(cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID))),
                        DB.usePhones().getList(cur.getInt(cur.getColumnIndex(TableContacts.KEY_CONTACTS_ID)))
                ));
            } while(cur.moveToNext());
        }
        db.close();
        return models;
    }

    public long add(ContactDataModel model){
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ContentValues content = new ContentValues();
        content.put(TableContacts.KEY_CONTACTS_FIRST_NAME, (String) model.get(ContactDataModel.FIRST_NAME));
        content.put(TableContacts.KEY_CONTACTS_SECOND_NAME, (String) model.get(ContactDataModel.SECOND_NAME));

        int id = (int) db.insert(TableContacts.TABLE_CONTACTS, null, content);

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
        db.update(TableContacts.TABLE_CONTACTS, content, TableContacts.KEY_CONTACTS_ID + " = " + id, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableContacts.TABLE_CONTACTS, TableContacts.KEY_CONTACTS_ID + " = " + id, null);
        db.delete(TableObjectsHasContacts.TABLE_OBJECTS_HAS_CONTACTS, TableObjectsHasContacts.KEY_OHC_FK_CONTACT_ID + " = " + id, null);
        db.delete(TableTaskHasContacts.TABLE_TASK_HAS_CONTACTS, TableTaskHasContacts.KEY_THC_FK_CONTACT_ID + " = " + id, null);
        db.close();
    }

    public ArrayList<ContactDataModel> getListByObject(int objectId){
        ArrayList<Integer> array = DB.useObjects().getBindedContacts(objectId);
        ArrayList<ContactDataModel> contacts = new ArrayList<>();
        for(int i = 0; i < array.size(); i++){
            contacts.add(DB.useContacts().get(array.get(i)));
        }
        return contacts;
    }

    public ArrayList<ContactDataModel> getListByTask(int taskId){
        ArrayList<Integer> array = DB.useTasks().getBindedContacts(taskId);
        ArrayList<ContactDataModel> contacts = new ArrayList<>();
        for(int i = 0; i < array.size(); i++){
            contacts.add(DB.useContacts().get(array.get(i)));
        }
        return contacts;
    }
}
