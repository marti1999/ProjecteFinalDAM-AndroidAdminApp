package com.example.marti.projecte_uf1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class FragmentCalc2 extends Fragment {
    @BindView(R.id.etResult)
    EditText etResult;
    Unbinder unbinder;
    private CalculatorActivity tabsActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_calc2, container, false);
        unbinder = ButterKnife.bind(this, view);
        etResult.setFocusable(false);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof tabsActivity) {
            this.tabsActivity = (CalculatorActivity) context;

        }
    }
    public void showText(double num) {
        etResult.setText(String.valueOf(num));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
