package net.shemand.anull.fragments.contact;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.shemand.anull.FrameManager;
import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVInfoAdapter;
import net.shemand.anull.fragments.base.FragmentInfo;

/**
 * Created by Shema on 30.05.2018.
 */

public class FragmentContactInfo extends FragmentInfo {

    public FragmentContactInfo(RVInfoAdapter adapter) {
        super(adapter);
        setHasFloatingButton(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        rootView = createRootView(rootView);
        return rootView;
    }

    @Override
    public void onClickFloatingButton() {

    }

    @Override
    public String getFragmentTitle() {
        return FrameManager.getMainContext().getResources().getText(R.string.standart_title_contact_info).toString();
    }
}
