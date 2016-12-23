package com.wwschrader.android.scavengehunter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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
 * Created by Warren on 12/9/2016.
 * Fragment for adding, modifying, or deleting hunt activities
 */

@SuppressWarnings("FieldCanBeLocal")
public class AdminActivityManagementFragment extends Fragment {

    private FloatingActionButton addActivityActionButton;
    private RecyclerView mRecyclerView;
    private DatabaseReference mDatabaseReference;
    private DatabaseReference mObjectiveReference;
    private FirebaseRecyclerAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_admin_activity_management, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.objective_recycler_view);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        mObjectiveReference = mDatabaseReference.child("objectives").child(NavigationActivity.huntUid);
        mAdapter = new ObjectivesRecyclerViewAdapter(
                HuntObjectives.class,
                R.layout.view_holder_objectives,
                ObjectiveRecyclerViewHolder.class,
                mObjectiveReference, getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

        addActivityActionButton = (FloatingActionButton) rootView.findViewById(R.id.activities_add_fab);
        addActivityActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment createObjectiveFragment = new CreateObjectiveDialogFragment();
                createObjectiveFragment.show(getFragmentManager(), "CreateObjectiveDialogFragment");
            }
        });

        return rootView;
    }
}
