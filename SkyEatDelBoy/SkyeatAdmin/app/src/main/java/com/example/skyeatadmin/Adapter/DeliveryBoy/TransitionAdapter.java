package com.example.skyeatadmin.Adapter.DeliveryBoy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyeatadmin.Constant;
import com.example.skyeatadmin.Model.Transition;
import com.example.skyeatadmin.R;

import java.util.ArrayList;
import java.util.Objects;


public class TransitionAdapter extends RecyclerView.Adapter<TransitionAdapter.MyViewHolder>
{
    Context context;
    String status;
    ArrayList<Transition.Data> transitionList;

    public TransitionAdapter(Context context, ArrayList<Transition.Data> transitionList) {
        this.context = context;
        this.transitionList = transitionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transition_layout,null);
        TransitionAdapter.MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position)
    {
        holder.txtSrNo.setText(String.valueOf(position+1));
        holder.txtDelivaryBoyName.setText(transitionList.get(position).getDriver_name());
        holder.txtDate.setText(Constant.mlongToDate(transitionList.get(position).getDate()));
        holder.txtAmount.setText(transitionList.get(position).getPaymet_amount());
        status=transitionList.get(position).getPayment_status();
        if(Objects.equals(status, "0"))
        {
            holder.imgStatus.setImageResource(R.drawable.baseline_hourglass_top_24);
        }
        else {
            holder.imgStatus.setImageResource(R.drawable.baseline_verified_24);
        }
    }

    @Override
    public int getItemCount() {
        return transitionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView txtSrNo,txtDelivaryBoyName,txtDate,txtAmount;
        ImageView imgStatus;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            txtSrNo=itemView.findViewById(R.id.txtSrNo);
            txtDelivaryBoyName=itemView.findViewById(R.id.txtDelivaryBoyName);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtAmount=itemView.findViewById(R.id.txtAmount);
            imgStatus=itemView.findViewById(R.id.imgStatus);
        }
    }
}
