package com.fintrex.fintrexfinance.QuickLinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.Details.HomeScreen;
import com.fintrex.fintrexfinance.Details.MailScreen;
import com.fintrex.fintrexfinance.R;

public class About extends AppCompatActivity {

    ImageView aboutBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutBack = findViewById(R.id.aboutback);

        aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backhome = new Intent(About.this, DashboardScreen.class);
                startActivity(backhome);
                finish();
            }
        });
    }
}