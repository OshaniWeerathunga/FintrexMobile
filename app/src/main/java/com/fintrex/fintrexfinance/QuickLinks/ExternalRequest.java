package com.fintrex.fintrexfinance.QuickLinks;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ExternalRequest extends BaseActivity {

    String mobileHolder,msgHolder,nameHolder;
    Button send;
    ImageView back;
    private Spinner spinner;
    Dialog dialog;

    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String ServerMessageURL = "https://online.fintrexfinance.com/loginControl/saveExternalRequest?";
    EditText sendermobile,sendername,msg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        sendermobile = (EditText) findViewById(R.id.senderMobile);
        sendername = (EditText) findViewById(R.id.senderName);
        msg = (EditText) findViewById(R.id.msg);
        send = findViewById(R.id.sendButton);
        back = findViewById(R.id.messageback);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileHolder = sendermobile.getText().toString().trim();
                nameHolder = sendername.getText().toString().trim();
                msgHolder = msg.getText().toString();


                //get user entered msg by encoding
                try {
                    byte[] msgdata = (msg.getText().toString()).getBytes("UTF-8");
                    msgHolder = Base64.encodeToString(msgdata, Base64.DEFAULT);

                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }



                if(msgHolder.isEmpty()) {
                    msg.setError("Please Enter Your Message");
                }
                if (mobileHolder.isEmpty()) {
                    sendermobile.setError("Please Enter Your Mobile Number");
                }
                if (nameHolder.isEmpty()) {
                    sendername.setError("Please Enter Your Name");
                }
                if (!msgHolder.isEmpty()&& !mobileHolder.isEmpty()&& !nameHolder.isEmpty()) {
                    if (!(validateName()) || !(PhoneValidation(mobileHolder))) {
                        Toast.makeText(ExternalRequest.this, "Please Enter the Valid Information", Toast.LENGTH_LONG).show();
                    } else {
                        SendMsgFunction(nameHolder, mobileHolder, msgHolder);
                    }
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //init dialogbox
        dialog = new Dialog(ExternalRequest.this);
        dialog.setContentView(R.layout.alert_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.contactcard_bg));
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        //init alert box ok button
        Button ok = dialog.findViewById(R.id.okbtn);
        //when user click ok btn
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godash = new Intent(ExternalRequest.this, DashboardScreen.class);
                startActivity(godash);
                finish();
                dialog.dismiss();
            }
        });

    }

    public void SendMsgFunction(final String name,final String mobile, final String msg){

        class SendMessageFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(ExternalRequest.this);
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
                    if (jsonObject.getString("status").equals("saved")) {

                        dialog.show();

                    } else {

                        Toast.makeText(ExternalRequest.this, "Message not sent successfully", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    Toast.makeText(ExternalRequest.this, "Please try again. Message not sent.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerMessageURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("fullname",params[0]);

                hashMap.put("mobile",params[1]);

                hashMap.put("text_request",params[2]);

                System.out.printf("user sent");
                System.out.println(hashMap);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        SendMessageFunctionClass msgFunctionClass = new SendMessageFunctionClass();
        msgFunctionClass.execute(name,mobile,msg);
    }


    public boolean PhoneValidation(String usermobile){
        Boolean patternok = false;
        sendermobile.setError(null);
        String mobilepattern = "[0-9]{10}";
        patternok = Pattern.compile(mobilepattern).matcher(usermobile).matches();

        if (!patternok){
            sendermobile.setError("Your Phone no is not valid");
            return false;
        }
        else {
            return true;
        }
    }

    public boolean validateName(){
        String val = sendername.getText().toString().trim();
        val = val.replace(" ","");
        System.out.println(val);

        Boolean patternnok = false;
        sendername.setError(null);
        String oldpattern = "[A-Za-z]{2,}";
        patternnok = Pattern.compile(oldpattern).matcher(val).matches();

            if (!patternnok){
                sendername.setError("Your Name is not valid");
                return false;
            }
            else {
                return true;
            }


    }

}