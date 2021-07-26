package com.fintrex.fintrexfinance.HelperClass;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.Details.HomeScreen;
import com.fintrex.fintrexfinance.MyApp;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseActivity extends AppCompatActivity implements LogoutListner {

    String LogoutURL = "http://202.124.175.29/Fintrex_Mobile/logout";
    URL url;
    String finalResult ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((MyApp) getApplication()).registerSessionListner(this);
        ((MyApp) getApplication()).startUserSession();
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();

        ((MyApp) getApplication()).onUserInteracted();
    }

    @Override
    public void onSessionLogout() {
        finish();
        Logout();
    }

    public void Logout(){
        class LogoutFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                //progressDialog = ProgressDialog.show(Otp.this,"Processing.....",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //progressDialog.dismiss();
                if(httpResponseMsg=="auth_fail"){
                    Toast.makeText(BaseActivity.this, "Successfully Logout", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), DashboardScreen.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(BaseActivity.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                }


            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(LogoutURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult = PostRequest.logout(url);

                return finalResult;
            }
        }

        LogoutFunctionClass logoutFunctionClass = new LogoutFunctionClass();
        logoutFunctionClass.execute();

    }
}
