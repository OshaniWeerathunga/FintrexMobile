package com.fintrex.fintrexfinance.QuickLinks;

import android.app.DatePickerDialog;
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

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.regex.Pattern;

public class NewSavingCreate extends BaseActivity implements AdapterView.OnItemSelectedListener {

    EditText savnic,savname,savcity,savmobile;
    Spinner savtype;
    ImageView newsavback;
    Button submit;
    DatePickerDialog.OnDateSetListener setListener;
    String NicHolder,NameHolder,AddressHolder,MobileHolder,TypeHolder="1";
    ProgressDialog progressDialog;
    URL url;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String SubmitSavingUrl="https://online.fintrexfinance.com/loginControl/saveSavingsForm?";
    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_saving_create);

        savnic = findViewById(R.id.savingNic);
        savname = findViewById(R.id.savingFullname);
        savcity = findViewById(R.id.savingaddress);
        savmobile = findViewById(R.id.savingmobile);
        savtype = findViewById(R.id.savAccountTypeSpinner);
        submit = findViewById(R.id.savSubmit);
        newsavback = findViewById(R.id.newsavback);

        //spinner setting for interest methods
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.newSacaccountType,
                R.layout.spinner_colorbg);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        savtype.setAdapter(adapter);
        savtype.setOnItemSelectedListener(this);

        //init dialogbox
        dialog = new Dialog(NewSavingCreate.this);
        dialog.setContentView(R.layout.alert_newaccont_create);
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
                dialog.dismiss();
                Intent gomail = new Intent(NewSavingCreate.this, FdAndSavings.class);
                startActivity(gomail);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NicHolder = savnic.getText().toString().trim();
                NameHolder = savname.getText().toString().trim();
                AddressHolder = savcity.getText().toString().trim();
                MobileHolder = savmobile.getText().toString().trim();

                if(validateNic() & validateName() & validateAddress() & validateMobile())
                {
                    SubmitSavingData(NicHolder,NameHolder,AddressHolder,MobileHolder,TypeHolder);
                }

            }
        });

        newsavback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private boolean validateNic(){
        String val = savnic.getText().toString().trim();
        val = val.replace(" ","");
        Boolean patternnok = false;

        if(val.isEmpty()){
            savnic.setError("Field cannot be empty");
            return false;
        }else{
            savnic.setError(null);
            String oldpattern = "[0-9]{9}[vV]";
            String newpattern = "[0-9]{12}";
            patternnok = Pattern.compile(newpattern).matcher(val).matches() || Pattern.compile(oldpattern).matcher(val).matches();

            if (!patternnok){
                savnic.setError("Your NIC is not valid");
                return false;
            }
            else {
                return true;
            }
        }

    }

    private boolean validateName(){
        String val = savname.getText().toString();
        val = val.replace(" ","");
        Boolean patternnok = false;

        if(val.isEmpty()){
            savname.setError("Field cannot be empty");
            return false;
        }else{
            savname.setError(null);
            String oldpattern = "[A-Za-z]{2,}";
            patternnok = Pattern.compile(oldpattern).matcher(val).matches();
            if (!patternnok){
                savname.setError("Your Name is not valid");
                return false;
            }
            else {
                return true;
            }
        }

    }

    private boolean validateAddress(){
        String val = savcity.getText().toString();
        val = val.replace(" ","");
        Boolean patternnok = false;

        if(val.isEmpty()){
            savcity.setError("Field cannot be empty");
            return false;
        }else{
            savcity.setError(null);
            String oldpattern = "[A-Za-z]{2,}";
            patternnok = Pattern.compile(oldpattern).matcher(val).matches();
            if (!patternnok){
                savcity.setError("Your City is not valid");
                return false;
            }
            else {
                return true;
            }
        }

    }

    private boolean validateMobile(){
        String val = savmobile.getText().toString().trim();
        boolean patternok = false;

        if(val.isEmpty()){
            savmobile.setError("Field cannot be empty");
            return false;
        }else{
            savmobile.setError(null);
            String mobilepattern = "[0-9]{10}";
            patternok = Pattern.compile(mobilepattern).matcher(val).matches();
            if (!patternok) {
                savmobile.setError("Your Mobile is not valid");
                return false;
            }else {
                return true;
            }
        }

    }

    public void SubmitSavingData(final String nic, final String name, final String address, final String mobile,final String type){

        class SavingDataClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(NewSavingCreate.this);
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

                        Toast.makeText(getApplicationContext(), "Account not created", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Please try again.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(SubmitSavingUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("savings_form_nic",params[0]);
                hashMap.put("savings_form_fullname",params[1]);
                hashMap.put("savings_form_city",params[2]);
                hashMap.put("savings_form_mobile",params[3]);
                hashMap.put("savings_form_account_type",params[4]);

                System.out.printf("user sent---");
                System.out.println(hashMap);

                finalResult = PostRequest.newAccount(url,hashMap);

                return finalResult;
            }
        }

        SavingDataClass savingDataClass = new SavingDataClass();
        savingDataClass.execute(nic,name,address,mobile,type);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String dropdownVal = parent.getItemAtPosition(position).toString();
        switch (dropdownVal){
            case "Saving Normal":
                TypeHolder="1";
                break;
            case "Senior Citizen":
                TypeHolder="2";
                break;
            case "Kids Savings":
                TypeHolder="3";
                break;
            case "Business Savings":
                TypeHolder="4";
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}