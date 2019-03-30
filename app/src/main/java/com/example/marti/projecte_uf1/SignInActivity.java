package com.example.marti.projecte_uf1;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {


    SQLiteManager manager = new SQLiteManager(this);

    public static String NEW_NAME = "NAME";

    // EditText name;
    EditText birth;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;
    @BindView(R.id.email)
    EditText email;
    @BindView(R.id.password1)
    EditText password1;
    @BindView(R.id.password2)
    EditText password2;

    Persona persona;
    @BindView(R.id.name)
    EditText name;

    @BindView(R.id.description)
    EditText description;
    @BindView(R.id.createAccount)
    Button createAccount;
    @BindView(R.id.phone3)
    EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Sign in");
        persona = new Persona();

        // name = findViewById(R.id.name);

        myCalendar = Calendar.getInstance();

        birth = (EditText) findViewById(R.id.birth);
        date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        birth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                new DatePickerDialog(SignInActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //Intent intent = getIntent();
        //String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        //name.setText(message);
    }

    private void updateLabel() {

        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        birth.setText(sdf.format(myCalendar.getTime()));

        Calendar today = Calendar.getInstance();

        int age = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < myCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
        persona.setEdat(age);
    }

    @OnClick(R.id.createAccount)
    public void onViewClicked() {

        boolean valid = true;

        if (name.getText().toString().equals("") || email.getText().toString().equals("")
                || password1.getText().toString().equals("") || password2.getText().toString().equals("")
                || birth.getText().toString().equals("") || phone.getText().toString().equals("")
                || description.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "All fields must be filled.",
                    Toast.LENGTH_LONG);

            toast.show();
            valid = false;
        } else if (DatabaseUtils.queryNumEntries(manager.getDatabaseInstance(),"Persona", "email =?", new String[] {email.getText().toString()}) != 0) { //todo canviar el checking
            Toast toast = Toast.makeText(getApplicationContext(),
                    "This email address is already in use.",
                    Toast.LENGTH_LONG);

            toast.show();
            valid = false;
        } else if (persona.getEdat() < 18) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "You must be over 18 years old.",
                    Toast.LENGTH_LONG);

            toast.show();
            valid = false;
        } else if (!PhoneNumberUtils.isGlobalPhoneNumber(phone.getText().toString())) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "The phone format is not valid",
                    Toast.LENGTH_LONG);

            toast.show();
            valid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "The email is not valid",
                    Toast.LENGTH_LONG);

            toast.show();
            valid = false;
        }


        if (valid) {
            if (password2.getText().toString().equals(password1.getText().toString())) {
                try {
                    persona.setEmail(email.getText().toString());
                    persona.setPassword(password1.getText().toString());
                    persona.setNom(name.getText().toString());
                    persona.setTelefon(phone.getText().toString());
                    persona.setDescripcio(description.getText().toString());


                    manager.insertPersona(persona); //todo canviar per el webservice


                    Intent reply = new Intent();
                    reply.putExtra(NEW_NAME, persona.getEmail());
                    setResult(RESULT_OK, reply);
                    finish();


                } catch (Exception ex) {

                }
            } else {
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Password don't match.",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        }




    }
}
