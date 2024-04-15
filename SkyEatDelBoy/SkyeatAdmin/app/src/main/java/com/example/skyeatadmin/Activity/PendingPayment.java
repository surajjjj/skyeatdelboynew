package com.example.skyeatadmin.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.skyeatadmin.Model.Area;
import com.example.skyeatadmin.Model.GAllOrder;
import com.example.skyeatadmin.Model.Login;
import com.example.skyeatadmin.Model.Sample;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;
import com.razorpay.AutoReadOtpHelper;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingPayment extends AppCompatActivity implements PaymentResultListener
{
    ArrayList<GAllOrder.Data> orderList =new ArrayList<>();
    ArrayList<String> CompleteOrderList=new ArrayList<>();
    ArrayList<String> arrayList;
    String truncatedValue,truncateAmount;
    String zoneName,zoneId,zone_id;
    double sum;
    ArrayList<Area.Data> allZoneList=new ArrayList<>();
    ArrayList<Login.Data> loginData=new ArrayList<>();
    ArrayList<Login.Data> managerList=new ArrayList<>();
    EditText txtRemainCash,txtWalletAmount,txtMassage,txtMinusAmount,btnUpdateWallet;
    double amount;
    ImageView imgEdit,imgBack;
    LinearLayout linLayoutAmount;
    String managerZone=null;
    String managerId;
    TextView btnSubmitCash;
    String[] stringArray;
    private AutoReadOtpHelper autoReadOtpHelper;

    String payAmount;
//    SharedPreferences sharedPreferences;
//    Context context;
    int i;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_pending_payment);

        txtRemainCash=findViewById(R.id.txtRemainCash);
        btnSubmitCash=findViewById(R.id.btnSubmitCash);
        txtMassage=findViewById(R.id.txtMassage);
        txtWalletAmount=findViewById(R.id.txtWalletAmount);
        imgEdit=findViewById(R.id.imgEdit);
        imgBack=findViewById(R.id.imgBack1);
        linLayoutAmount=findViewById(R.id.linLayoutAmount);
        txtMinusAmount=findViewById(R.id.txtMinusAmount);
        btnUpdateWallet=findViewById(R.id.btnUpdateWallet);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle != null)
        {
            managerZone=bundle.getString("managerZone");
            managerId=bundle.getString("managerId");
        }
        if(MainActivity.ZONE_NAME.equalsIgnoreCase("all"))
        {
            imgEdit.setVisibility(View.VISIBLE);
            btnSubmitCash.setVisibility(View.GONE);
            btnUpdateWallet.setVisibility(View.VISIBLE);

        }
        else {
            imgEdit.setVisibility(View.GONE);
            btnUpdateWallet.setVisibility(View.GONE);
            btnSubmitCash.setVisibility(View.VISIBLE);
        }
        getLogin();
        getAllPayment();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getSupportFragmentManager().popBackStack();
                onBackPressed();
            }
        });
        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linLayoutAmount.setVisibility(View.VISIBLE);

            }
        });
        txtWalletAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(txtMinusAmount.getText().toString().equals("")){
                   txtMinusAmount.setError("Enter Amount");
               }
               else
               {
                   if(Double.parseDouble(txtMinusAmount.getText().toString())>Double.parseDouble(txtRemainCash.getText().toString())){
                       txtMinusAmount.setError("Enter Valid Amount");
                   }
                   else {
                       amount= (Double.parseDouble(txtRemainCash.getText().toString()))-(Double.parseDouble(txtMinusAmount.getText().toString()));
                       DecimalFormat decimalFormat = new DecimalFormat("#.##");
                       truncateAmount = decimalFormat.format(amount);
                       txtWalletAmount.setText(truncateAmount);
                   }
               }
            }
        });
        btnUpdateWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWalletData(txtWalletAmount.getText().toString());
            }
        });

        btnSubmitCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validation())
                {
                    if(txtWalletAmount.getText().toString().equals("0"))
                    {
                        payAmount=txtRemainCash.getText().toString();
                    }
                    else {
                        payAmount=txtWalletAmount.getText().toString();
                    }
                    payNow(payAmount);
                }
            }
        });
    }
    public boolean validation() {
        if (txtRemainCash.getText().toString().equals("0")) {
            txtRemainCash.setError("Remain cash not be zero");
            Toast.makeText(PendingPayment.this, "Remain cash not be zero", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (txtMassage.getText().toString().isEmpty())
        {
            txtMassage.setError("Enter Massage");
            return false;
        }
//
        return true;
    }
    public void payNow(String pamount)
    {
       // String payAmount=String.valueOf(truncateAmount);
        int amt=Math.round(Float.parseFloat(pamount)*100);
        Checkout checkout=new Checkout();
        checkout.setKeyID("rzp_test_jDbq0kDFweU1xy");      //testing key

        //checkout.setKeyID("rzp_live_nmxyoDOdHOy5mZ");        //live key
        checkout.setImage(R.drawable.skyeat_logo);

        JSONObject object=new JSONObject();
        try {
            object.put("name","SkyEat");
            object.put("description","Delivery Payment");
            object.put("theme.color","#F35929");
            object.put("amount",amt);
            checkout.open(PendingPayment.this,object);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }

    public void updatePaymentStatus(String zone_id)
    {
            RetrofitClient.getInstance().getMyApi().updateWalletStatus("update_manager_payment_status",zone_id).enqueue(new Callback<Sample>() {
                @Override
                public void onResponse(Call<Sample> call, Response<Sample> response)
                {
                    boolean result=response.body().getResult();
                    if(result)
                    {
                        Toast.makeText(PendingPayment.this, "wallet Update", Toast.LENGTH_SHORT).show();
                        txtRemainCash.setText("");
                        txtWalletAmount.setText("");
                    }
//                    else {
//
//                    }

                }

                @Override
                public void onFailure(Call<Sample> call, Throwable t) {
                    Toast.makeText(PendingPayment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }

    public void getAllPayment() {

        RetrofitClient.getInstance().getMyApi().getOrder("get_order_flutter")
                .enqueue(new Callback<GAllOrder>() {
                    @Override
                    public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response)
                    {
                        // Toast.makeText(getContext(), "complete", Toast.LENGTH_SHORT).show();
                        orderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                        if(orderList.size()>0)
                        {
                                if(managerZone==null)
                                {
                                    stringArray = MainActivity.ZONE_NAME.split(",");
                                    managerId=MainActivity.ID;
                                }
                                else{
                                    stringArray = managerZone.split(",");

                                }

                                arrayList = new ArrayList<>(Arrays.asList(stringArray));
                                //arrySize=arrayList.size();
                                try
                                {
                                    for(i=0;i<arrayList.size();i++)
                                    {
                                        zoneName=arrayList.get(i);
                                        getZoneOrder(zoneName,i);
                                    }
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(PendingPayment.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                        }
                    }
                    @Override
                    public void onFailure(Call<GAllOrder> call, Throwable t) {
                        Toast.makeText(PendingPayment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    public void getZoneID(String zone_name)
    {
        RetrofitClient.getInstance().getMyApi().getZone("get_zone_flutter").enqueue(new Callback<Area>() {
            @Override
            public void onResponse(Call<Area> call, Response<Area> response) {
                allZoneList= (ArrayList<Area.Data>) response.body().getData();
                for (int a=0;a<allZoneList.size();a++)
                {
                    if(zone_name.equals(allZoneList.get(a).getTitle()))
                    {
                        zone_id=allZoneList.get(a).getZone_id();
                        updatePaymentStatus(zone_id);
                    }
                }
            }

            @Override
            public void onFailure(Call<Area> call, Throwable t)
            {
                Toast.makeText(PendingPayment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getZoneOrder(String zoneName,final int position) {
        RetrofitClient.getInstance().getMyApi().getZone("get_zone_flutter").enqueue(new Callback<Area>() {
            @Override
            public void onResponse(Call<Area> call, Response<Area> response) {
                allZoneList= (ArrayList<Area.Data>) response.body().getData();
                for (int a=0;a<allZoneList.size();a++)
                {
                    if(zoneName.equals(allZoneList.get(a).getTitle()))
                    {
                        zoneId=allZoneList.get(a).getZone_id();
                    }
                }
                try {
                    for(int j=0;j<orderList.size();j++)
                    {
                        if(orderList.get(j).getStatus().equalsIgnoreCase("Completed") && orderList.get(j).getDist_manager_payment_status()==null && zoneId.equals(orderList.get(j).getZone_id()))
                        {
                            CompleteOrderList.add(orderList.get(j).getGrand_totals());
                        }
                    }
                }
                catch (Exception e){
                    Log.e("error",e.getMessage());
                }

              if(position+1==arrayList.size())
              {

                  for (String number : CompleteOrderList)
                  {
                      double value = Double.parseDouble(number);
                      sum += value;
                  }
                  DecimalFormat decimalFormat = new DecimalFormat("#.##");
                  truncatedValue = decimalFormat.format(sum);
                  txtRemainCash.setText(truncatedValue);

//                  setWalletData(txtRemainCash.getText().toString());

              }
            }

            @Override
            public void onFailure(Call<Area> call, Throwable t)
            {
                Toast.makeText(PendingPayment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void setWalletData(String wallet)
    {
        RetrofitClient.getInstance().getMyApi().setWallet("update_manager_wallet",wallet,managerId).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response)
            {
               // assert response.body() != null;
                boolean result=response.body().getResult();
                if(result)
                {

                    txtRemainCash.setText("");
                    txtWalletAmount.setText("");
                    txtMinusAmount.setText("");
                    Toast.makeText(PendingPayment.this, "wallet update ", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(PendingPayment.this, "wallet not update ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Toast.makeText(PendingPayment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void getLogin() {
        RetrofitClient.getInstance().getMyApi().getLogin("get_new_loginflutter").enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                assert response.body() != null;
                loginData= (ArrayList<Login.Data>) response.body().getData();
                if(managerId==null)
                {
                    managerId=MainActivity.ID;
                }
                for(int i=0;i<loginData.size();i++)
                {
                    if(managerId.equals(loginData.get(i).getId()))
                    {
                        txtWalletAmount.setText(loginData.get(i).getWallet());
                    }
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Toast.makeText(PendingPayment.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentSuccess(String s)
    {
        Toast.makeText(PendingPayment.this, "Payment Successful", Toast.LENGTH_SHORT).show();
        setWalletData("0");
        zoneName="";
        for(i=0;i<arrayList.size();i++) {
            zoneName = arrayList.get(i);
            getZoneID(zoneName);
        }
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(PendingPayment.this, "Payment Failed", Toast.LENGTH_SHORT).show();
    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(autoReadOtpHelper);
//    }
}