package com.fintrex.fintrexfinance;

import android.app.Application;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.LogoutListner;

import java.sql.Time;
import java.util.Timer;
import java.util.TimerTask;

public class MyApp extends Application {

    private LogoutListner listner;
    private Timer timer;

    public void startUserSession() {
        cancelTimer();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                listner.onSessionLogout();
            }
        },1000*60*5);
    }

    private void cancelTimer() {
        if (timer != null) timer.cancel();
    }

    public void registerSessionListner(LogoutListner listner) {

        this.listner = listner;
    }

    public void onUserInteracted() {
        startUserSession();
    }
}
