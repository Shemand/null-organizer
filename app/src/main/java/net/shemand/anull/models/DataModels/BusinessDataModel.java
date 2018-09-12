package net.shemand.anull.models.DataModels;

import android.content.ContentValues;
import android.util.Log;

import net.shemand.anull.FrameManager;
import net.shemand.anull.MainActivity;
import net.shemand.anull.R;
import net.shemand.anull.datebase.DB;
import net.shemand.anull.datebase.tableInterfaces.TableBusiness;
import net.shemand.anull.datebase.tableInterfaces.TableTasks;
import net.shemand.anull.dialogs.Dialogs;

import java.util.ArrayList;
import java.util.Calendar;


import static net.shemand.anull.models.DataModels.BusinessDataModel.Important.*;

/**
 * Created by Shema on 08.05.2018.
 */

public class BusinessDataModel extends BaseDataModel {

    private String title;
    private String note;
    private long created;
    private long started;
    private long due;
    private long ended;
    private Important important;
    private int createdByType;
    private BaseDataModel createdByModel;
    private boolean actual;

    public enum Important {IMPORTANT_URGENT, IMPORTANT_NOT_URGENT, NOT_IMPORTANT_URGENT, NOT_IMPORTANT_NOT_URGENT;

        public static Important getByNumber(int num){
            switch(num){
                case 0: return Important.IMPORTANT_URGENT;
                case 1: return Important.IMPORTANT_NOT_URGENT;
                case 2: return Important.NOT_IMPORTANT_URGENT;
                case 3: return Important.NOT_IMPORTANT_NOT_URGENT;
                default:
                    Log.e("Important get", "Wrong number. returned value by 0");
                    return Important.IMPORTANT_URGENT;
            }
        }

        @Override
        public String toString() {
            switch(this){
                case IMPORTANT_URGENT:
                    return FrameManager.getMainContext().getResources().getStringArray(R.array.importants)[0];
                case IMPORTANT_NOT_URGENT:
                    return FrameManager.getMainContext().getResources().getStringArray(R.array.importants)[1];
                case NOT_IMPORTANT_URGENT:
                    return FrameManager.getMainContext().getResources().getStringArray(R.array.importants)[2];
                case NOT_IMPORTANT_NOT_URGENT:
                    return FrameManager.getMainContext().getResources().getStringArray(R.array.importants)[3];
            }
            Log.e("Enum Important", "return empty string statement");
            return "";
        }
    }


    public final static int ID        = 1;
    public final static int TITLE     = 2;
    public final static int IMPORTANT = 3;
    public final static int NOTE      = 4;
    public final static int CREATED   = 5;
    public final static int STARTED   = 6;
    public final static int DUE       = 7;
    public final static int ENDED     = 8;
    public final static int ACTUAL    = 9;
    public final static int CREATED_BY_TYPE = 10;
    public final static int CREATED_BY_MODEL = 11;

    public final static int CREATED_BY_NONE = 0;
    public final static int CREATED_BY_OBJECT = 1;
    public final static int CREATED_BY_CONTACT = 2;
    public final static int CREATED_BY_TASK = 3;

//    public final static int TYPE_STRING  = 1;
//    public final static int TYPE_NUMBER  = 2;
//    public final static int TYPE_DATE    = 3;
//    public final static int TYPE_BOOLEAN = 4;
//    public final static int TYPE_LIST    = 5;

    public BusinessDataModel(int id,
                             String title,
                             long started,
                             Important important){
        super(id);
        this.title = title;
        this.important = important;
        this.note = "";
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTimeZone(MainActivity.timeZone);
        this.created = currentTime.getTimeInMillis();
        this.started = started;
        this.due = 0;
        this.ended = 0;
        this.actual = true;
    }

