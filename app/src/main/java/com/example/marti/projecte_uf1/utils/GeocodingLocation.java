package com.example.marti.projecte_uf1.utils;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Result;

public class GeocodingLocation {


    public static void getAddressFromLocation(final String locationAddress, final String name,
                                              final Context context, final Handler handler) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                try{
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());
                    String result = null;
                    try {
                        List<Address> addressList = geocoder.getFromLocationName(locationAddress, 1);
                        if (addressList != null && addressList.size() > 0) {
                            Address address = addressList.get(0);
                            StringBuilder sb = new StringBuilder();
                            sb.append(address.getLatitude()).append(",");
                            sb.append(address.getLongitude());
                            result = sb.toString();
                        }
                    } catch (IOException e) {
                        Toast.makeText(context, "Unable to connect to Geocoder", Toast.LENGTH_LONG).show();

                    } finally {
                        Message message = Message.obtain();
                        message.setTarget(handler);
                        if (result != null) {
                            message.what = 1;
                            Bundle bundle = new Bundle();

                            bundle.putString("address", result);
                            message.setData(bundle);
                        } else {
                            message.what = 1;
                            Bundle bundle = new Bundle();
                            result = "Unable to get location for this address." ;
                            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
                            bundle.putString("address", result);
                            bundle.putString("warehouseName", name);

                            message.setData(bundle);
                        }
                        message.sendToTarget();
                    }
                } catch (Exception e){
                    Toast.makeText(context, "Unable to connect to Geocoder", Toast.LENGTH_LONG).show();
                }

            }
        };
        thread.start();
    }
}