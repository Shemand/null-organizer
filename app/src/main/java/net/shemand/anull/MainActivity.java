package net.shemand.anull;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import net.shemand.anull.datebase.DB;
import net.shemand.anull.dialogs.Dialogs;
import net.shemand.anull.fragments.base.FragmentInfo;
import net.shemand.anull.fragments.base.FragmentList;
import net.shemand.anull.fragments.business.FragmentBusinessAdd;
import net.shemand.anull.fragments.contact.FragmentContactAdd;
import net.shemand.anull.fragments.task.FragmentTaskAdd;
import net.shemand.anull.models.DataModels.BusinessDataModel;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import hirondelle.date4j.DateTime;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
                                                               FragmentList.OnFragmentInteractionListener,
                                                               FragmentInfo.OnFragmentInteractionListener {

    private FrameManager frameManager;

    DrawerLayout drawer;

    private int lastTakedClause = 0;
    private final static int DRAWER_ITEM_CONTACTS = 1;
    private final static int DRAWER_ITEM_TASKS = 2;
    private final static int DRAWER_ITEM_TODAY= 3;
    private final static int DRAWER_ITEM_OBJECTS = 4;

    private Calendar takedDate;

    private boolean useCalendar = false;

    public final static TimeZone timeZone = TimeZone.getTimeZone("Europe/Moscow");

    private CaldroidFragment caldroidFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Toolbar general_toolbar = (Toolbar) findViewById(R.id.general_toolbar);

        setSupportActionBar(general_toolbar);
        general_toolbar.setTitle(R.string.app_name);
        general_toolbar.setTitleTextColor(getResources().getColor(R.color.toolbar_text_color));
        frameManager = FrameManager.createInstance(MainActivity.this, getSupportFragmentManager());

        drawer = (DrawerLayout) findViewById(R.id.root_main_activity); // ссылка на drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle( // создание функции для обработчика на открытие и закрытия drawer
                this, drawer, general_toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                switch (lastTakedClause) {
                    case R.id.nav_contacts:
                        useCalendar = false;
                        FrameManager.getInstance().changeFrame().toList().withContacts();
                        break;
                    case R.id.nav_tasks:
                        useCalendar = false;
                        FrameManager.getInstance().changeFrame().toList().withTasks();
                        break;
                    case R.id.nav_today:
                        Calendar now = Calendar.getInstance();
                        Calendar setDate = Calendar.getInstance();
                        setDate.setTimeZone(MainActivity.timeZone);

                        FrameManager.getInstance().changeFrame().toList().withBusiness(setDate);
                        break;
                    case R.id.nav_objects:
                        useCalendar = false;
                        FrameManager.getInstance().changeFrame().toList().withObjects();
                        break;
                    default:
                        if(useCalendar)
                            if(takedDate != null) {
                                caldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(getResources().getColor(R.color.caldroid_holo_blue_light)), new Date(takedDate.getTimeInMillis()));
                                FrameManager.getInstance().changeFrame().toList().withBusiness(takedDate);
                            }

                }

            }


        };
        drawer.addDrawerListener(toggle); // установка обработчика дровера
        toggle.syncState(); // синхронизирование состояние доступности дровера


        NavigationView navigationView = (NavigationView) findViewById(R.id.drawer); // ссылка на навигейшн вью
        navigationView.setNavigationItemSelectedListener(this); // установка обработчика выбора пунктов навигатора
        Log.e("on", "Create");
        Log.e("onCreate", BusinessDataModel.Important.IMPORTANT_NOT_URGENT.ordinal() + "");
//        DB.useBusiness().add(new BusinessDataModel(0,"second", new Date(), BusinessDataModel.Important.NOT_IMPORTANT_URGENT));
//        DB.useBusiness().getList();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.e("onSave","InstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FrameManager.destroyManager();
        DB.destroyInstance();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("pau", "se");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("onResume", "Elements in stack" + frameManager.getElementsInStack());
        if(frameManager.getElementsInStack() == 0){
            Calendar currentTime = Calendar.getInstance();
            currentTime.setTimeZone(MainActivity.timeZone);
            frameManager.changeFrame().toList().withBusiness(currentTime);
            Log.e("onResume", "Add new element in stack, now =" + frameManager.getElementsInStack());
        }
    }

    // Реализация работы меню активности
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(MainActivity.timeZone);
        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
        args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
        caldroidFragment.setArguments(args);
        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                useCalendar = true;
                lastTakedClause = 0;
                takedDate = Calendar.getInstance();
                takedDate.setTimeInMillis(date.getTime());
                drawer.closeDrawers();
            }
        };
        caldroidFragment.setCaldroidListener(listener);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.drawer_calendar, caldroidFragment);
        t.commit();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(getApplicationContext(), "Lol", Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }


    // Реализация drawer
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);
        lastTakedClause = item.getItemId();
        caldroidFragment.setBackgroundDrawableForDate(new ColorDrawable(getResources().getColor(R.color.caldroid_holo_blue_light)), new Date());
        drawer.closeDrawers();
        return false;
    }

    @Override
    public void onBackPressed() { // обработчик кнопки back
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.root_main_activity);
        if (drawer.isDrawerOpen(GravityCompat.START)) { // если открыто меню
            drawer.closeDrawer(GravityCompat.START); // закрыть меню
        } else {
            if(frameManager.getPeek() instanceof FragmentTaskAdd ||
                    frameManager.getPeek() instanceof FragmentContactAdd ||
                    frameManager.getPeek() instanceof FragmentBusinessAdd) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(getResources().getText(R.string.dialog_leave_message)).setCancelable(false);
                builder.setPositiveButton(getResources().getText(R.string.dialog_button_accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        frameManager.backFrame();
                        if(frameManager.getElementsInStack() == 0) {
                            onSuperBackPressed();
                        }
                    }
                }).setNegativeButton(getResources().getText(R.string.dialog_button_cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.create().show();
            } else {
                frameManager.backFrame();
                if(frameManager.getElementsInStack() == 0) {
                    super.onBackPressed(); // выполнить днешнее действие
                }
            }

        }

    }

    public void onSuperBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onFragmentInteraction(int id) {

    }
}
