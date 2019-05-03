package com.example.marti.projecte_uf1.RequestorFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Cloth;
import com.example.marti.projecte_uf1.model.Warehouse;
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

public class clothFragment extends Fragment {
    @BindView(R.id.warehouse_recyclerview)
    RecyclerView rv;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    Unbinder unbinder;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    //  SwipeRefreshLayout swipeContainer;
//    RecyclerView rv;
    clothAdapter adapter;

    public clothFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();

        prefs = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cloth, container, false);
        unbinder = ButterKnife.bind(this, view);
        //TODO: canviar el parametre de la linia de sota al populate list
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateList(new Cloth());
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
        getActivity().setTitle("Search clothes");
             populateList(new Cloth());
    }

    private void populateList(Cloth c) {
      //  mAPIService.getWarehousesByCloth(c).enqueue(new Callback<List<Warehouse>>() {
        //TODO: fer servir la linia de dalt
        mAPIService.getWarehoues().enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (response.isSuccessful()) {
                    List<Warehouse> list = response.body();
                    setAdapter(new ArrayList<Warehouse>(list));
                    swipeContainer.setRefreshing(false);

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setAdapter(ArrayList<Warehouse> warehouses) {
        if (warehouses.size() > 0) {
            adapter = new clothAdapter(warehouses, getActivity());
            //rv
            mLayoutManager =  new GridLayoutManager(getActivity(), 2);
            rv.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), "No warehouse available", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
