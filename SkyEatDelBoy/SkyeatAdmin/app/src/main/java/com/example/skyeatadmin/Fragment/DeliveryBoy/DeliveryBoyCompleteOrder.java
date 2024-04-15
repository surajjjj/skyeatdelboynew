package com.example.skyeatadmin.Fragment.DeliveryBoy;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.example.skyeatadmin.Activity.Hotel.HotelMainActivity;
import com.example.skyeatadmin.Adapter.OrderAdapter;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryBoyCompleteOrder extends Fragment {
    TextView txtOrderNotAvlble;
    String driverID;
    OrderAdapter orderAdapter;
    RecyclerView lstDelCompete;
    ArrayList<GAllOrder.Data> completeOrderList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_delivery_boy_complete_order, container, false);
        txtOrderNotAvlble = view.findViewById(R.id.txtOrderNotAvalable);
        lstDelCompete = view.findViewById(R.id.lstDelCompete);
        completeOrderList.clear();
        driverID= DeliveryBoyMainActivity.DRIVER_ID;
        getCompleteOrder();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstDelCompete.setLayoutManager(llm);
        
        return view;
    }

    private void getCompleteOrder() {
        try {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyOrders("get_complete_order_delivery_boy_wise",driverID).enqueue(new Callback<GAllOrder>() {
                @Override
                public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response) {
                    assert response.body() != null;
                    completeOrderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                    if(completeOrderList.size()>0)
                    {
                        txtOrderNotAvlble.setVisibility(View.GONE);
                        orderAdapter = new OrderAdapter(getContext(),completeOrderList);
                        lstDelCompete.setAdapter(orderAdapter);
                    }
                    else {
                        txtOrderNotAvlble.setVisibility(View.VISIBLE);
                        lstDelCompete.setVisibility(View.GONE);
                    }
                }
                @Override
                public void onFailure(Call<GAllOrder> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}