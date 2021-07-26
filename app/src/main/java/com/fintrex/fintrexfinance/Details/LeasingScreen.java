package com.fintrex.fintrexfinance.Details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
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

public class LeasingScreen extends BaseActivity {

    String nic;
    ImageView leaseback;
    ProgressDialog progressDialog;

    String ServerLoginURL = "http://202.124.175.29/Fintrex_Mobile/indexControl/getLeasingData?";
    URL url;
    String finalResult ;

    RecyclerView recyclerView;
    LeaseAdapter adapter;

    List<Lease> leaseList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leasing_screen);

        leaseList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        nic=intent.getStringExtra(HomeScreen.Nic);

        //back to the home
        leaseback=findViewById(R.id.leaseback);
        leaseback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //call method to get lease data
        GetLeaseData();

    }

    public void GetLeaseData(){

        class LeaseFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(LeasingScreen.this);
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

                //timeout riderect to the dashboard
                if (httpResponseMsg.equals("Something Went Wrong")){
                    Intent intent=new Intent(getApplicationContext(), DashboardScreen.class);
                    startActivity(intent);
                    finish();
                }

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject leaseObject = array.getJSONObject(i);

                        String leaseNo = leaseObject.getString("leasingNo");
                        String totalOutstanding = leaseObject.getString("tot_outstanding");
                        String nextPayDate = leaseObject.getString("duedate");
                        String maturityDate = leaseObject.getString("maturity_date");
                        String rental = leaseObject.getString("rental_amt");
                        String lastPayAmount = leaseObject.getString("last_pay_amt");
                        String lastPayDate = leaseObject.getString("last_pay_date");
                        String vehicleNo = leaseObject.getString("vehicle_no");
                        String insuranceExpire = leaseObject.getString("insurance_date");

                        Lease lease = new Lease(leaseNo,totalOutstanding,nextPayDate,maturityDate,rental,lastPayAmount,lastPayDate,vehicleNo,insuranceExpire);
                        leaseList.add(lease);
                    }
                    adapter = new LeaseAdapter(LeasingScreen.this,leaseList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LeasingScreen.this,"Cannot Load Data. Please Check your Connection", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerLoginURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult =PostRequest.getData(url);

                return finalResult;
            }
        }

        LeaseFunctionClass leaseFunctionClass = new LeaseFunctionClass();
        leaseFunctionClass.execute();
    }

}