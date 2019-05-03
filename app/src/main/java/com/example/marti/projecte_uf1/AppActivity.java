package com.example.marti.projecte_uf1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.marti.projecte_uf1.DonorFragments.RewardsFragment;
import com.example.marti.projecte_uf1.RequestorFragments.clothFragment;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;
import com.example.marti.projecte_uf1.mutualFragments.AnnouncementsFragment;
import com.example.marti.projecte_uf1.mutualFragments.fragmentMap;
import com.example.marti.projecte_uf1.mutualFragments.profileFragment;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;
import com.example.marti.projecte_uf1.utils.asyncTask;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;

public class AppActivity extends AppCompatActivity {


    private Toolbar mTopToolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navView;
    private int backButtonCount;
    boolean doubleBackToExitPressedOnce = false;
    public File imageFile;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private StorageReference mStorageRef;
    public Donor currentDonor;
    public Requestor currentRequestor;


    private String user_type;

    private SharedPreferences.Editor prefsEditor;

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


        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();
        mStorageRef = FirebaseStorage.getInstance().getReference();


        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        navView = (NavigationView) findViewById(R.id.navview);

        user_type = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "");
        if (user_type.equalsIgnoreCase("donor")){
            navView.getMenu().setGroupVisible(R.id.donors_navview, true);
        }
        if (user_type.equalsIgnoreCase("requestor")){
            navView.getMenu().setGroupVisible(R.id.requestors_navview, true);

        }


        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        boolean fragmentTransaction = false;
                        Fragment fragment = null;
                        switch (menuItem.getItemId()) {

                            case R.id.donor_QR:
                                fragment = new FragmentQR();
                                fragmentTransaction = true;
                                break;
                            case R.id.donor_rewards:
                                fragment = new RewardsFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.annnouncements:
                                fragment = new AnnouncementsFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.warehouses:
                                fragment = new fragmentMap();
                                fragmentTransaction = true;
                                break;
                            case R.id.profile:
                                fragment = new profileFragment();
                                fragmentTransaction = true;
                                break;
                            case R.id.cloth:
                                fragment = new clothFragment();
                                fragmentTransaction = true;
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
        fragment = new AnnouncementsFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        navView.getMenu().getItem(0).setChecked(true);

        getSupportActionBar().setTitle("Announcements"); //TODO: canviar pel que faci falta

    }

    private void setCurrentUser() {
        currentDonor = null;
        currentRequestor = null;
        String userType = prefs.getString(PrefsFileKeys.LAST_LOGIN, "");
        String userId = prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);


        //    p = manager.getPersona(prefs.getString("LAST_LOGIN", "")); //TODO: s'hauria de poder esborrar

        String userType = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "");
        String userEmail = prefs.getString(PrefsFileKeys.LAST_LOGIN, "");


        TextView tvName = findViewById(R.id.navview_name);
        TextView tvEmail = findViewById(R.id.navview_email);
        tvName.setText(userEmail);
        tvEmail.setText(userType);


        try {
            downloadProfilePicture();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        asyncTask asyncTk = new asyncTask(AppActivity.this); //TODO: canviar per aconseguir la foto necessaria;
//        asyncTk.execute();

        return true;
    }

    public void downloadProfilePicture() throws IOException {
        String fileName = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "") + prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "");
        StorageReference ref = mStorageRef.child(fileName);
        imageFile = File.createTempFile("images", "jpg");

        ref.getFile(imageFile)
                .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                        changePictureWithAbsolutePath(imageFile);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        //de.hdodenhof.circleimageview.CircleImageView image;
                        de.hdodenhof.circleimageview.CircleImageView image = findViewById(R.id.profilePicture);
                        image.setImageResource(R.drawable.male);
                        imageFile = null;
                    }
                });
    }

    private void changePictureWithAbsolutePath(File imageFile) {
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

//        YoYo.with(Techniques.FadeOut)
//                .duration(2000)
//                .playOn(image);




        de.hdodenhof.circleimageview.CircleImageView image = findViewById(R.id.profilePicture);

        image.setImageBitmap(myBitmap);
        YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .playOn(image);
    }

    public File getProfilePicture(){
        return imageFile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logof) {


            finish();
            return true;

        } else if (id == R.id.action_deleteAccount) {
            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:


                            Toast.makeText(AppActivity.this, "put something here or delete button", Toast.LENGTH_SHORT).show();
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

        //TODO: fer que nomes conti com a 2 quan s'ha fet seguit de la primera vegada, posar un timer o algo per l'estil
//        if (backButtonCount >= 1) {
//            Intent intent = new Intent(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_HOME);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);
//        } else {
//            Toast.makeText(this, getString(R.string.doubleTapText), Toast.LENGTH_SHORT).show();
//            backButtonCount++;
//        }


        if (doubleBackToExitPressedOnce) {
            finishAffinity();

            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, R.string.doubleTapText, Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
