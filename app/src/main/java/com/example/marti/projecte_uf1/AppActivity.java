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
import android.view.View;
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
    // private int backButtonCount;
    boolean doubleBackToExitPressedOnce = false;
    public File imageFile;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private StorageReference mStorageRef;
    public Donor currentDonor;
    public Requestor currentRequestor;


    private String user_type;

    private SharedPreferences.Editor prefsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        ButterKnife.bind(this);


        initializeVariablesAndBindings();


        NavViewItemSelectedListener();


    }

    private void NavViewItemSelectedListener() {
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
    }

    private void initializeVariablesAndBindings() {

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

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


        setStartScreen();
    }

    private void setStartScreen() {
        if (user_type.equalsIgnoreCase("donor")) {
            navView.getMenu().setGroupVisible(R.id.donors_navview, true);

            Fragment fragment = null;
            fragment = new FragmentQR();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            navView.setCheckedItem(R.id.donor_QR);
            getSupportActionBar().setTitle("Donation QR");

        }
        if (user_type.equalsIgnoreCase("requestor")) {
            navView.getMenu().setGroupVisible(R.id.requestors_navview, true);

            Fragment fragment = null;
            fragment = new clothFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();

            navView.setCheckedItem(R.id.cloth);
            getSupportActionBar().setTitle("Search cloth");
        }
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

        userPictureSetListener();

        return true;
    }

    private void userPictureSetListener() {
        de.hdodenhof.circleimageview.CircleImageView image = findViewById(R.id.profilePicture);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                fragment = new profileFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, fragment)
                        .commit();
                drawerLayout.closeDrawers();
                navView.setCheckedItem(R.id.profile);
                getSupportActionBar().setTitle("Profile");
            }
        });
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

        de.hdodenhof.circleimageview.CircleImageView image = findViewById(R.id.profilePicture);

        image.setImageBitmap(myBitmap);
        YoYo.with(Techniques.FadeIn)
                .duration(1500)
                .playOn(image);
    }

    //this method is called from some child fragments
    public File getProfilePicture() {
        return imageFile;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logof) {
            finish();
            return true;
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

    }

    //checks if back button has been pressed twice between 2 seconds and, if so, the app is closed
    @Override
    public void onBackPressed() {

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
