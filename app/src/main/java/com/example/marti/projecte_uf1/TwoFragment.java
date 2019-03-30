package com.example.marti.projecte_uf1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class TwoFragment extends Fragment implements LoaderManager.LoaderCallbacks {
    @BindView(R.id.btnDownload)
    Button btnDownload;
    File apkStorage = null;
    File outputFile = null;
    File file = null;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.textView4)
    TextView textView4;
    private String downloadUrl = "", downloadFileName = "pdfFeminism.pdf";
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;

    Unbinder unbinder;
    public static final int OPERATION_SEARCH_LOADER = 22;
    public static final String OPERATION_URL_EXTRA = "url_that_return_json_data";

    public TwoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        unbinder = ButterKnife.bind(this, view);
        //getLoaderManager().initLoader(OPERATION_SEARCH_LOADER, null, this);
        imageView3.setVisibility(View.INVISIBLE);
        textView3.setVisibility(View.INVISIBLE);
        textView4.setVisibility(View.INVISIBLE);
        return view;
    }

    public void startLoader() {
        Bundle queryBundle = new Bundle();
        queryBundle.putString("URL", "test");
        LoaderManager loaderManager = getLoaderManager();
        Loader<String> loader = loaderManager.getLoader(OPERATION_SEARCH_LOADER);
        if (loader == null) {
            loaderManager.initLoader(OPERATION_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(OPERATION_SEARCH_LOADER, queryBundle, this);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @SuppressLint("StaticFieldLeak")
    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<String>(getActivity()) {

            @Override
            public String loadInBackground() {
                try {
                    URL url = new URL("http://www.sascwr.org/files/www/resources_pdfs/feminism/Definitions_of_Branches_of_Feminisn.pdf");//Create Download URl
                    HttpURLConnection c = (HttpURLConnection) url.openConnection();//Open Url Connection
                    c.setRequestMethod("GET");//Set Request Method to "GET" since we are grtting data
                    c.connect();//connect the URL Connection

                    //If Connection response is not OK then show Logs
                    if (c.getResponseCode() != HttpURLConnection.HTTP_OK) {
                        Log.e("PDFLoader", "Server returned HTTP " + c.getResponseCode()
                                + " " + c.getResponseMessage());

                    }

                    File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                    file = new File(path, downloadFileName);
                    path.mkdirs();


                    if (ContextCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {


                        Log.e("PDFLoader", "Permissions not granted yet");
                        // Permission is not granted
                        // Should we show an explanation?


//
//                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
//                                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//                            // Show an explanation to the user *asynchronously* -- don't block
//                            // this thread waiting for the user's response! After the user
//                            // sees the explanation, try again to request the permission.
//                        } else {
//                            // No explanation needed; request the permission
//                            ActivityCompat.requestPermissions(getActivity(),
//                                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                                    MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
//
//                            // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
//                            // app-defined int constant. The callback method gets the
//                            // result of the request.
//                        }
                    } else {
                        // Permission has already been granted
                    }


                    //Get File if SD card is present
//                    if (new CheckForSDCard().isSDCardPresent()) {
//
//                        apkStorage = new File(
//                                Environment.getExternalStorageDirectory() + "/"
//                                        + "NKDROID FILES");
//                    } else
//                        Toast.makeText(getActivity(), "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();
//
//                    //If File is not present create directory
//                    if (!apkStorage.exists()) {
//                        apkStorage.mkdir();
//                        Log.e( "PDFLoader", "Directory Created.");
//                    }

                    outputFile = new File(apkStorage, downloadFileName);//Create Output file in Main File

                    //Create New File if not present
                    if (!file.exists()) {
                        file.createNewFile();
                        Log.e("PDFLoader", "File Created");
                    }

                    FileOutputStream fos = new FileOutputStream(file);//Get OutputStream for NewFile Location

                    InputStream is = c.getInputStream();//Get InputStream for connection

                    byte[] buffer = new byte[1024];//Set buffer type
                    int len1 = 0;//init length
                    while ((len1 = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len1);//Write new file
                    }

                    //Close all connection after doing task
                    fos.close();
                    is.close();

                } catch (Exception e) {

                    //Read exception if something went wrong
                    e.printStackTrace();
                    file = null;
                    Log.e("PDFLoader", "Download Error Exception " + e.getMessage());
                }

                return null;
            }

            @Override
            protected void onStartLoading() {
                //Think of this as AsyncTask onPreExecute() method,you can start your progress bar,and at the end call forceLoad();
                forceLoad();
            }

            @Override
            protected void onStopLoading() {
                super.onStopLoading();
//                Toast toast = Toast.makeText(getActivity(),
//                        "The download has finished, it is in Downloads folder",
//                        Toast.LENGTH_LONG);
//
//                toast.show();
                imageView3.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Object o) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    @OnClick({R.id.btnDownload, R.id.imageView3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnDownload:
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
                startLoader();
                startLoader();
                break;
            case R.id.imageView3:



                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                file = new File(path, downloadFileName);
                Uri uri = Uri.fromFile(file);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setDataAndType(uri, "application/pdf");
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                try{
                    getActivity().startActivity(i);
                } catch(Exception ex) {
                    getActivity().startActivity(Intent.createChooser(i,"There is no default app to open PDFs, choose one: "));
                }

                break;
        }
    }


    public class CheckForSDCard {
        //Check If SD Card is present or not method
        public boolean isSDCardPresent() {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                return true;
            }
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(getActivity(), "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }
}