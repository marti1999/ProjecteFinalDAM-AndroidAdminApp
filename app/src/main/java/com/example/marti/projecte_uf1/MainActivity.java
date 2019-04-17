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
import com.example.marti.projecte_uf1.remote.ApiUtils;

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


    public boolean canLogin(Administrator admin) {
        isLogin = false;
        mAPIService.doLogin(admin).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if (response.isSuccessful()) {
                    isLogin = response.body().booleanValue();
                    if (isLogin) {
                        Toast.makeText(MainActivity.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(MainActivity.this, "is NOT ok", Toast.LENGTH_SHORT).show(); //todo canviar per un que no desapareixi
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

    public void launchLogIn(View v) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Administrator a = new Administrator();

        a.email = etEmail.getText().toString();
        a.password = etPassword.getText().toString();

        a.password = generatePasswordHash(a.password);




        if (canLogin(a)) {
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
