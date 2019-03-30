package com.example.marti.projecte_uf1;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class FragmentCalc1 extends Fragment {

    @BindView(R.id.etNum1)
    EditText etNum1;
    @BindView(R.id.etNum2)
    EditText etNum2;
    @BindView(R.id.btAdd)
    Button btAdd;
    @BindView(R.id.btSub)
    Button btSub;
    @BindView(R.id.btMult)
    Button btMult;
    @BindView(R.id.btDiv)
    Button btDiv;
    Unbinder unbinder;
    private CalculatorActivity act;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fragment_calc1, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CalculatorActivity) {
            this.act = (CalculatorActivity) context;


        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btAdd, R.id.btSub, R.id.btMult, R.id.btDiv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btAdd:
                //
                //this.tabsActivity.calculat
                try {
                    double num = Double.valueOf(etNum1.getText().toString()) + Double.valueOf(etNum2.getText().toString());
                    act.calculate(num);
            } catch (Exception ex) {

            }

                break;
            case R.id.btSub:
                try {
                    double num2 = Double.valueOf(etNum1.getText().toString()) - Double.valueOf(etNum2.getText().toString());
                    act.calculate(num2);
                } catch (Exception ex) {

                }

                break;
            case R.id.btMult:
                try {
                    double num3 = Double.valueOf(etNum1.getText().toString()) * Double.valueOf(etNum2.getText().toString());
                    act.calculate(num3);
                } catch (Exception ex) {

                }

                break;
            case R.id.btDiv:
                try {
                    double num4 = Double.valueOf(etNum1.getText().toString()) / Double.valueOf(etNum2.getText().toString());
                    act.calculate(num4);
                } catch (Exception ex) {

                }


                break;
        }
    }
}