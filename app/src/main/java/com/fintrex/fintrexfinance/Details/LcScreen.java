package com.fintrex.fintrexfinance.Details;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fintrex.fintrexfinance.HelperClass.Config;
import com.fintrex.fintrexfinance.HelperClass.Fd;
import com.fintrex.fintrexfinance.HelperClass.FdAdapter;
import com.fintrex.fintrexfinance.HelperClass.Lc;
import com.fintrex.fintrexfinance.HelperClass.LcAdapter;
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

public class LcScreen extends AppCompatActivity {

    String nic;
    ImageView lcback;

    String ServerLoginURL = "http://202.124.175.29/Fintrex_Mobile/indexControl/getLCData?";
    URL url;
    String finalResult ;

    RecyclerView recyclerView;
    LcAdapter adapter;

    List<Lc> lcList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lc_screen);
        lcList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Intent intent = getIntent();
        nic=intent.getStringExtra(HomeScreen.Nic);
        lcback=findViewById(R.id.lcback);

        lcback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        GetLcData();
    }

    public void GetLcData(){

        class LcFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(Otp.this,"Processing.....",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject lcObject = array.getJSONObject(i);

                        String lcNo = lcObject.getString("lcNo");
                        String lcMaturity = lcObject.getString("lcMaturityDate");
                        String lcAmount = lcObject.getString("lcAmount");

                        Lc lc = new Lc(lcNo,lcAmount,lcMaturity);
                        lcList.add(lc);
                    }
                    adapter = new LcAdapter(LcScreen.this,lcList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LcScreen.this,httpResponseMsg, Toast.LENGTH_LONG).show();

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

        LcFunctionClass lcFunctionClass = new LcFunctionClass();
        lcFunctionClass.execute();
    }
}