package com.example.skyeatadmin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyeatadmin.Model.Login;
import com.example.skyeatadmin.R;

import java.util.ArrayList;


public class ManagerListAdapter extends RecyclerView.Adapter<ManagerListAdapter.MyViewHolder>
{
    Context context;
    ArrayList<Login.Data> managerList;
    public OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener
    {
        void onClick(int position);
    }

    public ManagerListAdapter(Context context, ArrayList<Login.Data> managerList) {
        this.context = context;
        this.managerList = managerList;
    }

    @NonNull
    @Override
    public ManagerListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.delivaryboyitem_layout,null);
        ManagerListAdapter.MyViewHolder myViewHolder = new ManagerListAdapter.MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ManagerListAdapter.MyViewHolder holder, int position) {
        holder.txtDelivaryBoyName.setText(managerList.get(position).getName());

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
        return managerList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
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
