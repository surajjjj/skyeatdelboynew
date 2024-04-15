package com.example.skyeatadmin.Fragment.Hotel;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.skyeatadmin.Adapter.HotelViewAdapter;
import com.example.skyeatadmin.Adapter.ViewPagerAdapter;
import com.example.skyeatadmin.R;
import com.google.android.material.tabs.TabLayout;


public class HotelAllOrder extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    HotelViewAdapter viewPagerAdapter;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hotel_all_order, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager1);
        viewPagerAdapter = new HotelViewAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs1);
        tabLayout.setupWithViewPager(viewPager);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("title");
        return view;
    }
}