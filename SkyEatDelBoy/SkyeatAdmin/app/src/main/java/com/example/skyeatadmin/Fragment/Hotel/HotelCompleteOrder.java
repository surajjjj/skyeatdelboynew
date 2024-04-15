package com.example.skyeatadmin.Fragment.Hotel;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.Hotel.HotelMainActivity;
import com.example.skyeatadmin.Adapter.OrderAdapter;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelCompleteOrder extends Fragment {
    TextView txtOrderNotAvlble;
    String vendorID;
    OrderAdapter orderAdapter;
    RecyclerView lstHotelComplete;
    ArrayList<GAllOrder.Data> completeOrderList=new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_hotel_complete_order, container, false);
        txtOrderNotAvlble = view.findViewById(R.id.txtOrderNotAvalable);
        lstHotelComplete = view.findViewById(R.id.lstHotelComplete);
        completeOrderList.clear();
        vendorID= HotelMainActivity.VENDOR_ID;

        getCompleteOrder();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstHotelComplete.setLayoutManager(llm);
        return view;
    }

    private void getCompleteOrder() {
        RetrofitClient.getInstance().getMyApi().getHotelOrders("get_complete_order_hotel_wise",vendorID).enqueue(new Callback<GAllOrder>() {
            @Override
            public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response) {
                completeOrderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                if(completeOrderList.size()>0)
                {
                    txtOrderNotAvlble.setVisibility(View.GONE);
                    orderAdapter = new OrderAdapter(getContext(),completeOrderList);
                    lstHotelComplete.setAdapter(orderAdapter);
                }
                else {
                    txtOrderNotAvlble.setVisibility(View.VISIBLE);
                    lstHotelComplete.setVisibility(View.GONE);
                }
            }
            @Override
            public void onFailure(Call<GAllOrder> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}