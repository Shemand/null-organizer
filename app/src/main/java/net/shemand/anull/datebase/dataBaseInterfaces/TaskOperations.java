package net.shemand.anull.datebase.dataBaseInterfaces;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.DBHelper;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.datebase.tableInterfaces.TableObjects;
import net.shemand.anull.datebase.tableInterfaces.TableObjectsHasContacts;
import net.shemand.anull.datebase.tableInterfaces.TableTaskHasContacts;
import net.shemand.anull.datebase.tableInterfaces.TableTasks;
import net.shemand.anull.models.DataModels.BaseDataModel;
import net.shemand.anull.models.DataModels.ContactDataModel;
import net.shemand.anull.models.DataModels.ObjectDataModel;
import net.shemand.anull.models.DataModels.TaskDataModel;

import java.util.ArrayList;

/**
 * Created by Shema on 02.06.2018.
 */

public class TaskOperations {

    private DBHelper helper;

    public TaskOperations(DBHelper helper) {
        this.helper = helper;
    }

    public TaskDataModel get(int taskId) {
        SQLiteDatabase db;
        TaskDataModel model = null;
        TaskDataModel parent;
        BaseDataModel createdByModel;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        Cursor cur = db.query(TableTasks.TABLE_TASKS, null, TableTasks.KEY_TASKS_ID + " = " + taskId, null, null, null, null);
        if(cur.moveToFirst()){
            if(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_PARENT_ID)) == 0)
                parent = null;
            else
                parent = DB.useTasks().get(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_PARENT_ID)));

            if(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_OBJECT_ID)) == 0)
                if(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_CONTACT_ID)) != 0)
                    createdByModel = DB.useContacts().get(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_CONTACT_ID)));
                else
                    createdByModel = null;
            else
                createdByModel = DB.useObjects().get(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_OBJECT_ID)));

            model = new TaskDataModel(
                    cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_ID)),
                    cur.getString(cur.getColumnIndex(TableTasks.KEY_TASKS_TITLE)),
                    TaskDataModel.Important.getByNumber(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_IMPORTANT))),
                    cur.getString(cur.getColumnIndex(TableTasks.KEY_TASKS_NOTE)),
                    parent,
                    createdByModel,
                    DB.useContacts().getListByTask(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_ID))),
                    cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_CREATED)),
                    cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_STARTED)),
                    cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_DUE)),
                    cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_ENDED))
            );
        }
        db.close();
        return model;
    }

    public ArrayList<TaskDataModel> getList() {
        SQLiteDatabase db;
        ArrayList<TaskDataModel> models = new ArrayList<>();
        TaskDataModel parent;
        BaseDataModel createdByModel;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }



        Cursor cur = db.query(TableTasks.TABLE_TASKS, null, null, null, null, null, null);
        if(cur.moveToFirst()){
            do {
                if(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_PARENT_ID)) == 0)
                    parent = null;
                else
                    parent = DB.useTasks().get(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_PARENT_ID)));

                if(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_OBJECT_ID)) == 0)
                    if(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_CONTACT_ID)) != 0)
                        createdByModel = DB.useContacts().get(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_CONTACT_ID)));
                    else
                        createdByModel = null;
                else
                    createdByModel = DB.useObjects().get(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_FK_OBJECT_ID)));

                models.add(new TaskDataModel(
                        cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_ID)),
                        cur.getString(cur.getColumnIndex(TableTasks.KEY_TASKS_TITLE)),
                        TaskDataModel.Important.getByNumber(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_IMPORTANT))),
                        cur.getString(cur.getColumnIndex(TableTasks.KEY_TASKS_NOTE)),
                        parent,
                        createdByModel,
                        DB.useContacts().getListByTask(cur.getInt(cur.getColumnIndex(TableTasks.KEY_TASKS_ID))),
                        cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_CREATED)),
                        cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_STARTED)),
                        cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_DUE)),
                        cur.getLong(cur.getColumnIndex(TableTasks.KEY_TASKS_ENDED))
                ));
            } while(cur.moveToNext());
        }
        db.close();
        return models;
    }

    public long add(TaskDataModel model) {
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        ContentValues content = new ContentValues();
        content.put(TableTasks.KEY_TASKS_TITLE, model.get(TaskDataModel.TITLE).toString());
        content.put(TableTasks.KEY_TASKS_NOTE, model.get(TaskDataModel.NOTE).toString());
        content.put(TableTasks.KEY_TASKS_IMPORTANT, ((TaskDataModel.Important)model.get(TaskDataModel.IMPORTANT)).ordinal());
        content.put(TableTasks.KEY_TASKS_CREATED, (long) model.get(TaskDataModel.CREATED));

        if(model.get(TaskDataModel.PARENT_MODEL) != null)
            content.put(TableTasks.KEY_TASKS_FK_PARENT_ID, (int) ((TaskDataModel) model.get(TaskDataModel.PARENT_MODEL)).get(TaskDataModel.ID));
        else
            content.put(TableTasks.KEY_TASKS_FK_PARENT_ID, 0);

        if(model.get(TaskDataModel.CREATED_BY_MODEL) != null)
            if((int) model.get(TaskDataModel.CREATED_BY_TYPE) == TaskDataModel.CREATED_BY_OBJECT) {
                content.put(TableTasks.KEY_TASKS_FK_CONTACT_ID, 0);
                content.put(TableTasks.KEY_TASKS_FK_OBJECT_ID, (int) ((ObjectDataModel) model.get(TaskDataModel.CREATED_BY_MODEL)).get(ObjectDataModel.ID));
            } else if((int) model.get(TaskDataModel.CREATED_BY_TYPE) == TaskDataModel.CREATED_BY_CONTACT) {
                content.put(TableTasks.KEY_TASKS_FK_CONTACT_ID, (int) ((ContactDataModel) model.get(TaskDataModel.CREATED_BY_MODEL)).get(ContactDataModel.ID));
                content.put(TableTasks.KEY_TASKS_FK_OBJECT_ID, 0);
            } else {
                content.put(TableTasks.KEY_TASKS_FK_CONTACT_ID, 0);
                content.put(TableTasks.KEY_TASKS_FK_OBJECT_ID, 0);
            }
        else {
            content.put(TableTasks.KEY_TASKS_FK_CONTACT_ID, 0);
            content.put(TableTasks.KEY_TASKS_FK_OBJECT_ID, 0);
        }

        content.put(TableTasks.KEY_TASKS_STARTED, (long) model.get(TaskDataModel.STARTED));
        content.put(TableTasks.KEY_TASKS_DUE, (long) model.get(TaskDataModel.DUE));
        content.put(TableTasks.KEY_TASKS_ENDED, (long) model.get(TaskDataModel.ENDED));

        long id = db.insert(TableTasks.TABLE_TASKS, null, content);
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
        db.update(TableTasks.TABLE_TASKS, content, TableTasks.KEY_TASKS_ID + " = " + id, null);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableTasks.TABLE_TASKS, TableTasks.KEY_TASKS_ID + " = " + id, null);
