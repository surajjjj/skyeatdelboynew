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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.HotelListActivity;
import com.example.skyeatadmin.Adapter.ZoneAdapter;
import com.example.skyeatadmin.Constant;
import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Zone extends Fragment
{
    RecyclerView lstZone;
    SharedPreferences sharedPreferences;
    String ZONE_NAME;
    String zone_id,title,zone_name;
    String zoneName;
    ArrayList<Area.Data> zoneList=new ArrayList<>();
    ArrayList<Area.Data> allZoneList=new ArrayList<>();
   // ArrayList<Area.Data> zoneList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_zone, container, false);
        lstZone=view.findViewById(R.id.lstZone);

        allZoneList.clear();
        zoneList.clear();
        getZoneList();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstZone.setLayoutManager(llm);
        return view;
    }
    public void getZoneList()
    {
        RetrofitClient.getInstance().getMyApi().getZone("get_zone_flutter").enqueue(new Callback<Area>() {
            @Override
            public void onResponse(Call<Area> call, Response<Area> response) {
             //   String name=response.body().getData().get(0).getTitle();
                //Toast.makeText(getContext(), name.toString(), Toast.LENGTH_SHORT).show();
                allZoneList= (ArrayList<Area.Data>) response.body().getData();

                sharedPreferences=getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                if(sharedPreferences.contains("zone_name"))
                {
                    ZONE_NAME=sharedPreferences.getString("zone_name",null);
                }

                if(ZONE_NAME.equalsIgnoreCase("all"))
                {
                    ZoneAdapter zoneAdapter=new ZoneAdapter(getContext(),allZoneList);
                    lstZone.setAdapter(zoneAdapter);

                    zoneAdapter.setOnClickListener(new ZoneAdapter.OnClickListener() {
                        @Override
                        public void onClick(int position)
                        {
                            zone_id=allZoneList.get(position).getZone_id();
                            Intent intent=new Intent(getContext(), HotelListActivity.class);
                            intent.putExtra("zone_id",zone_id);
                            startActivity(intent);

                          //  getDeliveryBoy(zone_id);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//                            lstAllDeliveyBoy.setLayoutManager(layoutManager);
                        }
                    });
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
                            for (int j = 0; j < allZoneList.size(); j++)
                            {
                                title=allZoneList.get(j).getTitle();
                                if (title.equals(zoneName))
                                {
                                    zoneList.add(allZoneList.get(j));
                                }
                            }
                        }

                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    ZoneAdapter zoneAdapter=new ZoneAdapter(getContext(),zoneList);
                    lstZone.setAdapter(zoneAdapter);
                    zoneAdapter.setOnClickListener(new ZoneAdapter.OnClickListener() {
                        @Override
                        public void onClick(int position)
                        {
                            zone_id=zoneList.get(position).getZone_id();
                            Intent intent=new Intent(getContext(), HotelListActivity.class);
                            intent.putExtra("zone_id",zone_id);
                            startActivity(intent);

                            //  getDeliveryBoy(zone_id);
//                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
//                            lstAllDeliveyBoy.setLayoutManager(layoutManager);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<Area> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}