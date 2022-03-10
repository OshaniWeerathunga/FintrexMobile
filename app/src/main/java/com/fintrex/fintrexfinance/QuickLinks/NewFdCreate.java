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

public class NewFdCreate extends BaseActivity implements AdapterView.OnItemSelectedListener {

    Spinner interest,fdterm;
    ImageView newfdback;
    EditText fdnic,fdname,fdcity,fdmobile,fddeposit;
    Button submit;
    DatePickerDialog.OnDateSetListener setListener;
    String NicHolder,NameHolder,AddressHolder,MobileHolder,TermHolder="1",InterestHolder="1",DepositHolder,ReasonHolder;
    ProgressDialog progressDialog;
    URL url;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String SubmitFdUrl = "https://online.fintrexfinance.com/loginControl/saveFixedForm?";
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_fd_create);

        interest = findViewById(R.id.fdInterestSpinner);
        fdnic = findViewById(R.id.fdNic);
        fdname = findViewById(R.id.fdFullname);
        fdcity = findViewById(R.id.fdcity);
        fdmobile = findViewById(R.id.fdmobile);
        fdterm = findViewById(R.id.fdTermSpinner);
        fddeposit = findViewById(R.id.fdDepositAmount);
        submit = findViewById(R.id.fdSubmit);
        newfdback = findViewById(R.id.newfdback);

        //spinner setting for interest methods
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.newFdInterest,
                R.layout.spinner_colorbg);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        interest.setAdapter(adapter);
        interest.setOnItemSelectedListener(this);

        //spinner setting for terms
        ArrayAdapter<CharSequence>adapter2=ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.newFdTerm,
                R.layout.spinner_colorbg);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fdterm.setAdapter(adapter2);
        fdterm.setOnItemSelectedListener(this);

        //init dialogbox
        dialog = new Dialog(NewFdCreate.this);
        dialog.setContentView(R.layout.alert_newaccont_create);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            dialog.getWindow();
        }
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        //init alert box ok button
        Button ok = dialog.findViewById(R.id.okbtn);
        //when user click ok btn
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent godash = new Intent(NewFdCreate.this, FdAndSavings.class);
                startActivity(godash);
                dialog.dismiss();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                NicHolder = fdnic.getText().toString().trim();
                NameHolder = fdname.getText().toString().trim();
                AddressHolder = fdcity.getText().toString().trim();
                MobileHolder = fdmobile.getText().toString().trim();
                DepositHolder = fddeposit.getText().toString().trim();

                if(validateNic() & validateName() & validateAddress() & validateMobile() & validateDeposit())
                {
                    SubmitFdData(NicHolder,NameHolder,AddressHolder,MobileHolder,TermHolder, InterestHolder,DepositHolder);
                }

            }
        });

        newfdback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.fdInterestSpinner) {
            String dropdownInterest = parent.getItemAtPosition(position).toString();
            switch (dropdownInterest) {
                case "Monthly":
                    InterestHolder = "1";
                    break;
                case "Annually":
                    InterestHolder = "2";
                    break;
                case "Maturity":
                    InterestHolder = "3";
                    break;
            }
        }
        else if (parent.getId() == R.id.fdTermSpinner) {
            String dropdownTerm = parent.getItemAtPosition(position).toString();
            switch (dropdownTerm) {
                case "01 Month":
                    TermHolder = "1";
                    break;
                case "03 Months":
                    TermHolder = "2";
                    break;
                case "06 Months":
                    TermHolder = "3";
                    break;
                case "12 Months":
                    TermHolder = "4";
                    break;
                case "18 Months":
                    TermHolder = "5";
                    break;
                case "24 Months":
                    TermHolder = "6";
                    break;
                case "36 Months":
                    TermHolder = "7";
                    break;
                case "48 Months":
                    TermHolder = "8";
                    break;
                case "60 Months":
                    TermHolder = "9";
                    break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateNic(){
        String val = fdnic.getText().toString().trim();
        val = val.replace(" ","");
        Boolean patternnok = false;

        if(val.isEmpty()){
            fdnic.setError("Field cannot be empty");
            return false;
        }else{
            fdnic.setError(null);
            String oldpattern = "[0-9]{9}[vV]";
            String newpattern = "[0-9]{12}";
            patternnok = Pattern.compile(oldpattern).matcher(val).matches() | Pattern.compile(newpattern).matcher(val).matches();
            if (!patternnok){
                fdnic.setError("Your NIC is not valid");
                return false;
            }
            else {
                return true;
            }
        }

    }

    private boolean validateName(){
        String val = fdname.getText().toString().trim();
        val = val.replace(" ","");
        Boolean patternnok = false;

        if(val.isEmpty()){
            fdname.setError("Field cannot be empty");
            return false;
        }else{
            fdname.setError(null);
            String oldpattern = "[A-Za-z]{2,}";
            patternnok = Pattern.compile(oldpattern).matcher(val).matches();
            if (!patternnok){
                fdname.setError("Your Name is not valid");
                return false;
            }
            else {
                return true;
            }
        }

    }

    private boolean validateAddress(){
        String val = fdcity.getText().toString();
        val = val.replace(" ","");
        Boolean patternnok = false;

        if(val.isEmpty()){
            fdcity.setError("Field cannot be empty");
            return false;
        }else{
            fdcity.setError(null);
            String oldpattern = "[A-Za-z]{2,}";
            patternnok = Pattern.compile(oldpattern).matcher(val).matches();
            if (!patternnok){
                fdcity.setError("Your City is not valid");
                return false;
            }
            else {
                return true;
            }
        }

    }

    private boolean validateMobile(){
        String val = fdmobile.getText().toString().trim();
        Boolean patternok=false;

        if(val.isEmpty()){
            fdmobile.setError("Field cannot be empty");
            return false;
        }else{
            fdmobile.setError(null);
            String mobilepattern = "[0-9]{10}";
            patternok = Pattern.compile(mobilepattern).matcher(val).matches();
            if (!patternok) {
                fdmobile.setError("Your Mobile is not valid");
                return false;
            }else {
                return true;
            }
        }

    }

    private boolean validateDeposit(){
        String val = fddeposit.getText().toString();

        if(val.isEmpty()){
            fddeposit.setError("Field cannot be empty");
            return false;
        }else{
            fddeposit.setError(null);
            return true;
        }

    }

    public void SubmitFdData(final String nic, final String name, final String address, final String mobile,final String term,final String interest,final String deposit){

        class FdDataClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(NewFdCreate.this);
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
                    url = new URL(SubmitFdUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("fixed_form_nic",params[0]);
                hashMap.put("fixed_form_fullname",params[1]);
                hashMap.put("fixed_form_city",params[2]);
                hashMap.put("fixed_form_mobile",params[3]);
                hashMap.put("fixed_form_term",params[4]);
                hashMap.put("fixed_form_interest_payments",params[5]);
                hashMap.put("fixed_form_deposit_amt",params[6]);

                System.out.printf("user sent");
                System.out.println(hashMap);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        FdDataClass fdDataClass = new FdDataClass();
        fdDataClass.execute(nic,name,address,mobile,term,interest,deposit);
    }

}