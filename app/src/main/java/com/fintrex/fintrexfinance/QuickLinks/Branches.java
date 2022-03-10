package com.fintrex.fintrexfinance.QuickLinks;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Branches extends BaseActivity implements OnMapReadyCallback {

    //Initialize variable
    Spinner spType;
    Button btFind;
    ImageView branchback;
    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLat = 0, currentLong = 0;

    GoogleMap mMap;
    LatLng Gampaha = new LatLng(7.094224218444937,80.00791420497532);
    LatLng Kalutara = new LatLng(6.784459915599402,79.88448179733832);
    LatLng CityBranch = new LatLng(6.945588446963321,79.87819731268499);
    LatLng Negombo = new LatLng(7.213187705163467, 79.83961471268499);
    LatLng Kegalle = new LatLng(7.254625239707656, 80.35509719919438);
    LatLng Kandy = new LatLng(7.333115237836539, 80.63759637204824);
    LatLng Dabulla = new LatLng(7.8744260040853975, 80.65078997995407);
    LatLng Kuliyapitiya = new LatLng(7.5024092030562795, 80.36590400306552);
    LatLng Kurunagala = new LatLng(7.444136657900243, 80.34141063068995);
    LatLng HeadOffice = new LatLng(6.9454207128439505, 79.87815171779091);
    LatLng Kiribathgoda = new LatLng(6.977586881283544, 79.93022487035732);
    LatLng Matara = new LatLng(5.946329065546015, 80.52706769248744);

    //array for location
    ArrayList<LatLng>arrayList = new ArrayList<LatLng>();

    //array for branch names
    ArrayList<String> title = new ArrayList<String>();

    Dialog dialog;

    LinearLayout bottomdetails;
    TextView branchname,branchaddress;
    ImageView bottomClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_branches2);

        branchback=findViewById(R.id.branchback);
        bottomdetails=findViewById(R.id.bottomSheetBranches);

        FragmentManager fragmentManager = getSupportFragmentManager();
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(new LatLng(7,79))
                .zoom(7)
                .build();

        //Assign variables
        //supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        //supportMapFragment.getMapAsync(this);

        SupportMapFragment mapFragment = SupportMapFragment.newInstance(new GoogleMapOptions().camera(cameraPosition));
        fragmentManager.beginTransaction().replace(R.id.google_map,mapFragment).commit();
        mapFragment.getMapAsync(this);


        arrayList.add(Gampaha);
        arrayList.add(Kalutara);
        arrayList.add(CityBranch);
        arrayList.add(Negombo);
        arrayList.add(Kegalle);
        arrayList.add(Kandy);
        arrayList.add(Dabulla);
        arrayList.add(Kuliyapitiya);
        arrayList.add(Kurunagala);
        arrayList.add(HeadOffice);
        arrayList.add(Kiribathgoda);
        arrayList.add(Matara);

        title.add("Gampaha");
        title.add("Kalutara");
        title.add("CityBranch");
        title.add("Negombo");
        title.add("Kegalle");
        title.add("Kandy");
        title.add("Dambulla");
        title.add("Kuliyapitiya");
        title.add("Kurunegala");
        title.add("HeadOffice");
        title.add("Kiribathgoda");
        title.add("Matara");

        branchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap=googleMap;

        //LatLngBounds Srilanka = new LatLngBounds(new LatLng(23.63,68.14),new LatLng(28.20,97.34));
        //mMap.setLatLngBoundsForCameraTarget(Srilanka);

        //add marker to location
        for (int i=0;i<arrayList.size();i++){
            //add markers name
            for (int j=0;j<title.size();j++){
                mMap.addMarker(new MarkerOptions().position(arrayList.get(i)).title(String.valueOf(title.get(i))));
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLng(arrayList.get(i)));
        }

        //now will add onclick listner
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            String branch;
            String address;
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                switch (marker.getTitle()) {
                    case "CityBranch":
                        branch = "City Branch";
                        address = "101,Darmapala Mawatha,Colombo 07\n\n+94 117 977 700";
                        alert(branch,address);
                        break;
                    case "Gampaha":
                        branch = "Gampaha Branch";
                        address = "1st Floor,No.369/1/1,Colombo Road,Gampaha\n\n+94 337 977 977";
                        alert(branch,address);
                        break;
                    case "Kalutara":
                        branch = "Kalutara Branch";
                        address = "398A,Galle Road,Kalutara North,Kalutara\n+94 347 977 977";
                        alert(branch,address);
                        break;
                    case "Negombo":
                        branch = "Negombo Branch";
                        address = "No.600A 1/1,Chilaw Road,Kattuwa,Negombo\n\n+94 317 977 977";
                        alert(branch,address);
                        break;
                    case "Kegalle":
                        branch = "Kegalle Branch";
                        address = "No.440B,Kandy Road,Meepitiya,Kegalle\n\n+94 357 977 982";
                        alert(branch,address);
                        break;
                    case "Kandy":
                        branch = "Kandy Branch";
                        address = "No.206,Katugastota Road,Kandy\n\n+94 817 977 977";
                        alert(branch,address);
                        break;
                    case "Dambulla":
                        branch = "Dambulla Branch";
                        address = "No.318,Sangeetha Building,Kandy Road,Dambulla\n\n+94 667 977 977";
                        alert(branch,address);
                        break;
                    case "Kuliyapitiya":
                        branch = "Kuliyapitiya Branch";
                        address = "1st Floor,No.369/1/1,Colombo Road,Gampaha\n\n+94 377 977 968";
                        alert(branch,address);
                        break;
                    case "Kurunegala":
                        branch = "Kurunegala Branch";
                        address = "No.1st Floor,No.252,Negombo Road,Kurunegala\n\n+94 377 977 977";
                        alert(branch,address);
                        break;
                    case "HeadOffice":
                        branch = "Head Office";
                        address = "No.851,Dr.Danister De Silva Mawatha,Colombo 14\n\n+94 117 977 977";
                        alert(branch,address);
                        break;
                    case "Kiribathgoda":
                        branch = "Kiribathgoda Branch";
                        address = "No.63/3/B,Kandy Road,Kiribathgoda\n\n+94 117 977 400";
                        alert(branch,address);
                        break;
                    case "Matara":
                        branch = "Matara Branch";
                        address = "No.469A,Angarika Dharmapala Mawatha,Pamburana,Matara\n\n+94 417 977 977";
                        alert(branch,address);
                        break;
                }
                return false;
            }
        });
    }

    private void alert(String branch,String address){
        bottomdetails.setVisibility(View.VISIBLE);

        branchname = findViewById(R.id.branchName);
        branchaddress = findViewById(R.id.branchAddress);
        bottomClose = findViewById(R.id.close);

        branchname.setText(branch);
        branchaddress.setText(address);

        bottomClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomdetails.setVisibility(View.INVISIBLE);
            }
        });
    }
    /*
    private void alert(String branch,String address){
        AlertDialog dlg = new AlertDialog.Builder(Branches.this,AlertDialog.THEME_TRADITIONAL)
                .setTitle(branch)
                .setMessage(address)
                .create();
        dlg.show();
    }

    private void alert(String branch,String address){
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(Branches.this,R.style.BottomSheetDialogSheetTheme);
        TextView branchname,branchaddress;

        branchname = findViewById(R.id.branchName);
        branchaddress = findViewById(R.id.branchAddress);

        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(
                        R.layout.bottom_sheet_branches,
                        (LinearLayout)findViewById(R.id.bottomSheetBranches)
                );

        branchname.setText(branch);
        branchaddress.setText(address);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }

     */
}