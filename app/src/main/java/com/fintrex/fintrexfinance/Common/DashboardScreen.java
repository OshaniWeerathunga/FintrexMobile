package com.fintrex.fintrexfinance.Common;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fintrex.fintrexfinance.QuickLinks.About;
import com.fintrex.fintrexfinance.QuickLinks.Branches;
import com.fintrex.fintrexfinance.QuickLinks.Contacts;
import com.fintrex.fintrexfinance.QuickLinks.ExternalRequest;
import com.fintrex.fintrexfinance.QuickLinks.TermsConditions;
import com.fintrex.fintrexfinance.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class DashboardScreen extends AppCompatActivity {

    private BottomSheetBehavior bottomSheetBehavior;
    boolean expandbottom=false;
    Button login;
    TextView terms;
    LinearLayout branch,about,contact,rates,request,promotion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_screen);

        //init quick links
        branch=findViewById(R.id.branchlink);
        about=findViewById(R.id.aboutlink);
        contact=findViewById(R.id.contactlink);
        request=findViewById(R.id.requestlink);
        promotion=findViewById(R.id.promotionlink);
        terms=findViewById(R.id.terms);
        rates=findViewById(R.id.rateslink);

        //init login btn
        login=findViewById(R.id.loginbutton);

        //assign url for promotion and rates
        Uri promoUri = Uri.parse("https://fintrexfinance.com/promo");
        Uri ratesUri = Uri.parse("https://fintrexfinance.com/deposit-rates");

        //direct to the login page
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        //direct to the website rate page
        rates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,ratesUri);
                startActivity(intent);
            }
        });

        //direct to the terms and conditions page
        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardScreen.this, TermsConditions.class);
                startActivity(intent);
            }
        });

        //direct to the contant page
        contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardScreen.this, Contacts.class);
                startActivity(intent);
            }
        });

        //direct to the about page
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardScreen.this, About.class);
                startActivity(intent);
            }
        });

        //direct to the branch page
        branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardScreen.this, Branches.class);
                startActivity(intent);
            }
        });

        //direct to the request page
        request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DashboardScreen.this, ExternalRequest.class);
                startActivity(intent);
            }
        });

        //direct to the promotion site through the url
        promotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,promoUri);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finishAffinity();
        //Intent intent = new Intent(Intent.ACTION_MAIN);
        //intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivity(intent);
    }


}