package com.fintrex.fintrexfinance.Details;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.Fd;
import com.fintrex.fintrexfinance.HelperClass.FdAdapter;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FixedScreen extends BaseActivity {

    String nic,userFd;
    ImageView fdback;
    ProgressDialog progressDialog;
    CardView nodata;

    String ServerLoginURL = "https://online.fintrexfinance.com/indexControl/getFixedAmtData?";
    URL url;
    String finalResult ;

    RecyclerView recyclerView;
    FdAdapter adapter;

    List<Fd> fdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_screen);

        //screenshots not allowed
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);


        //get values from Home class
        Intent intent = getIntent();
        nic=intent.getStringExtra(HomeScreen.Nic);
        userFd=intent.getStringExtra(HomeScreen.Fixed);
        nodata=findViewById(R.id.fdNoDataCard);

        if (userFd.equals("-")){
            nodata.setVisibility(View.VISIBLE);
        }
        else {
            fdList = new ArrayList<>();

            recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //call method to get fd data
            GetFdData();
        }

        //back to home
        fdback = findViewById(R.id.fdback);
        fdback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void GetFdData(){

        class FixedFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(FixedScreen.this);
                progressDialog.show();
                progressDialog.setContentView(R.layout.progress_layout);
                progressDialog.getWindow().setBackgroundDrawableResource(
                        android.R.color.transparent
                );
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject fdObject = array.getJSONObject(i);


                        String fdNo = fdObject.getString("fdNo");
                        String fdValue = fdObject.getString("fdAmount");
                        String fdMaturity = fdObject.getString("fdmaturityDate");
                        String fdRenewal = fdObject.getString("renewal_indicator");
                        String fdRate = fdObject.getString("fdinterest");

                        Fd fd = new Fd(fdNo,fdValue,fdRate,fdMaturity,fdRenewal);
                        fdList.add(fd);
                    }
                    adapter = new FdAdapter(FixedScreen.this,fdList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FixedScreen.this,"Please Check Your Connection", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerLoginURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult = PostRequest.getData(url);

                return finalResult;
            }
        }

        FixedFunctionClass fixedFunctionClass = new FixedFunctionClass();
        fixedFunctionClass.execute();
    }

}