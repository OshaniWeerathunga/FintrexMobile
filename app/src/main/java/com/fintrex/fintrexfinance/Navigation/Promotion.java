package com.fintrex.fintrexfinance.Navigation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.HelperClass.PromotionAdapter;
import com.fintrex.fintrexfinance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Promotion extends BaseActivity {

    String ServerLoginURL = "https://online.fintrexfinance.com/loginControl/getPromotionBanners?";
    URL url;
    String finalResult ;
    ImageView proback;
    ProgressDialog progressDialog;
    RecyclerView recyclerView;
    //FdAdapter adapter;

    List<com.fintrex.fintrexfinance.HelperClass.Promotion> prolist;
    List<String> proimage = new ArrayList<String>();
    PromotionAdapter promotionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);

        recyclerView = (RecyclerView) findViewById(R.id.promotionrecyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prolist = new ArrayList<>();

        //back to home
        proback = findViewById(R.id.promotionback);
        proback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //call method to get promotion id data
        GetPromotionId();
    }

    public void GetPromotionId(){

        class FixedFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(Promotion.this);
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
                        JSONObject leaseObject = array.getJSONObject(i);

                        String bannerId = leaseObject.getString("banner_id");

                        String promotion = "https://online.fintrexfinance.com/getBanner?id="+bannerId;

                        proimage.add(promotion);

                        com.fintrex.fintrexfinance.HelperClass.Promotion pro = new com.fintrex.fintrexfinance.HelperClass.Promotion(promotion);
                        prolist.add(pro);
                    }

                    promotionAdapter = new PromotionAdapter(Promotion.this,prolist);
                    recyclerView.setAdapter(promotionAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Promotion.this,"Please Check Your Connection", Toast.LENGTH_LONG).show();

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