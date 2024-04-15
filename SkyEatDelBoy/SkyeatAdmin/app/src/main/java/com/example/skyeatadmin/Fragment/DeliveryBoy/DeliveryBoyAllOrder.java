package com.example.skyeatadmin.Fragment.DeliveryBoy;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import com.example.skyeatadmin.Adapter.DeliveryBoyViewAdapter;
import com.example.skyeatadmin.Adapter.HotelViewAdapter;
import com.example.skyeatadmin.R;
import com.google.android.material.tabs.TabLayout;

public class DeliveryBoyAllOrder extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    Toolbar toolbar;
    DeliveryBoyViewAdapter viewPagerAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delivery_boy_all_order, container, false);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager2);
        viewPagerAdapter = new DeliveryBoyViewAdapter(getChildFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs2);
        tabLayout.setupWithViewPager(viewPager);
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("title");
        return view;
    }
}