    public BusinessDataModel(int id,
                             String title,
                             Important important,
                             BaseDataModel createdByModel,
                             String note,
                             long created,
                             long started,
                             long due,
                             long ended){
        super(id);
        this.title = title;
        this.important = important;
        this.note = note;
        this.created = created;
        this.started = started;
        this.due = due;
        this.ended = ended;
        this.actual = true;
        this.createdByModel = createdByModel;
        if(createdByModel != null) {
            if (createdByModel instanceof ContactDataModel)
                this.createdByType = CREATED_BY_CONTACT;
            else if (createdByModel instanceof ObjectDataModel)
                this.createdByType = CREATED_BY_OBJECT;
            else if(createdByModel instanceof  TaskDataModel)
                this.createdByType = CREATED_BY_TASK;

            else
                Log.e("BusinessDataModel", "Created by type wrong!");
        } else
            this.createdByType = CREATED_BY_NONE;
    }

    public Object get(int id){
        switch(id){
            case ID:
                return this.getId();
            case TITLE:
                return this.title;
            case IMPORTANT:
                return this.important;
            case NOTE:
                return this.note;
            case STARTED:
                return this.started;
            case CREATED:
                return this.created;
            case DUE:
                    return this.due;
            case ENDED:
                    return this.ended;
            case ACTUAL:
                return this.actual;
            case CREATED_BY_TYPE:
                return this.createdByType;
            case CREATED_BY_MODEL:
                return this.createdByModel;
            default:
                Log.e("BusinessDataModel", "Unknown field in BusinessDataModel.get. ID: " + id);
        }
        return null;
    }

    @Override
    public boolean set(int id, Object value) {
        boolean flag = false;

        switch (id){
            case ID:
                if(value instanceof Integer)
                    return setId((int) value);
                else if(value instanceof Long)
                    return setId(((Long) value).intValue());
                break;
            case TITLE:
                if(value instanceof String)
                    return setTitle((String) value);
                else if(value instanceof Long)
                    return setId(((Long) value).intValue());
                break;
            case IMPORTANT:
                if(value instanceof Important)
                    return setImportant((Important) value);
                break;
            case NOTE:
                if(value instanceof String)
                    return setNote((String) value);
                break;
            case CREATED:
                if(value instanceof Long)
                    return setCreated((long) value);
                break;
            case STARTED:
                if(value instanceof Long)
                    return setStarted((long) value);
                break;
            case DUE:
                if(value instanceof Long)
                    return setDue((long) value);
                break;
            case ENDED:
                if(value instanceof Long)
                    return setEnded((long) value);
                break;
            case ACTUAL:
                if(value instanceof Boolean)
                    return setActual((boolean) value);
                break;
            case CREATED_BY_MODEL:
                return setCreatedByModel((BaseDataModel) value);
            default:
                Log.e("BusinessDataModel", "Unknown field in BusinessDataModel.set");
        }
        return false;
    }

    public String getFieldName(int id){

        switch(id){
            case ID:
                return FrameManager.getMainContext().getResources().getText(R.string.business_id).toString();
            case TITLE:
                return FrameManager.getMainContext().getResources().getText(R.string.business_title).toString();
            case IMPORTANT:
                return FrameManager.getMainContext().getResources().getText(R.string.business_important).toString();
            case NOTE:
                return FrameManager.getMainContext().getResources().getText(R.string.business_note).toString();
            case CREATED:
                return FrameManager.getMainContext().getResources().getText(R.string.business_created).toString();
            case STARTED:
                return FrameManager.getMainContext().getResources().getText(R.string.business_started).toString();
            case DUE:
                return FrameManager.getMainContext().getResources().getText(R.string.business_due).toString();
            case ENDED:
                return FrameManager.getMainContext().getResources().getText(R.string.business_ended).toString();
            case ACTUAL:
                return FrameManager.getMainContext().getResources().getText(R.string.business_actual).toString();
            case CREATED_BY_MODEL:
                if(this.createdByType == CREATED_BY_CONTACT)
                    return FrameManager.getMainContext().getResources().getText(R.string.business_created_by_contact).toString();
                else if(this.createdByType == CREATED_BY_OBJECT)
                    return FrameManager.getMainContext().getResources().getText(R.string.business_created_by_object).toString();
                else if(this.createdByType == CREATED_BY_TASK)
                    return FrameManager.getMainContext().getResources().getText(R.string.business_created_by_task).toString();
                else
                    return "None";
        }
        Log.e("BusinessDataModel", "Wrong getResourceId");
        return null;
    }

