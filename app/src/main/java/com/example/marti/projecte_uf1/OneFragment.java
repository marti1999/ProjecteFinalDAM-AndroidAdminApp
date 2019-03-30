package com.example.marti.projecte_uf1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class OneFragment extends Fragment {

    @BindView(R.id.btStart)
    Button btStart;
    @BindView(R.id.btEnd)
    Button btEnd;
    Unbinder unbinder;
    @BindView(R.id.imageView2)
    ImageView imageView2;

    private String sharedPrefFile = "prefsFile";
    private SharedPreferences prefs;
    private SharedPreferences.Editor prefsEditor;

    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(sharedPrefFile, getActivity().MODE_PRIVATE);
        prefsEditor = prefs.edit();
        //todo
//        if (prefs.getBoolean("ALERT", false)) {
//            imageView2.setImageResource(R.drawable.btpressed);
//        } else {
//            imageView2.setImageResource(R.drawable.btnormal);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btStart, R.id.btEnd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btStart:

//                boolean activat = prefs.getBoolean("ALERT", false);
//                if (activat) {
//                    getActivity().stopService(new Intent(getActivity(), myService.class));
//                    prefsEditor.putBoolean("ALERT", false);
//                    prefsEditor.apply();
//                } else {
//                    Intent servicio = new Intent(getActivity(), myService.class);
//
//                    getActivity().startService(servicio);
//
//                    prefsEditor.putBoolean("ALERT", true);
//                    prefsEditor.apply();
//                }


                break;
            case R.id.btEnd:


                break;
        }
    }

    @OnClick(R.id.imageView2)
    public void onViewClicked() {
        boolean activat = prefs.getBoolean("ALERT", false);
        if (activat) {
            getActivity().stopService(new Intent(getActivity(), myService.class));
            prefsEditor.putBoolean("ALERT", false);
            prefsEditor.apply();
            imageView2.setImageResource(R.drawable.btnormal);
        } else {
            Intent servicio = new Intent(getActivity(), myService.class);

            getActivity().startService(servicio);

            prefsEditor.putBoolean("ALERT", true);
            prefsEditor.apply();
            imageView2.setImageResource(R.drawable.btpressed);
        }

    }
}