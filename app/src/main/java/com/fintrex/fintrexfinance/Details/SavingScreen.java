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
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.HelperClass.Saving;
import com.fintrex.fintrexfinance.HelperClass.SavingAdapter;
import com.fintrex.fintrexfinance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SavingScreen extends BaseActivity {

    String nic;
    ImageView savback;

    String ServerLoginURL = "http://202.124.175.29/Fintrex_Mobile/indexControl/getSavingData?";
    URL url;
    String finalResult ;

    RecyclerView recyclerView;
    SavingAdapter adapter;

    List<Saving> savingList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saving_screen);

        savingList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        nic=intent.getStringExtra(HomeScreen.Nic);

        //back to home
        savback=findViewById(R.id.savback);
        savback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //call method to get saving values
        GetSavingData();
    }


    public void GetSavingData(){

        class SavingFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(SavingScreen.this);
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
                        JSONObject savingObject = array.getJSONObject(i);

                        String savingNo = savingObject.getString("savingNo");
                        String currentBalance = savingObject.getString("savingAmount");
                        String accountType = savingObject.getString("savingAccountType");

                        Saving saving = new Saving(savingNo,currentBalance,accountType);
                        savingList.add(saving);
                    }
                    adapter = new SavingAdapter(SavingScreen.this,savingList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(SavingScreen.this,"Please Check Your Connection", Toast.LENGTH_LONG).show();

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

        SavingFunctionClass savingFunctionClass = new SavingFunctionClass();
        savingFunctionClass.execute();
    }
}