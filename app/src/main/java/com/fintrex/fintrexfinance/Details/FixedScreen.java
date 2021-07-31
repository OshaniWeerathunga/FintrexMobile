package com.fintrex.fintrexfinance.Details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.Common.LoginScreen;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.Config;
import com.fintrex.fintrexfinance.HelperClass.Fd;
import com.fintrex.fintrexfinance.HelperClass.FdAdapter;
import com.fintrex.fintrexfinance.HelperClass.Lease;
import com.fintrex.fintrexfinance.HelperClass.LeaseAdapter;
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

    String nic;
    ImageView fdback;
    ProgressDialog progressDialog;

    String ServerLoginURL = "http://202.124.175.29/Fintrex_Mobile/indexControl/getFixedAmtData?";
    URL url;
    String finalResult ;

    RecyclerView recyclerView;
    FdAdapter adapter;

    List<Fd> fdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixed_screen);

        fdList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        nic=intent.getStringExtra(HomeScreen.Nic);

        //back to home
        fdback=findViewById(R.id.fdback);
        fdback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //call method to get fd data
        GetFdData();
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