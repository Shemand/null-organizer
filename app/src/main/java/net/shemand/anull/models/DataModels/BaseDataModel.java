package net.shemand.anull.models.DataModels;

/**
 * Created by Shema on 08.05.2018.
 */

public abstract class BaseDataModel {

    private int id;

    public BaseDataModel(int id) {
        this.id = id;
    }

    public abstract Object get(int id);
    public abstract boolean set(int id, Object value);

    public abstract String getFieldName(int id);
    public abstract int getTypeById(int id);

    public boolean setId(int value) {
        this.id = value;
        return true;
    }
    public int getId() {
        return id;
    }

}
