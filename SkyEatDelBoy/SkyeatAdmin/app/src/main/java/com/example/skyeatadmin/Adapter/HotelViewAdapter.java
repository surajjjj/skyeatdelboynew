package com.example.skyeatadmin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.skyeatadmin.Fragment.CancelOrder;
import com.example.skyeatadmin.Fragment.CompleteOrder;
import com.example.skyeatadmin.Fragment.Hotel.HotelCancelOrder;
import com.example.skyeatadmin.Fragment.Hotel.HotelCompleteOrder;
import com.example.skyeatadmin.Fragment.Hotel.HotelNewOrder;
import com.example.skyeatadmin.Fragment.NewOrder;

public class HotelViewAdapter extends FragmentPagerAdapter
{

    public HotelViewAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0)
        {
            fragment = new HotelNewOrder();

        }
        else if (position == 1)
        {
            fragment = new HotelCompleteOrder();
        }
        else if (position == 2)
        {
            fragment = new HotelCancelOrder();

        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;

        switch (position)
        {
            case 0:
                title = "New Order";
                break;
            case 1:
                title = "Complete";
                break;
            case 2:
                title = "Cancel";
                break;

        }
//        if (position == 0)
//        {
//            title = "New Order";
//        }
//        else if (position == 1)
//        {
//            title = "Complete";
//        }
//        else if (position == 2)
//        {
//            title = "Cancel";
//        }
        return title;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
