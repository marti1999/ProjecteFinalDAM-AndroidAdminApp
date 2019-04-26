package com.example.marti.projecte_uf1.mutualFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.marti.projecte_uf1.DonorFragments.rewardsAdapter;
import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.remote.ApiUtils;

import static android.content.Context.MODE_PRIVATE;


public class AnnouncementsFragment extends Fragment {

    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    rewardsAdapter adapter;
    RecyclerView rv;
    private RecyclerView.LayoutManager mLayoutManager;

    public AnnouncementsFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();

        prefs = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_announcements, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //TODO: aquiva el populate de la lista de announcements
    }
}
