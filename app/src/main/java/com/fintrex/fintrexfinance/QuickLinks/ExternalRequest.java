package com.fintrex.fintrexfinance.QuickLinks;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.Details.MailScreen;
import com.fintrex.fintrexfinance.Details.NewMailSend;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class ExternalRequest extends AppCompatActivity  {

    String mobileHolder,msgHolder,nameHolder;
    Button send;
    ImageView back;
    private Spinner spinner;
    Dialog dialog;

    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String ServerMessageURL = "http://202.124.175.29/Fintrex_Mobile/loginControl/saveExternalRequest?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);

        EditText sendermobile = (EditText) findViewById(R.id.senderMobile);
        EditText sendername = (EditText) findViewById(R.id.senderName);
        EditText msg = (EditText) findViewById(R.id.msg);
        send = findViewById(R.id.sendButton);
        back = findViewById(R.id.messageback);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mobileHolder = sendermobile.getText().toString().trim();
                nameHolder = sendername.getText().toString();
                msgHolder = msg.getText().toString();

                if(msgHolder.isEmpty()&&mobileHolder.isEmpty()&&nameHolder.isEmpty()) {
                    msg.setError("Field cannot be empty");
                    sendermobile.setError("Field cannot be empty");
                    sendername.setError("Field cannot be empty");
                }
                else {
                    if (PhoneValidation(mobileHolder)){
                        Toast.makeText(ExternalRequest.this, "Invalid Mobile number", Toast.LENGTH_LONG).show();
                        sendermobile.setError("Invalid Mobile number");

                    }
                    SendMsgFunction(nameHolder, mobileHolder, msgHolder);
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
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        //init alert box ok button
        Button ok = dialog.findViewById(R.id.okbtn);
        //when user click ok btn
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent godash = new Intent(ExternalRequest.this, DashboardScreen.class);
                startActivity(godash);
                finish();
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
                    Toast.makeText(ExternalRequest.this, httpResponseMsg, Toast.LENGTH_LONG).show();
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
        String mobilepattern = "[0-9]{10}";
        return Pattern.compile(mobilepattern).matcher(usermobile).matches();
    }

}