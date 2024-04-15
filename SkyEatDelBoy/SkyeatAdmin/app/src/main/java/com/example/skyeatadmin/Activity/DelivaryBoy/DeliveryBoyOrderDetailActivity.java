package com.example.skyeatadmin.Activity.DelivaryBoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.OrderDetailActivity;
import com.example.skyeatadmin.Adapter.DelivaryBoyAdapter;
import com.example.skyeatadmin.Adapter.ItemAdapter;
import com.example.skyeatadmin.Adapter.OrderAdapter;
import com.example.skyeatadmin.CustomDialog;
import com.example.skyeatadmin.Model.DeliveryBoy;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.Model.Hotel;
import com.example.skyeatadmin.Model.Sample;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryBoyOrderDetailActivity extends AppCompatActivity {
    RecyclerView lstItem,lstDeliveryBoy;
    CustomDialog customDialog;
    TextView txtReason,txtManagerName,txtSubmit,txtCancel,txtItemTotal,txtDeliveryFee,txtStoreCharge,txtTax;
    String managerName,reason;
    LinearLayout linLayout,linLayoutCancel,linLayoutPickUp,linLayoutDeliver;
    ItemAdapter itemAdapter;
    TextView txtCancelOrder;
    ImageView imgUserPhoneNo,imgBack;
    TextView btnAccept,btnReject,txtPickUp,txtDeliver;
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
    String status,deliveryState,saleId,zoneId,driverId,vendorId,phoneNo,userName,itemTotal,productDetails,allTotal,paymentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_order_detail);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            status=bundle.getString("status");
            deliveryState=bundle.getString("delivery_state");
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
        linLayoutPickUp=findViewById(R.id.linLayoutPickUp);
        linLayoutDeliver=findViewById(R.id.linLayoutDeliver);

        btnAccept=findViewById(R.id.btnAccept);
        btnReject=findViewById(R.id.btnReject);
        txtPickUp=findViewById(R.id.txtPickUp);
        txtDeliver=findViewById(R.id.txtDeliver);

        if(deliveryState.equals("RShipped"))
        {
            linLayout.setVisibility(View.VISIBLE);
            linLayoutCancel.setVisibility(View.GONE);
            linLayoutPickUp.setVisibility(View.GONE);
            linLayoutDeliver.setVisibility(View.GONE);

        } else if (deliveryState.equals("Accepted")) {
            linLayout.setVisibility(View.GONE);
            linLayoutCancel.setVisibility(View.GONE);
            linLayoutDeliver.setVisibility(View.GONE);
            linLayoutPickUp.setVisibility(View.VISIBLE);

        } else if (deliveryState.equals("Pickuped"))
        {
            linLayout.setVisibility(View.GONE);
            linLayoutCancel.setVisibility(View.GONE);
            linLayoutPickUp.setVisibility(View.GONE);
            linLayoutDeliver.setVisibility(View.VISIBLE);
        } else {
            linLayout.setVisibility(View.GONE);
            linLayoutCancel.setVisibility(View.GONE);
            linLayoutPickUp.setVisibility(View.GONE);
            linLayoutDeliver.setVisibility(View.GONE);
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
                Toast.makeText(DeliveryBoyOrderDetailActivity.this, phoneNo, Toast.LENGTH_SHORT).show();
            }
        });

        btnAccept.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                AcceptOrder();
                //Toast.makeText(getApplicationContext(), "Accept", Toast.LENGTH_SHORT).show();
            }
        });

        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rejectOrder();

                //  Toast.makeText(getApplicationContext(), "Reject", Toast.LENGTH_SHORT).show();
            }
        });
        txtCancelOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // cancelOrderPopPup();
            }
        });
        txtPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PickupOrder();
            }
        });
        txtDeliver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeliverOrder();
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
        txtOrderStatus.setText(deliveryState);
        txtUserName.setText(userName);
        try
        {
            array = new JSONArray(productDetails);
        }
        catch (JSONException e)
        {
            throw new RuntimeException(e);
        }

        itemAdapter=new ItemAdapter(getApplicationContext(), array,status,DeliveryBoyOrderDetailActivity.this);
        lstItem.setAdapter(itemAdapter);

        itemAdapter.setOnClickListener(new ItemAdapter.OnClickListener() {
            @Override
            public void onClick(int position)
            {
//                if(array.length()>1)
//                {
//                    deleteAlertDialog(position);
//                }
//                else {
//                    Toast.makeText(OrderDetailActivity.this, "This item not delete...", Toast.LENGTH_SHORT).show();
//                }
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
                    if(isAcceptOrder)
                    {
                        for(int i=0;i<deliveryBoyList.size();i++)
                        {
                            if(deliveryBoyList.get(i).getZone_id().equals(zoneId))
                            {
                                zoneDeliveryBoy.add(deliveryBoyList.get(i));
                            }
                        }
                        //Toast.makeText(getContext(), String.valueOf(deliveryBoyList.get(0).getName()),Toast.LENGTH_SHORT).show();
                        DelivaryBoyAdapter delivaryBoyAdapter=new DelivaryBoyAdapter(getApplicationContext(),zoneDeliveryBoy);
                        lstDeliveryBoy.setAdapter(delivaryBoyAdapter);

                        isAcceptOrder=false;
                        delivaryBoyAdapter.setOnClickListener(new DelivaryBoyAdapter.OnClickListener(){
                            @Override
                            public void onClick(int position) {
                                delivery_assigned=zoneDeliveryBoy.get(position).getDriver_id();
                              //  acceptOrder();
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
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Hotel> call, Throwable t) {
                Toast.makeText(DeliveryBoyOrderDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void AcceptOrder()
    {
        RetrofitClient.getInstance().getMyApi().acceptOrderByDeliveryBoy("accept_order_by_delivery_boy",saleId).enqueue(new Callback<Sample>()
        {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                assert response.body() != null;
                boolean result=response.body().getResult();
                if(result) {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order accepted", Toast.LENGTH_SHORT).show();
                    linLayout.setVisibility(View.GONE);
                    linLayoutCancel.setVisibility(View.GONE);
                    linLayoutPickUp.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order not accepted", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Toast.makeText(DeliveryBoyOrderDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void RejectOrder()
    {
        RetrofitClient.getInstance().getMyApi().rejectOrderByDeliveryBoy("reject_order_by_delivery_boy",saleId).enqueue(new Callback<Sample>()
        {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                assert response.body() != null;
                boolean result=response.body().getResult();
                if(result)
                {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order accepted", Toast.LENGTH_SHORT).show();
//                    linLayout.setVisibility(View.VISIBLE);
//                    linLayoutCancel.setVisibility(View.GONE);
//                    linLayoutPickUp.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order not accepted", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Toast.makeText(DeliveryBoyOrderDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void PickupOrder()
    {
        RetrofitClient.getInstance().getMyApi().pickupOrderByDeliveryBoy("pickup_order_by_delivery_boy",saleId).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response)
            {
                assert response.body() != null;
                boolean result=response.body().getResult();
                if(result)
                {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order Pickup", Toast.LENGTH_SHORT).show();
                    linLayout.setVisibility(View.GONE);
                    linLayoutCancel.setVisibility(View.GONE);
                    linLayoutPickUp.setVisibility(View.GONE);
                    linLayoutDeliver.setVisibility(View.VISIBLE);
                }
                else
                {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order not Pickup", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

            }
        });
    }

    public void DeliverOrder()
    {
        RetrofitClient.getInstance().getMyApi().deliverOrderByDeliveryBoy("deliver_order_by_delivery_boy",saleId).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response)
            {
                assert response.body() != null;
                boolean result=response.body().getResult();
                if(result)
                {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order Deliver", Toast.LENGTH_SHORT).show();
                    linLayout.setVisibility(View.GONE);
                    linLayoutCancel.setVisibility(View.GONE);
                    linLayoutPickUp.setVisibility(View.GONE);
                    linLayoutDeliver.setVisibility(View.GONE);
//                    Intent intent=new Intent(DeliveryBoyOrderDetailActivity.this,DeliveryBoyMainActivity.class);
//                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(DeliveryBoyOrderDetailActivity.this, "Order not Deliver", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {

            }
        });
    }

}