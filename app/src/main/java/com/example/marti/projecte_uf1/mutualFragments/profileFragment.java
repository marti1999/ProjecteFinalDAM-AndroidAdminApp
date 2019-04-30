package com.example.marti.projecte_uf1.mutualFragments;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;
import com.example.marti.projecte_uf1.remote.ApiUtils;
import com.example.marti.projecte_uf1.utils.PrefsFileKeys;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
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
    de.hdodenhof.circleimageview.CircleImageView image;
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private Donor donor;
    private Requestor requestor;
    private String userType;
    private String userId;
private final int GALLERY_REQUEST_CODE = 1;
    public profileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAPIService = ApiUtils.getAPIService();

        prefs = getActivity().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        prefsEditor = prefs.edit();

        userType = prefs.getString(PrefsFileKeys.LAST_LOGIN_TYPE, "");
        userId = prefs.getString(PrefsFileKeys.LAST_LOGIN_ID, "");

        if (userType.equalsIgnoreCase("donor")) {
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
        } else {
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
        pointsLabel.setText("Remaining Points");
        points.setText(String.valueOf(requestor.points));
        amountLabel.setText("Points per year");
        amount.setText(requestor.maxClaim.value);
        email.setText(requestor.email);
        password.setText("**********");
        name.setText(requestor.name);
        dni.setText(requestor.dni);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    @OnClick(R.id.image)
    public void onViewClicked() {
        pickFromGallery();
    }
    private void pickFromGallery(){
        //Create an Intent with action as ACTION_PICK
        Intent intent=new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        // Launching the Intent
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData returns the content URI for the selected Image
                    Uri selectedImage = data.getData();
                    image.setImageURI(selectedImage);
                    break;
            }
    }
}
