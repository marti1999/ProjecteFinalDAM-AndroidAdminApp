package com.example.marti.projecte_uf1.mutualFragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatImageView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.marti.projecte_uf1.AppActivity;
import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class profileFragment extends Fragment {

    @BindView(R.id.type)
    TextView type;
    @BindView(R.id.pointsLabel)
    TextView pointsLabel;
    @BindView(R.id.points)
    TextView points;
    @BindView(R.id.amountLabel)
    TextView amountLabel;
    @BindView(R.id.amount)
    TextView amount;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.password)
    TextView password;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.dni)
    TextView dni;
    Unbinder unbinder;
    @BindView(R.id.image)
    CircleImageView image;
    @BindView(R.id.imageEdit)
    FloatingActionButton imageEdit;
    @BindView(R.id.passwordEdit)
    AppCompatImageView passwordEdit;
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private Donor donor;
    private Requestor requestor;
    private String userType;
    private String userId;
    private final int GALLERY_REQUEST_CODE = 1;
    private StorageReference mStorageRef;

    public profileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initiateGlobalVariables();
    }

    private void fillRequestor() {
        mAPIService.getRequestorById(Integer.valueOf(userId)).enqueue(new Callback<Requestor>() {
            @Override
            public void onResponse(Call<Requestor> call, Response<Requestor> response) {
                if (response.isSuccessful()) {
                    requestor = response.body();
                    if (requestor != null) {
                        fillTextViewsRequestor(requestor);
                    } else {
                        Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Requestor> call, Throwable t) {
                Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void fillDonor() {
        mAPIService.getDonorById(Integer.valueOf(userId)).enqueue(new Callback<Donor>() {
            @Override
            public void onResponse(Call<Donor> call, Response<Donor> response) {
                if (response.isSuccessful()) {
                    donor = response.body();
                    if (donor != null) {
                        fillTextViewsDonor(donor);
                    } else {
                        Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<Donor> call, Throwable t) {
                Toast.makeText(getActivity(), "Error connecting to server", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initiateGlobalVariables() {
        mAPIService = ApiUtils.getAPIService();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        prefs = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        userType = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "");
        userId = prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "");
    }

    private void fillTextViewsDonor(Donor donor) {

        type.setText(userType);
        name.setText(donor.name);
        pointsLabel.setText("Points");
        points.setText(String.valueOf(donor.points));
        amountLabel.setText("Amount Given");
        amount.setText(String.valueOf(donor.ammountGiven));
        email.setText(donor.email);
        password.setText("**********");
        name.setText(donor.name);
        dni.setText(donor.dni);

    }

    private void fillTextViewsRequestor(Requestor requestor) {

        type.setText(userType);
        name.setText(requestor.name);
        pointsLabel.setText("Used points");
        points.setText(String.valueOf(requestor.points));
        amountLabel.setText("Points per year");
        amount.setText(String.valueOf(requestor.maxClaim.value));
        email.setText(requestor.email);
        password.setText("**********");
        name.setText(requestor.name);
        dni.setText(requestor.dni);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);

        if (userType.equalsIgnoreCase("donor")) {
            fillDonor();
        } else {
            fillRequestor();
        }
        // image.setImageResource(R.drawable.profilepicture);
        if (getActivity() instanceof AppActivity) {
            File picturePath = ((AppActivity) getActivity()).getProfilePicture();
            if (picturePath == null) {
                image.setImageResource(R.drawable.male);

            } else {
                changePictureWithAbsolutePath(picturePath);
            }
        } else {
            try {
                downloadProfilePicture();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode) {
                case GALLERY_REQUEST_CODE:
                    Uri selectedImage = data.getData();
                    image.setImageURI(selectedImage);
                    uploadImage(selectedImage);
                    break;
            }
    }

    public void uploadImage(Uri file) {

        // Uri file = Uri.fromFile(new File("path/to/images/rivers.jpg"));
        String fileName = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "") + prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "");
        StorageReference ref = mStorageRef.child(fileName);

        ref.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(), "Profile picture succesfully uploaded", Toast.LENGTH_LONG).show();
                try {
                    ((AppActivity) getActivity()).downloadProfilePicture();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Failed to upload profile picture", Toast.LENGTH_LONG).show();
            }
        });
    }

    public String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    public void downloadProfilePicture() throws IOException {
        String fileName = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "") + prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "");
        StorageReference ref = mStorageRef.child(fileName);
        final File imageFile = File.createTempFile("images", "jpg");

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
                        image.setImageResource(R.drawable.male);
                    }
                });
    }

    private void changePictureWithAbsolutePath(File imageFile) {
        Bitmap myBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());

        image.setImageBitmap(myBitmap);
        YoYo.with(Techniques.FadeIn)
                .duration(2000)
                .playOn(image);
    }

    @OnClick({R.id.imageEdit, R.id.passwordEdit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.imageEdit:
                pickImageFromGallery();
                break;
            case R.id.passwordEdit:

                break;
        }
    }

    private void showPasswordChangeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Input de new password");
        builder.setMessage("\nWrite it two times to minimize errors");


        final EditText input = new EditText(getActivity());
        final EditText input2 = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        input2.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);


        FrameLayout container = new FrameLayout(getActivity());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);
        params.rightMargin = getResources().getDimensionPixelSize(R.dimen.dialog_margin);

        input.setLayoutParams(params);
        input2.setLayoutParams(params);
        container.addView(input);
        container.addView(input2);

        builder.setView(container);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (input.getText().toString().equals(input2.getText().toString())) {
                    try {
                        String hash = generatePasswordHash(input.getText().toString());
                        if (userType.equalsIgnoreCase("donor")) {
                            Donor d = new Donor();
                            d.id = Integer.valueOf(userId);
                            d.password = hash;
                            //TODO: webservice post
                        } else {
                            Requestor r = new Requestor();
                            r.id = Integer.valueOf(userId);
                            r.password = hash;
                            //TODO: webservice post
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to change password", Toast.LENGTH_SHORT).show();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        Toast.makeText(getActivity(), "Failed to change password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Password do not match", Toast.LENGTH_SHORT).show();
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

    public String generatePasswordHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        digest.reset();
        digest.update(password.getBytes("utf8"));
        String newPassword = String.format("%040x", new BigInteger(1, digest.digest()));
        return newPassword;
    }
}
