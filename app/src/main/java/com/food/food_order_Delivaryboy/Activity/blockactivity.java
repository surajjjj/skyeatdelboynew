package com.food.food_order_Delivaryboy.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.food.food_order_Delivaryboy.R;

public class blockactivity extends AppCompatActivity {
TextView txtSubmit,txtCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blockactivity);
        txtSubmit=findViewById(R.id.txtCancel);
        txtCancel=findViewById(R.id.txtCancel);
        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://api.whatsapp.com/send?phone=+8767070841"));
                startActivity(intent);

            }
        });   txtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();

            }
        });
    }
}