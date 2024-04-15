package com.example.skyeatadmin.Fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.MainActivity;
import com.example.skyeatadmin.Activity.OrderDetailActivity;
import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.Adapter.OrderAdapter;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;
import com.example.skyeatadmin.Utils.CustPrograssbar;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CompleteOrder extends Fragment {
    RecyclerView lstData;
    CustPrograssbar custPrograssbar;
    //List<Results> results=new ArrayList<>();
    // List<Results.Data> results=new ArrayList<>();
    ArrayList<GAllOrder.Data> orderList =new ArrayList<>();
    ArrayList<GAllOrder.Data> CompleteOrderList=new ArrayList<>();
    Spinner spnrVillage;
    //MyAdapter myAdapter;
    OrderAdapter orderAdapter;
    public static boolean isCompleteOrder=false;

    SharedPreferences sharedPreferences;
    Context context;
    String ZONE_NAME;
    String zoneName,zoneId;
    ArrayList<Area.Data> allZoneList=new ArrayList<>();
   // public static int mposition;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_complete_order, container, false);
        lstData = view.findViewById(R.id.lstCompleteData);
        custPrograssbar = new CustPrograssbar();
        ZONE_NAME="";
        CompleteOrderList.clear();
        orderList.clear();
        getAllOrder();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstData.setLayoutManager(llm);
        isCompleteOrder=true;
        return view;
    }
    public void getAllOrder() {
        custPrograssbar.prograssCreate(getContext());
        RetrofitClient.getInstance().getMyApi().getOrder("get_order_flutter")
                .enqueue(new Callback<GAllOrder>() {
                    @Override
                    public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response)
                    {
                       // Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                        orderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                        if(orderList.size()>0)
                        {
//                            sharedPreferences=context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                            if(sharedPreferences.contains("zone_name"))
//                            {
//                                ZONE_NAME=sharedPreferences.getString("zone_name",null);
//                            }
                            if(MainActivity.ZONE_NAME.equalsIgnoreCase("all"))
                            {
                                for(int i=0;i<orderList.size();i++)
                                {
                                    if(orderList.get(i).getStatus().equalsIgnoreCase("Completed"))
                                    {
                                        CompleteOrderList.add(orderList.get(i));
                                    }
                                }
                                //int len= CompleteOrderList.size();
                                orderAdapter = new OrderAdapter(getContext(),CompleteOrderList);
                                lstData.setAdapter(orderAdapter);

                            }
                            else
                            {
                                String[] stringArray = MainActivity.ZONE_NAME.split(",");
                                ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(stringArray));
                                try
                                {
                                    for(int i=0;i<arrayList.size();i++)
                                    {
                                        zoneName=arrayList.get(i);
                                        getZoneOrder(zoneName);
                                    }

                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }


//                            orderAdapter.setOnClickListener(new OrderAdapter.OnClickListener() {
//                                @Override
//                                public void onClick(int position) {
//                                    NewOrder.mposition=position;
//                                    Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();
//                                    Intent intent=new Intent(getContext(), OrderDetailActivity.class);
//                                    startActivity(intent);
//                                }
//                            });
                        }
                    }
                    @Override
                    public void onFailure(Call<GAllOrder> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        custPrograssbar.closePrograssBar();
    }
    public void getZoneOrder(String zoneName) {
        RetrofitClient.getInstance().getMyApi().getZone("get_zone_flutter").enqueue(new Callback<Area>() {
            @Override
            public void onResponse(Call<Area> call, Response<Area> response) {
                allZoneList= (ArrayList<Area.Data>) response.body().getData();
                for (int i=0;i<allZoneList.size();i++)
                {
                    if(zoneName.equals(allZoneList.get(i).getTitle()))
                    {
                        zoneId=allZoneList.get(i).getZone_id();
                    }
                }
                for(int j=0;j<orderList.size();j++)
                {
                    if(orderList.get(j).getStatus().equalsIgnoreCase("Completed") && zoneId.equals(orderList.get(j).getZone_id()))
                    {
                        CompleteOrderList.add(orderList.get(j));
                    }
                }
                orderAdapter = new OrderAdapter(getContext(),CompleteOrderList);
                lstData.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<Area> call, Throwable t) {

            }
        });
    }
}