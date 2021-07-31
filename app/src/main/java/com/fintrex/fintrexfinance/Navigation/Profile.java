package com.fintrex.fintrexfinance.Navigation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Details.HomeScreen;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.fintrex.fintrexfinance.HelperClass.PostRequest.cookies;

public class Profile extends AppCompatActivity {

    private static final String POST_FIELD = "profile_upload";
    private EditText fullname,address,phone,nic;

    Button edit,save;
    final int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;
    String encodeImage,chooseimg;
    Uri filepath,uri;

    CircleImageView profile_img;
    ImageView pedit1,psave1,pedit2,psave2,pedit3,psave3,pedit4,psave4,pback;
    String usernic,username,usermobile,useraddress;
    String fullnameHolder,addressHolder,phoneHolder,nicHolder;
    private Menu action;
    Dialog dialog;

    ProgressDialog progressDialog;

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String ServerLoginURL = "http://202.124.175.29/Fintrex_Mobile/customerControl/updateCustomerDetails?";
    String ServerProImageSave = "http://202.124.175.29/Fintrex_Mobile/customerControl/uploadProfilePhoto?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        fullname=findViewById(R.id.name);
        nic=findViewById(R.id.nic);
        phone=findViewById(R.id.phone);
        address=findViewById(R.id.location);

        profile_img=findViewById(R.id.profile_image);
        edit=findViewById(R.id.edit_image);
        save=findViewById(R.id.save_image);

        //init edit and save of fullname
        pedit1=findViewById(R.id.profile_edit);
        psave1=findViewById(R.id.profile_save);

        //init edit and save of nic
        pedit2=findViewById(R.id.profile_edit2);
        psave2=findViewById(R.id.profile_save2);

        //init edit and save of address
        pedit3=findViewById(R.id.profile_edit3);
        psave3=findViewById(R.id.profile_save3);

        //init edit and save of mobile
        pedit4=findViewById(R.id.profile_edit4);
        psave4=findViewById(R.id.profile_save4);

        pback=findViewById(R.id.pback);

        Intent intent = getIntent();
        usernic=intent.getStringExtra(HomeScreen.Nic);
        username=intent.getStringExtra(HomeScreen.P_name);
        usermobile=intent.getStringExtra(HomeScreen.P_mobile);
        useraddress=intent.getStringExtra(HomeScreen.P_address);

        nic.setText(usernic);
        fullname.setText(username);
        phone.setText(usermobile);
        address.setText(useraddress);

        fullname.setFocusableInTouchMode(false);
        phone.setFocusableInTouchMode(false);
        address.setFocusableInTouchMode(false);
        nic.setFocusableInTouchMode(false);
        fullname.setFocusable(false);
        phone.setFocusable(false);
        address.setFocusable(false);
        nic.setFocusable(false);

