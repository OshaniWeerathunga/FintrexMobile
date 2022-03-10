package com.fintrex.fintrexfinance.QuickLinks;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.FdAdapter;
import com.fintrex.fintrexfinance.R;

public class About extends BaseActivity {

    ImageView aboutBack;
    TextView sinhala;
    FdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        aboutBack = findViewById(R.id.aboutback);

        aboutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

}
