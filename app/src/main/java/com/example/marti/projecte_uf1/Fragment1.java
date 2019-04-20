package com.example.marti.projecte_uf1;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment {

    SQLiteManager manager = new SQLiteManager(getActivity());
    personaAdapter adapter;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    Persona p = new Persona();

    FloatingActionButton fab;
    FloatingActionButton fab1;
    FloatingActionButton fab2;
    FloatingActionButton fab3;

    boolean isFABOpen;


    public Fragment1() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        prefs = getActivity().getSharedPreferences(sharedPrefFile, getActivity().MODE_PRIVATE);
        prefsEditor = prefs.edit();
        p = manager.getPersona(prefs.getString("LAST_LOGIN", ""));



        return inflater.inflate(R.layout.fragment_fragment1, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contacts"); //todo canviar

        fab = getView().findViewById(R.id.fab);
        fab1 = getView().findViewById(R.id.fab1);
        fab2 = getView().findViewById(R.id.fab2);
        fab3 = getView().findViewById(R.id.fab3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

// todo        populateList(); tornar-ho a activar quan faci falta
    }

    private void populateList() { //todo canviar-ho tot pel que faci falta. A poder ser, modificar el adapter y tal abans de crear un nou

//        manager.openRead();
        //ArrayList<Persona> list = manager.getListPersones();
        ArrayList<Persona> list = new ArrayList<Persona>();
        try{
            list = manager.getListContactes(p.getEmail());
        } catch (Exception ex) {
            populateList();
        }


        //

        adapter = new personaAdapter(list, getActivity());
        recyclerView = getView().findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        RecyclerView.LayoutManager mLayoutManagerCol = new GridLayoutManager(getActivity(), 3);

        recyclerView.setLayoutManager(mLayoutManagerCol);

        recyclerView.setAdapter(adapter);
    }

    private void showFABMenu(){
        isFABOpen=true;
        fab1.animate().setDuration(500).translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().setDuration(400).translationY(-getResources().getDimension(R.dimen.standard_105));
        fab3.animate().setDuration(300).translationY(-getResources().getDimension(R.dimen.standard_155));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fab1.animate().setDuration(500).translationY(0);
        fab2.animate().setDuration(400).translationY(0);
        fab3.animate().setDuration(300).translationY(0);
    }
}
