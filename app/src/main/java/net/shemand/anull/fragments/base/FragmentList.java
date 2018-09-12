package net.shemand.anull.fragments.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import net.shemand.anull.R;
import net.shemand.anull.adapters.base.RVListAdapter;
import net.shemand.anull.models.DataModels.BaseDataModel;

public abstract class FragmentList extends FragmentBase implements RVListAdapter.OnCardActionListener{

    private RecyclerView rv;
    private RVListAdapter rv_adapter;
    private RecyclerView.LayoutManager rv_manager;

    private OnFragmentInteractionListener mListener;

    private int currentPosition;

    public FragmentList(RVListAdapter adapter) {
        this.rv_adapter = adapter;
        setTypeFloatingButton(FragmentBase.FLOATING_TYPE_ADD);
        setHasOptionsMenu(false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(false);
    }

    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState);

    public View createRootView(View rootView) {
        rv = rootView.findViewById(R.id.business_rv);
        rv_manager = new LinearLayoutManager(getActivity());
        rv_adapter.setOnCardActionListener(this);
        rv.setAdapter(rv_adapter);
        rv.setLayoutManager(rv_manager);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onCardClick(View v, BaseDataModel model) {
        v.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.click));
    }

    @Override
    public void onCardLongClick(View v, int position) {
        currentPosition = position;
    }


    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(int id);
    }

    public RVListAdapter getRv_adapter() {
        return rv_adapter;
    }

    public int getContextItemPosition() { return currentPosition; }
}
