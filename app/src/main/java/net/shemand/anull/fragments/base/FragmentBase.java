package net.shemand.anull.fragments.base;

import android.support.v4.app.Fragment;

import net.shemand.anull.FrameManager;
import net.shemand.anull.MainActivity;
import net.shemand.anull.R;

import java.util.Calendar;

/**
 * Created by Shema on 01.06.2018.
 */

public abstract class FragmentBase extends Fragment {

    private boolean isFloatingButton = false;
    private int typeFloatingButton = FLOATING_TYPE_ADD;

    public final static int FLOATING_TYPE_ADD = 1;
    public final static int FLOATING_TYPE_DONE = 2;

    public abstract void onClickFloatingButton();
    public void setHasFloatingButton(boolean x) { isFloatingButton = x; }
    public boolean isFloatingButton() { return isFloatingButton; }

    public abstract String getFragmentTitle();

    public void setTypeFloatingButton(int x) { typeFloatingButton = x; }
    public int getTypeFloatingButton() { return typeFloatingButton; }

}
