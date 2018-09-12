package net.shemand.anull.dialogs;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.widget.Toast;

import net.shemand.anull.R;

/**
 * Created by Shema on 12.05.2018.
 */

public class Dialogs
{
    public static boolean lastUseDialog;

    public static final int ID_STRING    = 1;
    public static final int ID_NUMBER    = 2;
    public static final int ID_DATE      = 3;
    public static final int ID_BOOLEAN   = 4;
    public static final int ID_LIST      = 5;
    public static final int ID_REFERENCE = 6;
    public static final int ID_PHONE     = 7;
    public static final int ID_PLACE     = 8;
    public static final int ID_OBJECT    = 9;
    public static final int ID_ACCEPT    = 10;
    public static final int ID_MESSAGE   = 11;

    public static AlertDialog create(Activity activity, int id) {
        switch (id) {
            case ID_STRING:
                return createStringDialog(activity);
            case ID_NUMBER:
                return createNumberDialog(activity);
            case ID_DATE:
                return createDateDialog(activity);
            case ID_LIST:
                return createListDialog(activity);
            case ID_PHONE:
                return createPhoneDialog(activity);
            case ID_PLACE:
                return createPlaceDialog(activity);
            case ID_OBJECT:
                return createObjectDialog(activity);
        }
        return null;
    }

    public static void createMessageDialog(Activity activity,String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setCancelable(true);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.create().show();
    }

    public static boolean createAcceptDialog(Activity activity, String message) {
        final boolean flag = false;
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage(message).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_accept), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialogs.lastUseDialog = true;
            }
        });
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Dialogs.lastUseDialog = false;
            }
        });
        builder.create().show();
        return lastUseDialog;
    }

    private static AlertDialog createObjectDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getText(R.string.dialog_title)).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_save), null);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.object_dialog_view, null));
        return builder.create();
    }

    private static AlertDialog createPlaceDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getText(R.string.dialog_title)).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_save), null);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.place_dialog_view, null));
        return builder.create();
    }

    private static AlertDialog createPhoneDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getText(R.string.dialog_title)).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_save), null);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.phone_dialog_view, null));
        return builder.create();
    }

    private static AlertDialog createListDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getText(R.string.dialog_title)).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_save), null);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.list_dialog_view, null));
        return builder.create();
    }

    private static AlertDialog createStringDialog(final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getText(R.string.dialog_title)).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_save), null);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.string_dialog_view, null));
        return builder.create();
    }

    private static AlertDialog createNumberDialog(final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getText(R.string.dialog_title)).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_save), null);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.number_dialog_view, null));
        return builder.create();
    }

    private static AlertDialog createDateDialog(final Activity activity){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(activity.getResources().getText(R.string.dialog_title)).setCancelable(true);
        builder.setPositiveButton(activity.getResources().getText(R.string.dialog_button_save), null);
        builder.setNeutralButton(activity.getResources().getText(R.string.dialog_button_clear), null);
        builder.setNegativeButton(activity.getResources().getText(R.string.dialog_button_cancel), null);
        builder.setView(activity.getLayoutInflater().inflate(R.layout.date_dialog_view, null));
        return builder.create();
    }
}
