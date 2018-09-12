package net.shemand.anull.datebase;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableContacts;
import net.shemand.anull.datebase.tableInterfaces.TableContactsInternet;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhone;
import net.shemand.anull.datebase.tableInterfaces.TableContactsPhysical;
import net.shemand.anull.datebase.tableInterfaces.TableObjects;
import net.shemand.anull.datebase.tableInterfaces.TableObjectsHasContacts;
import net.shemand.anull.datebase.tableInterfaces.TableTaskHasContacts;
import net.shemand.anull.datebase.tableInterfaces.TableTasks;

/**
 * Created by Shema on 15.05.2018.
 */

public class DBHelper extends SQLiteOpenHelper implements   TableBusiness,
                                                            TableContacts,
                                                            TableObjects,
                                                            TableTasks,
                                                            TableContactsInternet,
                                                            TableContactsPhone,
                                                            TableContactsPhysical,
                                                            TableTaskHasContacts,
                                                            TableObjectsHasContacts {


    public static final int ID_TABLE_TASKS                = 1;
    public static final int ID_TABLE_BUSINESS             = 2;
    public static final int ID_TABLE_CONTACTS             = 3;
    public static final int ID_TABLE_OBJECTS              = 4;
    public static final int ID_TABLE_CONTACTS_INTERNET    = 8;
    public static final int ID_TABLE_CONTACTS_PHONE       = 9;
    public static final int ID_TABLE_CONTACTS_ADDR        = 10;
    public static final int ID_TABLE_TASK_HAS_CONTACTS    = 11;
    public static final int ID_TABLE_OBJECTS_HAS_CONTACTS = 12;


    private int dataBaseVerison;

    public DBHelper(String name, int version) {
        super(FrameManager.getMainContext(), name, null, version);
        dataBaseVerison = version;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        onUpdateDataBase(db, 0, dataBaseVerison);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        onUpdateDataBase(db, oldVersion, newVersion);
    }

    public void onUpdateDataBase(SQLiteDatabase db, int oldVersion, int newVersion){
        Log.e("DB updated", "With old version" + oldVersion);
        if(oldVersion == 0){
            boolean checker = false;
            checker |= !createTable(db, ID_TABLE_BUSINESS);
            checker |= !createTable(db, ID_TABLE_TASKS);
            checker |= !createTable(db, ID_TABLE_CONTACTS);
            checker |= !createTable(db, ID_TABLE_OBJECTS);
            checker |= !createTable(db, ID_TABLE_CONTACTS_ADDR);
            checker |= !createTable(db, ID_TABLE_CONTACTS_INTERNET);
            checker |= !createTable(db, ID_TABLE_CONTACTS_PHONE);
            checker |= !createTable(db, ID_TABLE_TASK_HAS_CONTACTS);
            checker |= !createTable(db, ID_TABLE_OBJECTS_HAS_CONTACTS);
            if(checker)
                Log.e("Error while creating db", "error on creating tables");
                // drop table
        }
    }


    private boolean createTable(SQLiteDatabase db, int id){

        StringBuilder query = new StringBuilder();
        try {
            switch (id) {

                case ID_TABLE_BUSINESS:

                    query.append("CREATE TABLE ").append(TABLE_BUSINESS).append("(");
                    query.append(KEY_BUSINESS_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_BUSINESS_TITLE).append(" TEXT,");
                    query.append(KEY_BUSINESS_NOTE).append(" TEXT,");
                    query.append(KEY_BUSINESS_IMPORTANT).append(" INTEGER,");
                    query.append(KEY_BUSINESS_CREATED).append(" INTEGER,");
                    query.append(KEY_BUSINESS_STARTED).append(" INTEGER,");
                    query.append(KEY_BUSINESS_DUE).append(" INTEGER,");
                    query.append(KEY_BUSINESS_ENDED).append(" INTEGER,");
                    query.append(KEY_BUSINESS_FK_TASK_ID).append(" INTEGER,");
                    query.append(KEY_BUSINESS_FK_OBJECT_ID).append(" INTEGER,");
                    query.append(KEY_BUSINESS_FK_CONTACT_ID).append(" INTEGER");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_TASKS:

                    query.append("CREATE TABLE ").append(TABLE_TASKS).append("(");
                    query.append(KEY_TASKS_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_TASKS_TITLE).append(" TEXT,");
                    query.append(KEY_TASKS_IMPORTANT).append(" INTEGER,");
                    query.append(KEY_TASKS_NOTE).append(" TEXT,");
                    query.append(KEY_TASKS_CREATED).append(" INTEGER,");
                    query.append(KEY_TASKS_STARTED).append(" INTEGER,");
                    query.append(KEY_TASKS_DUE).append(" INTEGER,");
                    query.append(KEY_TASKS_ENDED).append(" INTEGER,");
                    query.append(KEY_TASKS_COLOR).append(" INTEGER,");
                    query.append(KEY_TASKS_FK_CONTACT_ID).append(" INTEGER,");
                    query.append(KEY_TASKS_FK_OBJECT_ID).append(" INTEGER,");
                    query.append(KEY_TASKS_FK_PARENT_ID).append(" INTEGER");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_CONTACTS:

                    query.append("CREATE TABLE ").append(TABLE_CONTACTS).append("(");
                    query.append(KEY_CONTACTS_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_CONTACTS_FIRST_NAME).append(" TEXT,");
                    query.append(KEY_CONTACTS_SECOND_NAME).append(" TEXT");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_OBJECTS:

                    query.append("CREATE TABLE ").append(TABLE_OBJECTS).append("(");
                    query.append(KEY_OBJECTS_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_OBJECTS_NAME).append(" TEXT,");
                    query.append(KEY_OBJECTS_COLOR).append(" TEXT");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_CONTACTS_INTERNET:

                    query.append("CREATE TABLE ").append(TABLE_CONTACTS_INTERNET).append("(");
                    query.append(KEY_CONTACTS_INTERNET_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_CONTACTS_INTERNET_EMAIL).append(" TEXT,");
                    query.append(KEY_CONTACTS_INTERNET_FK_CONTACT_ID).append(" INTEGER");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_CONTACTS_PHONE:

                    query.append("CREATE TABLE ").append(TABLE_CONTACTS_PHONE).append("(");
                    query.append(KEY_CONTACTS_PHONE_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_CONTACTS_PHONE_NUMBER).append(" TEXT,");
                    query.append(KEY_CONTACTS_PHONE_TYPE).append(" INTEGER,");
                    query.append(KEY_CONTACTS_PHONE_FK_CONTACT_ID).append(" INTEGER");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_CONTACTS_ADDR:

                    query.append("CREATE TABLE ").append(TABLE_CONTACTS_ADDR).append("(");
                    query.append(KEY_CONTACTS_ADDR_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_CONTACTS_ADDR_NAME).append(" TEXT,");
                    query.append(KEY_CONTACTS_ADDR_CITY).append(" TEXT,");
                    query.append(KEY_CONTACTS_ADDR_STREET).append(" TEXT,");
                    query.append(KEY_CONTACTS_ADDR_HOUSE_NUMBER).append(" TEXT,");
                    query.append(KEY_CONTACTS_ADDR_FK_CONTACT_ID).append(" INTEGER");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_TASK_HAS_CONTACTS:

                    query.append("CREATE TABLE ").append(TABLE_TASK_HAS_CONTACTS).append("(");
                    query.append(KEY_THC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_THC_FK_TASK_ID).append(" INTEGER, ");
                    query.append(KEY_THC_FK_CONTACT_ID).append(" INTEGER");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
                case ID_TABLE_OBJECTS_HAS_CONTACTS:

                    query.append("CREATE TABLE ").append(TABLE_OBJECTS_HAS_CONTACTS).append("(");
                    query.append(KEY_OHC_ID).append(" INTEGER PRIMARY KEY AUTOINCREMENT,");
                    query.append(KEY_OHC_FK_OBJECT_ID).append(" INTEGER, ");
                    query.append(KEY_OHC_FK_CONTACT_ID).append(" INTEGER");
                    query.append(")");
                    db.execSQL(query.toString());

                    return true;
            }
        } catch(SQLiteException ex) {
            throw ex;
        }
        return false;

    }
}
