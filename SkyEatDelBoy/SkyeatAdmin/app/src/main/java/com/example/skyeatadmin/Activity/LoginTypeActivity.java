package com.example.skyeatadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.skyeatadmin.R;

public class LoginTypeActivity extends AppCompatActivity {

    TextView txtAdminLogin,txtHotelLogin,txtDeliveryBoyLogin;
    public static boolean isAdmin=false;
    public static boolean isHotel=false;
    public static boolean isDeliveryBoy=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_type);
        txtHotelLogin=findViewById(R.id.txtHotelLogin);
        txtAdminLogin=findViewById(R.id.txtAdminLogin);
        txtDeliveryBoyLogin=findViewById(R.id.txtDeliveryBoyLogin);

        txtAdminLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdmin=true;
                isDeliveryBoy=false;
                isHotel=false;
                Intent intent=new Intent(LoginTypeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        txtHotelLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdmin=false;
                isDeliveryBoy=false;
                isHotel=true;
                Intent intent=new Intent(LoginTypeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        txtDeliveryBoyLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isAdmin=false;
                isDeliveryBoy=true;
                isHotel=false;
                Intent intent=new Intent(LoginTypeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}