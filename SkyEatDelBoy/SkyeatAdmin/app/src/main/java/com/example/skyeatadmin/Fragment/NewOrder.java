package com.example.skyeatadmin.Fragment;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.MainActivity;
import com.example.skyeatadmin.Activity.OrderDetailActivity;
import com.example.skyeatadmin.Adapter.DelivaryBoyAdapter;
import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.Model.DeliveryBoy;
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
public class NewOrder extends Fragment {

    public static boolean isNewOrder=false;
    CustPrograssbar custPrograssbar;
    TextView txtOrderNotAvlble;
    Context context;

    //private boolean isDataLoaded = false;
    int len;
    RecyclerView lstData,lstDeliveryBoy;
    //List<Results> results=new ArrayList<>();
    // List<Results.Data> results=new ArrayList<>();
    ArrayList<GAllOrder.Data> orderList =new ArrayList<>();
    ArrayList<GAllOrder.Data> NewOrderList=new ArrayList<>();

    SharedPreferences sharedPreferences;
    String ZONE_NAME;
    String zoneName,zoneId;
    ArrayList<Area.Data> allZoneList=new ArrayList<>();
    OrderAdapter orderAdapter;


    public static int mposition;
   // @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_new_order, container, false);
        lstData = view.findViewById(R.id.lstData);
        txtOrderNotAvlble = view.findViewById(R.id.txtOrderNotAvalable);
        custPrograssbar = new CustPrograssbar();

        NewOrderList.clear();
        getAllOrder();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstData.setLayoutManager(llm);
        return view;
    }


    public void getAllOrder() {

        custPrograssbar.prograssCreate(getContext());
        RetrofitClient.getInstance().getMyApi().getOrder("get_order_flutter")
                .enqueue(new Callback<GAllOrder>() {
                    @Override
                    public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response)
                    {

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
                                try {
                                    for(int i=0;i<orderList.size();i++)
                                    {
                                        if(orderList.get(i).getStatus().equalsIgnoreCase("Placed") || orderList.get(i).getStatus().equalsIgnoreCase("Shipped"))
                                        {
                                            NewOrderList.add(orderList.get(i));
                                        }
                                    }
                                }
                                catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                len= NewOrderList.size();
                                if(len>0)
                                {
                                    txtOrderNotAvlble.setVisibility(View.GONE);
                                    orderAdapter = new OrderAdapter(getContext(),NewOrderList);
                                    lstData.setAdapter(orderAdapter);
                                }
                                else
                                {
                                    txtOrderNotAvlble.setVisibility(View.VISIBLE);
                                }
                            }
                            else {
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
                    if((orderList.get(j).getStatus().equalsIgnoreCase("Placed")|| orderList.get(j).getStatus().equalsIgnoreCase("Shipped")) && zoneId.equals(orderList.get(j).getZone_id()))
                    {
                        NewOrderList.add(orderList.get(j));
                    }
                }
                len= NewOrderList.size();
                if(len>0)
                {
                    txtOrderNotAvlble.setVisibility(View.GONE);
                    orderAdapter = new OrderAdapter(getContext(),NewOrderList);
                    lstData.setAdapter(orderAdapter);
                }
                else
                {
                    txtOrderNotAvlble.setVisibility(View.VISIBLE);
                }
//                orderAdapter = new OrderAdapter(getContext(),NewOrderList);
//                lstData.setAdapter(orderAdapter);
            }

            @Override
            public void onFailure(Call<Area> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}