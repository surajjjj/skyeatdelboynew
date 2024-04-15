package com.example.skyeatadmin.Fragment;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.CollectCash;
import com.example.skyeatadmin.Activity.DeliveryBoyActivity;
import com.example.skyeatadmin.Activity.MainActivity;
import com.example.skyeatadmin.Adapter.DelivaryBoyAdapter;
import com.example.skyeatadmin.Adapter.ZoneAdapter;
import com.example.skyeatadmin.Constant;
import com.example.skyeatadmin.CustomDialog;
import com.example.skyeatadmin.Interface.DrawerLocker;
import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.Model.DeliveryBoy;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class DeliveryBoyList extends Fragment
{
    Context context;
    public String ZONE_NAME;
    RecyclerView lstZone,lstAllDeliveyBoy;
    String zone_id,title,zone_name;
    String zoneName;
    MainActivity mainActivity;
    ArrayList<DeliveryBoy.Data> deliveryBoyList = new ArrayList<>();
    ArrayList<Area.Data> allZoneList=new ArrayList<>();
    ArrayList<Area.Data> zoneList=new ArrayList<>();
    ArrayList<DeliveryBoy.Data> zoneDeliveryBoy = new ArrayList<>();
     public static String deliveryBoyId,deliveryBoyName;

    SharedPreferences sharedPreferences;
//
//    /*
//     * onAttach(Context) is not called on pre API 23 versions of Android and onAttach(Activity) is deprecated
//     * Use onAttachToContext instead
//     */
//    @TargetApi(23)
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        onAttachToContext(context);
//    }
//
//    /*
//     * Deprecated on API 23
//     * Use onAttachToContext instead
//     */
//    @SuppressWarnings("deprecation")
//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
//            onAttachToContext(activity);
//        }
//    }
//
//    /*
//     * Called when the fragment attaches to the context
//     */
//    protected void onAttachToContext(Context context) {
//        if (context instanceof MainActivity)
//            mainActivity = (MainActivity) context;
//    }
//

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view= inflater.inflate(R.layout.fragment_delivery_boy, container, false);
        ((DrawerLocker)getActivity()).setDrawerLocked(false);
        lstZone=view.findViewById(R.id.lstZone);

        deliveryBoyList.clear();
        zoneList.clear();
       // getDeliveryBoy();
        getZoneList();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        lstZone.setLayoutManager(layoutManager);
        return view;
    }
