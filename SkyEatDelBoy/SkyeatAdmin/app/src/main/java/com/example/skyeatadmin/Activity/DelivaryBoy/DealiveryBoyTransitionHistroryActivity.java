package com.example.skyeatadmin.Activity.DelivaryBoy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.skyeatadmin.Adapter.DeliveryBoy.TransitionAdapter;
import com.example.skyeatadmin.Model.Transition;
import com.example.skyeatadmin.R;
import com.example.skyeatadmin.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealiveryBoyTransitionHistroryActivity extends AppCompatActivity {

    RecyclerView lstTransition;
    TransitionAdapter transitionAdapter;
    ArrayList<Transition.Data> transitionList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealivery_boy_transition_histrory);
        lstTransition=findViewById(R.id.lstTransition);
        transitionList.clear();
        getTransition();
        LinearLayoutManager llm=new LinearLayoutManager(DealiveryBoyTransitionHistroryActivity.this,LinearLayoutManager.VERTICAL,false);
        lstTransition.setLayoutManager(llm);


    }
    public void getTransition()
    {
        try {
            RetrofitClient.getInstance().getMyApi().getDeliveryBoyTransition("get_delivery_boy_earning_transition",DeliveryBoyMainActivity.DRIVER_ID).enqueue(new Callback<Transition>() {
                @Override
                public void onResponse(Call<Transition> call, Response<Transition> response) {
                    assert response.body() != null;
                    transitionList= (ArrayList<Transition.Data>) response.body().getData();
                    if(transitionList.size()>0)
                    {
                        transitionAdapter=new TransitionAdapter(getApplicationContext(),transitionList);
                        lstTransition.setAdapter(transitionAdapter);
                    }
                }

                @Override
                public void onFailure(Call<Transition> call, Throwable t) {
                    Log.d("",t.getMessage());

                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}