package com.example.skyeatadmin.Fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.MainActivity;
import com.example.skyeatadmin.Adapter.OrderAdapter;
import com.example.skyeatadmin.Constant;
import com.example.skyeatadmin.Interface.DrawerLocker;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectDateFragment extends Fragment {

    Button btnFromDate,btnToDate;
    TextView btnSubmit;
    RecyclerView lstData;
    DrawerLayout drawerLayout;
    TextView txtDelivaryBoyName;
    Calendar C;
    MainActivity mainActivity;
    String deliveryBoyID;
    OrderAdapter orderAdapter;

    String fromDate,toDate;
    ArrayList<GAllOrder.Data> orderList =new ArrayList<>();
    ArrayList<GAllOrder.Data> CompleteOrderList=new ArrayList<>();

  //  public static String deliveryBoyID,deliveryBoyName;
    /*
     * onAttach(Context) is not called on pre API 23 versions of Android and onAttach(Activity) is deprecated
     * Use onAttachToContext instead
     */
    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    /*
     * Deprecated on API 23
     * Use onAttachToContext instead
     */
    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            onAttachToContext(activity);
        }
    }

    /*
     * Called when the fragment attaches to the context
     */
    protected void onAttachToContext(Context context) {
        if (context instanceof MainActivity)
            mainActivity = (MainActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_select_date, container, false);
        ((DrawerLocker)getActivity()).setDrawerLocked(true);
      //  ((DrawerLocker)getActivity()).setDrawerLocked(true);

        btnFromDate=view.findViewById(R.id.btnFromDate);
        btnToDate=view.findViewById(R.id.btnToDate);
        btnSubmit=view.findViewById(R.id.btnSubmit);
        lstData=view.findViewById(R.id.lstData);
        txtDelivaryBoyName=view.findViewById(R.id.txtDelivaryBoyName);

        C=Calendar.getInstance();
        int day=C.get(Calendar.DAY_OF_MONTH);
        int month=C.get(Calendar.MONTH);
        int year=C.get(Calendar.YEAR);
        btnFromDate.setText(day+"/"+(month+1)+"/"+year);
        btnToDate.setText(day+"/"+(month+1)+"/"+year);

//        Bundle arguments = getArguments();
//        if (arguments != null) {
//            deliveryBoyID = arguments.getString("deliveryBoyID");
//            deliveryBoyName = arguments.getString("deliveryBoyName");
//            txtDelivaryBoyName.setText(deliveryBoyName);
//        }
        txtDelivaryBoyName.setText(DeliveryBoyList.deliveryBoyName);
        deliveryBoyID=DeliveryBoyList.deliveryBoyId;


        btnFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                DatePickerDialog DPD=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog DPD=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
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
                CompleteOrderList.clear();
                fromDate= Constant.dateToLong(btnFromDate.getText().toString());
                toDate= Constant.dateToLong(btnToDate.getText().toString());

                getAllOrder();
                LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                lstData.setLayoutManager(llm);
            }
        });
        return view;
    }
//        String query = "SELECT * FROM emp WHERE emp_id = ? AND hire_date BETWEEN ? AND ?";
    public void getAllOrder() {
        RetrofitClient.getInstance().getMyApi().getOrder("get_order_flutter")
                .enqueue(new Callback<GAllOrder>() {
                    @Override
                    public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response)
                    {
                        // Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                        orderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                        if(orderList.size()>0)
                        {
                            for(int i=0;i<orderList.size();i++)
                            {
                                if (deliveryBoyID.equals(orderList.get(i).getDelivery_assigned())) {
                                    CompleteOrderList.add(orderList.get(i));
                                }
//
//                                if(orderList.get(i).getStatus().equalsIgnoreCase("Completed"))
//                                {
//                                    CompleteOrderList.add(orderList.get(i));
//                                }
                            }
                            int len= CompleteOrderList.size();
                            Toast.makeText(getContext(), String.valueOf(len), Toast.LENGTH_SHORT).show();
                            orderAdapter = new OrderAdapter(getContext(),CompleteOrderList);
                            lstData.setAdapter(orderAdapter);
                        }
                    }
                    @Override
                    public void onFailure(Call<GAllOrder> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}