package net.shemand.anull.datebase.tableInterfaces;

/**
 * Created by Shema on 15.05.2018.
 */

public interface TableTasks {

    String TABLE_TASKS = "tasks";

    String KEY_TASKS_ID = "_id";
    String KEY_TASKS_TITLE = "task_name";
    String KEY_TASKS_IMPORTANT = "task_important";
    String KEY_TASKS_NOTE = "task_note";
    String KEY_TASKS_COLOR = "task_color";
    String KEY_TASKS_FK_PARENT_ID = "task_parent_id";
    String KEY_TASKS_FK_OBJECT_ID = "object_id";
    String KEY_TASKS_FK_CONTACT_ID = "contact_id";
    String KEY_TASKS_CREATED = "task_created_date";
    String KEY_TASKS_STARTED = "task_start_date";
    String KEY_TASKS_DUE = "task_due_date";
    String KEY_TASKS_ENDED = "task_ended_date";
}
