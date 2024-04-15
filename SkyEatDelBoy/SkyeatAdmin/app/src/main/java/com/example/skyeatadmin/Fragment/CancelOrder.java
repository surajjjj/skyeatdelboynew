package com.example.skyeatadmin.Fragment;

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
import android.widget.Toast;

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

public class CancelOrder extends Fragment {

    RecyclerView lstCancelData;
    ArrayList<GAllOrder.Data> orderList =new ArrayList<>();
    CustPrograssbar custPrograssbar;
    ArrayList<GAllOrder.Data> CancelOrderList=new ArrayList<>();
    OrderAdapter orderAdapter;
    SharedPreferences sharedPreferences;
    String ZONE_NAME;
    String zoneName,zoneId;
    ArrayList<Area.Data> allZoneList=new ArrayList<>();
   // public  boolean isCancelOrder=false;

    //public static int mposition;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_cancel_order, container, false);
        lstCancelData = view.findViewById(R.id.lstCancelData);
        custPrograssbar = new CustPrograssbar();
        CancelOrderList.clear();
        ZONE_NAME="";
        getAllOrder();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstCancelData.setLayoutManager(llm);
        return view;
    }

    public void getAllOrder() {

        custPrograssbar.prograssCreate(getContext());
        RetrofitClient.getInstance().getMyApi().getOrder("get_order_flutter")
                .enqueue(new Callback<GAllOrder>() {
                    @Override
                    public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response)
                    {
                        sharedPreferences=getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                        if(sharedPreferences.contains("zone_name"))
                        {
                            ZONE_NAME=sharedPreferences.getString("zone_name",null);
                        }
                        orderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                        if(orderList.size()>0)
                        {
                            if(ZONE_NAME.equalsIgnoreCase("all"))
                            {
                                for(int i=0;i<orderList.size();i++)
                                {
                                    if(orderList.get(i).getStatus().equalsIgnoreCase("Cancelled"))
                                    {
                                        CancelOrderList.add(orderList.get(i));
                                    }
                                }
                                int len= CancelOrderList.size();
                                orderAdapter = new OrderAdapter(getContext(),CancelOrderList);
                                lstCancelData.setAdapter(orderAdapter);
                            }
                            else
                            {
                                String[] stringArray = ZONE_NAME.split(",");
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
                    if(orderList.get(j).getStatus().equalsIgnoreCase("Cancelled") && zoneId.equals(orderList.get(j).getZone_id()))
                    {
                        CancelOrderList.add(orderList.get(j));
                    }
                }
                orderAdapter = new OrderAdapter(getContext(),CancelOrderList);
                lstCancelData.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<Area> call, Throwable t) {

            }
        });
    }
}