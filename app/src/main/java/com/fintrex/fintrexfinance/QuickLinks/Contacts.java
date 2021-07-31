package com.fintrex.fintrexfinance.QuickLinks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fintrex.fintrexfinance.R;

public class Contacts extends AppCompatActivity {

    ImageView fb, insta, linkdin, tweeter, back;
    TextView general,callcenter,city,kandy,kegalle,kiribath,dambulla,gampaha,kurune,kuliya,negombo,kalutara,matara;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        general = findViewById(R.id.general);
        callcenter = findViewById(R.id.callcenter);
        city = findViewById(R.id.city);
        kandy = findViewById(R.id.kandy);
        kegalle = findViewById(R.id.kegalle);
        kiribath = findViewById(R.id.kiribathgoda);
        dambulla = findViewById(R.id.dambulla);
        gampaha = findViewById(R.id.gampaha);
        kurune = findViewById(R.id.kurunegala);
        kuliya = findViewById(R.id.kuliyapitiya);
        negombo = findViewById(R.id.negombo);
        kalutara = findViewById(R.id.kaluatara);
        matara = findViewById(R.id.matara);

        fb = findViewById(R.id.fblink);
        insta = findViewById(R.id.instalink);
        linkdin = findViewById(R.id.linkdinlink);
        tweeter = findViewById(R.id.tweeterlink);
        back = findViewById(R.id.cback);

        Uri fbUri = Uri.parse("https://www.facebook.com/fintrexfinancelimited/");
        Uri youtubeUri = Uri.parse("https://youtube.com/c/FintrexFinance");
        Uri linkdinUri = Uri.parse("https://lk.linkedin.com/company/melsta-regal-finance-ltd");
        Uri tweeterUri = Uri.parse("https://twitter.com/fintrexfinance");

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,fbUri);
                startActivity(intent);
            }
        });

        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,youtubeUri);
                startActivity(intent);
            }
        });

        linkdin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,linkdinUri);
                startActivity(intent);
            }
        });

        tweeter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_VIEW,tweeterUri);
                startActivity(intent);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        general.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "0112977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94115977700";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        callcenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94117200100";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        kurune.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94377977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        kandy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94815200100";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        negombo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94317977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        kiribath.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94117977400";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        kalutara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94347977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        kegalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94357977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        kuliya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94377977966";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        gampaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94337977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        dambulla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94667977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });

        matara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+94417977977";
                Intent generalcall = new Intent(Intent.ACTION_CALL);
                generalcall.setData(Uri.parse("tel:"+number));
                startActivity(generalcall);
            }
        });
    }
}