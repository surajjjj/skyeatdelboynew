package com.example.skyeatadmin.Activity;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.skyeatadmin.Adapter.DelivaryBoyAdapter;
import com.example.skyeatadmin.Adapter.HotelListAdapter;
import com.example.skyeatadmin.Adapter.ItemAdapter;
import com.example.skyeatadmin.Adapter.OrderAdapter;
import com.example.skyeatadmin.Adapter.ZoneAdapter;
import com.example.skyeatadmin.CustomDialog;
import com.example.skyeatadmin.Fragment.AllOrder;
import com.example.skyeatadmin.Fragment.NewOrder;
import com.example.skyeatadmin.Model.DeliveryBoy;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.Model.Hotel;
import com.example.skyeatadmin.Model.Sample;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class OrderDetailActivity extends AppCompatActivity
{
    RecyclerView lstItem,lstDeliveryBoy;
    CustomDialog customDialog;
    TextView txtReason,txtManagerName,txtSubmit,txtCancel,txtItemTotal,txtDeliveryFee,txtStoreCharge,txtTax;
    String managerName,reason;
    LinearLayout linLayout,linLayoutCancel;
    ItemAdapter itemAdapter;
    TextView txtCancelOrder;
    ImageView imgUserPhoneNo,imgDelBoyPhone,imgHotelPhone,imgBack;
    TextView btnAccept,btnReject;
    String delivery_assigned;
    boolean isCancelOrder=false;
    JSONArray array = null;
    JSONObject itemTotalJsonArray;
    String gtotal;
    TextView txtDBoyName,txtHotelName,txtOrderStatus;
    public boolean isAcceptOrder=false;

    TextView txtAdd, txtSaleId,txtUserName,txtSaleCode,txtBuyer,txtPaymentType,txtAllTotal,txtPaymentStatus,txtDeliveryState;
    List<GAllOrder.Data> orderList =new ArrayList<>();

    ArrayList<DeliveryBoy.Data> deliveryBoyList = new ArrayList<>();
    ArrayList<DeliveryBoy.Data> zoneDeliveryBoy = new ArrayList<>();
    ArrayList<Hotel.Data> allHotelList=new ArrayList<>();
    String status,saleId,zoneId,driverId,vendorId,phoneNo,delBoyPhoneNo,hotelPhoneNo,userName,itemTotal,productDetails,allTotal,paymentType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            status=bundle.getString("status");
            saleId=bundle.getString("saleId");
            zoneId=bundle.getString("zoneId");
            driverId=bundle.getString("driverId");
            vendorId=bundle.getString("vendorId");
            //coustmAddress=bundle.getString("co6ustmAddress");
            userName=bundle.getString("userName");
            phoneNo=bundle.getString("phoneNo");
            productDetails=bundle.getString("productDetails");
            itemTotal=bundle.getString("itemTotal");
            allTotal=bundle.getString("allTotal");
            //allTotal=bundle.getString("allTotal");
            paymentType=bundle.getString("paymentType");
        }

        initialiseView();
    }
    public void initialiseView()
    {
        txtPaymentType=findViewById(R.id.txtPaymentType);
        txtAllTotal=findViewById(R.id.txtAllTotal);
        txtAdd=findViewById(R.id.txtAdd);
        txtSaleId=findViewById(R.id.txtSaleId);
        lstItem=findViewById(R.id.lstItem);
        txtUserName=findViewById(R.id.txtUserName);
        imgUserPhoneNo=findViewById(R.id.imgUserPhoneNo);
        imgDelBoyPhone=findViewById(R.id.imgDelBoyPhone);
        imgHotelPhone=findViewById(R.id.imgHotelPhone);
        txtCancelOrder=findViewById(R.id.txtCancelOrder);
        txtItemTotal=findViewById(R.id.txtItemTotal);
        txtDeliveryFee=findViewById(R.id.txtDeliveryFee);
        txtStoreCharge=findViewById(R.id.txtStoreCharge);
        txtTax=findViewById(R.id.txtTax);
        txtDBoyName=findViewById(R.id.txtDBoyName);
        txtHotelName=findViewById(R.id.txtHotelName);
        txtOrderStatus=findViewById(R.id.txtOrderStatus);
        imgBack=findViewById(R.id.imgBack);

        linLayout=findViewById(R.id.linLayout);
        linLayoutCancel=findViewById(R.id.linLayoutCancel);
        btnAccept=findViewById(R.id.btnAccept);
        btnReject=findViewById(R.id.btnReject);

        if(status.equalsIgnoreCase("Placed"))
        {
            linLayoutCancel.setVisibility(View.GONE);
            linLayout.setVisibility(View.VISIBLE);
        } else if (status.equalsIgnoreCase("Shipped"))
        {
            linLayout.setVisibility(View.GONE);
            linLayoutCancel.setVisibility(View.VISIBLE);
        } else
        {
            linLayout.setVisibility(View.GONE);
            linLayoutCancel.setVisibility(View.GONE);
        }

       // getOderDetails();
        getDeliveryBoy();
        getHotel();
        setData();
        LinearLayoutManager llm=new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        lstItem.setLayoutManager(llm);

        imgUserPhoneNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(phoneNo, ""))
                {
                    makePhoneCall(phoneNo);
                    //Toast.makeText(OrderDetailActivity.this, phoneNo, Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgDelBoyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Objects.equals(delBoyPhoneNo, ""))
                {
                    makePhoneCall(delBoyPhoneNo);
                    //Toast.makeText(OrderDetailActivity.this, delBoyPhoneNo, Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgHotelPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makePhoneCall(hotelPhoneNo);
                //Toast.makeText(OrderDetailActivity.this, hotelPhoneNo, Toast.LENGTH_SHORT).show();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

                // Toast.makeText(getContext(), "Accept", Toast.LENGTH_SHORT).show();
                isAcceptOrder=true;
                openPopup();
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rejectOrder();
             //   cancelOrderPopPup();
              //  Toast.makeText(getApplicationContext(), "Reject", Toast.LENGTH_SHORT).show();


            }
        });
        txtCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelOrderPopPup();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //getSupportFragmentManager().popBackStack();
                onBackPressed();
            }
        });
    }
    public void setData() {
        txtSaleId.setText("SaleID #"+saleId);
        txtAdd.setText(OrderAdapter.coustmAddress);
        txtOrderStatus.setText(status);
        txtUserName.setText(userName);
        try
        {
            array = new JSONArray(productDetails);


        } catch (JSONException e)
        {
            throw new RuntimeException(e);
        }
        itemAdapter=new ItemAdapter(getApplicationContext(), array,status,OrderDetailActivity.this);
        lstItem.setAdapter(itemAdapter);

        itemAdapter.setOnClickListener(new ItemAdapter.OnClickListener() {
            @Override
            public void onClick(int position)
            {
                if(array.length()>1)
                {
                    deleteAlertDialog(position);
                }
                else {
                    Toast.makeText(OrderDetailActivity.this, "This item not delete...", Toast.LENGTH_SHORT).show();
                }
            }
        });
        try
        {
            itemTotalJsonArray=new JSONObject(itemTotal);
            txtItemTotal.setText(itemTotalJsonArray.getString("itemTotal"));
            txtDeliveryFee.setText(itemTotalJsonArray.getString("driver_tips"));
            txtStoreCharge.setText(itemTotalJsonArray.getString("packingCommission"));
            txtTax.setText(itemTotalJsonArray.getString("tax"));

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        txtPaymentType.setText(paymentType);
        txtAllTotal.setText(allTotal);
    }
    public void getDeliveryBoy() {
        RetrofitClient.getInstance().getMyApi().getDeliveryBoy("get_del_boy_flutter").enqueue(new Callback<DeliveryBoy>() {
            @Override
            public void onResponse(Call<DeliveryBoy> call, Response<DeliveryBoy> response)
            {
                assert response.body() != null;
                deliveryBoyList=(ArrayList<DeliveryBoy.Data>) response.body().getData();
                if(deliveryBoyList.size()>0)
                {
                    if(isAcceptOrder) {
                        for (int i = 0; i < deliveryBoyList.size(); i++) {
                            if (deliveryBoyList.get(i).getZone_id().equals(zoneId)) {
                                zoneDeliveryBoy.add(deliveryBoyList.get(i));
                            }
                        }
                        //Toast.makeText(getContext(), String.valueOf(deliveryBoyList.get(0).getName()),Toast.LENGTH_SHORT).show();
                        DelivaryBoyAdapter delivaryBoyAdapter = new DelivaryBoyAdapter(getApplicationContext(), zoneDeliveryBoy);
                        lstDeliveryBoy.setAdapter(delivaryBoyAdapter);

                        isAcceptOrder = false;
                        delivaryBoyAdapter.setOnClickListener(new DelivaryBoyAdapter.OnClickListener() {
                            @Override
                            public void onClick(int position) {
                                delivery_assigned = zoneDeliveryBoy.get(position).getDriver_id();
                                acceptOrder();
                                //Toast.makeText(OrderDetailActivity.this, String.valueOf(delivery_assigned), Toast.LENGTH_SHORT).show();

                                //delivaryBoyAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                    else
                    {
                        for(int j=0;j<deliveryBoyList.size();j++)
                        {
                            if(Objects.equals(deliveryBoyList.get(j).getDriver_id(), driverId))
                            {
                                txtDBoyName.setText(deliveryBoyList.get(j).getName()+" "+deliveryBoyList.get(j).getLast_name());
                                delBoyPhoneNo=deliveryBoyList.get(j).getPhone();
                            }
                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<DeliveryBoy> call, Throwable t)
            {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getHotel() {
        RetrofitClient.getInstance().getMyApi().getHotel("get_hotel_flutter").enqueue(new Callback<Hotel>() {
            @Override
            public void onResponse(Call<Hotel> call, Response<Hotel> response)
            {
//                String hotelName=response.body().getData().get(0).getName();
//                Toast.makeText(HotelListActivity.this, hotelName, Toast.LENGTH_SHORT).show();
                allHotelList= (ArrayList<Hotel.Data>) response.body().getData();
                if(allHotelList.size()>0)
                {
                    for(int i=0;i<allHotelList.size();i++)
                    {
                        if(vendorId.equals(allHotelList.get(i).getVendor_id()))
                        {
                            txtHotelName.setText(allHotelList.get(i).getName());
                            hotelPhoneNo=allHotelList.get(i).getPhone();
                        }
                    }
                }


            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {
                Toast.makeText(OrderDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void acceptOrder()
    {
        RetrofitClient.getInstance().getMyApi().acceptOrder("approve_update",delivery_assigned,saleId)
                .enqueue(new Callback<Sample>() {
                    @Override
                    public void onResponse(Call<Sample> call, Response<Sample> response)
                    {
                        assert response.body() != null;
                        String status=response.body().getResult().toString();
                        if(status=="true")
                        {
                            Toast.makeText(OrderDetailActivity.this, "Delivery assign successful", Toast.LENGTH_SHORT).show();
                           // getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AllOrder()).commit();
//                            Intent intent=new Intent(OrderDetailActivity.this,MainActivity.class);
//                            startActivity(intent);
                            linLayout.setVisibility(View.GONE);
                            linLayoutCancel.setVisibility(View.VISIBLE);
                        }
                        else {
                            Toast.makeText(OrderDetailActivity.this, "Delivery not assign", Toast.LENGTH_SHORT).show();
                            linLayout.setVisibility(View.VISIBLE);
                            linLayoutCancel.setVisibility(View.GONE);
                        }

                    }
                    @Override
                    public void onFailure(Call<Sample> call, Throwable t)
                    {
                        Toast.makeText(OrderDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void rejectOrder()
    {
        RetrofitClient.getInstance().getMyApi().rejectOrder("approve_update","Cancelled",saleId).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response)
            {
                String status=response.body().getResult().toString();
                if(status=="true")
                {
                    if(isCancelOrder)
                    {
                        Toast.makeText(OrderDetailActivity.this, "Order cancel successful", Toast.LENGTH_SHORT).show();
                        if(customDialog.isShowing())
                        {
                            customDialog.dismiss();
                        }
                    }
                    else
                    {
                        Toast.makeText(OrderDetailActivity.this, "Order reject successful", Toast.LENGTH_SHORT).show();
                    }
                    linLayoutCancel.setVisibility(View.GONE);
                    linLayout.setVisibility(View.GONE);

                }
                else {
                    Toast.makeText(OrderDetailActivity.this, "Order not Reject", Toast.LENGTH_SHORT).show();
//                    linLayoutCancel.setVisibility(View.GONE);
                    linLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

            }
        });
    }
    public void openPopup()
    {
        CustomDialog customDialog1 = new CustomDialog(OrderDetailActivity.this);
        customDialog1.setContentView(R.layout.popup_delivery_boy);
        lstDeliveryBoy=customDialog1.findViewById(R.id.lstAllDeliveyBoy);

        zoneDeliveryBoy.clear();
        getDeliveryBoy();
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lstDeliveryBoy.setLayoutManager(layoutManager);

        Window window = customDialog1.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());

            // Set the desired width and height of the dialog
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

            window.setAttributes(layoutParams);
        }

        customDialog1.setTitle("Delivery Boy List");
        customDialog1.show();

    }

    public void cancelOrderPopPup()
    {
        customDialog = new CustomDialog(OrderDetailActivity.this);
        customDialog.setContentView(R.layout.cancel_popup);
        txtReason=customDialog.findViewById(R.id.txtReason);
        txtManagerName=customDialog.findViewById(R.id.txtManagerName);
        txtSubmit=customDialog.findViewById(R.id.txtSubmit);
        txtCancel=customDialog.findViewById(R.id.txtCancel);

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                //cancelOrder();
                if(validation())
                {
                    cancelOrder();
                }
            }
        });
        txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customDialog.isShowing())
                {
                    customDialog.dismiss();
                }
            }
        });


        Window window = customDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
            layoutParams.copyFrom(window.getAttributes());

            // Set the desired width and height of the dialog
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
            layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(layoutParams);
        }
        customDialog.show();
    }
    public boolean validation() {
        if (txtReason.getText().toString().isEmpty())
        {
            txtReason.setError("Enter Reason");
            return false;
        }
        if (txtManagerName.getText().toString().isEmpty()) {
            txtManagerName.setError("Enter Manger Name");
            return false;
        }
        return true;
    }

    public void cancelOrder()
    {

        managerName=txtManagerName.getText().toString();
        reason=txtReason.getText().toString();
        RetrofitClient.getInstance().getMyApi().cancelOrder("insert_reason",saleId,zoneId,managerName,vendorId,reason)
                .enqueue(new Callback<Sample>() {
                    @Override
                    public void onResponse(Call<Sample> call, Response<Sample> response) {
                        assert response.body() != null;
                        String status=response.body().getResult().toString();
                        if(status=="true")
                        {
//                            Toast.makeText(OrderDetailActivity.this, "Order cancel successful", Toast.LENGTH_SHORT).show();
//                            if(customDialog.isShowing())
//                            {
//                                customDialog.dismiss();
//                            }
//                            linLayoutCancel.setVisibility(View.GONE);
//                            linLayout.setVisibility(View.GONE);
                            isCancelOrder=true;
                            rejectOrder();

                        }
                        else {
                            Toast.makeText(OrderDetailActivity.this, "Order not cancel", Toast.LENGTH_SHORT).show();
                            linLayoutCancel.setVisibility(View.VISIBLE);
                            linLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Sample> call, Throwable t)
                    {
                        Toast.makeText(OrderDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    public void deleteAlertDialog(final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(OrderDetailActivity.this);
        builder.setTitle("Alert Dialog!!!");
        builder.setMessage("Are you sure you want to delete this item?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try
                {
                    JSONObject object = null;
                    try {
                        object = array.getJSONObject(pos);
                        Double price= Double.valueOf(object.getString("price"));
                        Double total=Double.valueOf(txtAllTotal.getText().toString());
                        if(total>0 && total>=price)
                        {
                            Double mtotal= total-price;
                            DecimalFormat decimalFormat = new DecimalFormat("#.##");
                            gtotal = String.valueOf(decimalFormat.format(mtotal));

                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }


                    RetrofitClient.getInstance().getMyApi().deleteItem("delete_item",pos,gtotal,saleId).enqueue(new Callback<Sample>() {
                        @Override
                        public void onResponse(Call<Sample> call, Response<Sample> response)
                        {
                            boolean result=response.body().getResult();
                            if(result)
                            {
                                txtAllTotal.setText(String.valueOf(gtotal));
                                array.remove(pos);
                                itemAdapter.notifyDataSetChanged();
                            }

                        }

                        @Override
                        public void onFailure(Call<Sample> call, Throwable t) {

                        }
                    });

                } catch (Exception e) {
                   // throw new RuntimeException(e);
                    Toast.makeText(OrderDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
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

    public void makePhoneCall(String phoneNumber) {
    //    String aphoneNumber = "7972763887"; // Replace with the phone number you want to call
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+phoneNumber));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent);
        } else {
            // You may need to request permission at runtime
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
    }


}