    @Override
    public int getTypeById(int id){
        switch(id){
            case ID:
                return Dialogs.ID_NUMBER;
            case TITLE:
                return Dialogs.ID_STRING;
            case IMPORTANT:
                return Dialogs.ID_LIST;
            case NOTE:
                return Dialogs.ID_STRING;
            case CREATED:
                return Dialogs.ID_DATE;
            case STARTED:
                return Dialogs.ID_DATE;
            case DUE:
                return Dialogs.ID_DATE;
            case ENDED:
                return Dialogs.ID_DATE;
            case ACTUAL:
                return Dialogs.ID_BOOLEAN;
            case CREATED_BY_MODEL:
                return Dialogs.ID_REFERENCE;
        }
        Log.e("BusinessDatamodel", "getTypeId. Wrong parametr");
        return 0;
    }

    public boolean setTitle(String title) {
        this.title = title;
        ContentValues content = new ContentValues();
        content.put(TableBusiness.KEY_BUSINESS_TITLE, title);
        DB.useBusiness().update(getId(), content);
        return true;
    }

    public boolean setNote(String note) {
        this.note = note;
        ContentValues content = new ContentValues();
        content.put(TableBusiness.KEY_BUSINESS_NOTE, note);
        DB.useBusiness().update(getId(), content);
        return true;
    }

    public boolean setCreated(long created) {
        this.created = created;
        ContentValues content = new ContentValues();
        content.put(TableBusiness.KEY_BUSINESS_CREATED, created);
        DB.useBusiness().update(getId(), content);
        return true;
    }

    public boolean setEnded(long ended) {
        if(ended > started || ended == 0){
            this.ended = ended;
            ContentValues content = new ContentValues();
            content.put(TableBusiness.KEY_BUSINESS_ENDED, ended);
            DB.useBusiness().update(getId(), content);
            return true;
        }
        return false;
    }

    public boolean setDue(long due) {
        this.due = due;
        ContentValues content = new ContentValues();
        content.put(TableBusiness.KEY_BUSINESS_DUE, due);
        DB.useBusiness().update(getId(), content);
        return true;
    }

    public boolean setActual(boolean actual) {
        this.actual = actual;
        ContentValues content = new ContentValues();
        return true;
    }

    public boolean setImportant(Important important) {
        this.important = important;
        ContentValues content = new ContentValues();
        content.put(TableBusiness.KEY_BUSINESS_IMPORTANT, important.ordinal());
        DB.useBusiness().update(getId(), content);
        return true;
    }

    public boolean setStarted(long started){
        if(started > created || started == 0){
            this.started = started;
            ContentValues content = new ContentValues();
            content.put(TableBusiness.KEY_BUSINESS_STARTED, started);
            DB.useBusiness().update(getId(), content);
            return true;
        }
        return false;
    }

    public boolean setCreatedByModel(BaseDataModel createdByModel) {
        this.createdByModel = createdByModel;
        ContentValues content = new ContentValues();
        if(createdByModel != null)
            if(createdByModel instanceof ContactDataModel) {
                this.createdByType = CREATED_BY_CONTACT;
                content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, (int) createdByModel.get(ContactDataModel.ID));
                content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, 0);
            } else if(createdByModel instanceof ObjectDataModel) {
                this.createdByType = CREATED_BY_OBJECT;
                content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, (int) createdByModel.get(ObjectDataModel.ID));
                content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, 0);
            } else if(createdByModel instanceof TaskDataModel){
                this.createdByType = CREATED_BY_TASK;
                content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, 0);
                content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, (int) createdByModel.get(TaskDataModel.ID));
            } else
                return false;
        else {
            this.createdByType = CREATED_BY_NONE;
            content.put(TableBusiness.KEY_BUSINESS_FK_CONTACT_ID, 0);
            content.put(TableBusiness.KEY_BUSINESS_FK_OBJECT_ID, 0);
            content.put(TableBusiness.KEY_BUSINESS_FK_TASK_ID, 0);
        }
        return true;
    }
}
