package com.example.marti.projecte_uf1.SignIn;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.interfaces.ApiMecAroundInterfaces;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;
import com.example.marti.projecte_uf1.remote.ApiUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Register3Fragment extends Fragment {
    @BindView(R.id.donorBt)
    ImageButton donorBt;
    @BindView(R.id.requestorBt)
    ImageButton requestorBt;
    @BindView(R.id.questionLayout)
    LinearLayout questionLayout;
    @BindView(R.id.donorLayout)
    LinearLayout donorLayout;

    Unbinder unbinder;
    @BindView(R.id.register3layout)
    LinearLayout register3layout;
    @BindView(R.id.spinnerMembers)
    Spinner spinnerMembers;
    @BindView(R.id.spinnerIncome)
    Spinner spinnerIncome;
    @BindView(R.id.requestorLayout)
    LinearLayout requestorLayout;
    @BindView(R.id.saveRequestorBt)
    Button saveRequestorBt;
    @BindView(R.id.errorLayout)
    LinearLayout errorLayout;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    Requestor requestor;
    private ApiMecAroundInterfaces mAPIService;


    public Register3Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(sharedPrefFile, getActivity().MODE_PRIVATE);
        prefsEditor = prefs.edit();
        mAPIService = ApiUtils.getAPIService();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_register3, container, false);


        unbinder = ButterKnife.bind(this, view);
        fillSpinners();

        saveRequestorButtonClickListener();

        DonorButtonClickListener();

        RequestorButtonClickListener();

        return view;
    }

    private void RequestorButtonClickListener() {
        requestorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donorBt.getBackground().setAlpha(0);
                disableRequestorDonorButtons();
                final ViewGroup transitionsContainer = (ViewGroup) getView().findViewById(R.id.register3layout);

                TransitionManager.beginDelayedTransition(transitionsContainer);
                requestorLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    private void DonorButtonClickListener() {
        donorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestorBt.getBackground().setAlpha(0);
                disableRequestorDonorButtons();



                Donor d0 = ((RegisterActivity) getActivity()).getDonor();

                mAPIService.insertDonor(d0).enqueue(new Callback<Donor>() {
                    @Override
                    public void onResponse(Call<Donor> call, Response<Donor> response) {
                        if (response.isSuccessful()) {
                            Donor d = response.body();
                            if (d != null) {
                                succesfulMessage();
                            } else {
                                errorMessage();
                            }
                        } else {
                            errorMessage();
                        }
                    }

                    @Override
                    public void onFailure(Call<Donor> call, Throwable t) {
                        errorMessage();
                    }
                });
            }
        });
    }

    private void saveRequestorButtonClickListener() {
        saveRequestorBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestorHouseholdData();
                Requestor r0 = ((RegisterActivity) getActivity()).getRequestor(requestor);


                mAPIService.insertRequestor(r0).enqueue(new Callback<Requestor>() {
                    @Override
                    public void onResponse(Call<Requestor> call, Response<Requestor> response) {
                        if (response.isSuccessful()) {
                            Requestor r = response.body();
                            if (r != null) {
                                requestorLayout.setVisibility(View.GONE);
                                succesfulMessage();
                            } else {
                                requestorLayout.setVisibility(View.GONE);
                                errorMessage();
                            }
                        } else {
                            requestorLayout.setVisibility(View.GONE);
                            errorMessage();
                        }
                    }

                    @Override
                    public void onFailure(Call<Requestor> call, Throwable t) {
                        requestorLayout.setVisibility(View.GONE);
                        errorMessage();
                    }
                });

            }
        });
    }

    private void disableRequestorDonorButtons() {
        donorBt.setEnabled(false);
        requestorBt.setEnabled(false);
    }

    private void setRequestorHouseholdData() {
        requestor = new Requestor();
        requestor.householdIncome = Double.valueOf(spinnerIncome.getSelectedItem().toString());
        requestor.householdMembers = Integer.valueOf(spinnerMembers.getSelectedItem().toString());
    }

    private void errorMessage() {
        errorLayout.setVisibility(View.VISIBLE);
        ((RegisterActivity) getActivity()).hideBackButton();
        ((RegisterActivity) getActivity()).showNextButton("Back to login");
    }

    private void succesfulMessage() {
        donorLayout.setVisibility(View.VISIBLE);

        ((RegisterActivity) getActivity()).hideBackButton();
        ((RegisterActivity) getActivity()).showNextButton("Finish");
    }

    private void fillSpinners() {
        ArrayAdapter<CharSequence> membersAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.members, android.R.layout.simple_spinner_item);
        membersAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMembers.setAdapter(membersAdapter);

        ArrayAdapter<CharSequence> incomeAdapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.income, android.R.layout.simple_spinner_item);
        incomeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerIncome.setAdapter(incomeAdapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


}