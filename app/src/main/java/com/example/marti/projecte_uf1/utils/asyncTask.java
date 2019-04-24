package com.example.marti.projecte_uf1.utils;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.marti.projecte_uf1.R;

import java.io.InputStream;
import java.net.URL;

public class asyncTask extends AsyncTask<Void, Integer, Bitmap> {
    //TODO: canviar tot el async tasc pel que faci falta

    private Activity activityContext;
    public asyncTask(Activity activity) {
        activityContext = activity;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        String urldisplay = "http://icons.iconarchive.com/icons/webalys/kameleon.pics/512/Woman-9-icon.png";

        try {
            Bitmap bmp = null;
            try {
                InputStream in = new URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;


            //return dowloadImage(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Toast.makeText(getApplicationContext(), "Downloading profile picture...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        super.onPostExecute(result);

        ImageView pic = activityContext.findViewById(R.id.profilePicture);
        pic.setAdjustViewBounds(true);
        pic.setImageBitmap(result);


    }
}