        //init dialogbox
        dialog = new Dialog(Profile.this);
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

            }
        });


        //chose image from gallery
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityCompat.requestPermissions(
                        Profile.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CODE_GALLERY_REQUEST
                );

            }
        });

        //save choosed image in server side
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String imagepath=getRealPathFromURI(filepath);
                ImageSaveFunction(encodeImage);
                //uploadFile(imagepath);

            }
        });


        //edit fullname
        pedit1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psave1.setVisibility(View.VISIBLE);

                fullname.setFocusableInTouchMode(true);
                //phone.setFocusableInTouchMode(true);
                //address.setFocusableInTouchMode(true);
            }
        });
        psave1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                fullnameHolder = fullname.getText().toString().trim();
                String type = "4";

                System.out.printf("user sent");
                System.out.println(fullnameHolder+type);

                // Sending Student Name, Phone Number, Class to method to update on server.
                ChangeProfiledataFunction(fullnameHolder,type);

                psave1.setVisibility(View.INVISIBLE);

                fullname.setFocusableInTouchMode(false);
                //phone.setFocusableInTouchMode(false);
                //address.setFocusableInTouchMode(false);
                //nic.setFocusableInTouchMode(false);
                fullname.setFocusable(false);
                //phone.setFocusable(false);
                //address.setFocusable(false);
                //nic.setFocusable(false);
            }
        });

        //edit nic
        pedit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psave2.setVisibility(View.VISIBLE);

                nic.setFocusableInTouchMode(true);
                //phone.setFocusableInTouchMode(true);
                //address.setFocusableInTouchMode(true);
            }
        });
        psave2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                nicHolder = nic.getText().toString().trim();
                String type = "7";

                // Sending Student Name, Phone Number, Class to method to update on server.
                ChangeProfiledataFunction(nicHolder,type);

                psave2.setVisibility(View.INVISIBLE);

                nic.setFocusableInTouchMode(false);
                nic.setFocusable(false);
            }
        });

        //edit address
        pedit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psave3.setVisibility(View.VISIBLE);

                address.setFocusableInTouchMode(true);

            }
        });
        psave3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                addressHolder = address.getText().toString().trim();
                String type = "6";

                // Sending Student Name, Phone Number, Class to method to update on server.
                ChangeProfiledataFunction(addressHolder,type);

                psave3.setVisibility(View.INVISIBLE);

                address.setFocusableInTouchMode(false);
                address.setFocusable(false);

            }
        });

        //edit mobile
        pedit4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                psave4.setVisibility(View.VISIBLE);

                phone.setFocusableInTouchMode(true);
            }
        });
        psave4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                phoneHolder = phone.getText().toString().trim();
                String type = "4";

                // Sending Student Name, Phone Number, Class to method to update on server.
                ChangeProfiledataFunction(phoneHolder,type);

                psave4.setVisibility(View.INVISIBLE);

                phone.setFocusableInTouchMode(false);
                phone.setFocusable(false);
            }
        });

        pback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void ChangeProfiledataFunction(final String fullname, final String type){

        class ChangeProfileDataFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(Profile.this);
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

                        Toast.makeText(Profile.this, "Field Not Changed", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerLoginURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                hashMap.put("entered_text",params[0]);

                hashMap.put("type",params[1]);

                System.out.printf("user sent");
                System.out.println(hashMap);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        ChangeProfileDataFunctionClass changeProfileDataFunctionClass = new ChangeProfileDataFunctionClass();
        changeProfileDataFunctionClass.execute(fullname,type);
    }

    public void ImageSaveFunction(final String imagedata){

        class ImageSaveFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(Profile.this);
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

                        Toast.makeText(Profile.this, httpResponseMsg, Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerProImageSave);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                hashMap.put("profile_upload",params[0]);

                System.out.printf("user sent------");
                System.out.println(hashMap);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        ImageSaveFunctionClass imageSaveFunctionClass = new ImageSaveFunctionClass();
        imageSaveFunctionClass.execute(imagedata);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == CODE_GALLERY_REQUEST){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent,"Select Image"),CODE_GALLERY_REQUEST);
            }
            else{
                Toast.makeText(getApplicationContext(),"You don't have permission to access gallery",Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            filepath = data.getData();
            try{
                InputStream inputStream = getContentResolver().openInputStream(filepath);
                chooseimg = inputStream.toString();
                bitmap = BitmapFactory.decodeStream(inputStream);
                profile_img.setImageBitmap(bitmap);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            encodeBitmapImage(bitmap);

        }
        super.onActivityResult(requestCode, resultCode, data);

/*
        if (requestCode == RESULT_OK) {
                uri = data.getData();
                if (uri == null) {
                    return;
                }
                File file = new File(getRealPathFromURI(uri));
            System.out.printf("user sent------");
            System.out.println(uri);

            final Handler handler = new Handler();
                MediaScannerConnection.scanFile(
                        this, new String[]{file.toString()}, null,
                        new MediaScannerConnection.OnScanCompletedListener() {
                            public void onScanCompleted(String path, final Uri uri) {

                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        uploadFile(uri);
                                        System.out.printf("user sent------");
                                        System.out.println(uri);

                                    }
                                });
                            }
                        });
        }
*/

    }

    private String getRealPathFromURI(Uri uri) {
        if (uri == null)
        return null;

        final String scheme = uri.getScheme();
        String data = null;

        if (scheme == null){
            data=uri.getPath();
        }
        else if (ContentResolver.SCHEME_FILE.equals(scheme)){
            data=uri.getPath();
        }
        else if (ContentResolver.SCHEME_CONTENT.equals(scheme)){
            Cursor cursor = getApplicationContext().getContentResolver().query(uri,new String[]{MediaStore.Images.ImageColumns.DATA},null,null,null);
            if (null != cursor){
                if (cursor.moveToFirst()){
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if(index>-1){
                        data=cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        System.out.printf("user sent respose------");
        System.out.println(data);

        return data;
    }

    private String uploadFile(String resourceUri) {
        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        final File sourceFile = new File(resourceUri);
        String serverResponseMessage = null;
        String responce = null;

        if (!sourceFile.isFile()) {

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "File not found !", Toast.LENGTH_LONG).show();
                }
            });

            return "no file";
        } else {
            try {
                FileInputStream fileInputStream = new FileInputStream(sourceFile.getPath());
                URL url = new URL(ServerProImageSave);
                conn = (HttpURLConnection) url.openConnection();

                if(cookies!=null && cookies.size()>0){
                    conn.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                    System.out.printf("cookie sent");
                    System.out.println(cookies);
                }

                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(true); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty(POST_FIELD, sourceFile.getName());

                dos = new DataOutputStream(conn.getOutputStream());
                System.out.printf("dos value     ");
                System.out.println(dos);

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"" + POST_FIELD + "\";filename="
                        + sourceFile.getName() + lineEnd);
                dos.writeBytes(lineEnd);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                int serverResponseCode = conn.getResponseCode();
                serverResponseMessage = conn.getResponseMessage();
                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);
                if (serverResponseCode <= 200) {

                    runOnUiThread(new Runnable() {
                        public void run() {

                            Toast.makeText(Profile.this, "File Upload Complete.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                fileInputStream.close();
                dos.flush();
                dos.close();
            }
            catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                Toast.makeText(Profile.this, "Exeption part.",
                        Toast.LENGTH_SHORT).show();
            }

        }
        System.out.printf("user sent respose------");
        System.out.println(responce);

        return responce;
    }

    private void encodeBitmapImage(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        encodeImage = Base64.encodeToString(outputStream.toByteArray(),Base64.DEFAULT);
        //ImageSaveFunction(encodeImage);

    }



}