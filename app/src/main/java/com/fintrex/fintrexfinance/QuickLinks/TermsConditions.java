package com.fintrex.fintrexfinance.QuickLinks;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.R;

public class TermsConditions extends BaseActivity {

    ImageView termsback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_conditions);

        termsback = findViewById(R.id.externalTermsback);

        termsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TermsConditions.this, DashboardScreen.class);
                startActivity(intent);
            }
        });
    }
}