package com.example.skyeatadmin.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyeatadmin.Activity.MainActivity;
import com.example.skyeatadmin.Activity.OrderDetailActivity;
import com.example.skyeatadmin.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>
{
    Context context;
    JSONArray orderList;
    String status;
    private Activity parentActivity;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
      public OnClickListener onClickListener;


    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public interface OnClickListener
    {
        void onClick(int position);
    }

    public ItemAdapter(Context context, JSONArray orderList,String status,Activity parentActivity) {
        this.context = context;
        this.orderList = orderList;
        this.status = status;
        this.parentActivity = parentActivity;
    }
    //    public ItemAdapter(Context context, List<GAllOrder.Data> orderList) {
//        this.context = context;
//        this.orderList = orderList;
//    }

    @NonNull
    @Override
    public ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_list,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.MyViewHolder holder, int position) {

        try
        {
            JSONObject object = orderList.getJSONObject(position);
            String foodType=object.getString("foodType");
            if(foodType.equalsIgnoreCase("Veg"))
            {
                holder.imgVeg.setImageResource(R.drawable.ic_veg);
            }
            else {
                holder.imgVeg.setImageResource(R.drawable.ic_nonveg);
            }
            holder.txtItemName.setText(object.getString("product_name"));
            holder.txtQuantity.setText(object.getString("qty"));
            holder.txtPrice.setText(object.getString("price"));

//            if(status.equalsIgnoreCase("Placed"))
//            {
//                holder.imgDelete.setVisibility(View.VISIBLE);
//            } else if (status.equalsIgnoreCase("Shipped"))
//            {
//                holder.imgDelete.setVisibility(View.VISIBLE);
//            } else {
//                holder.imgDelete.setVisibility(View.GONE);
//            }
        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
            throw new RuntimeException(e);
        }
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onClickListener != null)
                {
                    onClickListener.onClick(position);
                }
//                if(orderList.length()==1)
//                {
//                    Toast.makeText(context, "this item can not delete...", Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    deleteAlertDialog(position);
//                }
            }
        });
    }

    public void deleteAlertDialog(int possition) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentActivity);
        builder.setTitle("Alert Dialog!!!");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

//               // dialog.dismiss();
//                deleteItem();



            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog and continue with the activity
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteItem()
    {

    }
    @Override
    public int getItemCount() {
        return orderList.length();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName,txtQuantity,txtPrice;
        ImageView imgDelete,imgVeg;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtItemName=itemView.findViewById(R.id.txtItemName);
            imgDelete=itemView.findViewById(R.id.imgDelete);
            txtQuantity=itemView.findViewById(R.id.txtQuantity);
            imgVeg=itemView.findViewById(R.id.imgVeg);
        }
    }
}
