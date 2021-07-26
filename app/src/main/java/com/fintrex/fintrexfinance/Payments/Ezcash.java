package com.fintrex.fintrexfinance.Payments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.R;

public class Ezcash extends BaseActivity {

    ImageView ezback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ezcash);

        ezback=findViewById(R.id.ezback);

        ezback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}