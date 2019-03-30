package com.example.marti.projecte_uf1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CalculatorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        getSupportActionBar().setTitle("Calculator");


    }public void calculate(double num) {
        FragmentCalc2 bottomFragment = (FragmentCalc2) this.getSupportFragmentManager().findFragmentById(R.id.bottomFragment);
        bottomFragment.showText(num);
    }
}
