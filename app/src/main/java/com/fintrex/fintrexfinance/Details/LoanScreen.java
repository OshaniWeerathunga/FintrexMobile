package com.fintrex.fintrexfinance.Details;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.Loan;
import com.fintrex.fintrexfinance.HelperClass.LoanAdapter;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LoanScreen extends BaseActivity {

    String nic,userLoan;
    ImageView loanback;
    CardView nodata;

    String ServerLoginURL = "https://online.fintrexfinance.com/indexControl/getLoanData?";
    URL url;
    String finalResult ;

    RecyclerView recyclerView;
    LoanAdapter adapter;

    List<Loan> loanList;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_screen);

        //screenshots not allowed
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);


        //get values from Home class
        Intent intent = getIntent();
        nic=intent.getStringExtra(HomeScreen.Nic);
        userLoan=intent.getStringExtra(HomeScreen.Loan);
        nodata=findViewById(R.id.loanNoDataCard);

        if (userLoan.equals("-")){
            nodata.setVisibility(View.VISIBLE);
        }
        else {
            loanList = new ArrayList<>();

            recyclerView = (RecyclerView) findViewById(R.id.loanrecyclerview);
            recyclerView.setHasFixedSize(true);

            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //call method to get loan data
            GetLoanData();
        }

        //back to the home
        loanback = findViewById(R.id.loanback);
        loanback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void GetLoanData(){

        class LoanFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(LoanScreen.this);
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
                        JSONObject loansObject = array.getJSONObject(i);

                        String loanNo = loansObject.getString("loanNo");
                        String loanOutstanding = loansObject.getString("tot_outstanding");
                        String loanInstallment = loansObject.getString("installment_amt");
                        String loanNextDue = loansObject.getString("next_duedate");
                        String loanAmount = loansObject.getString("loan_amt");
                        String loanmaturity = loansObject.getString("maturity_date");
                        String loanLastPayAmount = loansObject.getString("last_pay_amt");
                        String loanLastPayDate = loansObject.getString("last_pay_date");

                        Loan loan = new Loan(loanNo,loanOutstanding,loanInstallment,loanNextDue,loanAmount,loanmaturity,loanLastPayAmount,loanLastPayDate);
                        loanList.add(loan);
                    }
                    adapter = new LoanAdapter(LoanScreen.this,loanList);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoanScreen.this,"Please Check Your Connection", Toast.LENGTH_LONG).show();

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

        LoanFunctionClass loanFunctionClass = new LoanFunctionClass();
        loanFunctionClass.execute();
    }
}