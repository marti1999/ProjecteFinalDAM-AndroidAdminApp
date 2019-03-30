package com.example.marti.projecte_uf1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppActivity extends AppCompatActivity {


    private Toolbar mTopToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private int backButtonCount;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private Persona p;

    SQLiteManager manager = new SQLiteManager(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        ButterKnife.bind(this);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        //getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        backButtonCount = 0;
        p =  new Persona();


        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();


        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navView = (NavigationView) findViewById(R.id.navview);






        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        switch (menuItem.getItemId()) {
                            case R.id.menu_seccion_1:
                                fragment = new Fragment1();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_seccion_2:
                                fragment = new Fragment2();
                                fragmentTransaction = true;
                                break;


                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            getSupportActionBar().setTitle(menuItem.getTitle());
                        }

                        drawerLayout.closeDrawers();
                        return true;

                    }
                }
        );

        Fragment fragment = null;
        fragment = new Fragment1();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        navView.getMenu().getItem(0).setChecked(true);


        getSupportActionBar().setTitle("Contacts"); //todo canviar pel que faci falta

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);





        p = manager.getPersona(prefs.getString("LAST_LOGIN", "")); //todo canviar per el webservice request;


        TextView tvName = findViewById(R.id.navview_name);
        TextView tvEmail = findViewById(R.id.navview_email);
        tvName.setText(p.getNom());
        tvEmail.setText(p.getEmail());

        new asyncTask().execute(); //todo canviar per aconseguir la foto necessaria;


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logof) {


            finish();
            return true;

        } else if ( id == R.id.action_deleteAccount) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which){
                        case DialogInterface.BUTTON_POSITIVE:



                            manager.deletePersona(p.getEmail()); //todo canviar

                            finish();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("This account will be deleted\nAre you sure?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }



        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onResume() {
        super.onResume();
        backButtonCount = 0;
    }

    @Override
    public void onBackPressed() {

//todo fer que nomes conti com a 2 quan s'ha fet seguit de la primera vegada, posar un timer o algo per l'estil
        if (backButtonCount >= 1) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(this, getString(R.string.doubleTapText), Toast.LENGTH_SHORT).show();
            backButtonCount++;
        }


    }



    class asyncTask extends AsyncTask<Void, Integer, Bitmap> { //todo canviar tot el async tasc pel que faci falta
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
            Toast.makeText(getApplicationContext(), "Downloading profile picture...", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Bitmap result) {
            super.onPostExecute(result);

            ImageView pic = findViewById(R.id.profilePicture);
            pic.setAdjustViewBounds(true);
            pic.setImageBitmap(result);

            // pic.getLayoutParams().height = 50;


        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