//        db.delete(TableTaskHasContacts.TABLE_TASK_HAS_CONTACTS, TableTaskHasContacts.KEY_THC_FK_TASK_ID + " = " + id, null);

        db.close();
    }

    public void deleteContact(int id){
        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch(Exception e) {
            db = helper.getReadableDatabase();
        }
        db.delete(TableTaskHasContacts.TABLE_TASK_HAS_CONTACTS, TableTaskHasContacts.KEY_THC_FK_CONTACT_ID + " = " + id, null);
        db.close();
    }

    public long bindingContact(int taskId, int contactId){

        SQLiteDatabase db;
        try{
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
        }

        ContentValues content = new ContentValues();
        content.put(TableTaskHasContacts.KEY_THC_FK_TASK_ID, taskId);
        content.put(TableTaskHasContacts.KEY_THC_FK_CONTACT_ID, contactId);

        long id = db.insert(TableTaskHasContacts.TABLE_TASK_HAS_CONTACTS, null, content);
        db.close();
        return id;
    }

    public ArrayList<Integer> getBindedContacts(int taskId){
        ArrayList<Integer> contactsId = new ArrayList<>();
        SQLiteDatabase db;
        try {
            db = helper.getWritableDatabase();
        } catch (Exception e) {
            db = helper.getReadableDatabase();
            e.printStackTrace();
        }

        Cursor cur = db.query(TableTaskHasContacts.TABLE_TASK_HAS_CONTACTS, null, TableTaskHasContacts.KEY_THC_FK_TASK_ID + " = " + taskId, null, null, null, null, null);
        if(cur.moveToFirst()){
            do{
                contactsId.add(cur.getInt(cur.getColumnIndex(TableTaskHasContacts.KEY_THC_FK_CONTACT_ID)));
            } while(cur.moveToNext());
        }
        db.close();
        return contactsId;
    }
}
