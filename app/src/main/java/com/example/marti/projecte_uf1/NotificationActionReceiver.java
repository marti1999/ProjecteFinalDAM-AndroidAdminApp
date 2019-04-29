package com.example.marti.projecte_uf1;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;


public class NotificationActionReceiver extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SQLiteManager manager = new SQLiteManager(context);

        try{
            manager.openWrite();
        } catch (Exception ex) {
            Log.e("myTag", ex.getMessage());
        }

        if (intent.getAction().equalsIgnoreCase("CONFIRM")) {

            Toast.makeText(context, "Attendance confirmed", Toast.LENGTH_LONG).show();

            String id = null;

            manager.updateEsdeveniment(id, true);



            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(11111);
        } else if (intent.getAction().equalsIgnoreCase("CANCEL")) {

            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(11111);

        }
    }
}
