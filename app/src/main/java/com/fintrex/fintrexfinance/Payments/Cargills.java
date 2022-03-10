package com.fintrex.fintrexfinance.Payments;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.R;

public class Cargills extends BaseActivity {

    ImageView cargillsback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargills);

        cargillsback=findViewById(R.id.cargillsback);

        cargillsback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}