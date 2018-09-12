package net.shemand.anull;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import net.shemand.anull.fragments.base.FragmentBase;
import net.shemand.anull.controllers.AddController;
import net.shemand.anull.controllers.InfoController;
import net.shemand.anull.controllers.ListController;

import java.util.Stack;

/**
 * Created by Shema on 29.04.2018.
 */

public class FrameManager {

    private Stack<FragmentBase> stateStack;

    private final static int container = R.id.container;

    private static FrameManager instance = null;
    private static Context context;
    private FragmentManager fManager;
    private FragmentTransaction transfer;

    // elements
    private FloatingActionButton addButton;


    private Fragment Frame;
    private int drawerClause;
    private String header;

    public static final int FRAME_BUSINESS_LIST   = 1;
    public static final int FRAME_BUSINESS_INFO   = 2;
    public static final int FRAME_BUSINESS_ADD    = 3;
    public static final int FRAME_CONTACTS_LIST   = 4;
    public static final int FRAME_CONTACT_INFO    = 5;
    public static final int FRAME_CONTACT_ADD     = 6;
    public static final int FRAME_TASK_INFO       = 8;
    public static final int FRAME_TASK_ADD        = 9;
    public static final int FRAME_OBJECTS_LIST    = 10;
    public static final int FRAME_OBJECT_INFO     = 11;
    public static final int FRAME_OBJECT_ADD      = 12;
    public static final int FRAME_PLACES_LIST     = 13;
    public static final int FRAME_INTERNET_LIST   = 14;
    public static final int FRAME_PHONES_LIST     = 15;
    public static final int FRAME_TASKS_LIST      = 16;

    private FrameManager(Context context, FragmentManager manager) {
        // initialization
        this.context = context;
        fManager = manager;

        // find views
        addButton = ((MainActivity) context).findViewById(R.id.addButton);

        // add listeners
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stateStack.peek().onClickFloatingButton();
            }
        });

        // another actions
        stateStack = new Stack<>();
    }

    public static FrameManager createInstance(Context context, FragmentManager manager) {
        if (instance == null) {
            instance = new FrameManager(context, manager);
        }
        return instance;
    }

    public static FrameManager getInstance() {
        if (instance == null) {
            return null;
        }
        return instance;
    }

    public static void destroyManager() {
        instance = null;
    }

    public static Context getMainContext(){
        return context;
    }

    public int getElementsInStack() {
        return stateStack.size();
    }

    public void changeState(FragmentBase frag, boolean replace) {
        if(replace)
            stateStack.pop();
        stateStack.add(frag);
        setFrameSettings();
    }

    private void setFrameSettings(){
        transfer = fManager.beginTransaction();
        transfer.replace(container, stateStack.peek());
        transfer.commit();
        if (stateStack.peek().isFloatingButton()) {
            addButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.INVISIBLE);
        }
        if(stateStack.peek().getTypeFloatingButton() == FragmentBase.FLOATING_TYPE_DONE)
            addButton.setImageResource(R.drawable.ic_done_black_24dp);
        else
            addButton.setImageResource(R.drawable.ic_add_floating_24dp);
        ((MainActivity)getMainContext()).setTitle(stateStack.peek().getFragmentTitle());
    }

    public void backFrame(){
        stateStack.pop();
        if(getElementsInStack() > 0) {
            setFrameSettings();
        }
    }

    public FragmentBase getPeek(){
        return stateStack.peek();
    }



    public FrameChanger changeFrame() { return changeFrame(false); }
    public FrameChanger changeFrame(boolean replace) {
        transfer = fManager.beginTransaction(); // todo "maybe it's wrong"
        return new FrameChanger(replace);
    }

    public class FrameChanger {

        boolean replace;

        FrameChanger(boolean replace) {
            this.replace = replace;
        }

        public ListController toList() {
            return new ListController(replace);
        }

        public InfoController toInfo() {
            return new InfoController(replace);
        }

        public AddController toAdd() {
            return new AddController(replace);
        }
    }
}