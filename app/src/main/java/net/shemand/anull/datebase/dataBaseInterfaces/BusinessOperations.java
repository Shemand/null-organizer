package net.shemand.anull.datebase.dataBaseInterfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.shemand.anull.MainActivity;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.DBHelper;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableTasks;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.BusinessDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Shema on 02.06.2018.
 */

public class BusinessOperations {

    private DBHelper helper;

    public BusinessOperations(DBHelper helper) {
        this.helper = helper;
    }

    public ArrayList<BusinessDataModel> getList(long dateInMillis) {
        SQLiteDatabase db;
        BusinessDataModel.Important tmpImportant;
        ArrayList<BusinessDataModel> models = new ArrayList<>();
        BaseDataModel createdByModel;

        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }
        Cursor cur;
        Calendar input = Calendar.getInstance();
        input.setTimeZone(MainActivity.timeZone);
        input.setTimeInMillis(dateInMillis);

        Calendar date = Calendar.getInstance();
        date.setTimeZone(MainActivity.timeZone);

        if(dateInMillis != 0) {
            date.setTimeInMillis(0);
            date.set(Calendar.YEAR, input.get(Calendar.YEAR));
            date.set(Calendar.MONTH, input.get(Calendar.MONTH));
            date.set(Calendar.DAY_OF_MONTH, input.get(Calendar.DAY_OF_MONTH)+1);
            input.setTimeInMillis(date.getTimeInMillis());
            input.set(Calendar.DAY_OF_MONTH, input.get(Calendar.DAY_OF_MONTH)-1);

            cur = db.query(TableBusiness.TABLE_BUSINESS, null, TableBusiness.KEY_BUSINESS_STARTED + " >= " + (input.getTimeInMillis()) + " AND " + TableBusiness.KEY_BUSINESS_STARTED + " < " + (date.getTimeInMillis() - 1000), null, null, null, null);
        } else {
            cur = db.query(TableBusiness.TABLE_BUSINESS, null, null, null, null, null, null);
        }
        if(cur.moveToFirst()){
            do {
                Log.e("data", cur.getLong(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_ID)) + " : " + cur.getLong(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_STARTED)));
                if(cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID)) == 0)
                    if(cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID)) == 0)
                        if(cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_FK_TASK_ID)) != 0)
                            createdByModel = DB.useTasks().get(cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_FK_TASK_ID)));
                        else
                            createdByModel = null;
                    else
                        createdByModel = DB.useContacts().get(cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID)));
                else
                    createdByModel = DB.useObjects().get(cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID)));

                models.add(new BusinessDataModel(
                        cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_ID)),
                        cur.getString(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_TITLE)),
                        BusinessDataModel.Important.getByNumber(cur.getInt(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_IMPORTANT))),
                        createdByModel,
                        cur.getString(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_NOTE)),
                        cur.getLong(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_CREATED)),
                        cur.getLong(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_STARTED)),
                        cur.getLong(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_DUE)),
                        cur.getLong(cur.getColumnIndex(TableBusiness.KEY_BUSINESS_ENDED))
                ));
            } while(cur.moveToNext());
        }
        db.close();
        return models;
    }

    public long add(BusinessDataModel model) {
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }
        ContentValues content = new ContentValues();

        if(model.get(BusinessDataModel.CREATED_BY_MODEL) != null)
            if((int) model.get(BusinessDataModel.CREATED_BY_TYPE) == BusinessDataModel.CREATED_BY_OBJECT) {
                content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, (int) ((ObjectDataModel) model.get(BusinessDataModel.CREATED_BY_MODEL)).get(ObjectDataModel.ID));
                content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, 0);
            } else if((int) model.get(BusinessDataModel.CREATED_BY_TYPE) == BusinessDataModel.CREATED_BY_CONTACT) {
                content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, (int) ((ContactDataModel) model.get(BusinessDataModel.CREATED_BY_MODEL)).get(ContactDataModel.ID));
                content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, 0);
            } else if((int) model.get(BusinessDataModel.CREATED_BY_TYPE) == BusinessDataModel.CREATED_BY_TASK){
                content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, (int) ((TaskDataModel) model.get(BusinessDataModel.CREATED_BY_MODEL)).get(TaskDataModel.ID));
            }
        else {
            content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, 0);
            content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, 0);
            content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, 0);
        }

        content.put(TableBusiness.KEY_BUSINESS_TITLE, model.get(BusinessDataModel.TITLE).toString());
        content.put(TableBusiness.KEY_BUSINESS_NOTE, model.get(BusinessDataModel.NOTE).toString());
        content.put(TableBusiness.KEY_BUSINESS_IMPORTANT, ((BusinessDataModel.Important)model.get(BusinessDataModel.IMPORTANT)).ordinal());
        content.put(TableBusiness.KEY_BUSINESS_CREATED, (long) model.get(BusinessDataModel.CREATED));
        content.put(TableBusiness.KEY_BUSINESS_STARTED, (long) model.get(BusinessDataModel.STARTED));
        content.put(TableBusiness.KEY_BUSINESS_DUE, (long) model.get(BusinessDataModel.DUE));
        content.put(TableBusiness.KEY_BUSINESS_ENDED, (long) model.get(BusinessDataModel.ENDED));

        long id = db.insert(TableBusiness.TABLE_BUSINESS, null, content);

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
        db.update(TableBusiness.TABLE_BUSINESS, content, TableBusiness.KEY_BUSINESS_ID + " = " + id, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableBusiness.TABLE_BUSINESS, TableBusiness.KEY_BUSINESS_ID + " = " + id, null);
        db.close();
    }

//        void add(contactId);
//        void add(taskId);
//        void add(objectid);
//        void edit(ContentValues content) {
//            SQLiteDatabase db = helper.getWritableDatabase();
//            db.update(DBHelper.TABLE_BUSINESS, content, "_id = 0", null);
//        }
//        void delete(businessId);
//        void get(businessid);
//        void getList(date)
//        void tungleContact(Contact id);
}
