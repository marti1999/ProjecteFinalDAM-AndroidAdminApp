package com.example.marti.projecte_uf1;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.CLIPBOARD_SERVICE;


public class NotificationActionReceiver extends BroadcastReceiver {

    public static final String EXTRA_WORD = "1";

    @Override
    public void onReceive(Context context, Intent intent) {


        if (intent.getAction().equalsIgnoreCase("COPY_TO_CLIPBOARD")) {

            Bundle extras = intent.getExtras();
            String word = extras.getString(EXTRA_WORD);
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("WORD", word);
            clipboard.setPrimaryClip(clip);
            Intent closeIntent = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(closeIntent);
            Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_LONG).show();


            NotificationManager notificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.cancel(11111);
        }


    }
}
