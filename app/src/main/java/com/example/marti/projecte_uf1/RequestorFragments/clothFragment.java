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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Classification;
import com.example.marti.projecte_uf1.model.Cloth;
import com.example.marti.projecte_uf1.model.Color;
import com.example.marti.projecte_uf1.model.Gender;
import com.example.marti.projecte_uf1.model.Size;
import com.example.marti.projecte_uf1.model.Warehouse;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class clothFragment extends Fragment {
    //  @BindView(R.id.warehouse_recyclerview)
    RecyclerView rv;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout swipeContainer;
    Unbinder unbinder;
    @BindView(R.id.spinClothType)
    Spinner spinClothType;
    @BindView(R.id.spinClothSize)
    Spinner spinClothSize;
    @BindView(R.id.spinClothColor)
    Spinner spinClothColor;
    @BindView(R.id.spinClothGender)
    Spinner spinClothGender;
    @BindView(R.id.chooseCloth)
    Button generateQr;
    @BindView(R.id.empty_view)
    TextView emptyView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    //  SwipeRefreshLayout swipeContainer;
//    RecyclerView rv;
    clothAdapter adapter;
    Cloth selectedCloth;

    public clothFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();

        prefs = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        fillSpinners();
    }

    private void fillSpinners() {
        setSizeSpinner();
        setGenderSpinner();
        setColorSpinner();
        setClassificationSpinner();
    }

    private void setClassificationSpinner() {
        mAPIService.getClothClassifications().enqueue(new Callback<List<Classification>>() {
            @Override
            public void onResponse(Call<List<Classification>> call, Response<List<Classification>> response) {
                if (response.isSuccessful()) {
                    List<Classification> list = response.body();
                    ArrayAdapter<Classification> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    spinClothType.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Classification>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setColorSpinner() {
        mAPIService.getClothColors().enqueue(new Callback<List<Color>>() {
            @Override
            public void onResponse(Call<List<Color>> call, Response<List<Color>> response) {
                if (response.isSuccessful()) {
                    List<Color> list = response.body();
                    ArrayAdapter<Color> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    spinClothColor.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Color>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setGenderSpinner() {
        mAPIService.getClothGenders().enqueue(new Callback<List<Gender>>() {
            @Override
            public void onResponse(Call<List<Gender>> call, Response<List<Gender>> response) {
                if (response.isSuccessful()) {
                    List<Gender> list = response.body();
                    ArrayAdapter<Gender> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    spinClothGender.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Gender>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSizeSpinner() {
        mAPIService.getClothSizes().enqueue(new Callback<List<Size>>() {
            @Override
            public void onResponse(Call<List<Size>> call, Response<List<Size>> response) {
                if (response.isSuccessful()) {
                    List<Size> list = response.body();
                    ArrayAdapter<Size> cAdapter =
                            new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);

                    spinClothSize.setAdapter(cAdapter);
                    cAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Size>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_cloth, container, false);
        unbinder = ButterKnife.bind(this, view);
        emptyView.setText("Select a cloth to see\navailable warehouses");
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
        //populateList(new Cloth());
    }

    private void populateList(Cloth c) {
        mAPIService.getWarehousesByCloth(c).enqueue(new Callback<List<Warehouse>>() {
            //TODO: fer servir la linia de dalt
            //mAPIService.getWarehoues().enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Warehouse> list = response.body();
                        setAdapter(new ArrayList<Warehouse>(list));

                    } else {
                        if (emptyView.getVisibility() == View.GONE) {

                            emptyView.setText("Cloth not available.\nPlease, try another.");
                            YoYo.with(Techniques.FadeIn).duration(1300).playOn(emptyView);
                            emptyView.setVisibility(View.VISIBLE);

                            Toast.makeText(getActivity(), "No warehouse available", Toast.LENGTH_SHORT).show();

                        }
                    }

                    swipeContainer.setRefreshing(false);

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    swipeContainer.setRefreshing(false);

                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                swipeContainer.setRefreshing(false);

            }
        });
    }

    private void setAdapter(ArrayList<Warehouse> warehouses) {
        if (warehouses.size() > 0) {
            YoYo.with(Techniques.FadeOut).duration(1300).playOn(emptyView);
            emptyView.setVisibility(View.GONE);

            adapter = new clothAdapter(warehouses, getActivity());
            rv = getView().findViewById(R.id.warehouse_recyclerview);
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            rv.setLayoutManager(mLayoutManager);

            rv.setAdapter(adapter);
        } else {
//            if (emptyView.getVisibility()== View.GONE) {
//
//                emptyView.setText("No warehouse available for this cloth.\\nPlease, try another.");
//                YoYo.with(Techniques.FadeIn).duration(1300).playOn(emptyView);
//                emptyView.setVisibility(View.VISIBLE);
//            }
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.chooseCloth)
    public void onViewClicked() {
        selectedCloth = new Cloth();
        selectedCloth.classificationId = ((Classification) spinClothType.getSelectedItem()).id;
        selectedCloth.colorId = ((Color) spinClothColor.getSelectedItem()).id;
        selectedCloth.genderId = ((Gender) spinClothGender.getSelectedItem()).id;
        selectedCloth.sizeId = ((Size) spinClothSize.getSelectedItem()).id;

        populateList(selectedCloth);
    }
}
