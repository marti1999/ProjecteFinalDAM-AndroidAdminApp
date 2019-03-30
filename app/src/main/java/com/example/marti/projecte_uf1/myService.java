package com.example.marti.projecte_uf1;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class myService extends Service {
    private static final String TAG = "MyService";

    private MediaPlayer player;

    @Override
    public IBinder onBind(Intent i) {

        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "FirstService started");

        player = MediaPlayer.create(this, R.raw.alert);
        // This will play the ringtone continuously until we stop the service.
        player.setLooping(true);
        // It will start the player
        player.start();
        //  Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        //this.stopSelf();
        //return START_STICKY;

        //
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onDestroy() {

        super.onDestroy();
        player.stop();
        Log.d(TAG, "FirstService destroyed");
    }
}