package ua.lviv.iot.phoenix.noq.fragments;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import ua.lviv.iot.phoenix.noq.R;

public class TabFragment extends Fragment {

    private View view;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    Toolbar toolbar;
    Fragment fragment;

    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tab, container, false);

        toolbar = view.findViewById(R.id.toolbar);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        pagerAdapter = new FragmentAdapter(getChildFragmentManager());

        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:{
                    fragment = new ListOfCafesFragment();
                    fragment.setArguments(new Bundle());
                    return fragment;
                }
                case 1:{
                    fragment = new ListOfMealsFragment();
                    fragment.setArguments(new Bundle());
                    return fragment;
                }
                case 2:{
                    fragment = new TimeFragment();
                    fragment.setArguments(new Bundle());
                    return fragment;
                }
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                //
                //Your tab titles
                //
                case 0:return "Profile";
                case 1:return "Search";
                case 2: return "Contacts";
                default:return null;
            }
        }
    }


}
