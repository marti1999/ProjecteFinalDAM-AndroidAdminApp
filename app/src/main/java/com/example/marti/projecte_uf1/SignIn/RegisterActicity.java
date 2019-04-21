package com.example.marti.projecte_uf1.SignIn;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.marti.projecte_uf1.R;
import com.example.marti.projecte_uf1.model.Donor;
import com.example.marti.projecte_uf1.model.Requestor;
import com.transitionseverywhere.ChangeText;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class RegisterActicity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button nextBt;
    private Button backBt;
    private ViewGroup transitionsContainer;
    private Donor donor;
    private Requestor requestor;


    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acticity);
        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.registerLayout);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        backBt = (Button) findViewById(R.id.backBt);
        backBt.setVisibility(View.INVISIBLE);

        backBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentTab > 0) {
                    currentTab--;
                    if (currentTab == 0) {
                        TransitionManager.beginDelayedTransition(transitionsContainer);

                        backBt.setVisibility(View.INVISIBLE);


                    }

                    showNextButton("Next");

                    viewPager.setCurrentItem(currentTab);
                }
            }
        });


        nextBt = (Button) findViewById(R.id.nextBt);
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());

                if (page instanceof Register1Fragment) {
                    //   if (((Register1Fragment) page).isInfoOk()) { //todo habilitar el if
                    donor = ((Register1Fragment) page).getUser();
                    nextTab(transitionsContainer);

                    //  }
                }

                if (page instanceof Register2Fragment) {
                    //     if (((Register2Fragment) page).isInfoOk()) { //todo habilitar el if

                    try {
                        Donor donor2 = ((Register2Fragment) page).getUser();
                        donor.password = donor2.password;
                        donor.securityAnswer = donor2.securityAnswer;
                        donor.securityQuestion = donor2.securityQuestion;
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }


                    nextTab(transitionsContainer);

                    //   }
                }

                if (page instanceof Register3Fragment) {
                    finish();
                }


            }
        });


        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(viewPager);
    }

    public boolean createRequestor(Requestor requestor) {
        requestor.name = donor.name;
        requestor.lastName = donor.lastName;
        requestor.email = donor.email;
        requestor.birthDate = donor.birthDate;
        requestor.gender = donor.gender;
        requestor.dni = donor.dni;
        requestor.password = donor.password;
        requestor.securityQuestion = donor.securityQuestion;
        requestor.securityAnswer = donor.securityAnswer;


        //todo add requestor to db
        return true;
    }

    public void showNextButton(String text) {
        nextBt.setText(text);
        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.registerLayout);

        TransitionManager.beginDelayedTransition(transitionsContainer);
        nextBt.setVisibility(View.VISIBLE);
    }


    public void hideBackButton() {
        final ViewGroup transitionsContainer = (ViewGroup) findViewById(R.id.registerLayout);

        TransitionManager.beginDelayedTransition(transitionsContainer);
        backBt.setVisibility(View.INVISIBLE);
    }

    private void nextTab(ViewGroup transitionsContainer) {
        if (currentTab < 2) {
            currentTab++;
        }

        if (currentTab == 2) {
            TransitionManager.beginDelayedTransition(transitionsContainer);
            nextBt.setVisibility(View.INVISIBLE);


        }

        if (backBt.getVisibility() == View.INVISIBLE) {
            TransitionManager.beginDelayedTransition(transitionsContainer);
            backBt.setVisibility(View.VISIBLE);
        }
        TransitionManager.beginDelayedTransition(transitionsContainer);

        viewPager.setCurrentItem(currentTab);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Register1Fragment(), "Screen1");
        adapter.addFragment(new Register2Fragment(), "Screen2");
        adapter.addFragment(new Register3Fragment(), "Screen3");
        viewPager.setAdapter(adapter);

    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();
        private Fragment mCurrentFragment;


        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }
}
