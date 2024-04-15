package com.example.skyeatadmin.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Activity.DelivaryBoy.DeliveryBoyMainActivity;
import com.example.skyeatadmin.Fragment.DeliveryBoyList;
import com.example.skyeatadmin.Model.DeliveryBoy;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.Model.Sample;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CollectCash extends AppCompatActivity {

    TextView txtDriverName,btnCollectCash;
    EditText txtGullak,txtCompeteOrder,txtTotalAmount;
    Double totalAmount=0.0;
    long dateTimeInMillis;
    Calendar C;
    ImageView imgBack;
    ArrayList<GAllOrder.Data> completeOrderList=new ArrayList<>();

    public String completeOrder,gullak,interest,paymentStatus,paidAmount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect_cash);
        txtDriverName=findViewById(R.id.txtDriverName);
        txtGullak=findViewById(R.id.txtGullak);
        txtCompeteOrder=findViewById(R.id.txtCompeteOrder);
        txtTotalAmount=findViewById(R.id.txtTotalAmount);
        btnCollectCash=findViewById(R.id.btnCollectCash);
        imgBack=findViewById(R.id.imgBack);
        completeOrderList.clear();
        getDeliveryBoyGullak();
        getCompleteOrder();

        txtDriverName.setText(DeliveryBoyList.deliveryBoyName);
        btnCollectCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                C= Calendar.getInstance();
                dateTimeInMillis = C.getTimeInMillis();
                paidAmount=txtTotalAmount.getText().toString();
                insertPaymentRecord();
            }
        });
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    public void getDeliveryBoyGullak() {
        try {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyGullak("get_delivery_boy_gullak",DeliveryBoyList.deliveryBoyId).enqueue(new Callback<DeliveryBoy>() {
                @Override
                public void onResponse(Call<DeliveryBoy> call, Response<DeliveryBoy> response) {
                    try {
                        gullak=response.body().getData().get(0).getGullak();
                        interest=response.body().getData().get(0).getInterest();
                        paymentStatus=response.body().getData().get(0).getPayment_status();

                        txtGullak.setText(gullak);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onFailure(Call<DeliveryBoy> call, Throwable t) {
                    Toast.makeText(CollectCash.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }

    }
    private void getCompleteOrder() {
        try {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyOrders("gullak_complete_order_delivery_boy",DeliveryBoyList.deliveryBoyId).enqueue(new Callback<GAllOrder>() {
                @Override
                public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response) {
                    assert response.body() != null;
                    completeOrderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                    if(completeOrderList.size()>0)
                    {
                        completeOrder= String.valueOf(completeOrderList.size());

                        for(int i=0;i<completeOrderList.size();i++)
                        {
                            totalAmount=totalAmount+Double.parseDouble(completeOrderList.get(i).getGrand_totals());
                        }
                        txtCompeteOrder.setText(completeOrder);
                        txtTotalAmount.setText(String.valueOf(Math.round(totalAmount)));

//
                    }
                    else{
                        txtCompeteOrder.setText("0");
                        txtTotalAmount.setText("0.0");
                    }

                }
                @Override
                public void onFailure(Call<GAllOrder> call, Throwable t) {
                    Toast.makeText(CollectCash.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
    }
    public void insertPaymentRecord()
    {
        try {
            RetrofitClient.getInstance().getMyApi().insertDelBoyPayment("insert_del_boy_company_payment",DeliveryBoyList.deliveryBoyId,DeliveryBoyList.deliveryBoyName, String.valueOf(dateTimeInMillis), paidAmount).enqueue(new Callback<Sample>() {
                @Override
                public void onResponse(Call<Sample> call, Response<Sample> response) {
                    assert response.body() != null;
                    boolean result= response.body().getResult();
                    if(result)
                    {
                        updateDeliveryPaymentStatus();
                    }
                }

                @Override
                public void onFailure(Call<Sample> call, Throwable t) {
                    Toast.makeText(CollectCash.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
        catch (Exception e)
        {
            Log.d("Error :",e.getMessage());
        }

    }
    public void updateDeliveryPaymentStatus() {
        RetrofitClient.getInstance().getMyApi().updateDeliveryBoyPaymentSatus("update_delivery_boy_payment_status",DeliveryBoyList.deliveryBoyId).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                //assert response.body() != null;
                boolean results= response.body().getResult();
                if(results)
                {
                    Toast.makeText(CollectCash.this, "Cash Collect Successful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Log.d("",t.getMessage());
            }
        });
    }
}