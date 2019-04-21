package com.example.marti.projecte_uf1.SignIn;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marti.projecte_uf1.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class Register1Fragment extends Fragment {


    @BindView(R.id.ivMale)
    ImageView ivMale;
    @BindView(R.id.ivFemale)
    ImageView ivFemale;
    Unbinder unbinder;
    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;
    private boolean maleSelected = false;
    private boolean femaleSelected = false;


    public Register1Fragment() {
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
        View view = inflater.inflate(R.layout.fragment_register1, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    private void increaseFemale(){
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