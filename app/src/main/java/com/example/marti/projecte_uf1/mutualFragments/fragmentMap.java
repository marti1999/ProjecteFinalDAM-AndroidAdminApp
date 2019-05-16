package com.example.marti.projecte_uf1.mutualFragments;

import android.content.Context;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Warehouse;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.GeocodingLocation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class fragmentMap extends Fragment {
    private GoogleMap mMap;
    Geocoder gc;

    public ApiMecAroundInterfaces mAPIService;
    List<Warehouse> list;
    String address;

    public fragmentMap() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAPIService = ApiUtils.getAPIService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_fragment_map, container, false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);

        setMapProperties(mapFragment);


        setWarehouses();


        return rootView;
    }

    private void setWarehouses() {
        mAPIService.getWarehoues().enqueue(new Callback<List<Warehouse>>() {
            @Override
            public void onResponse(Call<List<Warehouse>> call, Response<List<Warehouse>> response) {
                if (response.isSuccessful()) {
                    list = response.body();
                    for (Warehouse item : list
                    ) {
                        address = item.street + ", " + item.number + " " + item.postalCode + " " + item.city;

                        GeocodingLocation locationAddress = new GeocodingLocation();
                        locationAddress.getAddressFromLocation(address, item.name,
                                getActivity(), new GeocoderHandler());
                    }
                } else{
                    Toast.makeText(getActivity(), getString(R.string.cannot_connect_to_server2), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Warehouse>> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.cannot_connect_to_server2), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setMapProperties(SupportMapFragment mapFragment) {
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

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            String name;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString(getString(R.string.address));
                    name = bundle.getString(getString(R.string.warehousename));
                    break;
                default:
                    locationAddress = null;
                    name = null;
            }
            String[] latlong = locationAddress.split(",");
            double latitude = Double.parseDouble(latlong[0]);
            double longitude = Double.parseDouble(latlong[1]);
            LatLng location = new LatLng(latitude, longitude);

            addMarker(location, name);

        }
    }

    public void addMarker(LatLng latlng, String name) {
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latlng);
        markerOptions.title(name);

        mMap.addMarker(markerOptions);
    }


}