//
//    @Override
//    public void onResume()
//    {
//
//        super.onResume();
//        ((DrawerLocker)getActivity()).setDrawerLocked(false);
//
//    }

    @SuppressLint("ResourceAsColor")
    public void getDeliveryBoy(String zoneId)
    {
        CustomDialog customDialog = new CustomDialog(getContext());
        customDialog.setContentView(R.layout.popup_delivery_boy);
        lstAllDeliveyBoy=customDialog.findViewById(R.id.lstAllDeliveyBoy);
        Window window = customDialog.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(R.color.purple_500);
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());

            // Set the desired width and height of the dialog
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(layoutParams);
        }


        RetrofitClient.getInstance().getMyApi().getDeliveryBoy("get_del_boy_flutter").enqueue(new Callback<DeliveryBoy>() {
            @Override
            public void onResponse(Call<DeliveryBoy> call, Response<DeliveryBoy> response)
            {
                zoneDeliveryBoy.clear();
                deliveryBoyList= (ArrayList<DeliveryBoy.Data>) response.body().getData();
                if(deliveryBoyList.size()>0)
                {
                    for(int i=0;i<deliveryBoyList.size();i++)
                    {
                        if(deliveryBoyList.get(i).getZone_id().equals(zoneId))
                        {
                            zoneDeliveryBoy.add(deliveryBoyList.get(i));
                        }
                    }
                    if(zoneDeliveryBoy.size()==0)
                    {
                        Toast.makeText(getContext(),"Delivery Boy not avalable   ",Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        DelivaryBoyAdapter delivaryBoyAdapter=new DelivaryBoyAdapter(getContext(),zoneDeliveryBoy);
                        lstAllDeliveyBoy.setAdapter(delivaryBoyAdapter);
                        if(!customDialog.isShowing()){
                            customDialog.show();
                        }
                        delivaryBoyAdapter.setOnClickListener(new DelivaryBoyAdapter.OnClickListener() {
                            @Override
                            public void onClick(int position)
                            {
                                //Toast.makeText(getContext(), String.valueOf(position), Toast.LENGTH_SHORT).show();

                                 deliveryBoyId=zoneDeliveryBoy.get(position).getDriver_id();
                                 deliveryBoyName=zoneDeliveryBoy.get(position).getName() +" "+zoneDeliveryBoy.get(position).getLast_name();

                                if(MainActivity.isCollectCash)
                                {
                                    customDialog.hide();
                                    Intent i = new Intent(getContext(), CollectCash.class);
                                    startActivity(i);
                                }
                                else {
                                    customDialog.hide();
                                    Intent i = new Intent(getContext(), DeliveryBoyActivity.class);
                                    startActivity(i);
                                }
                            }
                        });
                    }
                }
            }
            @Override
            public void onFailure(Call<DeliveryBoy> call, Throwable t)
            {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getZoneList() {
        RetrofitClient.getInstance().getMyApi().getZone("get_zone_flutter").enqueue(new Callback<Area>() {
            @Override
            public void onResponse(Call<Area> call, Response<Area> response) {
                //   String name=response.body().getData().get(0).getTitle();
                //Toast.makeText(getContext(), name.toString(), Toast.LENGTH_SHORT).show();
                sharedPreferences=getContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                if(sharedPreferences.contains("zone_name"))
                {
                    ZONE_NAME=sharedPreferences.getString("zone_name",null);
                }
                allZoneList= (ArrayList<Area.Data>) response.body().getData();


                if(ZONE_NAME.equalsIgnoreCase("all"))
                {
                    ZoneAdapter zoneAdapter=new ZoneAdapter(getContext(),allZoneList);
                    lstZone.setAdapter(zoneAdapter);

                    zoneAdapter.setOnClickListener(new ZoneAdapter.OnClickListener() {
                        @Override
                        public void onClick(int position)
                        {
                            zone_id=allZoneList.get(position).getZone_id();
                            getDeliveryBoy(zone_id);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            lstAllDeliveyBoy.setLayoutManager(layoutManager);
                        }
                    });
                }
                else
                {
                    String[] stringArray = ZONE_NAME.split(",");
                    ArrayList<String> arrayList = new ArrayList<>(Arrays.asList(stringArray));
                    try
                    {
//                        JSONArray jsonArray = new JSONArray(jsonData);
//                        JSONObject zone;
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
                        Toast.makeText(mainActivity, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    ZoneAdapter zoneAdapter=new ZoneAdapter(getContext(),zoneList);
                    lstZone.setAdapter(zoneAdapter);
                    zoneAdapter.setOnClickListener(new ZoneAdapter.OnClickListener() {
                        @Override
                        public void onClick(int position)
                        {
                            zone_id=zoneList.get(position).getZone_id();
                            getDeliveryBoy(zone_id);
                            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                            lstAllDeliveyBoy.setLayoutManager(layoutManager);
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

//    public void nextFragment()
//    {
////        SelectDateFragment selectDateFragment = new SelectDateFragment();
////        Bundle bundle = new Bundle();
////        bundle.putString("deliveryBoyId",deliveryBoyId);
////        bundle.putString("deliveryBoyName",deliveryBoyName);
////        selectDateFragment.setArguments(bundle);
//
//        FragmentManager fragmentManager = mainActivity.getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.fragment_container, new SelectDateFragment());
//        fragmentTransaction.commit();
//        fragmentTransaction.addToBackStack("DeliveryLis");
//    }

}