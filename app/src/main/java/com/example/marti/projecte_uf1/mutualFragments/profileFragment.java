package com.example.marti.projecte_uf1.mutualFragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private ApiMecAroundInterfaces mAPIService;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private Donor donor;
    private Requestor requestor;
    private String userType;
    private String userId;

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
                            //TODO: cridar el metode que omple la pantalla
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
                            //TODO: cridar el metode que omple la pantalla
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
    }

    private void fillTextViewsRequestor(Requestor donor){

        type.setText(userType);
        name.setText(donor.name);
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

    @OnClick(R.id.password)
    public void onViewClicked() {
    }
}
