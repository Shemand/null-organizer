package net.shemand.anull.datebase.dataBaseInterfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.shemand.anull.datebase.DBHelper;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.models.DataModels.AddrDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.InternetDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 03.06.2018.
 */

public class PlaceOperations {

    private DBHelper helper;

    public PlaceOperations(DBHelper helper){
        this.helper = helper;
    }

    public ArrayList<AddrDataModel> getList(int contactId) {
        ArrayList<AddrDataModel> places = new ArrayList<>();
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }
        Cursor cur = db.query(TableContactsPhysical.TABLE_CONTACTS_ADDR, null, TableContactsPhysical.KEY_CONTACTS_ADDR_FK_CONTACT_ID + " = " + contactId, null, null, null, null, null);
        if(cur.moveToFirst()){
            do{
                places.add(new AddrDataModel(
                        cur.getInt(cur.getColumnIndex(TableContactsPhysical.KEY_CONTACTS_ADDR_ID)),
                        cur.getInt(cur.getColumnIndex(TableContactsPhysical.KEY_CONTACTS_ADDR_FK_CONTACT_ID)),
                        cur.getString(cur.getColumnIndex(TableContactsPhysical.KEY_CONTACTS_ADDR_NAME)),
                        cur.getString(cur.getColumnIndex(TableContactsPhysical.KEY_CONTACTS_ADDR_CITY)),
                        cur.getString(cur.getColumnIndex(TableContactsPhysical.KEY_CONTACTS_ADDR_STREET)),
                        cur.getString(cur.getColumnIndex(TableContactsPhysical.KEY_CONTACTS_ADDR_HOUSE_NUMBER))
                ));
            } while(cur.moveToNext());
        }
        db.close();
        return places;
    }

    public long add(AddrDataModel model){

        if((int) model.get(AddrDataModel.CONTACT_ID) == 0)
            return -1;

        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ContentValues content = new ContentValues();
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_FK_CONTACT_ID, (int) model.get(AddrDataModel.CONTACT_ID));
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_NAME, (String) model.get(AddrDataModel.NAME));
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_CITY, (String) model.get(AddrDataModel.CITY));
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_STREET, (String) model.get(AddrDataModel.STREET));
        content.put(TableContactsPhysical.KEY_CONTACTS_ADDR_HOUSE_NUMBER, (String) model.get(AddrDataModel.HOUSE_NUMBER));

        long id = db.insert(TableContactsPhysical.TABLE_CONTACTS_ADDR, null, content);

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
        db.update(TableContactsPhysical.TABLE_CONTACTS_ADDR, content, TableContactsPhysical.KEY_CONTACTS_ADDR_ID + " = " + id, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableContactsPhysical.TABLE_CONTACTS_ADDR, TableContactsPhysical.KEY_CONTACTS_ADDR_ID + " = " + id, null);
        db.close();
    }

}
