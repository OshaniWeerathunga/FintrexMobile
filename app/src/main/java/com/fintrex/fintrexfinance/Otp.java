package com.fintrex.fintrexfinance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.Common.LoginScreen;
import com.fintrex.fintrexfinance.Details.HomeScreen;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HttpsURLConnection;

public class Otp extends BaseActivity {

    ProgressDialog progressDialog;
    TextView timer;

    // buttons for generating OTP and verifying OTP
    private Button verifyOTPBtn;

    PinView userpin;

    PinView pinView;

    String OtpHolder="default";
    String userLeasing, userFixed, userLoans, userSaving, username, userlastlogin ;

    String NameHolder,NicHolder,PhoneHolder;

    boolean otpok = false ;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String ServerLoginURL = "https://online.fintrexfinance.com/loginControl/check_otp?";
    String HomeURL = "https://online.fintrexfinance.com/indexControl/getCustomerData?";
    String FinalHttpData = "";
    BufferedWriter bufferedWriter ;
    BufferedReader bufferedReader ;
    OutputStream outputStream ;
    StringBuilder stringBuilder = new StringBuilder();
    PostRequest passrequest = new PostRequest();
    String Result;
    public final static String Nic = "nic";
    public final static String Name = "name";
    public final static String Leasing = "lease";
    public final static String Loans ="loans";
    public final static String Fixed ="fixed";
    public final static String Saving ="save";
    public final static String User ="username";
    public final static String Login ="login";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        //screenshots not allowed
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);


        // initializing variables for button and Edittext.
        userpin = findViewById(R.id.pin);
        verifyOTPBtn = findViewById(R.id.idBtnVerify);
        timer = findViewById(R.id.timer);

        //init timer duration
        long duration = TimeUnit.MINUTES.toMillis(2);

        //init coundown
        new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //when tick
                //convert milisecond to minutes and second
                String sDuration = String.format(Locale.ENGLISH,"Time remaining %120d sec"
                ,TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)
                //,TimeUnit.MINUTES.toSeconds(millisUntilFinished)-
                        //TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished))
                );

                //set converted string on text view
                timer.setText(sDuration);
            }

            @Override
            public void onFinish() {
                if (otpok) {
                    timer.setVisibility(View.GONE);
                }else{
                    Intent intent = new Intent(getApplicationContext(), DashboardScreen.class);
                    startActivity(intent);
                    finish();
                }


            }
        }.start();

        //get username and nic
        Intent intent = getIntent();
        NicHolder=intent.getStringExtra(LoginScreen.Nic);
        NameHolder=intent.getStringExtra(LoginScreen.Name);
        PhoneHolder=intent.getStringExtra(LoginScreen.Phone);

        // initializing on click listener
        // for verify otp button
        verifyOTPBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtpHolder=userpin.getText().toString();
                if (TextUtils.isEmpty(userpin.getText().toString())) {
                     Toast.makeText(Otp.this, "Please enter OTP", Toast.LENGTH_SHORT).show();
                } else {

                    OtpFunction(OtpHolder);
                }


            }
        });

    }

    public void OtpFunction(final String userOTP){

        class OtpFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(Otp.this);
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
                    if (jsonObject.getString("status").equals("OTP_confirm")) {

                        //check otp status to timer visibibility
                        otpok = true;

                        GetDataFunction();

                    } else if(jsonObject.getString("status").equals("send_again")) {

                        Intent intent = new Intent(getApplicationContext(), DashboardScreen.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Toast.makeText(Otp.this, "OTP is incorrect", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerLoginURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("ent_otp",params[0]);

                System.out.printf("user sent otp");
                System.out.println(hashMap);

                finalResult =PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        OtpFunctionClass otpFunctionClass = new OtpFunctionClass();
        otpFunctionClass.execute(userOTP);
    }

    public void GetDataFunction(){

        class LoginFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(Otp.this);
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
                    if(jsonObject!=null) {
                        userLeasing = jsonObject.getString("Leasing_Amount");
                        userFixed = jsonObject.getString("Fixed_Amount");
                        userLoans = jsonObject.getString("Loan_Amount");
                        userSaving = jsonObject.getString("Saving_Amount");
                        username = jsonObject.getString("user");
                        userlastlogin = jsonObject.getString("Last_login");

                        showJSON();
                    }
                    else{
                        Toast.makeText(Otp.this,"Cannot Load Data.Please Check your connection", Toast.LENGTH_LONG).show();
                    }


                }catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(HomeURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult =PostRequest.getData(url);

                return finalResult;
            }
        }

        LoginFunctionClass loginFunctionClass = new LoginFunctionClass();
        loginFunctionClass.execute();
    }

    public void showJSON(){

        Intent intent = new Intent(getApplicationContext(), HomeScreen.class);
        intent.putExtra(Leasing,userLeasing);
        intent.putExtra(Fixed,userFixed);
        intent.putExtra(Loans,userLoans);
        intent.putExtra(Saving,userSaving);
        intent.putExtra(User,username);
        intent.putExtra(Nic,NicHolder);
        intent.putExtra(Name,NameHolder);
        intent.putExtra(Login,userlastlogin);
        startActivity(intent);
        finish();

    }

    public class OtpParseClass {

        public String postRequest(HashMap<String,String> Data) {

            try {
                url = new URL(ServerLoginURL);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(60000);

                httpURLConnection.setConnectTimeout(60000);

                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoInput(true);

                httpURLConnection.setDoOutput(true);

                outputStream = httpURLConnection.getOutputStream();

                bufferedWriter = new BufferedWriter(

                        new OutputStreamWriter(outputStream, "UTF-8"));

                bufferedWriter.write(FinalDataParse(Data));

                bufferedWriter.flush();

                bufferedWriter.close();

                outputStream.close();

                if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                    bufferedReader = new BufferedReader(
                            new InputStreamReader(
                                    httpURLConnection.getInputStream()
                            )
                    );
                    FinalHttpData = bufferedReader.readLine();
                }
                else {
                    FinalHttpData = "Something Went Wrong";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return FinalHttpData;
        }

        public String FinalDataParse(HashMap<String, String> hashMap2) throws UnsupportedEncodingException {

            for(Map.Entry<String, String> map_entry : hashMap2.entrySet()){

                stringBuilder.append("&");

                stringBuilder.append(URLEncoder.encode(map_entry.getKey(), "UTF-8"));

                stringBuilder.append("=");

                stringBuilder.append(URLEncoder.encode(map_entry.getValue(), "UTF-8"));

            }

            Result = stringBuilder.toString();

            return Result ;
        }
    }


}