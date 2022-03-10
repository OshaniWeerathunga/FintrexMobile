package com.fintrex.fintrexfinance.HelperClass;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.MyApp;

import java.net.MalformedURLException;
import java.net.URL;

public class BaseActivity extends AppCompatActivity implements LogoutListner {

    String LogoutURL = "https://online.fintrexfinance.com/logout";
    URL url;
    String finalResult ;
    public static final int LENGTH_LONG = 2;

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
        //finish();
        Logout();
    }


    public void Logout(){
        class LogoutFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                if(httpResponseMsg.equals("ok") || (httpResponseMsg.equals("auth_fail"))){
                    Intent intent = new Intent(getApplicationContext(), DashboardScreen.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(BaseActivity.this, "Your Session has expired.", Toast.LENGTH_LONG).show();

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
