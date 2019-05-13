package com.example.marti.projecte_uf1.DonorFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Reward;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class RewardsFragment extends Fragment {

    rewardsAdapter adapter;
    RecyclerView rv;
    @BindView(R.id.empty_view)
    TextView emptyView;
    Unbinder unbinder;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
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

        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        unbinder = ButterKnife.bind(this, view);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateList();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(R.string.rewards);
        populateList();
    }

    private void populateList() {

        String currentUserIdString = prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, null);
        int currentUserId = Integer.valueOf(currentUserIdString);
        mAPIService.getAvailableRewardByDonor(currentUserId).enqueue(new Callback<List<Reward>>() {
            @Override
            public void onResponse(Call<List<Reward>> call, Response<List<Reward>> response) {
                if (response.isSuccessful()) {
                    List<Reward> list = response.body();
                    setAdapter(new ArrayList<Reward>(list));
                    swipeContainer.setRefreshing(false);

                } else {
                    Toast.makeText(getActivity(), getString(R.string.cannot_connect_to_server2), Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<List<Reward>> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.cannot_connect_to_server2), Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);
            }
        });

    }

    private void setAdapter(ArrayList<Reward> list) {

        if (list.size() > 0) {

            try{
                YoYo.with(Techniques.FadeOut).duration(1300).playOn(emptyView);
            } catch(Exception ex){

            }
            emptyView.setVisibility(View.GONE);
            String currentUserIdString = prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, null);
            int currentUserId = Integer.valueOf(currentUserIdString);

            adapter = new rewardsAdapter(list, getActivity(), currentUserId);
            rv = getView().findViewById(R.id.reward_recyclerview);
            mLayoutManager = new LinearLayoutManager(getActivity());

            rv.setLayoutManager(mLayoutManager);
            rv.addItemDecoration(new DividerItemDecoration(getContext(),
                    DividerItemDecoration.VERTICAL));

            rv.setAdapter(adapter);
        } else {
            if (emptyView.getVisibility() == View.GONE) {

                try{
                    YoYo.with(Techniques.FadeIn).duration(1300).playOn(emptyView);
                } catch(Exception ex){

                }
                emptyView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
