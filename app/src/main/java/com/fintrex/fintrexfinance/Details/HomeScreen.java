package com.fintrex.fintrexfinance.Details;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.H.SliderAdp;
import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.LeaseAdapter;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.HelperClass.Promotion;
import com.fintrex.fintrexfinance.Navigation.Profile;
import com.fintrex.fintrexfinance.Otp;
import com.fintrex.fintrexfinance.Payments.Cargills;
import com.fintrex.fintrexfinance.Payments.Cdm;
import com.fintrex.fintrexfinance.Payments.Ezcash;
import com.fintrex.fintrexfinance.Payments.Online;
import com.fintrex.fintrexfinance.QuickLinks.Branches;
import com.fintrex.fintrexfinance.QuickLinks.Contacts;
import com.fintrex.fintrexfinance.R;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class HomeScreen extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    TextView leasing,fixed,saving,loans,lc,headerusername,logout;
    ProgressDialog progressDialog;
    //Button logout;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    CardView cardView1,cardView2,cardView3,cardView4;
    ImageView menu, mail, cargills,cdm,ezcash,online;
    String name,nic,userlease,userfd,usersav,userloan;

    public final static String Name = "name";
    public final static String Leasing = "lease";
    public final static String Loans ="loans";
    public final static String Fixed ="fixed";
    public final static String Saving ="save";
    public final static String Nic="nic", P_name="name", P_address="address", P_mobile="mobile";

    String profilenic,profileName,profilePhone,profileAddress;

    String lease, fd,loan,savings,lcs;
    Locale sriLocale = new Locale("en","LK");

    LinearLayout contentView;

    String ServerLoginURL = "http://202.124.175.29/Fintrex_Mobile/ProfileControl/getCustomerProfileData?";
    String HomeURL = "http://202.124.175.29/Fintrex_Mobile/indexControl/getCustomerData?";
    String LogoutURL = "http://202.124.175.29/Fintrex_Mobile/logout";
    String PromotionURL = "http://202.124.175.29/Fintrex_Mobile/loginControl/getPromotionBanners?";
    String PromotionImageURL = "http://202.124.175.29/Fintrex_Mobile/getBanner?";
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String finalResult ;

    SliderView sliderView;
    List<String> proimage = new ArrayList<String>();
    //String[] images = new String[proimage.size()];
    SliderAdp sliderAdp;

    List<Promotion> promotionList;
    RecyclerView recyclerView;
    LeaseAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        //call promotionBanner Ids
        GetPromotionIDFunction();

        //init variable and assign id
        menu=findViewById(R.id.menu);
        drawerLayout=findViewById(R.id.drawer_layout);
        navigationView=findViewById(R.id.navigation_view);
        toolbar=findViewById(R.id.toolbar);
        contentView=findViewById(R.id.content);
        logout=findViewById(R.id.logouttv);
        mail=findViewById(R.id.mail);

        //payment methods
        cargills=findViewById(R.id.cargills);
        cdm=findViewById(R.id.cdm);
        ezcash=findViewById(R.id.ezcash);
        online=findViewById(R.id.online);

        //menu header init
        View header = navigationView.getHeaderView(0);
        headerusername = header.findViewById(R.id.headerNic);

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

        //get values from OTP class
        Intent intent = getIntent();
        nic=intent.getStringExtra(Otp.Nic);
        name=intent.getStringExtra(Otp.Name);
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

        //call navigation
        navigationDrawer();

        //click details cards for get more details
        cardView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, LeasingScreen.class);
                intent.putExtra(Nic,nic);
                startActivity(intent);
            }
        });
        cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, FixedScreen.class);
                intent.putExtra(Nic,nic);
                startActivity(intent);
            }
        });
        cardView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, LoanScreen.class);
                intent.putExtra(Nic,nic);
                startActivity(intent);
            }
        });
        cardView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HomeScreen.this, SavingScreen.class);
                intent.putExtra(Nic,nic);
                startActivity(intent);
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

    public void GetPromotionIDFunction(){

        class ProfileFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject leaseObject = array.getJSONObject(i);

                        String bannerId = leaseObject.getString("banner_id");

                        String promotion = "http://202.124.175.29/Fintrex_Mobile/getBanner?id="+bannerId;

                        proimage.add(promotion);

                    }
                     //images=proimage.toArray(new String[array.length()]);
                    String[] images = new String[proimage.size()];

                    for (int j=0;j<proimage.size();j++){
                        images[j] = proimage.get(j);
                    }

                    //asign slider variable
                    sliderView=findViewById(R.id.slider);
                    // init adapter
                    sliderAdp = new SliderAdp(images);
                    //set adapter
                    sliderView.setSliderAdapter(sliderAdp);
                    //set indicator animation
                    sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
                    //set transformation
                    sliderView.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
                    //start autocycle
                    sliderView.startAutoCycle();


                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(PromotionURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult = PostRequest.getData(url);

                return finalResult;
            }
        }

        ProfileFunctionClass profileFunctionClass = new ProfileFunctionClass();
        profileFunctionClass.execute();
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
            case R.id.nav_profile:
                GetProfileDataFunction();
                break;
            case R.id.nav_branches:
                Intent intentbranch=new Intent(HomeScreen.this, Branches.class);
                startActivity(intentbranch);
                break;
            case R.id.nav_contact:
                Intent intentcont=new Intent(HomeScreen.this, Contacts.class);
                startActivity(intentcont);
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

                if(httpResponseMsg=="auth_fail"){
                    Toast.makeText(HomeScreen.this, "Successfully Logout", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(), DashboardScreen.class);
                    startActivity(intent);
                    finish();

                }else {
                    Toast.makeText(HomeScreen.this, httpResponseMsg, Toast.LENGTH_LONG).show();
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

    //get user profile data
    public void GetProfileDataFunction(){

        class ProfileFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(HomeScreen.this);
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
                    if(jsonObject!=null) {
                        profilenic = jsonObject.getString("nicNo");
                        profileName = jsonObject.getString("fullname");
                        //profileAddress = jsonObject.getString("address");
                        profilePhone = jsonObject.getString("mobileNo");

                        showJSON();
                    }


                }catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HomeScreen.this,httpResponseMsg, Toast.LENGTH_LONG).show();

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

        ProfileFunctionClass profileFunctionClass = new ProfileFunctionClass();
        profileFunctionClass.execute();
    }
    //pass user profile data into profile class
    public void showJSON(){

        Intent intent = new Intent(getApplicationContext(), Profile.class);
        intent.putExtra(Nic,profilenic);
        intent.putExtra(P_name,profileName);
        //intent.putExtra(P_address,profileAddress);
        intent.putExtra(P_mobile,profilePhone);
        intent.putExtra(Nic,nic);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        Toast.makeText(HomeScreen.this,"Please Logout..", Toast.LENGTH_LONG).show();
    }

    
}