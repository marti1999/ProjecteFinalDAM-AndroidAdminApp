package com.example.marti.projecte_uf1;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Announcement;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;
import com.google.android.gms.dynamic.IFragmentWrapper;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class myService extends Service {
    private static final String TAG = "MyService";

    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
    private SharedPreferences prefs;
    private ApiMecAroundInterfaces mAPIService;
    String userType;
    int number = 0;

    @Override
    public IBinder onBind(Intent i) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        userType = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "");
        mAPIService = ApiUtils.getAPIService();


        callAsynchronousTask();
        //
        return super.onStartCommand(intent, flags, startId);
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            mAPIService.getAnnouncementsNewNumber(userType).enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    if (response.isSuccessful()) {
                                        if (response.body()!= null){

                                            if (number < response.body()){
                                                Toast.makeText(myService.this, "NEW NOTIFICATION", Toast.LENGTH_SHORT).show();
                                                number = response.body();
                                            }


                                        }
                                        else {
                                            Toast.makeText(myService.this, "Service number null", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(myService.this, "Service reponse unsuccessful", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(myService.this, t.getMessage(), Toast.LENGTH_LONG).show();

                                }
                            });
                        } catch (Exception e) {

                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 5000); //execute in every 50000 ms
    }


    @Override
    public void onDestroy() {

        super.onDestroy();

        Log.d(TAG, "FirstService destroyed");
    }
}