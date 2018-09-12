package net.shemand.anull.models.DataModels;

import android.content.ContentValues;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.tableInterfaces.TableObjects;

import java.util.ArrayList;

/**
 * Created by Shema on 02.06.2018.
 */

public class ObjectDataModel extends BaseDataModel {

    private String name;
    private int color;
    private ArrayList<ContactDataModel> contacts;

    public final static int ID = 1;
    public final static int NAME = 2;
    public final static int COLOR = 3;
    public final static int CONTACTS = 4;

    public ObjectDataModel(int id,
                           String name,
                           int color,
                           ArrayList<ContactDataModel> contacts) {
        super(id);
        this.name = name;
        this.color = color;
        this.contacts = contacts;
    }

    @Override
    public Object get(int id) {
        switch(id){
            case ID:
                return this.getId();
            case NAME:
                return this.name;
            case COLOR:
                return this.color;
            case CONTACTS:
                return this.contacts;
            default:
                Log.e("ObjectDataModel.get", "Wrong ID parametr");
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
            case NAME:
                if(value instanceof String)
                    return setName((String) value);
                break;
            case COLOR:
                if(value instanceof Integer)
                    return setColor((int) value);
                break;
            default:
                Log.e("ObjectDataModel.set", "Wrong ID parametr");
                break;
        }
        return false;
    }

    @Override
    public String getFieldName(int id) {
        return "";
    }

    @Override
    public int getTypeById(int id) {
        return 0;
    }

    public boolean setName(String name) {
        this.name = name;
        ContentValues content = new ContentValues();
        content.put(TableObjects.KEY_OBJECTS_NAME, name);
        DB.useObjects().update(getId(), content);
        return true;
    }

    public boolean setColor(int color) {
        this.color = color;
        ContentValues content = new ContentValues();
        content.put(TableObjects.KEY_OBJECTS_COLOR, color);
        DB.useObjects().update(getId(), content);
        return true;
    }

    public boolean addContact(ContactDataModel model){
        contacts.add(model);
        return true;
    }
}
