package com.example.marti.projecte_uf1.SignIn;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.remote.ApiUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Register1Fragment extends Fragment {


    @BindView(R.id.ivMale)
    ImageView ivMale;
    @BindView(R.id.ivFemale)
    ImageView ivFemale;
    Unbinder unbinder;
    @BindView(R.id.etName)
    TextInputEditText etName;
    @BindView(R.id.etLastName)
    TextInputEditText etLastName;
    @BindView(R.id.etDNI)
    TextInputEditText etDNI;
    @BindView(R.id.etMail)
    TextInputEditText etMail;
    @BindView(R.id.etBirth)
    TextInputEditText etBirth;

    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private ApiMecAroundInterfaces mAPIService;

    private SharedPreferences.Editor prefsEditor;
    private boolean maleSelected = false;
    private boolean femaleSelected = false;
    private boolean userDuplicaated;
    int age = 0;
    public static String NEW_NAME = "NAME";
    ProgressDialog pd;


    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;


    public Register1Fragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(sharedPrefFile, getActivity().MODE_PRIVATE);
        prefsEditor = prefs.edit();
        mAPIService = ApiUtils.getAPIService();


    }

    private void updateBithLabel() {

        String myFormat = "yyyy/MM/dd";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);

        etBirth.setText(simpleDateFormat.format(myCalendar.getTime()));

        Calendar today = Calendar.getInstance();

        age = today.get(Calendar.YEAR) - myCalendar.get(Calendar.YEAR);
        if (today.get(Calendar.DAY_OF_YEAR) < myCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_register1, container, false);
        unbinder = ButterKnife.bind(this, view);

         pd = new ProgressDialog(getActivity());

        myCalendar = Calendar.getInstance();
        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateBithLabel();

            }

        };

        etBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public Donor getUser() {
        Donor donor = new Donor();
        donor.name = etName.getText().toString();
        donor.lastName = etLastName.getText().toString();
        donor.dni = etDNI.getText().toString();
        donor.email = etMail.getText().toString();
        donor.birthDate = etBirth.getText().toString();
        if (maleSelected) {
            donor.gender = "male";
        } else {
            donor.gender = "female";
        }

        return donor;
    }

    public boolean isInfoOk() {

        if (isNull()) return false;

        if (isUnderAge()) return false;

        if (isEmailFormatInvalid()) return false;

        if (isNIFFormatInvalid()) return false;

        if (isGenderNotSelected()) return false;

        return true;
    }

    private boolean isUserDuplicated() {
        Donor donor = new Donor();
        donor.dni = etDNI.getText().toString();
        donor.email = etMail.getText().toString();

        pd.setMessage(getString(R.string.checkInformation));
        pd.show();


        mAPIService.isUserDuplicated(donor).enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

                if (response.isSuccessful()) {
                    userDuplicaated = response.body();

                    pd.dismiss();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getActivity(), getString(R.string.cannot_connect_to_server2), Toast.LENGTH_SHORT).show();
                userDuplicaated = false;
                pd.dismiss();
            }
        });


        Toast.makeText(getActivity(), "Test", Toast.LENGTH_SHORT).show();

        if (userDuplicaated) {
            Toast.makeText(getActivity(), getString(R.string.userAlreadyExist), Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    private boolean isGenderNotSelected() {
        if (!maleSelected && !femaleSelected) {
            Toast toast = Toast.makeText(getActivity(),
                    getString(R.string.genderNotSelected),
                    Toast.LENGTH_LONG);

            toast.show();
            return true;
        }
        return false;
    }

    private boolean isNIFFormatInvalid() {
        if (!validateNIF(etDNI.getText().toString())) {
            Toast toast = Toast.makeText(getActivity(),
                    getString(R.string.dniNotValid),
                    Toast.LENGTH_LONG);

            toast.show();
            return true;
        }
        return false;
    }

    private boolean isEmailFormatInvalid() {
        if (!Patterns.EMAIL_ADDRESS.matcher(etMail.getText().toString()).matches()) {
            Toast toast = Toast.makeText(getActivity(),
                    getString(R.string.emailNotValid),
                    Toast.LENGTH_LONG);

            toast.show();
            return true;
        }
        return false;
    }

    private boolean isUnderAge() {
        if (age < 18) {
            Toast toast = Toast.makeText(getActivity(),
                    getString(R.string.overAgeRequired),
                    Toast.LENGTH_LONG);

            toast.show();
            return true;
        }
        return false;
    }

    private boolean isNull() {
        if (etName.getText().toString().equals("")
                || etMail.getText().toString().equals("")
                || etBirth.getText().toString().equals("")
                || etLastName.getText().toString().equals("")
                || etDNI.getText().toString().equals("")) {
            Toast toast = Toast.makeText(getActivity(),
                    getString(R.string.fillUp),
                    Toast.LENGTH_LONG);

            toast.show();
            return true;
        }
        return false;
    }

    public boolean validateNIF(String nif) {

        boolean result = false;
        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");
        Matcher matcher = pattern.matcher(nif);

        if (matcher.matches()) {
            String letra = matcher.group(2);
            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";
            int index = Integer.parseInt(matcher.group(1));
            index = index % 23;
            String reference = letras.substring(index, index + 1);

            if (reference.equalsIgnoreCase(letra)) {
                result = true;
            } else {
                result = false;
            }
        } else {
            result = false;
        }


        return result;

    }

    private void increaseMale() {
        femaleSelected = false;
        maleSelected = true;

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ivMale, "scaleX", 1.25f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ivMale, "scaleY", 1.25f);
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.start();


        scaleDownX = ObjectAnimator.ofFloat(ivFemale, "scaleX", .90f);
        scaleDownY = ObjectAnimator.ofFloat(ivFemale, "scaleY", .90f);
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);

        scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.start();
    }

    private void increaseFemale() {
        maleSelected = false;
        femaleSelected = true;

        ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(ivFemale, "scaleX", 1.25f);
        ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(ivFemale, "scaleY", 1.25f);
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);

        AnimatorSet scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.start();

        scaleDownX = ObjectAnimator.ofFloat(ivMale, "scaleX", 0.75f);
        scaleDownY = ObjectAnimator.ofFloat(ivMale, "scaleY", 0.75f);
        scaleDownX.setDuration(1000);
        scaleDownY.setDuration(1000);

        scaleDown = new AnimatorSet();
        scaleDown.play(scaleDownX).with(scaleDownY);

        scaleDown.start();
    }

    @OnClick({R.id.ivMale, R.id.ivFemale})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivMale:
                if (!maleSelected) {
                    increaseMale();
                } else {
                    increaseFemale();
                }


                break;
            case R.id.ivFemale:

                if (!femaleSelected) {
                    increaseFemale();

                } else {
                    increaseMale();
                }

                break;
        }
    }
}