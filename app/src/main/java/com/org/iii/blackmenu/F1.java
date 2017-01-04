package com.org.iii.blackmenu;

import android.app.Activity;
import android.content.Context;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.design.widget.TabLayout;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class F1 extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Toolbar toolbar;
    private Activity mActivity;
    private Rice rice;
    private Noodle noodle;
    private Soup soup;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
//        Log.v("will", "F1-onAttach");
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        myContext = (FragmentActivity) activity;
//    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rice = new Rice();
        noodle = new Noodle();
        soup = new Soup();

//        Log.v("will", "F1-onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f1_layout, container, false);

//        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
//        setSupportActionBar(toolbar);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        Log.v("will", "F1-onCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        Log.v("will", "F1-onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
//        setupViewPager(viewPager);
//        tabLayout.setupWithViewPager(viewPager);
//        Log.v("will", "F1-onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
//        Log.v("will", "F1-onResume");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        adapter.addFragment(rice, "飯類");
        adapter.addFragment(noodle, "麵類");
        adapter.addFragment(soup, "湯類");
        Log.v("will", "F1-adapter:" + adapter);
//        Log.v("will", "F1-setupViewPager");
//        adapter.notifyDataSetChanged();
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.v("will", "F1-getItem" + position);
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            Log.v("will", "F1-mFragmentList:" + mFragmentList);
            mFragmentTitleList.add(title);
//            Log.v("will", "F1-title:" + title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}
