package com.example.marti.projecte_uf1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.marti.projecte_uf1.SignIn.RegisterActivity;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.NotificationHelper;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;
import com.unstoppable.submitbuttonview.SubmitButton;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    EditText etEmail;
    EditText etPassword;
    CheckBox checkBox;
    SubmitButton testButton;

    public static final String EXTRA_PERSONA = PrefsFileKeys.NAME;
    public static final String EXTRA_EMAIL = PrefsFileKeys.EMAIL;
    public static Integer SIGNIN_REQUEST = 1;
    public static final String EXTRA_WORD = "1";

    public static final String LOGIN_OK = "true";

  //  SQLiteManager manager = new SQLiteManager(this);
    @BindView(R.id.tvSignIn)
    TextView tvSignIn;
    @BindView(R.id.tvForgotPassword)
    TextView tvForgotPassword;

    private String sharedPrefFile = PrefsFileKeys.FILE_NAME;
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
                //TODO: Agafar de la nova activity signIn
                //etEmail.setText(data.getStringExtra(SignInActivity.NEW_NAME));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        try { //todo s'hauria de treure un cop s'hagi tret el que depen
      //      manager.openWrite();

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

        String oldName = prefs.getString(PrefsFileKeys.EMAIL, "");
        etEmail.setText(oldName);
        if (!oldName.equals("")) {
            checkBox.setChecked(true);
        }

    }

    public void BindView() {
        etEmail = findViewById(R.id.etMail);
        etPassword = findViewById(R.id.etPassword);
        checkBox = findViewById(R.id.checkBox);
        testButton = findViewById(R.id.testButton);
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    testClick();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void launchSignIn(View v) {
        Intent launch = new Intent(this, RegisterActivity.class);
        startActivityForResult(launch, SIGNIN_REQUEST);
    }

    public String generatePasswordHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(password.getBytes("utf8"));
        String newPassword = String.format("%040x", new BigInteger(1, digest.digest()));
        return newPassword;
    }


    public boolean canLogin(Donor donor) throws InterruptedException, IOException, NoSuchAlgorithmException {

        isLogin = false;


        mAPIService.doLoginBoth(donor).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    String result = response.body();
                    String[] resultArray = result.split("-");

                    if (resultArray[0].equals(LOGIN_OK)) {
                        isLogin = true;

                        String loginType = resultArray[1];
                        String userId = resultArray[2];
                        prefsEditor.putString(PrefsFileKeys.LAST_LOGIN_TYPE, loginType);
                        prefsEditor.apply();
                        prefsEditor.putString(PrefsFileKeys.LAST_LOGIN_ID, userId);
                        prefsEditor.apply();

                        prefsEditor.putString(PrefsFileKeys.LAST_LOGIN, etEmail.getText().toString());
                        prefsEditor.apply();

                        loginButtonCorrectAnimation();

                    } else {
                        isLogin = false;

                        loginButtonIncorrectAnimation(null);


                    }

                } else {
                    isLogin = false;

                    String errorMessage = response.message() + " " + response.code();
                    loginButtonIncorrectAnimation(errorMessage);

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                isLogin = false;

                String errorMessage = t.getMessage();

                loginButtonIncorrectAnimation(errorMessage);

            }
        });


        return isLogin;

    }

    private void loginButtonCorrectAnimation() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testButton.doResult(true);
                        }
                    });
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        launchLogInActivity();
                    }
                });


            }
        });
    }


    private void loginButtonIncorrectAnimation(final String errorMessage) {

        // testButton.doResult(false);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            testButton.doResult(false);
                            if (errorMessage != null) {

                                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                            } else {
                                YoYo.with(Techniques.Shake)
                                        .duration(700)
                                        .playOn(findViewById(R.id.etMail));
                                YoYo.with(Techniques.Shake)
                                        .duration(700)
                                        .playOn(findViewById(R.id.etPassword));
                            }
                        }
                    });
                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testButton.reset();
                    }
                });

            }


        });
    }

    public void launchLogInActivity() {


        rememberUserEmail();

        Intent launch = new Intent(this, AppActivity.class);
//            launch.putExtra(EXTRA_PERSONA, per.getNom());
//            launch.putExtra(EXTRA_EMAIL, per.getEmail());


        //Log.d("nom", String.valueOf(per.getId()));
        startActivity(launch);

    }

    public void LogInClick(View v) throws IOException, NoSuchAlgorithmException, InterruptedException {

        Donor donor = new Donor();
        donor.email = etEmail.getText().toString();
        donor.password = etPassword.getText().toString();

        donor.password = generatePasswordHash(donor.password);


        canLogin(donor);
    }

    public void testClick() throws IOException, NoSuchAlgorithmException, InterruptedException {


        Donor donor = new Donor();
        donor.email = etEmail.getText().toString();
        donor.password = etPassword.getText().toString();

        donor.password = generatePasswordHash(donor.password);


        canLogin(donor);
    }

    private void rememberUserEmail() {
        if (checkBox.isChecked()) {
            String email = etEmail.getText().toString();
            prefsEditor.putString(PrefsFileKeys.EMAIL, email);
            prefsEditor.apply();
        } else {
            prefsEditor.putString(PrefsFileKeys.EMAIL, "");
            prefsEditor.apply();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        testButton.reset();
        etPassword.setText("");
        setRememberName();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

      //  manager.close();
    }

    @OnClick(R.id.tvForgotPassword)
    public void onViewClicked() {

        if (etEmail.getText().toString().equalsIgnoreCase("")) {
            Toast.makeText(this, "Write an email first", Toast.LENGTH_LONG).show();
        } else {


            mAPIService.getQuestionBothByMail(etEmail.getText().toString()).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            showAlertDialogQuestion(response.body());
                        }
                    } else {
                        Toast.makeText(MainActivity.this, response.code() + response.message(), Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });


        }

    }

    public void showAlertDialogQuestion(String question) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Answer the security question");
        builder.setMessage("\n"+question);

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        FrameLayout container = new FrameLayout(this);
        FrameLayout.LayoutParams params = new  FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin= getResources().getDimensionPixelSize(R.dimen.dialog_margin);

        input.setLayoutParams(params);
        container.addView(input);

        builder.setView(container);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                try {
                    getNewPassword(input.getText().toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void getNewPassword(String answerRaw) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        String answer = generatePasswordHash(answerRaw);
        // Toast.makeText(this, answer, Toast.LENGTH_LONG).show();
        String body = etEmail.getText().toString() + "-" + answer;

        mAPIService.getNewPassword(body).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    String arr[] = response.body().split("-");
                    if (arr[0].equalsIgnoreCase("true")) {

                        sendNotification(arr[1]);
                        Toast.makeText(MainActivity.this, "Check notifications", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, arr[1], Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Can't connect with server", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void sendNotification(String word){
        NotificationHelper nHelper = new NotificationHelper(this);
        nHelper.createNotificationNewPassword("Password changed!", "This is your new password: " + word , word);
    }



}
