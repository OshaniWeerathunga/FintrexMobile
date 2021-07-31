package com.fintrex.fintrexfinance.Common;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.Otp;
import com.fintrex.fintrexfinance.QuickLinks.About;
import com.fintrex.fintrexfinance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class LoginScreen extends AppCompatActivity {

    EditText name,nic,phone;
    ImageView loginBack;
    Button login;

    String NameHolder,NicHolder,PhoneHolder;
    ProgressDialog progressDialog;

    String termsAccept="2";
    String device = "Mobile";

    boolean CheckEditText ;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String ServerLoginURL = "http://202.124.175.29/Fintrex_Mobile/loginControl/login?";
    String TermsAcceptURL = "http://202.124.175.29/Fintrex_Mobile/loginControl/checkTermsConditions?";
    public final static String Nic = "nic";
    public final static String Name = "name";
    public final static String Phone = "phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        nic=findViewById(R.id.nic_et);
        name=findViewById(R.id.name_et);
        phone=findViewById(R.id.phone_et);
        login=findViewById(R.id.requestOtp_btn);

        loginBack = findViewById(R.id.aboutback);

        loginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backhome = new Intent(LoginScreen.this, DashboardScreen.class);
                startActivity(backhome);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                GetCheckEditTextIsEmptyOrNot();

                if(CheckEditText){
                    TermsFunction(NicHolder);
                    //LoginFunction(NicHolder, PhoneHolder, NameHolder);
                }
                else {

                    Toast.makeText(LoginScreen.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }


    public void GetCheckEditTextIsEmptyOrNot(){

        NameHolder = name.getText().toString();
        NicHolder = nic.getText().toString().trim();
        PhoneHolder = phone.getText().toString().trim();

        if(!validateNic() | !validatePhone() | !validateName())
        {

            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }

    public void TermsFunction(final String nic){

        class TermsFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(LoginScreen.this);
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
                    if (jsonObject.getString("status").equals("user_not_agreed")) {

                        ShowStartDialog();

                    }else if (jsonObject.getString("status").equals("user_agreed")){

                        LoginFunction(NicHolder, PhoneHolder, NameHolder, termsAccept, device);
                    }
                }catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(TermsAcceptURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("nic",params[0]);

                finalResult =PostRequest.postRequest(url,hashMap);

                System.out.printf("user sent");
                System.out.println(hashMap);

                return finalResult;
            }
        }

        TermsFunctionClass termsFunctionClass = new TermsFunctionClass();
        termsFunctionClass.execute(nic);
    }

    public void LoginFunction(final String nic, final String mobile, final String username, final String termsAccept, final String device){

        class LoginFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(LoginScreen.this);
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
                    if (jsonObject.getString("status").equals("nic_ok")) {

                            Intent intent = new Intent(LoginScreen.this, Otp.class);
                            intent.putExtra(Nic, NicHolder);
                            intent.putExtra(Name, NameHolder);
                            intent.putExtra(Phone, PhoneHolder);
                            startActivity(intent);
                            finish();

                    } else {

                        Toast.makeText(LoginScreen.this, "Fields not Matched. Please Check Again", Toast.LENGTH_LONG).show();
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

                hashMap.put("nic",params[0]);

                hashMap.put("mobile",params[1]);

                hashMap.put("user_name",params[2]);

                hashMap.put("terms_condition_check",params[3]);

                hashMap.put("device",params[4]);

                System.out.printf("user sent");
                System.out.println(hashMap);

                finalResult =PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        LoginFunctionClass loginFunctionClass = new LoginFunctionClass();
        loginFunctionClass.execute(nic,mobile,username,termsAccept,device);
    }

    private boolean validateNic(){
        String val = nic.getText().toString();

        if(val.isEmpty()){
            nic.setError("Field cannot be empty");
            return false;
        }else{
            nic.setError(null);
            return true;
        }

    }

    private boolean validateName(){
        String val = name.getText().toString();

        if(val.isEmpty()){
            name.setError("Field cannot be empty");
            return false;
        }else{
            name.setError(null);
            return true;
        }

    }

    private boolean validatePhone(){
        String val = phone.getText().toString();

        if(val.isEmpty()){
            phone.setError("Field cannot be empty");
            return false;
        }else{
            phone.setError(null);
            return true;
        }

    }

    private void ShowStartDialog(){

        final Dialog dialog = new Dialog(LoginScreen.this);
        dialog.setContentView(R.layout.terms_accept_alert);

        CheckBox accept = dialog.findViewById(R.id.accepted);
        final Button okterms = dialog.findViewById(R.id.okbtnterms);

        okterms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (termsAccept=="1"){

                    LoginFunction(NicHolder, PhoneHolder, NameHolder, termsAccept, device);
                    dialog.dismiss();

                }else if (termsAccept=="2"){

                    dialog.dismiss();
                }
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (accept.isChecked()){
                    //okterms.setEnabled(true);
                    termsAccept="1";
                    okterms.setBackgroundColor(getResources().getColor(android.R.color.holo_green_dark));
                }
            }
        });

        dialog.show();

    }
}