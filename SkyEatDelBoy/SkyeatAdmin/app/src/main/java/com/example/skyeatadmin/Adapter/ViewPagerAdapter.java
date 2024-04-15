package com.example.skyeatadmin.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.skyeatadmin.Fragment.CancelOrder;
import com.example.skyeatadmin.Fragment.CompleteOrder;
import com.example.skyeatadmin.Fragment.NewOrder;

import java.util.HashMap;

public class ViewPagerAdapter extends FragmentPagerAdapter
{
  //  private HashMap<Integer, Fragment> fragmentMap = new HashMap<>();

    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position)
    {
        Fragment fragment = null;

            // Create a new instance of the Fragment based on the tab position
//            switch (position) {
//                case 0:
//                    fragment = new NewOrder();
//                    break;
//                case 1:
//                    fragment = new CompleteOrder();
//                    break;
//                case 2:
//                    fragment = new CancelOrder();
//                    break;
//                // Add more cases for other tab positions and corresponding Fragment classes
//            }


        if (position == 0)
        {
                fragment = new NewOrder();

        }
        else if (position == 1)
        {
            fragment = new CompleteOrder();
        }
        else if (position == 2)
        {
            fragment = new CancelOrder();

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
