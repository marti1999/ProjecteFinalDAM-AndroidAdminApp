package com.example.marti.projecte_uf1;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import static android.content.Context.NOTIFICATION_SERVICE;
import static android.support.v4.content.ContextCompat.getSystemService;


public class Fragment2 extends Fragment {

    SQLiteManager manager = new SQLiteManager(getActivity());
    esdevenimentAdapter adapter;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
  //  ViewGroup group;

    public Fragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragment2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        group = getView().findViewById(R.id.frame2);
//        TransitionManager.beginDelayedTransition(group);

        getActivity().setTitle("Events");
        populateList();


    }

    private void populateList() {
        ArrayList<Esdeveniment> list = manager.getListEsdeveniments();
        adapter = new esdevenimentAdapter(list, getActivity());
        recyclerView = getView().findViewById(R.id.my_recycler_view2);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapter);
    }


}

