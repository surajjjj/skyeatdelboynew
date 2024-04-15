package com.food.food_order_Delivaryboy.Activity.DelivaryBoy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.food.food_order_Delivaryboy.Activity.DeliveryBoyActivity;
import com.food.food_order_Delivaryboy.Activity.LoginActivity;
import com.food.food_order_Delivaryboy.Activity.SplashActivity;
import com.food.food_order_Delivaryboy.Activity.blockactivity;
import com.food.food_order_Delivaryboy.Constant;
import com.food.food_order_Delivaryboy.CustomDialog;
import com.food.food_order_Delivaryboy.Fragment.DeliveryBoy.DeliveryBoyAllOrder;

import com.food.food_order_Delivaryboy.Model.Area;
import com.food.food_order_Delivaryboy.Model.DeliveryBoy;
import com.food.food_order_Delivaryboy.Model.GAllOrder;
import com.food.food_order_Delivaryboy.Model.Sample;
import com.food.food_order_Delivaryboy.R;
import com.food.food_order_Delivaryboy.Retrofit.RetrofitClient;
import com.food.food_order_Delivaryboy.utilities.Config;
import com.food.food_order_Delivaryboy.utilities.ConnectionDetector;
import com.food.food_order_Delivaryboy.utilities.JSONParser;
import com.google.android.material.navigation.NavigationView;
import com.onesignal.OneSignal;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeliveryBoyMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, PaymentResultListener {

    NavigationView navigationView;
    Toolbar toolbar;
    Calendar C;
    AsyncTask<String, Void, String> updatetoken;

    LinearLayout liLayoutMyEarning,linLayoutFixEarning;
    long dateTimeInMillis;
    int day,month,year,minute,second;
    CustomDialog customDialog,customDialog1;
    SharedPreferences sharedPreferences;
    private DrawerLayout drawerLayout;
    public ImageView imgGullak;
    ToggleButton togglebutton;
    Switch switch1;
    private static final String  ONESIGNAL_APP_ID="56948f15-2d75-41b4-b96d-b028cbb8a5a7";


    public String orderCharge,gullak,interest,completeOrder,delCompleteOrder;
    public  String paymentStatus;

    public boolean isCloseApp=false;
    Double totalAmount=0.0;
    int amt;
    Date currentDate;
    String paidAmount;
    int delTotalAmout=0;
    String deliveryBoyEarning;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView txtManagerName,txtVersionCode,txtGullak,txtCompeteOrder,txtFixEarning,txtPerOrderCharge,txtTotalAmount,txtDelBoyCompeteOrder,txtRequestToPay,txtDelBoyTotalAmount;
    TextView txtCancel,txtPayNow,txtCancelPopup,status;
    String date;
    public static String DRIVER_ID,DRIVER_NAME,ZONE_ID;
    AsyncTask<String, Void, String> getAllOrdersnew_Async;
    AsyncTask<String, Void, String> getAllOrdersnew_Asyncn;
    AsyncTask<String, Void, String> getAllOrders_Async;
    JSONParser jsonParser=new JSONParser();
    ArrayList<GAllOrder.Data> completeOrderList=new ArrayList<>();
    ArrayList<GAllOrder.Data> delCompleteOrderList=new ArrayList<>();
    ConnectionDetector cd;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_boy_main);
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE,OneSignal.LOG_LEVEL.NONE);
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);
        OneSignal.promptForPushNotifications();


        drawerLayout=findViewById(R.id.nav_view);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        toolbar=findViewById(R.id.toolbar);
        status=findViewById(R.id.status);
        navigationView=findViewById(R.id.navigation_view);
        cd=new ConnectionDetector(this);
        txtVersionCode=navigationView.findViewById(R.id.txtVersionCode);
        View headerView = navigationView.getHeaderView(0);
        txtManagerName= headerView.findViewById(R.id.txtManagerName);
        imgGullak=headerView.findViewById(R.id.imgGullak);
        switch1=headerView.findViewById(R.id.switch1);
        //txtVersionCode = findViewById(R.id.txtVersionCode);
        navigationView.setNavigationItemSelectedListener(this);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DeliveryBoyAllOrder());
        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_draw_open,R.string.navigation_draw_open);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolbar.setTitle("All Orders");


        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new DeliveryBoyAllOrder()).commit();
            navigationView.setCheckedItem(R.id.menuAllOrder);
        }
        sharedPreferences=getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("zone_id"))
        {
            ZONE_ID=sharedPreferences.getString("zone_id",null);
        }
        if(sharedPreferences.contains("delivery_boy_id"))
        {
            DRIVER_ID=sharedPreferences.getString("delivery_boy_id",null);
        }
        if(sharedPreferences.contains("first_name") && sharedPreferences.contains("last_name"))
        {
            DRIVER_NAME=sharedPreferences.getString("first_name",null)+" "+sharedPreferences.getString("last_name",null);
        }
        OneSignal.promptForPushNotifications();
        String push= OneSignal.getDeviceState().getUserId();
       Toast.makeText(this, ""+push, Toast.LENGTH_SHORT).show();

        if(cd.isConnectingToInternet()){
            String url= null;

            try {
                url = Config.get_url+
                        "action=update_deliveryboy_token" +
                        "&driver_id=" + URLEncoder.encode(DRIVER_ID=sharedPreferences.getString("delivery_boy_id",null) ,"utf-8")+
                        "&fcm_token=" + URLEncoder.encode(""+push, "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            updatetoken = new UpdateToken();
            updatetoken.execute(url);

        }else{
//                                Toast.makeText(HotelAdmin_MenusActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
//                                finish();
        }
        txtManagerName.setText("Mr. "+DRIVER_NAME);
     //   txtVersionCode.setText(SplashActivity.versionName);

        if(cd.isConnectingToInternet()){
            String url= null;
            try {
                url = Config.get_url+
                        "action=get_all_del_boy"+
                "&driver_id="+ URLEncoder.encode(DRIVER_ID,"utf-8");
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
            ;
            //Toast.makeText(this, ""+db.getDelivaryboyId(), Toast.LENGTH_SHORT).show();
            getAllOrders_Async=new GetAllOrders_Async();
            getAllOrders_Async.execute(url);
        }else{
            Toast.makeText(this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            finish();
        }
        switch1.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if(cd.isConnectingToInternet()){
                    String url= null;
                    try {
                        url = Config.get_url+
                                "action=update_del_boy_status"+
                                "&driver_id="+ URLEncoder.encode(DRIVER_ID,"utf-8")+
                                "&livestatus="+ URLEncoder.encode("true","utf-8")+
                                "&drivingMode="+ URLEncoder.encode("1","utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                  //  Toast.makeText(this, ""+DRIVER_ID, Toast.LENGTH_SHORT).show();
                    getAllOrdersnew_Async=new GetAllOrdersnew_Async();
                    getAllOrdersnew_Async.execute(url);
                }else{
                   // Toast.makeText(Collectdel_cash_main_admin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }
             //   status.setText("Avaliable");
            } else {
                if(cd.isConnectingToInternet()){
                    String url= null;
                    try {
                        url = Config.get_url+
                                "action=update_del_boy_status_false"+
                                "&driver_id="+ URLEncoder.encode(DRIVER_ID,"utf-8")+
                                "&livestatus="+ URLEncoder.encode("false","utf-8")+
                                "&drivingMode="+ URLEncoder.encode("0","utf-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    //Toast.makeText(this, ""+db.getDelivaryboyId(), Toast.LENGTH_SHORT).show();
                    getAllOrdersnew_Asyncn=new GetAllOrdersnew_Asyncn();
                    getAllOrdersnew_Asyncn.execute(url);
                }else{
                    // Toast.makeText(Collectdel_cash_main_admin.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                }

            }
        });


//        date=day+"/"+(month+1)+""+year;

      //  dateTimeInMillis = C.getTimeInMillis();

//        getOrderCharge();
//        getDeliveryBoyGullak();
//        getCompleteOrder();

        try {
            completeOrderList.clear();;
            delCompleteOrderList.clear();
            getOrderCharge();
            getDeliveryBoyGullak();
            getCompleteOrder();
            getDelBoyCompleteOrder();


        }
        catch (Exception e)
        {
            Toast.makeText(DeliveryBoyMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        imgGullak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                isCloseApp=false;
                openGullakPopPup();
                //Toast.makeText(DeliveryBoyMainActivity.this, "Skyeat Gullak", Toast.LENGTH_SHORT).show();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try
                {

                    Intent intent=new Intent(DeliveryBoyMainActivity.this, DeliveryBoyMainActivity.class);
                    startActivity(intent);
//                    delCompleteOrder="";
//                    orderCharge="";
//                    delTotalAmout=0;
//                    gullak="";
//                    completeOrder="";
//                    totalAmount=0.0;
//                    completeOrderList.clear();
//                    delCompleteOrderList.clear();
//                    txtDelBoyCompeteOrder.setText("");
//                    txtDelBoyTotalAmount.setText("");
//                    txtPerOrderCharge.setText("");
//                    txtGullak.setText("");
//                    txtTotalAmount.setText("");
//                    txtCompeteOrder.setText("");
//                    getOrderCharge();
//                    getDeliveryBoyGullak();
//                    getCompleteOrder();
//                    getDelBoyCompleteOrder();


                }
                catch (Exception e)
                {
                    Toast.makeText(DeliveryBoyMainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.d("",e.getMessage());
                }
                finally {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    public void openGullakPopPup()
    {
        try {
            customDialog = new CustomDialog(DeliveryBoyMainActivity.this);
            customDialog.setContentView(R.layout.skyeat_gullak);
            customDialog.setCancelable(false);

            txtTotalAmount=customDialog.findViewById(R.id.txtTotalAmount);

            txtCompeteOrder=customDialog.findViewById(R.id.txtCompeteOrder);
            txtGullak=customDialog.findViewById(R.id.txtGullak);
            txtCancel=customDialog.findViewById(R.id.txtCancel);
            txtPayNow=customDialog.findViewById(R.id.txtPayNow);

            Window window = customDialog.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(R.color.purple_500);
            if (window != null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(window.getAttributes());

                // Set the desired width and height of the dialog
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;

                window.setAttributes(layoutParams);
            }

            txtGullak.setText(gullak);
            txtCompeteOrder.setText(completeOrder);
            txtTotalAmount.setText(String.valueOf(Math.round(totalAmount)));
            //txtTotalAmount.setText(String.valueOf(totalAmount));

            txtPayNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    C= Calendar.getInstance();
                    day=C.get(Calendar.DAY_OF_MONTH);
                    month=C.get(Calendar.MONTH)+1;
                    year=C.get(Calendar.YEAR);
                    minute=C.get(Calendar.MINUTE);
                    year=C.get(Calendar.SECOND);

                    dateTimeInMillis = C.getTimeInMillis();
                    payNow(txtTotalAmount.getText().toString());
                    //payNow("1");
                }
            });

            txtCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(isCloseApp)
                    {
                        customDialog.dismiss();
                        finishAffinity();
                    }
                    else {
                        customDialog.dismiss();
                    }

                }
            });


        }
        catch (Exception e){
            Log.d("Error",e.getMessage());
        }
        finally {
            customDialog.show();
        }
    }

    @SuppressLint("ResourceAsColor")
    public void openMyEarnPopPup()
    {
        try {
            customDialog1 = new CustomDialog(DeliveryBoyMainActivity.this);
            customDialog1.setContentView(R.layout.activity_delivery_boy_earning);

            txtPerOrderCharge=customDialog1.findViewById(R.id.txtPerOrderCharge);
            txtDelBoyCompeteOrder=customDialog1.findViewById(R.id.txtDelBoyCompeteOrder);
            txtDelBoyTotalAmount=customDialog1.findViewById(R.id.txtDelBoyTotalAmount);
            txtRequestToPay=customDialog1.findViewById(R.id.txtRequestToPay);
            txtCancelPopup=customDialog1.findViewById(R.id.txtCancelPopup);
            txtFixEarning=customDialog1.findViewById(R.id.txtFixEarning);
            linLayoutFixEarning=customDialog1.findViewById(R.id.linLayoutFixEarning);
            liLayoutMyEarning=customDialog1.findViewById(R.id.liLayoutMyEarning);
            //  customDialog1.setCancelable(false);

            Window window = customDialog1.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(R.color.purple_500);
            if (window != null) {
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(window.getAttributes());

                // Set the desired width and height of the dialog
                layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
                layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT;

                window.setAttributes(layoutParams);
            }

            if(paymentStatus == null || paymentStatus.equals(""))
            {
                liLayoutMyEarning.setVisibility(View.VISIBLE);
                linLayoutFixEarning.setVisibility(View.GONE);
                txtDelBoyCompeteOrder.setText(delCompleteOrder);
                txtPerOrderCharge.setText(orderCharge);
                txtDelBoyTotalAmount.setText(String.valueOf(delTotalAmout));
                if(delTotalAmout>0)
                {
                    txtRequestToPay.setVisibility(View.VISIBLE);
                }
                else {
                    txtRequestToPay.setVisibility(View.INVISIBLE);
                }
                //deliveryBoyEarning= String.valueOf(delTotalAmout);
            }
            else {
                liLayoutMyEarning.setVisibility(View.GONE);
                linLayoutFixEarning.setVisibility(View.VISIBLE);
                txtRequestToPay.setVisibility(View.INVISIBLE);
                txtFixEarning.setText(paymentStatus);
               // deliveryBoyEarning=paymentStatus;
            }
            txtRequestToPay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    C= Calendar.getInstance();
                    day=C.get(Calendar.DAY_OF_MONTH);
                    month=C.get(Calendar.MONTH);
                    year=C.get(Calendar.YEAR);
                    minute=C.get(Calendar.MINUTE);
                    year=C.get(Calendar.SECOND);
                    dateTimeInMillis = C.getTimeInMillis();
//                    date=day+"/"+(month+1)+"/"+year;
//                    //date= String.valueOf(dateTimeInMillis);
//                    //requestToPay();
                     currentDate = new Date();

                    insertDeliveryBoyEarnRecord();

                }
            });

            txtCancelPopup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    customDialog1.dismiss();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
        finally {
            customDialog1.show();
        }
    }

    private void getCompleteOrder() {
        try {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyOrders("gullak_complete_order_delivery_boy",DRIVER_ID).enqueue(new Callback<GAllOrder>() {
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

//                    totalAmount=Integer.parseInt(completeOrder) * Integer.parseInt(orderCharge);
                        if(gullak!=null)
                        {
                               // assert gullak != null;
                            if(totalAmount>=Integer.parseInt(gullak)){
                                openGullakPopPup();
                                isCloseApp=true;
                            }
                        }
                    }

                }
                @Override
                public void onFailure(Call<GAllOrder> call, Throwable t) {
                    Toast.makeText(DeliveryBoyMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }
    }

    public void getDeliveryBoyGullak() {
        try {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyGullak("get_delivery_boy_gullak",DRIVER_ID).enqueue(new Callback<DeliveryBoy>() {
                @Override
                public void onResponse(Call<DeliveryBoy> call, Response<DeliveryBoy> response) {
                    try {
                        gullak=response.body().getData().get(0).getGullak();
                        interest=response.body().getData().get(0).getInterest();
                        paymentStatus=response.body().getData().get(0).getPayment_status();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                @Override
                public void onFailure(Call<DeliveryBoy> call, Throwable t) {
                    Toast.makeText(DeliveryBoyMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Error",e.getMessage());
        }

    }
    private void getDelBoyCompleteOrder() {
        try {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyOrders("myearning_complete_order_delivery_boy",DRIVER_ID).enqueue(new Callback<GAllOrder>() {
                @Override
                public void onResponse(Call<GAllOrder> call, Response<GAllOrder> response) {
                    assert response.body() != null;
                    delCompleteOrderList= (ArrayList<GAllOrder.Data>) response.body().getData();
                    try {
                        if(delCompleteOrderList.size()>0)
                        {
                            delCompleteOrder= String.valueOf(delCompleteOrderList.size());
                            delTotalAmout=Integer.parseInt(delCompleteOrder) * Integer.parseInt(orderCharge);


                            //Toast.makeText(DeliveryBoyEarningActivity.this, completeOrder, Toast.LENGTH_SHORT).show();
                        }
                    } catch (NumberFormatException e) {
                        throw new RuntimeException(e);
                    }

                }
                @Override
                public void onFailure(Call<GAllOrder> call, Throwable t) {
                    Toast.makeText(DeliveryBoyMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
        catch (Exception e)
        {
            Log.d("Error :",e.getMessage());
        }
    }
    public void getOrderCharge() {
        try {
            RetrofitClient.getInstance().getMyApi().getOrderCharge("get_order_charg",ZONE_ID).enqueue(new Callback<Area>() {
                @Override
                public void onResponse(Call<Area> call, Response<Area> response) {
                    orderCharge=response.body().getData().get(0).getOrder_charg();

                }
                @Override
                public void onFailure(Call<Area> call, Throwable t) {
                    Toast.makeText(DeliveryBoyMainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        catch (Exception e)
        {
            Log.e("Error :",e.getMessage());
        }
    }
    public void insertPaymentRecord(){
        try {
            RetrofitClient.getInstance().getMyApi().insertDelBoyPayment("insert_del_boy_company_payment",DRIVER_ID,DRIVER_NAME, String.valueOf(dateTimeInMillis), paidAmount).enqueue(new Callback<Sample>() {
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
                    Toast.makeText(DeliveryBoyMainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        }
        catch (Exception e)
        {
            Log.e("Error :",e.getMessage());
        }
    }

    public void insertDeliveryBoyEarnRecord()
    {
        try {
            RetrofitClient.getInstance().getMyApi().insertDelBoyPayment("insert_del_boy_earn_payment",DRIVER_ID,DRIVER_NAME, Constant.dateToLong(String.valueOf(currentDate)), String.valueOf(delTotalAmout)).enqueue(new Callback<Sample>() {
                @Override
                public void onResponse(Call<Sample> call, Response<Sample> response) {
                    assert response.body() != null;
                    boolean result= response.body().getResult();
                    if(result)
                    {
//                        Toast.makeText(DeliveryBoyMainActivity.this, "Request send Successfully", Toast.LENGTH_SHORT).show();
//                        txtRequestToPay.setVisibility(View.GONE);

                        //customDialog1.dismiss();
                        requestToPay();
                    }

                }

                @Override
                public void onFailure(Call<Sample> call, Throwable t) {
                   // Log.d("",t.getMessage());
                    Toast.makeText(DeliveryBoyMainActivity.this, "Your Request send not successful.So please contact to your manager", Toast.LENGTH_LONG).show();

                }
            });

        }
        catch (Exception e)
        {
            Log.e("Error :",e.getMessage());
        }
    }
    public void updateDeliveryPaymentStatus() {
        RetrofitClient.getInstance().getMyApi().updateDeliveryBoyPaymentSatus("update_delivery_boy_payment_status",DRIVER_ID).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
                //assert response.body() != null;
                boolean results= response.body().getResult();
                if(results)
                {
                    customDialog.dismiss();
                   // insertPaymentRecord();

                }

            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Log.e("",t.getMessage());

            }
        });
    }
    public void requestToPay()
    {
        RetrofitClient.getInstance().getMyApi().updateDeliveryBoyEarnStatus("update_delivery_boy_earn_status",DRIVER_ID).enqueue(new Callback<Sample>() {
            @Override
            public void onResponse(Call<Sample> call, Response<Sample> response) {
              //  assert response.body() != null;
                boolean results= response.body().getResult();
                if(results)
                {
                    customDialog1.dismiss();

//                    insertDeliveryBoyEarnRecord();

                    Toast.makeText(DeliveryBoyMainActivity.this, "Request send successfully", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DeliveryBoyMainActivity.this, "Request send not successfully", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<Sample> call, Throwable t) {
                Log.e("",t.getMessage());

            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.menuAllOrder:
                toolbar.setTitle("All Orders");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DeliveryBoyAllOrder()).commit();

                break;
            case R.id.menuCompleteOrder:

                Intent i = new Intent(DeliveryBoyMainActivity.this, DeliveryBoyActivity.class);
                //Intent intent=new Intent(getContext(), OrderDetailActivity.class);
                startActivity(i);
                break;

            case R.id.menuMyEarning:
//                Intent intent1 = new Intent(DeliveryBoyMainActivity.this, DeliveryBoyEarningActivity.class);
//                //Intent intent=new Intent(getContext(), OrderDetailActivity.class);
//                startActivity(intent1);
                openMyEarnPopPup();
                break;

            case R.id.menuMyTransition:
                Intent intent1 = new Intent(DeliveryBoyMainActivity.this, DealiveryBoyTransitionHistroryActivity.class);
                startActivity(intent1);
                break;
            case R.id.menuLogout:

                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.clear();
                editor.commit();
                Intent intent = new Intent(DeliveryBoyMainActivity.this, LoginActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                finish();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public void payNow(String pamount)
    {
        // String payAmount=String.valueOf(truncateAmount);
        paidAmount=pamount;
        amt=Math.round(Float.parseFloat(pamount)*100);
        Checkout checkout=new Checkout();
        checkout.setKeyID("rzp_test_jDbq0kDFweU1xy");      //testing key

       // checkout.setKeyID("rzp_live_nmxyoDOdHOy5mZ");        //live key
        checkout.setImage(R.drawable.skyeat_logo);

        JSONObject object=new JSONObject();
        try {
            object.put("name","SkyEat");
            object.put("description","Delivery Payment");
            object.put("theme.color","#F35929");
            object.put("amount",amt);
            checkout.open(DeliveryBoyMainActivity.this,object);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public void onPaymentSuccess(String s) {
      //  Toast.makeText(DeliveryBoyMainActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
        insertPaymentRecord();
       // updateDeliveryPaymentStatus();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(DeliveryBoyMainActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();

    }
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(yourBroadcastReceiverInstance);
//    }

    public void exitAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DeliveryBoyMainActivity.this);
        builder.setTitle("Alert Dialog!!!");
        builder.setMessage("Are you sure you want to exit?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Perform any necessary actions and close the activity
                //super.onBackPressed();
                dialog.dismiss();
                finishAffinity();
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

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer((GravityCompat.START));
        }
        else {
            exitAlertDialog();
            // super.onBackPressed();
        }
    }

    class GetAllOrdersnew_Async extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(SkyEatAdminMain_OrderDetails.this);
//            dialog.setMessage("Getting all orders");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getAllOrders_Async.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //   dialog.dismiss();
            if(result==null||result.trim().length()<=0){
                Toast.makeText(DeliveryBoyMainActivity.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){



//                    Intent intent=new Intent(Collectdel_cash_main_admin.this,HomeActivity.class);
//                    startActivity(intent);
                    Toast.makeText(DeliveryBoyMainActivity.this  , "Drive Mode Is On", Toast.LENGTH_SHORT).show();
                  //  finish();



                }
                else
                {
                  // Toast.makeText(DeliveryBoyMainActivity.this  , "not Cash received", Toast.LENGTH_SHORT).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class GetAllOrdersnew_Asyncn extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(SkyEatAdminMain_OrderDetails.this);
//            dialog.setMessage("Getting all orders");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getAllOrders_Async.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //   dialog.dismiss();
            if(result==null||result.trim().length()<=0){
                Toast.makeText(DeliveryBoyMainActivity.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){



//                    Intent intent=new Intent(Collectdel_cash_main_admin.this,HomeActivity.class);
//                    startActivity(intent);
                    Toast.makeText(DeliveryBoyMainActivity.this  , "Drive Mode Is Off", Toast.LENGTH_SHORT).show();
                    //  finish();



                }
                else
                {
                    //  Toast.makeText(Collectdel_cash_main_admin.this  , "not Cash received", Toast.LENGTH_SHORT).show();


                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }    class GetAllOrders_Async extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(SkyEatAdminMain_OrderDetails.this);
//            dialog.setMessage("Getting all orders");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getAllOrders_Async.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //   dialog.dismiss();
            if (result == null || result.trim().length() <= 0) {
                Toast.makeText(DeliveryBoyMainActivity.this, "No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }

            try {
                JSONObject jsonObject = new JSONObject(result);
                if (jsonObject.getString("result").equals("true")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        final String driver_id=jobj.getString("driver_id");
                        final String block=jobj.getString("block");
                        final String livestatus=jobj.getString("livestatus");
                        if (livestatus.equals("true"))
                        {
                            switch1.setChecked(true);
                        }
                        else {
                            switch1.setChecked(false);
                        }

                        if (block.equals("1"))
                        {
                            Intent intent=new Intent(DeliveryBoyMainActivity.this, blockactivity.class);
                             startActivity(intent);
                             finish();

                        }
                        else {

                        }

//                    Intent intent=new Intent(Collectdel_cash_main_admin.this,HomeActivity.class);
//                    startActivity(intent);
                     //   Toast.makeText(DeliveryBoyMainActivity.this, "Drive Mode Is Off", Toast.LENGTH_SHORT).show();
                        //  finish();


                    }
                } else {
                    //  Toast.makeText(Collectdel_cash_main_admin.this  , "not Cash received", Toast.LENGTH_SHORT).show();


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    class UpdateToken extends AsyncTask<String, Void, String> {
        ProgressDialog dialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            dialog=new ProgressDialog(CategoriesActivity.this);
//            dialog.setMessage("Getting trending menus");
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    getcoupon.cancel(true);
//                    finish();
//                }
//            });
//            dialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            return jsonParser.doGetRequest(params[0]);
        }
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            //    dialog.dismiss();
            if(result==null||result.trim().length()<=0){
                Toast.makeText(DeliveryBoyMainActivity.this,"No response from server, Please check your internet connection", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                JSONObject jsonObject=new JSONObject(result);
                if(jsonObject.getString("result").equals("true")){
                    JSONArray jsonArray1=jsonObject.getJSONArray("data");
                    for(int j=0;j<jsonArray1.length();j++){
                        JSONObject jobj1=jsonArray1.getJSONObject(j);
                        Toast.makeText(DeliveryBoyMainActivity.this, "Success", Toast.LENGTH_SHORT).show();


                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}