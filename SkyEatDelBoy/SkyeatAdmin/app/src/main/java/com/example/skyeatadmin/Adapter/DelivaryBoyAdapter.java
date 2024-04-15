package com.example.skyeatadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.skyeatadmin.Model.DeliveryBoy;
import com.example.skyeatadmin.R;
import java.util.ArrayList;

public class DelivaryBoyAdapter extends RecyclerView.Adapter<DelivaryBoyAdapter.MyViewHolder>
{
    Context context;
    ArrayList<DeliveryBoy.Data> delivaryBoyList;
    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener
    {
        void onClick(int position);
    }
    public DelivaryBoyAdapter(Context context, ArrayList<DeliveryBoy.Data> delivaryBoyList) {
        this.context = context;
        this.delivaryBoyList = delivaryBoyList;
    }

    @NonNull
    @Override
    public DelivaryBoyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.delivaryboyitem_layout,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DelivaryBoyAdapter.MyViewHolder holder, int position) {
        holder.txtDelivaryBoyName.setText(delivaryBoyList.get(position).getName()+" "+delivaryBoyList.get(position).getLast_name());

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
        return delivaryBoyList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView txtDelivaryBoyName;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtDelivaryBoyName=itemView.findViewById(R.id.txtDelivaryBoyName);
            cardView=itemView.findViewById(R.id.cardView);
        }
    }
}
