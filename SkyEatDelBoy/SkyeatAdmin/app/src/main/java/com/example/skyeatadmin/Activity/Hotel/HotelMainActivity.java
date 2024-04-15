package com.example.skyeatadmin.Activity.Hotel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.example.skyeatadmin.Activity.DeliveryBoyActivity;
import com.example.skyeatadmin.Activity.LoginTypeActivity;
import com.example.skyeatadmin.Fragment.AllOrder;
import com.example.skyeatadmin.Fragment.Hotel.HotelAllOrder;
import com.example.skyeatadmin.R;
import com.google.android.material.navigation.NavigationView;

public class HotelMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    NavigationView navigationView;
    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    private DrawerLayout drawerLayout;
    public static String VENDOR_ID,HOTEL_NAME;


    private ActionBarDrawerToggle drawerToggle;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_main);
        drawerLayout=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        navigationView=findViewById(R.id.navigation_view);
        View headerView = navigationView.getHeaderView(0);
//        txtManagerName= headerView.findViewById(R.id.txtManagerName);
//        txtVersionCode = findViewById(R.id.txtVersionCode);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HotelAllOrder());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_draw_open,R.string.navigation_draw_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setTitle("All Orders");

        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HotelAllOrder()).commit();
            navigationView.setCheckedItem(R.id.menuAllOrder);
        }

        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("hotel_name"))
        {
            HOTEL_NAME=sharedPreferences.getString("hotel_name",null);
        }
        if(sharedPreferences.contains("vendor_id"))
        {
            VENDOR_ID=sharedPreferences.getString("vendor_id",null);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menuCompleteOrders:

                Intent i = new Intent(HotelMainActivity.this, DeliveryBoyActivity.class);
                //Intent intent=new Intent(getContext(), OrderDetailActivity.class);
                startActivity(i);
                break;
            case R.id.menuLogout:

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(HotelMainActivity.this, LoginTypeActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}