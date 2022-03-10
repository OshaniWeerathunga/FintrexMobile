package com.fintrex.fintrexfinance.QuickLinks;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.H.SliderAdp;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FdAndSavings extends BaseActivity {

    ImageView back,newsaving,newFd;
    String PromotionURL = "https://online.fintrexfinance.com/loginControl/getPromotionBanners?";
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String finalResult ;
    SliderView sliderView;
    List<String> proimage = new ArrayList<String>();
    //int[] images = {R.drawable.slide2,R.drawable.slide3};
    String[] images = new String[proimage.size()];
    SliderAdp sliderAdp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fd_and_savings);

        //display slider banner
        GetPromotionIDFunction();

        back = findViewById(R.id.newAccountback);
        newFd = findViewById(R.id.newFdCreate);
        newsaving = findViewById(R.id.newSavingCreate);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FdAndSavings.this, DashboardScreen.class);
                startActivity(intent);
                finish();
            }
        });

        newFd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FdAndSavings.this, NewFdCreate.class);
                startActivity(intent);
            }
        });

        newsaving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewSavingCreate.class);
                startActivity(intent);
            }
        });

    }

    public void GetPromotionIDFunction(){

        class ProfileFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                System.out.printf("Promotion code httpreuest : ");
                System.out.println(httpResponseMsg);

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject leaseObject = array.getJSONObject(i);

                        String bannerId = leaseObject.getString("banner_id");

                        String promotion = "https://online.fintrexfinance.com/getBanner?id="+bannerId;

                        proimage.add(promotion);

                    }
                    //images=proimage.toArray(new String[array.length()]);
                    String[] images = new String[proimage.size()];

                    for (int j=0;j<proimage.size();j++){
                        images[j] = proimage.get(j);
                    }

                    System.out.printf("Promotion code httpreuest : ");
                    System.out.println(images);

                    //asign slider variable
                    sliderView=findViewById(R.id.slider);
                    // init adapter
                    sliderAdp = new SliderAdp(images);
                    //set adapter
                    sliderView.setSliderAdapter(sliderAdp);
                    //set indicator animation
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                    //set transformation
                    sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                    //start autocycle
                    sliderView.startAutoCycle();


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(PromotionURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult = PostRequest.getData(url);

                return finalResult;
            }
        }

        ProfileFunctionClass profileFunctionClass = new ProfileFunctionClass();
        profileFunctionClass.execute();
    }

}