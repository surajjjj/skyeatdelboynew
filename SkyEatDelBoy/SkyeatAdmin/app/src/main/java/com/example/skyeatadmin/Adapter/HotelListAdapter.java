package com.example.skyeatadmin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.example.skyeatadmin.CustomDialog;
import com.example.skyeatadmin.Model.Hotel;
import com.example.skyeatadmin.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HotelListAdapter extends RecyclerView.Adapter<HotelListAdapter.MyViewHolder>
{
    Context context;
    ArrayList<Hotel.Data> hotelList=new ArrayList<>();

    int pos;
    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener
    {
        void onClick(int position);
    }


    public HotelListAdapter(Context context, ArrayList<Hotel.Data> hotelList) {
        this.context = context;
        this.hotelList = hotelList;
    }

    @NonNull
    @Override
    public HotelListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_hotel_list,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HotelListAdapter.MyViewHolder holder, int position)
    {
        //pos=position;
        holder.txtHotelName.setText(hotelList.get(position).getName());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null) {
                    onClickListener.onClick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return hotelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        CardView cardView;
        TextView txtHotelName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHotelName=itemView.findViewById(R.id.txtHotelName);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }

}
