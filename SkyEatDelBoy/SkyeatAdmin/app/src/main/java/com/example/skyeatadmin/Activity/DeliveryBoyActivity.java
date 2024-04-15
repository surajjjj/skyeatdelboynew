package com.example.skyeatadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.example.skyeatadmin.Activity.Hotel.HotelMainActivity;
import com.example.skyeatadmin.Adapter.OrderAdapter;
import com.example.skyeatadmin.Constant;
import com.example.skyeatadmin.Fragment.DeliveryBoyList;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryBoyActivity extends AppCompatActivity
{
  //  UPDATE `tbl_sale` SET `del_boy_earning_status` ='0' WHERE `delivery_assigned`='194';
    Button btnFromDate,btnToDate;
    TextView btnSubmit,txtOrderNotFound;
    RecyclerView lstData;
    DrawerLayout drawerLayout;
    TextView txtDelivaryBoyName;
    Context context;
    Calendar C;
    MainActivity mainActivity;
    String deliveryBoyID,vendorID;
    OrderAdapter orderAdapter;

    String fromDate,toDate;
    ArrayList<GAllOrder.Data> orderList =new ArrayList<>();
    ArrayList<GAllOrder.Data> CompleteOrderList=new ArrayList<>();


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_select_date);

        btnFromDate=findViewById(R.id.btnFromDate);
        btnToDate=findViewById(R.id.btnToDate);
        btnSubmit=findViewById(R.id.btnSubmit);
        txtOrderNotFound=findViewById(R.id.txtOrderNotFound);
        lstData=findViewById(R.id.lstData);
        txtDelivaryBoyName=findViewById(R.id.txtDelivaryBoyName);

        C=Calendar.getInstance();
        int day=C.get(Calendar.DAY_OF_MONTH);
        int month=C.get(Calendar.MONTH);
        int year=C.get(Calendar.YEAR);
        btnFromDate.setText(day+"/"+(month+1)+"/"+year);
        btnToDate.setText(day+"/"+(month+1)+"/"+year);
        if(LoginTypeActivity.isDeliveryBoy)
        {
            //txtDelivaryBoyName.setVisibility(View.VISIBLE);
            txtDelivaryBoyName.setText(DeliveryBoyMainActivity.DRIVER_NAME);
            deliveryBoyID= DeliveryBoyMainActivity.DRIVER_ID;

        }
        else if(LoginTypeActivity.isAdmin)
        {
           // txtDelivaryBoyName.setVisibility(View.VISIBLE);
            txtDelivaryBoyName.setText(DeliveryBoyList.deliveryBoyName);
            deliveryBoyID=DeliveryBoyList.deliveryBoyId;
        }
        else if(LoginTypeActivity.isHotel) {
            vendorID= HotelMainActivity.VENDOR_ID;
            txtDelivaryBoyName.setText(HotelMainActivity.HOTEL_NAME);

        }

        btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog DPD=new DatePickerDialog(DeliveryBoyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnFromDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        //    Toast.makeText(MainActivity.this, "DatePicker...", Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                DPD.getDatePicker().setMaxDate(C.getTimeInMillis());
                DPD.show();
            }
        });

        btnToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog DPD=new DatePickerDialog(DeliveryBoyActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        btnToDate.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                        //    Toast.makeText(MainActivity.this, "DatePicker...", Toast.LENGTH_SHORT).show();
                    }
                }, year, month, day);
                DPD.getDatePicker().setMaxDate(C.getTimeInMillis());
                DPD.show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                fromDate= Constant.dateToLong(btnFromDate.getText().toString());
                toDate= Constant.dateToLong(btnToDate.getText().toString());
                CompleteOrderList.clear();

                if(LoginTypeActivity.isHotel)
                {
                    getHotelOrder();
                }
                else {
                    getAllOrder();
                }
                LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
                lstData.setLayoutManager(llm);
            }
        });

    }

    public void getHotelOrder()
    {
        RetrofitClient.getInstance().getMyApi().getHotelOrder("get_hotel_order_flutter_date_wise",vendorID,fromDate,toDate).enqueue(new Callback<GAllOrder>() {
            @Override
            public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response)
            {
                CompleteOrderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                int len= CompleteOrderList.size();
                if(len==0)
                {
                    txtOrderNotFound.setVisibility(View.VISIBLE);
                    lstData.setVisibility(View.GONE);
                }
                // Toast.makeText(getApplicationContext(), String.valueOf(len), Toast.LENGTH_SHORT).show();
                else
                {
                    txtOrderNotFound.setVisibility(View.GONE);
                    lstData.setVisibility(View.VISIBLE);
                    orderAdapter = new OrderAdapter(getApplicationContext(),CompleteOrderList);
                    lstData.setAdapter(orderAdapter);
                }


            }

            @Override
            public void onFailure(Call<GAllOrder> call, Throwable t) {

            }
        });
    }

    public void getAllOrder() {

        RetrofitClient.getInstance().getMyApi().getDeliveryBoyOrder("get_order_flutter_date_wise",deliveryBoyID,fromDate,toDate).enqueue(new Callback<GAllOrder>() {
            @Override
            public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response) {
                orderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                        if(orderList.size()>0)
                        {
                            for(int i=0;i<orderList.size();i++)
                            {
                                if (orderList.get(i).getStatus().equalsIgnoreCase("Completed")) {
                                    CompleteOrderList.add(orderList.get(i));
                                }
                            }
                            int len= CompleteOrderList.size();
                            if(len==0)
                            {
                                txtOrderNotFound.setVisibility(View.VISIBLE);
                                lstData.setVisibility(View.GONE);
                            }
                           // Toast.makeText(getApplicationContext(), String.valueOf(len), Toast.LENGTH_SHORT).show();
                           else
                            {
                                txtOrderNotFound.setVisibility(View.GONE);
                                lstData.setVisibility(View.VISIBLE);
                                orderAdapter = new OrderAdapter(getApplicationContext(),CompleteOrderList);
                                lstData.setAdapter(orderAdapter);
                            }
                        }
            }

            @Override
            public void onFailure(Call<GAllOrder> call, Throwable t) {

            }
        });
    }
}