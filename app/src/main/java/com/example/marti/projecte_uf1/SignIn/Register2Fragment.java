package com.example.marti.projecte_uf1.SignIn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.model.Donor;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class Register2Fragment extends Fragment {


    @BindView(R.id.etPassword)
    TextInputEditText etPassword;
    @BindView(R.id.etPassword2)
    TextInputEditText etPassword2;
    @BindView(R.id.etQuestion)
    TextInputEditText etQuestion;
    @BindView(R.id.etAnswer)
    TextInputEditText etAnswer;
    Unbinder unbinder;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    public Register2Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(sharedPrefFile, getActivity().MODE_PRIVATE);
        prefsEditor = prefs.edit();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register2, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    public Donor getUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        Donor donor = new Donor();

        donor.password = etPassword.getText().toString();
        donor.password = generatePasswordHash(donor.password);
        donor.securityAnswer = etAnswer.getText().toString();
        donor.securityAnswer = generatePasswordHash(donor.securityAnswer);
        donor.securityQuestion = etQuestion.getText().toString();

        return donor;
    }

    public boolean isInfoOk(){



        if (isNull()) return false;

        if (passwordDontMatch()) return false;


        return true;
    }

    private boolean isNull() {
        if (etPassword.getText().toString().equals("")
                || etPassword2.getText().toString().equals("")
                || etQuestion.getText().toString().equals("")
                || etAnswer.getText().toString().equals("")){

            Toast toast = Toast.makeText(getActivity(),
                    "All fields must be filled up.",
                    Toast.LENGTH_LONG);

            toast.show();
            return true;
        }
        return false;
    }

    private boolean passwordDontMatch() {
        if (!etPassword.getText().toString().equals(etPassword2.getText().toString())){
            Toast toast = Toast.makeText(getActivity(),
                    "Password do not match.",
                    Toast.LENGTH_LONG);

            toast.show();
            return true;
        }
        return false;
    }

    public String generatePasswordHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(password.getBytes("utf8"));
        String newPassword = String.format("%040x", new BigInteger(1, digest.digest()));
        return newPassword;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}