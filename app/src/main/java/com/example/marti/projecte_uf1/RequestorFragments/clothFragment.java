package com.example.marti.projecte_uf1.RequestorFragments;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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
import com.example.marti.projecte_uf1.utils.GeocodingLocation;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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
    @BindView(R.id.fragment_cloth_form)
    LinearLayout fragmentClothForm;
    @BindView(R.id.fragment_cloth_list)
    LinearLayout fragmentClothList;
    @BindView(R.id.fragment_cloth_map)
    LinearLayout fragmentClothMap;
    @BindView(R.id.fabHideMap)
    FloatingActionButton fabHideMap;
    @BindView(R.id.fragment_cloth_parent)
    LinearLayout fragmentClothParent;
    private RecyclerView.LayoutManager mLayoutManager;
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    clothAdapter adapter;
    Cloth selectedCloth;
    private GoogleMap mMap;
    private Warehouse chosenWarehouse;
    private String chosenWarehouseAddress;

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
        emptyView.setVisibility(View.VISIBLE);

        initializeSwipeContainer();
        bindMap();

        return view;
    }

    private void initializeSwipeContainer() {
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateList(selectedCloth);
            }
        });
        swipeContainer.setColorSchemeResources(R.color.colorPrimary,
                android.R.color.holo_green_dark,
                android.R.color.holo_orange_dark,
                android.R.color.holo_blue_dark);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Search clothes");
    }

    private void populateList(Cloth c) {
        mAPIService.getWarehousesByCloth(c).enqueue(new Callback<List<Warehouse>>() {

            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        List<Warehouse> list = response.body();

                        if (list.size() > 0) {
                            setAdapter(new ArrayList<Warehouse>(list));
                        } else {

                            emptyView.setText("Cloth not available.\nPlease, try another.");
                            YoYo.with(Techniques.FadeIn).duration(1300).playOn(emptyView);
                            emptyView.setVisibility(View.VISIBLE);
                        }

                    } else {
                        Toast.makeText(getActivity(), "Can't connect with server, try again later", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
                swipeContainer.setRefreshing(false);
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

            adapter = new clothAdapter(warehouses, getActivity(), clothFragment.this);
            rv = getView().findViewById(R.id.warehouse_recyclerview);
            mLayoutManager = new GridLayoutManager(getActivity(), 2);
            rv.setLayoutManager(mLayoutManager);

            YoYo.with(Techniques.FadeIn).duration(1300).playOn(rv);
            rv.setAdapter(adapter);
        }
    }

    private void bindMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear();

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(41.59, 1.52))
                        .zoom(7.5f)
                        .bearing(0)
                        .build();

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3500, null);
            }
        });
    }


    @OnClick({R.id.chooseCloth, R.id.fabHideMap})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chooseCloth:
                setSelectedCloth();
                populateList(selectedCloth);
                break;

            case R.id.fabHideMap:
                makeFormAndListVisivle();
                break;
        }
    }

    @SuppressLint("RestrictedApi")
    private void makeFormAndListVisivle() {
        YoYo.with(Techniques.FadeOut).duration(1250).playOn(fragmentClothParent);
        YoYo.with(Techniques.FadeOut).duration(1250).playOn(fabHideMap);
        YoYo.with(Techniques.FadeIn).duration(1250).playOn(fragmentClothParent);

        fragmentClothForm.setVisibility(View.VISIBLE);
        fragmentClothList.setVisibility(View.VISIBLE);
        fabHideMap.setVisibility(View.GONE);
        fragmentClothMap.setVisibility(View.GONE);
    }

    private void setSelectedCloth() {
        selectedCloth = new Cloth();
        selectedCloth.classificationId = ((Classification) spinClothType.getSelectedItem()).id;
        selectedCloth.colorId = ((Color) spinClothColor.getSelectedItem()).id;
        selectedCloth.genderId = ((Gender) spinClothGender.getSelectedItem()).id;
        selectedCloth.sizeId = ((Size) spinClothSize.getSelectedItem()).id;
    }

    //handler que passa la location al mapa quan el message "what" és actualitzat amb nova informació
    //no es crida a ningún lloc, actua com a listener.
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            String[] latlong = locationAddress.split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            LatLng location = new LatLng(latitude, longitude);

            addMarker(location);

        }
    }


    //aquest mètode és cridat des de l'adapter del recycler view d'aquest fragment.
    public void showWarehouseOnMap(Warehouse warehouse) {
        chosenWarehouse = warehouse;
        String address = warehouse.street + ", " + warehouse.number + " " + warehouse.postalCode + " " + warehouse.city;
        chosenWarehouseAddress = address;
        GeocodingLocation locationAddress = new GeocodingLocation();
        locationAddress.getAddressFromLocation(address, warehouse.name,
                getActivity(), new GeocoderHandler());
    }

    public void addMarker(LatLng latlng) {
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        markerOptions.title(chosenWarehouseAddress);

        CameraPosition googlePlex = CameraPosition.builder()
                .target(latlng)
                .zoom(13f)
                .bearing(0)
                .build();

        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 3500, null);
        mMap.addMarker(markerOptions);

        makeMapVisible();
    }

    @SuppressLint("RestrictedApi")
    private void makeMapVisible() {
        YoYo.with(Techniques.FadeOut).duration(1250).playOn(fragmentClothParent);
        YoYo.with(Techniques.FadeIn).duration(1250).playOn(fragmentClothParent);
        YoYo.with(Techniques.FadeIn).duration(1250).playOn(fabHideMap);
        fragmentClothForm.setVisibility(View.GONE);
        fragmentClothList.setVisibility(View.GONE);
        fabHideMap.setVisibility(View.VISIBLE);

        fragmentClothMap.setVisibility(View.VISIBLE);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}
