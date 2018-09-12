package net.shemand.anull.datebase;

import net.shemand.anull.datebase.dataBaseInterfaces.BusinessOperations;
import net.shemand.anull.datebase.dataBaseInterfaces.ContactOperations;
import net.shemand.anull.datebase.dataBaseInterfaces.InternetOperations;
import net.shemand.anull.datebase.dataBaseInterfaces.ObjectOperations;
import net.shemand.anull.datebase.dataBaseInterfaces.PhoneOperations;
import net.shemand.anull.datebase.dataBaseInterfaces.PlaceOperations;
import net.shemand.anull.datebase.dataBaseInterfaces.TaskOperations;
import net.shemand.anull.models.DataModels.InternetDataModel;

/**
 * Created by Shema on 15.05.2018.
 */

public class DB {

    private final static String DATABASE_NAME = "DBone";
    private final static int DATABASE_VERSION = 1;

    private BusinessOperations business;
    private TaskOperations tasks;
    private ContactOperations contacts;
    private ObjectOperations objects;
    private PlaceOperations places;
    private InternetOperations internets;
    private PhoneOperations phones;


    public static DB instance;

    private DBHelper helper;

    private DB(){
        helper = new DBHelper(DATABASE_NAME, DATABASE_VERSION);
        business = new BusinessOperations(helper);
        tasks = new TaskOperations(helper);
        contacts = new ContactOperations(helper);
        places = new PlaceOperations(helper);
        internets = new InternetOperations(helper);
        phones = new PhoneOperations(helper);
        objects = new ObjectOperations(helper);
    }

    public static DB getInstance() {
        if(instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public static void destroyInstance() {
        instance = null;
    }

    public static BusinessOperations useBusiness() {
        return DB.getInstance().business;
    }

    public static TaskOperations useTasks() {
        return DB.getInstance().tasks;
    }

    public static ContactOperations useContacts() {
        return DB.getInstance().contacts;
    }

    public static PlaceOperations usePlaces() { return DB.getInstance().places; }

    public static InternetOperations useInternets() { return DB.getInstance().internets; }

    public static PhoneOperations usePhones() { return DB.getInstance().phones; }

    public static ObjectOperations useObjects() {
        return DB.getInstance().objects;
    }

}
