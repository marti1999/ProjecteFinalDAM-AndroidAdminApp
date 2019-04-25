package com.example.marti.projecte_uf1.DonorFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.esdevenimentAdapter;
import com.example.marti.projecte_uf1.model.Reward;

import java.util.ArrayList;


public class RewardsFragment extends Fragment {

    rewardsAdapter adapter;
    RecyclerView rv;
    private RecyclerView.LayoutManager mLayoutManager;

    public RewardsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_rewards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Rewards");
    }
    private void populateList(){
        //TODO: populate list
        ArrayList<Reward> list = new ArrayList<>();
        adapter = new rewardsAdapter(list, getActivity());
        rv = getView().findViewById(R.id.my_recycler_view2);
        mLayoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(mLayoutManager);

        rv.setAdapter(adapter);
    }

}
