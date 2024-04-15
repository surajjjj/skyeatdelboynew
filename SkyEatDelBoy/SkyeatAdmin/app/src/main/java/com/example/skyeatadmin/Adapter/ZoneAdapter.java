package com.example.skyeatadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.R;

import java.util.ArrayList;

public class ZoneAdapter extends RecyclerView.Adapter<ZoneAdapter.MyViewHolder>
{
    Context context;
    public OnClickListener onClickListener;
    ArrayList<Area.Data> zoneList;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener
    {
        void onClick(int position);
    }
    public ZoneAdapter(Context context, ArrayList<Area.Data> zoneList) {
        this.context = context;
        this.zoneList = zoneList;
    }

    @NonNull
    @Override
    public ZoneAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_zone_item,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ZoneAdapter.MyViewHolder holder, int position)
    {
        holder.txtZoneTitle.setText(zoneList.get(position).getTitle());
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
        return zoneList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtZoneTitle;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtZoneTitle=itemView.findViewById(R.id.txtZoneTitle);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
