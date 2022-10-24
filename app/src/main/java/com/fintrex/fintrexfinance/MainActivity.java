package com.fintrex.fintrexfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.Details.HomeScreen;
import com.fintrex.fintrexfinance.Details.NewMailSend;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIMER=3000;
    ImageView logo;
    Animation topAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hooks
        logo = findViewById(R.id.logo_img);

        //animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        //set Animation on elements
        logo.setAnimation(topAnimation);


        //set time to automatically redirect to the Dashboard
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), DashboardScreen.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_TIMER);


    }


}