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

import java.util.ArrayList;
import java.util.List;

public class RegisterActicity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button nextBt;
    private Button backBt;
    private ViewGroup transitionsContainer;


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
                Log.d("CURRENTpAGE ", String.valueOf(currentTab));
                if (currentTab > 0){
                    currentTab--;
                    if (currentTab == 0){
                        TransitionManager.beginDelayedTransition(transitionsContainer);

                        backBt.setVisibility(View.INVISIBLE);

                    }
                    viewPager.setCurrentItem(currentTab);
                }
            }
        });


        nextBt = (Button) findViewById(R.id.nextBt);
        nextBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("CURRENTpAGE ", String.valueOf(currentTab));

                Fragment page = getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewpager + ":" + viewPager.getCurrentItem());

                if (page instanceof Register1Fragment){
                    if (((Register1Fragment) page).isInfoOk()){
                        if (currentTab<1){
                            currentTab++;
                        }

                        if (backBt.getVisibility()== View.INVISIBLE){
                            TransitionManager.beginDelayedTransition(transitionsContainer);

                            backBt.setVisibility(View.VISIBLE);


                        }
                        TransitionManager.beginDelayedTransition(transitionsContainer);

                        viewPager.setCurrentItem(currentTab);

                    }
                }



            }
        });



        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);


        tabLayout.setupWithViewPager(viewPager);
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Register1Fragment(), "Button");
        adapter.addFragment(new Register2Fragment(), "PDF");
        //adapter.addFragment(new ThreeFragment(), "THREE");
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
