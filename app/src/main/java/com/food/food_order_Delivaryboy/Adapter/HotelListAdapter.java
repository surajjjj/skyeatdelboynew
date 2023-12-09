package com.food.food_order_Delivaryboy.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.food.food_order_Delivaryboy.Model.Hotel;
import com.food.food_order_Delivaryboy.R;

import java.util.ArrayList;

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
