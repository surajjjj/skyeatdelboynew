package com.example.skyeatadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.example.skyeatadmin.Activity.Hotel.HotelMainActivity;
import com.example.skyeatadmin.Model.DeliveryBoy;
import com.example.skyeatadmin.Model.HotelLogin;
import com.example.skyeatadmin.Model.Login;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginActivity extends AppCompatActivity {
    TextView btnLoginNow;
    TextView txtLogin;
    public boolean isLoginValid=false;
    String email,password,username,zone_name,id;
    String hotelEmail,hotelPassword,hotelId,hotelName;
    String delFirstName,delLastName,delBoyId,delEmailId,delPassword,delZoneId;
    TextInputEditText txtUserName,txtPassword;
    CheckBox chkRememberMe;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    ArrayList<Login.Data> loginData=new ArrayList<>();
    ArrayList<HotelLogin.Data> hotelLoginData=new ArrayList<HotelLogin.Data>();
    ArrayList<DeliveryBoy.Data> delBoyLoginData=new ArrayList<DeliveryBoy.Data>();
    BottomSheetDialog mBottomSheetDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState)   {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        btnLoginNow=findViewById(R.id.btnLoginNow);
        mBottomSheetDialog = new BottomSheetDialog(this);

        btnLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(!mBottomSheetDialog.isShowing())
                {
                    buttonLogin();
                }
            }
        });
    }
    public void buttonLogin() {

        View rootView = getLayoutInflater().inflate(R.layout.login_layout, null);
        mBottomSheetDialog.setContentView(rootView);
        txtUserName = rootView.findViewById(R.id.txtUserName);
        txtPassword = rootView.findViewById(R.id.txtPassword);
        chkRememberMe = rootView.findViewById(R.id.chkRememberMe);
        txtLogin = rootView.findViewById(R.id.txtLogin);

        chkRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean check)
            {

            }
        });

        txtLogin.setOnClickListener(view ->
        {
//            email="ss@gmail.com";
//            password="123456";

            if(validation())
            {
                email= Objects.requireNonNull(txtUserName.getText()).toString().trim();
                password= Objects.requireNonNull(txtPassword.getText()).toString().trim();

                getDeliveryBoyLogin();
//                if(LoginTypeActivity.isAdmin)
//                {
//                     getAdminLogin();
//                } else if (LoginTypeActivity.isHotel)
//                {
////                    email= Objects.requireNonNull(txtUserName.getText()).toString().trim();
////                    password= Constant.hashPassword(Objects.requireNonNull(txtPassword.getText()).toString().trim());
//                    getHotelLogin();
////                    String pass="3df50f77f4b1719b86b8d7929d0cec3365b3d5c73cf504b1071aeafefc579eebcb2f36dfa9301708791a0696c829f0d7a92516a8dc7e44a51dc29a0560ceb202";
//
////                        String dpass=Constant.decrypt("3df50f77f4b1719b86b8d7929d0cec3365b3d5c73cf504b1071aeafefc579eebcb2f36dfa9301708791a0696c829f0d7a92516a8dc7e44a51dc29a0560ceb202");
////                        Toast.makeText(LoginActivity.this, dpass.toString(), Toast.LENGTH_SHORT).show();
//                } else if (LoginTypeActivity.isDeliveryBoy)
//                {
//                    getDeliveryBoyLogin();
//                }
            }
        });
        mBottomSheetDialog.show();
    }

    private void getDeliveryBoyLogin()
    {
        RetrofitClient.getInstance().getMyApi().getDeliveryBoyLogin("get_delivery_boy_login",email,password).enqueue(new Callback<DeliveryBoy>() {
            @Override
            public void onResponse(Call<DeliveryBoy> call, Response<DeliveryBoy> response) {
                assert response.body() != null;
                delBoyLoginData= (ArrayList<DeliveryBoy.Data>) response.body().getData();
                if(delBoyLoginData.size()>0){
                    delBoyId=delBoyLoginData.get(0).getDriver_id();
                    delFirstName=delBoyLoginData.get(0).getName();
                    delLastName=delBoyLoginData.get(0).getLast_name();
                    delEmailId=delBoyLoginData.get(0).getEmail();
                    delPassword=delBoyLoginData.get(0).getPasstemp();
                    delZoneId=delBoyLoginData.get(0).getZone_id();
                    if(chkRememberMe.isChecked())
                    {
                        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("login", "DeliveryBoy");
                        editor.putString("delivery_boy_id", delBoyId);
                        editor.putString("first_name", delFirstName);
                        editor.putString("last_name", delLastName);
                        editor.putString("email", delEmailId);
                        editor.putString("password", delPassword);
                        editor.putString("zone_id", delZoneId);
                        editor.apply();
                    }
                    else
                    {
                        sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        editor = sharedPreferences.edit();
                        editor.putString("login", "DeliveryBoy");
                        editor.putString("delivery_boy_id", delBoyId);
                        editor.putString("first_name", delFirstName);
                        editor.putString("last_name", delLastName);
                        editor.putString("zone_id", delZoneId);
                        editor.apply();
                    }
                    isLoginValid=false;
                    //Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(LoginActivity.this, DeliveryBoyMainActivity.class);
                    startActivity(intent);
                }
                else{
                    isLoginValid=false;
                    Toast.makeText(LoginActivity.this, "Incorrect username and password", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<DeliveryBoy> call, Throwable t) {
                Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getHotelLogin()
    {

         RetrofitClient.getInstance().getMyApi().getHotelLogin("get_hotel_login",email,password).enqueue(new Callback<HotelLogin>() {
             @Override
             public void onResponse(Call<HotelLogin> call, Response<HotelLogin> response) {
                 assert response.body() != null;
                 hotelLoginData= (ArrayList<HotelLogin.Data>) response.body().getData();
                 if(hotelLoginData.size()>0)
                 {

                     hotelEmail=hotelLoginData.get(0).getEmail();
                     hotelPassword=hotelLoginData.get(0).getTemppass();
                     hotelName=hotelLoginData.get(0).getName();
                     hotelId=hotelLoginData.get(0).getVendor_id();

                     if(chkRememberMe.isChecked())
                     {
                         sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                         editor = sharedPreferences.edit();
                         editor.putString("login", "Hotel");
                         editor.putString("hotel_name", hotelName);
                         editor.putString("password", hotelPassword);
                         editor.putString("email", hotelEmail);
                         editor.putString("vendor_id", hotelId);
                         editor.apply();
                     }
                     else {
                         sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                         editor = sharedPreferences.edit();
                         editor.putString("login", "Hotel");
                         editor.putString("hotel_name", hotelName);
                         editor.putString("vendor_id", hotelId);
                         editor.apply();

                     }
                     isLoginValid=false;
                     //Toast.makeText(LoginActivity.this, "Login", Toast.LENGTH_SHORT).show();
                     Intent intent=new Intent(LoginActivity.this, HotelMainActivity.class);
                     startActivity(intent);

                 }
                 else
                 {
                     Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                     isLoginValid=false;
                 }

             }

             @Override
             public void onFailure(Call<HotelLogin> call, Throwable t) {
                 Toast.makeText(LoginActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();

             }
         });

//        Toast.makeText(this, ""+hashedPassword, Toast.LENGTH_SHORT).show();


    }

    public void getAdminLogin() {
        RetrofitClient.getInstance().getMyApi().getLogin("get_new_loginflutter").enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                assert response.body() != null;
                loginData= (ArrayList<Login.Data>) response.body().getData();
                for(int i=0;i<loginData.size();i++)
                {
                    if(loginData.get(i).getEmail().equals(email) && loginData.get(i).getPassword().equals(password))
                    {
                        isLoginValid=true;
                        id=loginData.get(i).getId();
                        username=loginData.get(i).getName();
                        zone_name=loginData.get(i).getZone_name();

                        //Toast.makeText(LoginActivity.this, "true..", Toast.LENGTH_SHORT).show();
                    }
                }
                if(isLoginValid)
                {
                    isLoginValid=false;
                    saveSharedPref();

                }
                else {
                    Toast.makeText(getApplicationContext(), "Incorrect username or password", Toast.LENGTH_SHORT).show();
                    isLoginValid=false;
                }

            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveSharedPref()
    {
        if(chkRememberMe.isChecked())
        {
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("login", "Admin");
            editor.putString("username", username);
            editor.putString("password", password);
            editor.putString("email", email);
            editor.putString("zone_name", zone_name);
            editor.putString("id", id);
            editor.apply();
        }
        else
        {
            sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            editor = sharedPreferences.edit();
            editor.putString("login", "Admin");
            editor.putString("username", username);
            editor.putString("zone_name", zone_name);
            editor.putString("id", id);
            editor.apply();
        }

        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);

    }
    public boolean validation() {
        if (txtUserName.getText().toString().isEmpty()) {
            txtUserName.setError("Enter Email ID");
            return false;
        }

        if (txtPassword.getText().toString().isEmpty())
        {
            txtPassword.setError("Enter Password");
            return false;
        }

        return true;
    }
}