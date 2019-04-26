package com.example.marti.projecte_uf1.DonorFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.esdevenimentAdapter;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Reward;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class RewardsFragment extends Fragment {

    rewardsAdapter adapter;
    RecyclerView rv;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;


    public RewardsFragment() {

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

        return inflater.inflate(R.layout.fragment_rewards, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Rewards");
        populateList();
    }
    private void populateList(){

        String currentUserIdString = prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, null);
        int currentUserId = Integer.valueOf(currentUserIdString);
        mAPIService.getAvailableRewardByDonor(currentUserId).enqueue(new Callback<List<Reward>>() {
            @Override
            public void onResponse(Call<List<Reward>> call, Response<List<Reward>> response) {
                if (response.isSuccessful()){
                    List<Reward> list = response.body();
                    // Toast.makeText(getActivity(), String.valueOf(list.size()), Toast.LENGTH_SHORT).show();
                    setAdapter(new ArrayList<Reward>(list));

                } else{
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Reward>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

 }

    private void setAdapter(ArrayList<Reward> list) {
        String currentUserIdString = prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, null);
        int currentUserId = Integer.valueOf(currentUserIdString);

        adapter = new rewardsAdapter(list, getActivity(), currentUserId);
        rv = getView().findViewById(R.id.reward_recyclerview);
        mLayoutManager = new LinearLayoutManager(getActivity());

        rv.setLayoutManager(mLayoutManager);
        rv.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));

        rv.setAdapter(adapter);
    }

}
