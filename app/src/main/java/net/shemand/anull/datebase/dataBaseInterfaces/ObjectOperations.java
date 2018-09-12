package net.shemand.anull.datebase.dataBaseInterfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.DBHelper;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhone;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.datebase.tableInterfaces.TableObjects;
import net.shemand.anull.datebase.tableInterfaces.TableObjectsHasContacts;
import net.shemand.anull.datebase.tableInterfaces.TableTaskHasContacts;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.PhoneDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 02.06.2018.
 */

public class ObjectOperations {

    private DBHelper helper;

    public ObjectOperations(DBHelper helper) {
        this.helper = helper;
    }

    public ObjectDataModel get(int objectId){
        SQLiteDatabase db;

        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ObjectDataModel model = null;

        Cursor cur = db.query(TableObjects.TABLE_OBJECTS, null, TableObjects.KEY_OBJECTS_ID + " = " + objectId, null, null, null, null);
        if(cur.moveToFirst()){
            model = new ObjectDataModel(
                    cur.getInt(cur.getColumnIndex(TableObjects.KEY_OBJECTS_ID)),
                    cur.getString(cur.getColumnIndex(TableObjects.KEY_OBJECTS_NAME)),
                    cur.getInt(cur.getColumnIndex(TableObjects.KEY_OBJECTS_COLOR)),
                    DB.useContacts().getListByObject(cur.getInt(cur.getColumnIndex(TableObjects.KEY_OBJECTS_ID))));
        }
        db.close();
        return model;
    }

    public ArrayList<ObjectDataModel> getList() {
        ArrayList<ObjectDataModel> objects = new ArrayList<>();
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }
        Cursor cur = db.query(TableObjects.TABLE_OBJECTS, null, null, null, null, null, null, null);
        if(cur.moveToFirst()){
            do{
                objects.add(new ObjectDataModel(
                        cur.getInt(cur.getColumnIndex(TableObjects.KEY_OBJECTS_ID)),
                        cur.getString(cur.getColumnIndex(TableObjects.KEY_OBJECTS_NAME)),
                        cur.getInt(cur.getColumnIndex(TableObjects.KEY_OBJECTS_COLOR)),
                        DB.useContacts().getListByObject(cur.getInt(cur.getColumnIndex(TableObjects.KEY_OBJECTS_ID)))
                ));
            } while(cur.moveToNext());
        }
        db.close();
        return objects;
    }

    public long add(ObjectDataModel model){
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ContentValues content = new ContentValues();
        content.put(TableObjects.KEY_OBJECTS_NAME, (String) model.get(ObjectDataModel.NAME));
        content.put(TableObjects.KEY_OBJECTS_COLOR, (int) model.get(ObjectDataModel.COLOR));

        long id = db.insert(TableObjects.TABLE_OBJECTS, null, content);

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
        db.update(TableObjects.TABLE_OBJECTS, content, TableObjects.KEY_OBJECTS_ID + " = " + id, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableObjects.TABLE_OBJECTS, TableObjects.KEY_OBJECTS_ID + " = " + id, null);
        db.delete(TableObjectsHasContacts.TABLE_OBJECTS_HAS_CONTACTS, TableObjectsHasContacts.KEY_OHC_FK_OBJECT_ID + " = " + id, null);
        db.close();
    }

    public void deleteContact(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableObjectsHasContacts.TABLE_OBJECTS_HAS_CONTACTS, TableObjectsHasContacts.KEY_OHC_FK_CONTACT_ID + " = " + id, null);
        db.close();
    }

    public long bindingContact(int objectId, int contactId){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
        }
        Log.e("object and contact", objectId + " : " + contactId);
        ContentValues content = new ContentValues();
        content.put(TableObjectsHasContacts.KEY_OHC_FK_OBJECT_ID, objectId);
        content.put(TableObjectsHasContacts.KEY_OHC_FK_CONTACT_ID, contactId);

        long id = db.insert(TableObjectsHasContacts.TABLE_OBJECTS_HAS_CONTACTS, null, content);
        db.close();
        Log.e("rowid", id + "");
        return id;
    }

    public ArrayList<Integer> getBindedContacts(int objectId){
        ArrayList<Integer> contactsId = new ArrayList<>();
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        Cursor cur = db.query(TableObjectsHasContacts.TABLE_OBJECTS_HAS_CONTACTS, null, TableObjectsHasContacts.KEY_OHC_FK_OBJECT_ID + " = " + objectId, null, null, null, null, null);
        if(cur.moveToFirst()){
            do{
                contactsId.add(cur.getInt(cur.getColumnIndex(TableObjectsHasContacts.KEY_OHC_FK_CONTACT_ID)));
            } while(cur.moveToNext());
        }
        db.close();
        return contactsId;
    }
}
