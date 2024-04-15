package com.example.skyeatadmin.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyOrderDetailActivity;
import com.example.skyeatadmin.Activity.LoginTypeActivity;
import com.example.skyeatadmin.Activity.OrderDetailActivity;
import com.example.skyeatadmin.Constant;
import com.example.skyeatadmin.Fragment.NewOrder;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder>
{
    Context context;
    public String Address,phoneNo,userName,payment_type;
    ArrayList<GAllOrder.Data> orderList;
    //public OnClickListener onClickListener;
    public static String coustmAddress,saleId;



    //JSONObject json;

//    public void setOnClickListener(OnClickListener onClickListener) {
//        this.onClickListener = onClickListener;
//    }
//
//    public interface OnClickListener
//    {
//        void onClick(int position);
//    }

    public OrderAdapter(Context context, ArrayList<GAllOrder.Data> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_order_row,null);
        MyViewHolder myViewHolder = new MyViewHolder(view);

        return myViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.txtSaleID.setText("SaleID #"+orderList.get(position).getSale_id());
        holder.txtDate.setText(Constant.longToDate(orderList.get(position).delivary_datetime));
        Address=orderList.get(position).getShipping_address();

        payment_type=orderList.get(position).getPayment_type();
        holder.txtStatus.setText(orderList.get(position).getStatus());
        holder.txt_city.setText("");
        int backgroundColor;
        if (payment_type.equals("online")) {
            holder.linOnlinePayment.setBackgroundColor(ContextCompat.getColor(context, R.color.colorGreen));
        }
        else {
            holder.linOnlinePayment.setBackgroundColor(Color.WHITE);
        }

        //holder.linOnlinePayment.setBackgroundColor(backgroundColor);
        try
        {
            JSONObject json = new JSONObject(Address);
            holder.txtAddress.setText(json.getString("addressSelect"));


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(context, String.valueOf(position), Toast.LENGTH_SHORT).show();
                coustmAddress=holder.txtAddress.getText().toString();
                String saleId=orderList.get(position).getSale_id();
                String coustmAddress=orderList.get(position).getShipping_address();
                String status=orderList.get(position).getStatus();
                String deliveryState=orderList.get(position).getDelivery_state();
                try
                {
                    JSONObject json1 = new JSONObject(coustmAddress);
                    userName= json1.getString("username");
                    phoneNo= json1.getString("phone");

                } catch (JSONException e)
                {
                    throw new RuntimeException(e);
                }
                String driverId=orderList.get(position).getDelivery_assigned();
                String productDetails=orderList.get(position).getProduct_details();
                String itemTotal=orderList.get(position).getItem_total();
                String zoneId=orderList.get(position).getZone_id();
                String paymentType=orderList.get(position).getPayment_type();
                String vendorId=orderList.get(position).getVendor();
                Double allTotal= Double.valueOf(orderList.get(position).getGrand_totals());

                if(LoginTypeActivity.isDeliveryBoy)
                {
                    Intent intent=new Intent(context, DeliveryBoyOrderDetailActivity.class);
                    intent.putExtra("status",status);
                    intent.putExtra("delivery_state",deliveryState);
                    intent.putExtra("saleId",saleId);
                    intent.putExtra("zoneId",zoneId);
                    intent.putExtra("vendorId",vendorId);
                    // intent.putExtra("coustmAddress",coustmAddress);
                    intent.putExtra("userName",userName);
                    intent.putExtra("phoneNo",phoneNo);
                    intent.putExtra("driverId",driverId);
                    intent.putExtra("productDetails",productDetails);
                    intent.putExtra("itemTotal",itemTotal);
                    intent.putExtra("paymentType",paymentType);
                    intent.putExtra("allTotal",String.valueOf(allTotal));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    OrderAdapter.this.context.startActivity(intent);
                }
                else {
                    Intent intent=new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("status",status);
                    intent.putExtra("saleId",saleId);
                    intent.putExtra("zoneId",zoneId);
                    intent.putExtra("vendorId",vendorId);
                    // intent.putExtra("coustmAddress",coustmAddress);
                    intent.putExtra("userName",userName);
                    intent.putExtra("phoneNo",phoneNo);
                    intent.putExtra("driverId",driverId);
                    intent.putExtra("productDetails",productDetails);
                    intent.putExtra("itemTotal",itemTotal);
                    intent.putExtra("paymentType",paymentType);
                    intent.putExtra("allTotal",String.valueOf(allTotal));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    OrderAdapter.this.context.startActivity(intent);
                }
//                if (onClickListener != null) {
//                    onClickListener.onClick(position);
//                }
                //[{"productId":"1698","product_name":"Samosa ","price":"36.0974","strike":"36.0974","qty":2,"userId":"872","cartId":null,"shopId":null,"image":"https:\/\/skynetitsolutions.com\/skyeat_flutter\/uploads\/restaurantproduct_image\/restaurantproduct_1698.jpg","tax":0,"discount":"0","packingCharge":0,"totalSteps":0,"multipleVariant":false,"variantId":null,"variantName":"Samosa ","foodType":"Veg","addonGroup":[],"variantGroup":[],"combinationVariant":[],"combinationAllVariant":[]},{"productId":"1729","product_name":"Matka Kulfi","price":"58.9591","strike":"58.9591","qty":1,"userId":"872","cartId":null,"shopId":null,"image":"https:\/\/skynetitsolutions.com\/skyeat_flutter\/uploads\/restaurantproduct_image\/restaurantproduct_1729.jpg","tax":0,"discount":"0","packingCharge":0,"totalSteps":0,"multipleVariant":false,"variantId":null,"variantName":"Matka Kulfi","foodType":"Veg","addonGroup":[],"variantGroup":[],"combinationVariant":[],"combinationAllVariant":[]}]
            }
        });

    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
       public  TextView txtSaleID,txtAddress,txtDate,txtStatus,txt_city;
       public CardView cardView;
        LinearLayout linOnlinePayment;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            txtSaleID=itemView.findViewById(R.id.txtSaleID);
            txtAddress=itemView.findViewById(R.id.txtAddress);
            txtDate=itemView.findViewById(R.id.txtDate);
            txt_city=itemView.findViewById(R.id.txt_city);
            cardView=itemView.findViewById(R.id.cardView);
            txtStatus=itemView.findViewById(R.id.txtStatus);
            linOnlinePayment=itemView.findViewById(R.id.linOnlinePayment);
        }
    }


}
