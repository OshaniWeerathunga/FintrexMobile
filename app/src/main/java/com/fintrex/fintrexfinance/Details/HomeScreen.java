package com.fintrex.fintrexfinance.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.Navigation.Promotion;
import com.fintrex.fintrexfinance.Otp;
import com.fintrex.fintrexfinance.Payments.Cargills;
import com.fintrex.fintrexfinance.Payments.Cdm;
import com.fintrex.fintrexfinance.Payments.Ezcash;
import com.fintrex.fintrexfinance.Payments.Online;
import com.fintrex.fintrexfinance.QuickLinks.About;
import com.fintrex.fintrexfinance.QuickLinks.Branches;
import com.fintrex.fintrexfinance.QuickLinks.Contacts;
import com.fintrex.fintrexfinance.QuickLinks.Rates;
import com.fintrex.fintrexfinance.R;
import com.google.android.material.navigation.NavigationView;
import com.zolad.zoominimageview.ZoomInImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;


public class HomeScreen extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView leasing,fixed,saving,loans,lc,headerusername,logout,homeusername,homeuserlogin;
    ProgressDialog progressDialog;
    //Button logout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView cardView1,cardView2,cardView3,cardView4,cardView5;
    ImageView menu, mail, cargills,cdm,ezcash,online;
    String name,lastlogin,nic,userlease,userfd,usersav,userloan;

    public final static String Nic="nic", P_name="name", P_address="address", P_mobile="mobile";
    public final static String Saving="save", Leasing="lease", Fixed="fd", Loan="loan", Gold="gold";

    LinearLayout contentView;

    String LogoutURL = "https://online.fintrexfinance.com/logout";
    String PromotionURL = "https://online.fintrexfinance.com/loginControl/getPromotionBanners?";
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String finalResult ;

    //assign url for promotion and rates
    Uri promoUri = Uri.parse("https://fintrexfinance.com/promo");
    Uri ratesUri = Uri.parse("https://fintrexfinance.com/deposit-rates");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //screenshots not allowed
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);


        //init variable and assign id
        menu=findViewById(R.id.menu);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        toolbar=findViewById(R.id.toolbar);
        contentView=findViewById(R.id.content);
        logout=findViewById(R.id.homelogout);
        mail=findViewById(R.id.mail);

        //payment methods
        cargills=findViewById(R.id.cargills);
        cdm=findViewById(R.id.cdm);
        ezcash=findViewById(R.id.ezcash);
        online=findViewById(R.id.online);

        //menu header init
        View header = navigationView.getHeaderView(0);
        headerusername = header.findViewById(R.id.headerNic);
        homeusername = findViewById(R.id.homeUserName);
        homeuserlogin = findViewById(R.id.homeUserLogin);

        //assign ids for details cards values
        leasing=findViewById(R.id.lease_amount);
        fixed=findViewById(R.id.fixed_amount);
        saving=findViewById(R.id.saving_amount);
        loans=findViewById(R.id.loans_amount);

        //assign ids for cards view
        cardView1=findViewById(R.id.cardView);
        cardView2=findViewById(R.id.cardView2);
        cardView3=findViewById(R.id.cardView3);
        cardView4=findViewById(R.id.cardView4);
        cardView5=findViewById(R.id.cardView5);

        //get values from OTP class
        Intent intent = getIntent();
        nic=intent.getStringExtra(Otp.Nic);
        name=intent.getStringExtra(Otp.User);
        lastlogin=intent.getStringExtra(Otp.Login);
        userlease=intent.getStringExtra(Otp.Leasing);
        userloan=intent.getStringExtra(Otp.Loans);
        usersav=intent.getStringExtra(Otp.Saving);
        userfd=intent.getStringExtra(Otp.Fixed);


        //asign values
        leasing.setText(userlease);
        fixed.setText(userfd);
        loans.setText(userloan);
        saving.setText(usersav);
        headerusername.setText(name);
        homeusername.setText("Welcome\n\n"+name);
        homeuserlogin.setText("Last Login\n\n"+lastlogin);

        //call navigation
        navigationDrawer();

        //click details cards for get more details
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, LeasingScreen.class);
                intent.putExtra(Nic,nic);
                intent.putExtra(Leasing,userlease);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, FixedScreen.class);
                intent.putExtra(Nic,nic);
                intent.putExtra(Fixed,userfd);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, LoanScreen.class);
                intent.putExtra(Nic,nic);
                intent.putExtra(Loan,userloan);
                startActivity(intent);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, SavingScreen.class);
                intent.putExtra(Nic,nic);
                intent.putExtra(Saving,usersav);
                startActivity(intent);
            }
        });
        cardView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = "1";

            }
        });

        //logout from home
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //direct to the mailscreen
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this,MailScreen.class);
                startActivity(intent);
            }
        });

        //click payment methods to get payment details
        cargills.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, Cargills.class);
                startActivity(intent);
            }
        });
        cdm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, Cdm.class);
                startActivity(intent);
            }
        });
        ezcash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, Ezcash.class);
                startActivity(intent);
            }
        });
        online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, Online.class);
                startActivity(intent);
            }
        });

    }

    private void navigationDrawer() {
        //navigation drawer menu
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    //when we select each items in a navigation
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_about:
                //GetProfileDataFunction();
                Intent intentabout=new Intent(HomeScreen.this, About.class);
                startActivity(intentabout);
                break;
            case R.id.nav_branches:
                Intent intentbranch=new Intent(HomeScreen.this, Branches.class);
                startActivity(intentbranch);
                break;
            case R.id.nav_contact:
                Intent intentcont=new Intent(HomeScreen.this, Contacts.class);
                startActivity(intentcont);
                break;
            case R.id.nav_fdrates:
                Intent intentrates=new Intent(HomeScreen.this, Rates.class);
                startActivity(intentrates);
                break;
            case R.id.nav_promotions:
                Intent intentpromotion=new Intent(HomeScreen.this, Promotion.class);
                startActivity(intentpromotion);
                break;
            case R.id.nav_logout:
                logout();
                break;
        }
        return true;
    }

    public void logout(){
        class LoginFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);
                //Toast.makeText(HomeScreen.this, httpResponseMsg, Toast.LENGTH_SHORT).show();

                if(httpResponseMsg.equals("ok") || (httpResponseMsg.equals("auth_fail"))){

                    Toast.makeText(HomeScreen.this, "Successfully Logout", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), DashboardScreen.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(HomeScreen.this, "Please Signout again...", Toast.LENGTH_SHORT).show();
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

        LoginFunctionClass loginFunctionClass = new LoginFunctionClass();
        loginFunctionClass.execute();

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(HomeScreen.this,"Please Logout..", Toast.LENGTH_LONG).show();
    }




}