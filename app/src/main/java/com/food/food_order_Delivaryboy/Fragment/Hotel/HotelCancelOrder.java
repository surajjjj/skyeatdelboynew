package com.food.food_order_Delivaryboy.Fragment.Hotel;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.food.food_order_Delivaryboy.Activity.Hotel.HotelMainActivity;
import com.food.food_order_Delivaryboy.Adapter.OrderAdapter;
import com.food.food_order_Delivaryboy.Model.GAllOrder;
import com.food.food_order_Delivaryboy.R;
import com.food.food_order_Delivaryboy.Retrofit.RetrofitClient;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HotelCancelOrder extends Fragment {
    TextView txtOrderNotAvlble;
    Context context;
    String vendorID;
    OrderAdapter orderAdapter;
    RecyclerView lstHotelCancel;
    ArrayList<GAllOrder.Data> cancelOrderList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_hotel_cancel_order, container, false);
        txtOrderNotAvlble = view.findViewById(R.id.txtOrderNotAvalable);
        lstHotelCancel = view.findViewById(R.id.lstHotelCancel);
        cancelOrderList.clear();
        vendorID= HotelMainActivity.VENDOR_ID;
        getCancelOrder();
        LinearLayoutManager llm=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        lstHotelCancel.setLayoutManager(llm);
        return view;
    }

    private void getCancelOrder() {
        RetrofitClient.getInstance().getMyApi().getHotelOrders("get_cancel_order_hotel_wise",vendorID).enqueue(new Callback<GAllOrder>() {
            @Override
            public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response) {
                cancelOrderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                if(cancelOrderList.size()>0)
                {
                    txtOrderNotAvlble.setVisibility(View.GONE);
                    orderAdapter = new OrderAdapter(getContext(),cancelOrderList);
                    lstHotelCancel.setAdapter(orderAdapter);
                }
                else {
                    txtOrderNotAvlble.setVisibility(View.VISIBLE);
                    lstHotelCancel.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<GAllOrder> call, Throwable t) {
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}