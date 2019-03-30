package com.example.marti.projecte_uf1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    CheckBox checkBox;
    public static final String EXTRA_MESSAGE = "MESSAGE";
    public static final String EXTRA_PERSONA = "NAME";
    public static final String EXTRA_EMAIL = "EMAIL";
    public static Integer SIGNIN_REQUEST = 1;
    SQLiteManager manager = new SQLiteManager(this);
    @BindView(R.id.tvSignIn)
    TextView tvSignIn;

    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

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

        try {
            manager.openWrite();

            // manager.close();
        } catch (Exception ex) {

        }

        manager.esdevenimentsEntries();

        this.personaEntries();

        // getSupportActionBar().setTitle("Log in");


        etEmail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        checkBox = findViewById(R.id.checkBox);

        prefs = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        String oldName = prefs.getString("EMAIL", "");
        etEmail.setText(oldName);

        tvSignIn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    manager.esdevenimentsEntries();

                } catch (Exception ex) {

                }
                return true;
            }
        });

    }

    public void launchSignIn(View v) {
        Intent launch = new Intent(this, SignInActivity.class);
        //String email = etEmail.getText().toString();
        //launch.putExtra(EXTRA_MESSAGE, email);
        startActivityForResult(launch, SIGNIN_REQUEST);
    }

    public void launchLogIn(View v) {

        // manager.openRead();
        String realPassword = manager.getPassword(etEmail.getText().toString());

        if (realPassword.equals(etPassword.getText().toString())) {
            if (checkBox.isChecked()) {
                String email = etEmail.getText().toString();
                prefsEditor.putString("EMAIL", email);
                prefsEditor.apply();
            } else {
                prefsEditor.putString("EMAIL", "");
                prefsEditor.apply();
                etEmail.setText("");
            }

            Persona per = manager.getPersona(etEmail.getText().toString());

            Intent launch = new Intent(this, AppActivity.class);
            launch.putExtra(EXTRA_PERSONA, per.getNom());
            launch.putExtra(EXTRA_EMAIL, per.getEmail());

            prefsEditor.putString("LAST_LOGIN", per.getEmail());
            prefsEditor.apply();

            Log.d("nom", String.valueOf(per.getId()));
            startActivity(launch);
            etPassword.setText("");

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Email and  password do not match",
                    Toast.LENGTH_LONG);

            toast.show();

            // Intent launch = new Intent(this, AppActivity.class);
            //  startActivity(launch);
        }
//manager.close();

    }

    public void personaEntries() {
        Persona p = new Persona();
        p.setId(100);
        p.setEdat(34);
        p.setEmail("carles@gmail.com");
        p.setPassword("carles");
        p.setTelefon("64815483");
        p.setDescripcio("Bye bye");
        p.setNom("Carles");
        try {
            manager.insertPersona(p);
        } catch (Exception ex) {

        }


        Persona p2 = new Persona();
        p2.setId(101);
        p2.setEdat(19);
        p2.setEmail("marti@gmail.com");
        p2.setPassword("marti");
        p2.setTelefon("3181531");
        p2.setDescripcio("Hi there~");
        p2.setNom("Martí");
        try {
            manager.insertPersona(p2);
        } catch (Exception ex) {

        }

        Persona p3 = new Persona();
        p3.setId(102);
        p3.setEdat(56);
        p3.setEmail("mario@gmail.com");
        p3.setPassword("mario");
        p3.setTelefon("65789456");
        p3.setDescripcio("Description for mario");
        p3.setNom("Mario");
        try {
            manager.insertPersona(p3);
        } catch (Exception ex) {

        }

        Persona p4 = new Persona();
        p4.setId(103);
        p4.setEdat(34);
        p4.setEmail("test@gmail.com");
        p4.setPassword("a");
        p4.setTelefon("64815483");
        p4.setDescripcio("Bye bye");
        p4.setNom("Test");
        try {
            manager.insertPersona(p4);
        } catch (Exception ex) {

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        manager.close();
    }
}
