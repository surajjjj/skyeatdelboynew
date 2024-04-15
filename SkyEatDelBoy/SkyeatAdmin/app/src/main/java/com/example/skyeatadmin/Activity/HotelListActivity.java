package com.example.skyeatadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Adapter.HotelListAdapter;
import com.example.skyeatadmin.CustomDialog;
import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.Model.Hotel;
import com.example.skyeatadmin.Model.Sample;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelListActivity extends AppCompatActivity {
    String zone_id;
    RecyclerView lstData;
    HotelListAdapter hotelListAdapter;
    CustomDialog customDialog,customDialog1;
    TextView txtCancel,txtUpdate,txtPassword,txtHotelName,txtIncreasePer,txtDecreasePer;
    public boolean isIncreasePer=false;
    String vendorId,hotelName;
    int incPercentage,decPercentage;
    ArrayList<Hotel.Data> hotelList=new ArrayList<>();
    ArrayList<Hotel.Data> allHotelList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel_list);
        lstData=findViewById(R.id.lstData);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            zone_id=bundle.getString("zone_id");
        }
        allHotelList.clear();
        hotelList.clear();
        getHotel(zone_id);
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        lstData.setLayoutManager(llm);
    }

    public void getHotel(String zoneId) {
        RetrofitClient.getInstance().getMyApi().getHotel("get_hotel_flutter").enqueue(new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, Response<Hotel> response)
            {
//                String hotelName=response.body().getData().get(0).getName();
//                Toast.makeText(HotelListActivity.this, hotelName, Toast.LENGTH_SHORT).show();
                allHotelList= (ArrayList<Hotel.Data>) response.body().getData();
                if(allHotelList.size()>0)
                {
                    for(int i=0;i<allHotelList.size();i++)
                    {
                        if(zoneId.equals(allHotelList.get(i).getZone_id()))
                        {
                            hotelList.add(allHotelList.get(i));
                        }
                    }
                }
                hotelListAdapter=new HotelListAdapter(getApplicationContext(),hotelList);
                lstData.setAdapter(hotelListAdapter);

                hotelListAdapter.setOnClickListener(new HotelListAdapter.OnClickListener() {
                    @Override
                    public void onClick(int position) {
                        vendorId=hotelList.get(position).getVendor_id();
                        hotelName=hotelList.get(position).getName();

                        openUpdatePricePopPup(hotelName);

                    }
                });

                //Toast.makeText(HotelListActivity.this, String.valueOf(hotelList.size()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {
                Toast.makeText(HotelListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void openUpdatePricePopPup(String hotelName) {
        try {
            customDialog1 = new CustomDialog(HotelListActivity.this);
            customDialog1.setContentView(R.layout.popup_hotel_price);
            txtCancel=customDialog1.findViewById(R.id.txtCancel);
            txtUpdate=customDialog1.findViewById(R.id.txtUpdate);
            txtHotelName=customDialog1.findViewById(R.id.txtHotelName);
            txtIncreasePer=customDialog1.findViewById(R.id.txtIncreasePer);
            txtDecreasePer=customDialog1.findViewById(R.id.txtDecreasePer);
            txtPassword=customDialog1.findViewById(R.id.txtPassword);

            //  customDialog1.setCancelable(false);

            Window window = customDialog1.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(R.color.purple_500);
            if (window != null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(window.getAttributes());

                // Set the desired width and height of the dialog
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                window.setAttributes(layoutParams);
            }

            txtHotelName.setText(hotelName);
            txtIncreasePer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    if(b)
                    {
                        txtDecreasePer.setText("");
                        isIncreasePer=true;
                    }
                }

//                @Override
//                public void onClick(View view)
//                {
//                    txtDecreasePer.setText("");
//                    isIncreasePer=true;
//
//                }
            });
            txtDecreasePer.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean b) {
                    txtIncreasePer.setText("");
                    isIncreasePer=false;
                }
//                @Override
//                public void onClick(View view) {
//                    txtIncreasePer.setText("");
//                    isIncreasePer=false;
//                }
            });

            txtUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   if(txtPassword.getText().toString().trim().equals("Suraj@123"))
                   {
                       if(isIncreasePer)
                       {
                           if(txtIncreasePer.getText().toString().equals(""))
                           {
                               txtIncreasePer.setError("Enter Percentage");
                           }
                           else {
                               incPercentage= Integer.parseInt(txtIncreasePer.getText().toString());
                               if(incPercentage<=0){
                                   txtIncreasePer.setError("Invalid Percentage");
                               }
                               else {
                                   increasePrice(vendorId,incPercentage);
                               }
                           }
                       }
                       else
                       {
                           if(txtDecreasePer.getText().toString().equals(""))
                           {
                               txtDecreasePer.setError("Enter Percentage");
                           }
                           else {
                               decPercentage= Integer.parseInt(txtDecreasePer.getText().toString());
                               if(decPercentage<=0){
                                   txtDecreasePer.setError("Invalid Percentage");
                               }
                               else {
                                   decreasePrice(vendorId,decPercentage);
                               }

                           }
                       }
                   }
                   else {
                       txtPassword.setError("Incorrect Password");
                   }
                }
            });
            txtCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    vendorId="";

                    customDialog1.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
        finally {
            customDialog1.show();
        }
    }
    public void increasePrice(String vendorId,int percentage)
    {
        RetrofitClient.getInstance().getMyApi().updateRestaurantProductPrice("update_restaurant_product_price",vendorId,percentage).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                boolean result=response.body().getResult();
                if(result)
                {
                    Toast.makeText(HotelListActivity.this, "Price update successfully", Toast.LENGTH_SHORT).show();
                    customDialog1.dismiss();
                }
                else {
                    Toast.makeText(HotelListActivity.this, "Price not update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Log.e("",t.getMessage());
            }
        });
    }

    public void decreasePrice(String vendorId,int percentage)
    {
        RetrofitClient.getInstance().getMyApi().updateRestaurantProductPrice("decrease_restaurant_product_price",vendorId,percentage).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                boolean result=response.body().getResult();
                if(result)
                {
                    Toast.makeText(HotelListActivity.this, "Price update successfully", Toast.LENGTH_SHORT).show();
                    customDialog1.dismiss();
                }
                else {
                    Toast.makeText(HotelListActivity.this, "Price not update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Log.e("",t.getMessage());
            }
        });
    }
}