package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.wwschrader.android.scavengehunter.adapters.ObjectivesRecyclerViewAdapter;
import com.wwschrader.android.scavengehunter.objects.HuntObjectives;
import com.wwschrader.android.scavengehunter.viewholders.ObjectiveRecyclerViewHolder;

/**
 * Created by Warren on 1/3/2017.
 * Fragment for viewing objectives
 */

@SuppressWarnings("FieldCanBeLocal")
public class HuntObjectivesFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mObjectiveReference;
    private FirebaseRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hunt_objectives, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.hunt_objectives_recycler);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mObjectiveReference = mDatabaseReference.child("objectives").child(NavigationActivity.huntUid);
        mAdapter = new ObjectivesRecyclerViewAdapter(
                HuntObjectives.class,
                R.layout.view_holder_objectives,
                ObjectiveRecyclerViewHolder.class,
                mObjectiveReference, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }
}
