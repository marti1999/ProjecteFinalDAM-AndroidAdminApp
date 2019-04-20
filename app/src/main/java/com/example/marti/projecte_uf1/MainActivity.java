package com.example.marti.projecte_uf1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Administrator;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;
import com.example.marti.projecte_uf1.remote.ApiUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    CheckBox checkBox;

    public static final String EXTRA_PERSONA = "NAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static Integer SIGNIN_REQUEST = 1;
    SQLiteManager manager = new SQLiteManager(this);
    @BindView(R.id.tvSignIn)
    TextView tvSignIn;

    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    private ApiMecAroundInterfaces mAPIService;

    Boolean isLogin;
    int incorrectAttempts;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SIGNIN_REQUEST) {
            if (resultCode == RESULT_OK) {
                etEmail.setText(data.getStringExtra(SignInActivity.NEW_NAME));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try { //todo s'hauria de treure un cop s'hagi tret el que depen
            manager.openWrite();

            // manager.close();
        } catch (Exception ex) {

        }

        mAPIService = ApiUtils.getAPIService();


        // getSupportActionBar().setTitle("Log in");


        BindView();
        setRememberName();


    }

    public void setRememberName() {
        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        String oldName = prefs.getString("EMAIL", "");
        etEmail.setText(oldName);
    }

    public void BindView() {
        etEmail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        checkBox = findViewById(R.id.checkBox);
    }

    public void launchSignIn(View v) {
        Intent launch = new Intent(this, SignInActivity.class);
        startActivityForResult(launch, SIGNIN_REQUEST);
    }

    public String generatePasswordHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(password.getBytes("utf8"));
        String newPassword = String.format("%040x", new BigInteger(1, digest.digest()));
        return newPassword;
    }


    public boolean canLogin(Requestor requestor) throws InterruptedException, IOException, NoSuchAlgorithmException {

        isLogin = false;
        incorrectAttempts = 0;

        Donor donor = new Donor();
        donor.email = requestor.email;
        donor.password = requestor.password;


        mAPIService.doLoginRequestor(requestor).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    isLogin = response.body().booleanValue();
                    if (isLogin) {
                        Toast.makeText(MainActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                        prefsEditor.putString("LAST_LOGIN_TYPE", "Requestor");
                        prefsEditor.apply();

                      //  launchLogInActivity();

                    } else {
                        Toast.makeText(MainActivity.this, "Requestor wrong email / password", Toast.LENGTH_SHORT).show(); //todo canviar per un que no desapareixi

                        incorrectAttempts++;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Response UNSUCCESFUL " + response.message() + response.code(), Toast.LENGTH_LONG).show();//todo canviar per un que no desapareixi

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILURE " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


//            try {
//                Response<Boolean> response = mAPIService.doLoginDonor(donor).execute();
//                if (response.isSuccessful()) {
//                    isLogin = response.body().booleanValue();
//                    if (isLogin) {
//                        Toast.makeText(MainActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
//                        prefsEditor.putString("LAST_LOGIN_TYPE", "Donor");
//                        prefsEditor.apply();
//
//
//                    } else {
//                        //     Toast.makeText(MainActivity.this, "is NOT ok", Toast.LENGTH_SHORT).show(); //todo canviar per un que no desapareixi
//                    }
//                } else {
//                    Toast.makeText(MainActivity.this, "Response UNSUCCESFUL " + response.message() + response.code(), Toast.LENGTH_LONG).show();//todo canviar per un que no desapareixi
//
//                }
//            } catch (Exception ex) {
//                Toast.makeText(MainActivity.this, "FAILURE " + ex.getMessage(), Toast.LENGTH_LONG).show();
//            }


        mAPIService.doLoginDonor(donor).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    isLogin = response.body().booleanValue();
                    if (isLogin) {
                        Toast.makeText(MainActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                        prefsEditor.putString("LAST_LOGIN_TYPE", "Donor");
                        prefsEditor.apply();

                      //  launchLogInActivity();


                    } else {
                        incorrectAttempts++;
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Response UNSUCCESFUL " + response.message() + response.code(), Toast.LENGTH_LONG).show();//todo canviar per un que no desapareixi

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(MainActivity.this, "FAILURE " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });




        return isLogin;
    }

    public void launchLogInActivity() {
        rememberUserEmail();

        prefsEditor.putString("LAST_LOGIN", etEmail.getText().toString());
        prefsEditor.apply();

        Intent launch = new Intent(this, AppActivity.class);
//            launch.putExtra(EXTRA_PERSONA, per.getNom());
//            launch.putExtra(EXTRA_EMAIL, per.getEmail());


        //Log.d("nom", String.valueOf(per.getId()));
        startActivity(launch);
        etPassword.setText(""); //todo s'hauria de canviar, queda feo quan s'executa
    }

    public void LogInClick(View v) throws IOException, NoSuchAlgorithmException, InterruptedException {

        Requestor requestor = new Requestor();
        requestor.email = etEmail.getText().toString();
        requestor.password = etPassword.getText().toString();

        requestor.password = generatePasswordHash(requestor.password);


        canLogin(requestor);

    }

    private void rememberUserEmail() {
        if (checkBox.isChecked()) {
            String email = etEmail.getText().toString();
            prefsEditor.putString("EMAIL", email);
            prefsEditor.apply();
        } else {
            prefsEditor.putString("EMAIL", "");
            prefsEditor.apply();
            etEmail.setText("");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.close();
    }
}
