package com.fintrex.fintrexfinance.Details;

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
import android.widget.TextView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.QuickLinks.ExternalRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NewMailSend extends BaseActivity {

    String nicHolder,mobileHolder,msgHolder,typeHolder,toHolder="1";
    Button send;
    ImageView back;
    EditText type;
    TextView to;
    Dialog dialog;

    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String ServerMessageURL = "http://202.124.175.29/Fintrex_Mobile/indexControl/saveMsg?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        EditText msg = (EditText) findViewById(R.id.message);
        type = findViewById(R.id.messagetype);
        to = findViewById(R.id.messageto);
        send = findViewById(R.id.sendMail);
        back = findViewById(R.id.mailback);

        /*
        //loadSpinnerData
        LoadSpinnerDataFunction();

        //msg type categories
        List<String> categoriestype = new ArrayList<>();
        categoriestype.add("Request Message");
        categoriestype.add("Complaint Message");


        //msg to categories
        List<String> categoriesto = new ArrayList<>();
        categoriesto.add("Call Center");
        categoriesto.add("Branch Manager");

        //Style the spinnertype
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, R.layout.customize_spinner,categoriestype);
        //Dropdown layout style spinnertype
        dataAdapter.setDropDownViewResource(R.layout.customize_spinner_dropdown);

        //Style the spinnertype
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>(this, R.layout.customize_spinner,categoriesto);
        //Dropdown layout style spinnertype
        dataAdapter2.setDropDownViewResource(R.layout.customize_spinner_dropdown);

        //Attaching data adpter spinner to msgtype
        spinnertype.setAdapter(dataAdapter);
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Message Type"))
                {
                    //do nothing
                }
                else
                {
                    //selecting a spinner item
                    if(parent.getItemAtPosition(position).toString().equals("Complaint Message")){
                        typeHolder="1";
                    }
                    else if(parent.getItemAtPosition(position).toString().equals("Request Message")){
                        typeHolder="2";
                    }
                    //typeHolder = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    //Toast.makeText(parent.getContext(),"selected: " + typeHolder, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Attaching data adpter spinner to msgto
        spinnerto.setAdapter(dataAdapter2);
        spinnerto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Message Type"))
                {
                    //do nothing
                }
                else
                {
                    //selecting a spinner item
                    if(parent.getItemAtPosition(position).toString().equals("Call Center")){
                        toHolder="1";
                    }
                    else if(parent.getItemAtPosition(position).toString().equals("Branch Manager")){
                        toHolder="2";
                    }
                    //typeHolder = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    //Toast.makeText(parent.getContext(),"selected: " + typeHolder, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


         */

        //init dialogbox
        dialog = new Dialog(NewMailSend.this);
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
                Intent gomail = new Intent(NewMailSend.this,MailScreen.class);
                startActivity(gomail);
                finish();
            }
        });

        //send the new msg
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get a value of user's msg
                msgHolder = msg.getText().toString();
                typeHolder = type.getText().toString();
                //check msg is empty or not
                if(msgHolder.isEmpty()) {
                    msg.setError("Please Type Your Message");
                }
                if (typeHolder.isEmpty()){
                    type.setError("Please Enter the Message Subject");
                }
                if (!msgHolder.isEmpty()&&!typeHolder.isEmpty()){
                    //call send msg function
                    SendMsgFunction(typeHolder, msgHolder, toHolder);
                }
            }
        });

        //back to mailscreen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomail = new Intent(NewMailSend.this,MailScreen.class);
                startActivity(gomail);
                finish();
            }
        });
    }

    //send new message
    public void SendMsgFunction(final String subject,final String msg, final String to){

        class SendMessageFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(NewMailSend.this);
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

                        Toast.makeText(NewMailSend.this, "Message not sent", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    Toast.makeText(NewMailSend.this, "Please try again.Message not sent", Toast.LENGTH_LONG).show();
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

                hashMap.put("subject",params[0]);

                hashMap.put("msg_txt",params[1]);

                hashMap.put("msg_to",params[2]);

                System.out.printf("user sent");
                System.out.println(hashMap);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        SendMessageFunctionClass msgFunctionClass = new SendMessageFunctionClass();
        msgFunctionClass.execute(subject,msg,to);
    }

}