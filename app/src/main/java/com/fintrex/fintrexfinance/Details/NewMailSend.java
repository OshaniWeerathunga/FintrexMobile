package com.fintrex.fintrexfinance.Details;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.Navigation.Profile;
import com.fintrex.fintrexfinance.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.HashMap;

public class NewMailSend extends BaseActivity {

    String nicHolder,mobileHolder,msgHolder,typeHolder,toHolder="1";
    Button send,addImage;
    ImageView back,attachmentname;
    EditText type;
    TextView to;
    Dialog dialog;
    Uri uri;
    final int CODE_GALLERY_REQUEST = 999;
    Bitmap bitmap;

    ProgressDialog progressDialog;
    String finalResult, sImage,filemanagerstring,selectedImagePath,path ;
    HashMap<String,String> hashMap = new HashMap<>();
    URL url;
    String ServerMessageURL = "https://online.fintrexfinance.com/indexControl/saveMsg?";
    String ServerProImageSave = "https:///online.fintrexfinance.com/customerControl/uploadProfilePhoto?";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mail);

        //screenshots not allowed
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);


        EditText msg = (EditText) findViewById(R.id.message);
        type = findViewById(R.id.messagetype);
        to = findViewById(R.id.messageto);
        send = findViewById(R.id.sendMail);
        back = findViewById(R.id.mailback);
        addImage = findViewById(R.id.addImage);
        attachmentname = findViewById(R.id.imageattach);

        /*
        //loadSpinnerData
        LoadSpinnerDataFunction();

        //msg type categories
        List<String> categoriestype = new ArrayList<>();
        categoriestype.add("Request Message");
        categoriestype.add("Complaint Message");


        //msg to categories
        List<String> categoriesto = new ArrayList<>();
        categoriesto.add("Call Center");
        categoriesto.add("Branch Manager");

        //Style the spinnertype
        ArrayAdapter<String> dataAdapter;
        dataAdapter = new ArrayAdapter<>(this, R.layout.customize_spinner,categoriestype);
        //Dropdown layout style spinnertype
        dataAdapter.setDropDownViewResource(R.layout.customize_spinner_dropdown);

        //Style the spinnertype
        ArrayAdapter<String> dataAdapter2;
        dataAdapter2 = new ArrayAdapter<>(this, R.layout.customize_spinner,categoriesto);
        //Dropdown layout style spinnertype
        dataAdapter2.setDropDownViewResource(R.layout.customize_spinner_dropdown);

        //Attaching data adpter spinner to msgtype
        spinnertype.setAdapter(dataAdapter);
        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Message Type"))
                {
                    //do nothing
                }
                else
                {
                    //selecting a spinner item
                    if(parent.getItemAtPosition(position).toString().equals("Complaint Message")){
                        typeHolder="1";
                    }
                    else if(parent.getItemAtPosition(position).toString().equals("Request Message")){
                        typeHolder="2";
                    }
                    //typeHolder = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    //Toast.makeText(parent.getContext(),"selected: " + typeHolder, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Attaching data adpter spinner to msgto
        spinnerto.setAdapter(dataAdapter2);
        spinnerto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (parent.getItemAtPosition(position).equals("Choose Message Type"))
                {
                    //do nothing
                }
                else
                {
                    //selecting a spinner item
                    if(parent.getItemAtPosition(position).toString().equals("Call Center")){
                        toHolder="1";
                    }
                    else if(parent.getItemAtPosition(position).toString().equals("Branch Manager")){
                        toHolder="2";
                    }
                    //typeHolder = parent.getItemAtPosition(position).toString();
                    //show selected spinner item
                    //Toast.makeText(parent.getContext(),"selected: " + typeHolder, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


         */

        //init dialogbox
        dialog = new Dialog(NewMailSend.this);
        dialog.setContentView(R.layout.alert_layout);
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
                Intent gomail = new Intent(NewMailSend.this,MailScreen.class);
                startActivity(gomail);
                finish();
            }
        });

        //send the new msg
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get a value of user's msg
                //msgHolder = msg.getText().toString();
                //typeHolder = type.getText().toString();


                //get user entered msg by encoding
                try {
                    byte[] maildata = (msg.getText().toString()).getBytes("UTF-8");
                    byte[] subjectdata = (type.getText().toString()).getBytes("UTF-8");
                    msgHolder = Base64.encodeToString(maildata, Base64.DEFAULT);
                    typeHolder = Base64.encodeToString(subjectdata, Base64.DEFAULT);

                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }


                //check msg is empty or not
                if(msgHolder.isEmpty()) {
                    msg.setError("Please Type Your Message");
                }
                if (typeHolder.isEmpty()){
                    type.setError("Subject cannot be blank");
                }
                if (!msgHolder.isEmpty()&&!typeHolder.isEmpty()){
                    //call send msg function
                    SendMsgFunction(typeHolder, msgHolder, toHolder);


                }
            }
        });

        //back to mailscreen
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gomail = new Intent(NewMailSend.this,MailScreen.class);
                startActivity(gomail);
                finish();
            }
        });

        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //check condition
                if(ContextCompat.checkSelfPermission(NewMailSend.this
                , Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
                    //when permission not granted
                    //request permission
                    ActivityCompat.requestPermissions(NewMailSend.this
                    ,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    ,100);
                }else {
                    //when permission is granted
                    selectimage();
                }
            }
        });
    }

    private void selectimage() {
        //Intent intent = new Intent(Intent.ACTION_VIEW);
        //intent.setType("image/jpeg");
        //startActivityForResult(Intent.createChooser(intent,"Select Image")
        //,100);
        ActivityCompat.requestPermissions(
                NewMailSend.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                CODE_GALLERY_REQUEST
        );
    }

    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition
        if(requestCode==100 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            //when permisson is granted
            //call method
            selectimage();
        }else {
            //when permission is denied
            Toast.makeText(getApplicationContext()
            ,"Permission Denied.",Toast.LENGTH_SHORT).show();
        }
    }

     */

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
        super.onActivityResult(requestCode, resultCode, data);
        //check condition
        if (requestCode == CODE_GALLERY_REQUEST && resultCode == RESULT_OK && data != null){
            //when result is ok
            //initialize uri
            uri = data.getData();

            try {
                //initialize bitmap
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);

                //Initialize byte stream
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                //Compress bitmap
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
                //initialize byte array
                byte[] bytes = stream.toByteArray();

                //display image
                attachmentname.setImageBitmap(bitmap);

                //get base64 encode string
                sImage = Base64.encodeToString(bytes,Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
            }

            /*
            try{
                InputStream inputStream = getContentResolver().openInputStream(uri);
                String chooseimg = inputStream.toString();
                bitmap = BitmapFactory.decodeStream(inputStream);

            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            encodeBitmapImage(bitmap);

             */
        }
    }

    //encode image
    private void encodeBitmapImage(Bitmap bitmap){
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        byte[] imageBytes = outputStream.toByteArray();
        sImage = Base64.encodeToString(imageBytes,Base64.DEFAULT);
        //ImageSaveFunction(encodeImage);

    }

    //send image
    public void SendImageFunction(final Uri bitmapimg){

        class SendMessageFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(NewMailSend.this);
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

                Toast.makeText(NewMailSend.this, httpResponseMsg, Toast.LENGTH_LONG).show();

                /*
                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    if (jsonObject.getString("status").equals("saved")) {

                        dialog.show();

                    } else {

                        Toast.makeText(NewMailSend.this, "Message not sent", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    Toast.makeText(NewMailSend.this, "Please try again.Message not sent", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                 */
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerProImageSave);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("profile_upload",params[0]);


                System.out.printf("user sent");
                System.out.println(hashMap);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        SendMessageFunctionClass msgFunctionClass = new SendMessageFunctionClass();
        msgFunctionClass.execute(String.valueOf(bitmapimg));
    }






    //send new message
    public void SendMsgFunction(final String subject,final String msg, final String to){

        class SendMessageFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = new ProgressDialog(NewMailSend.this);
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

                        Toast.makeText(NewMailSend.this, "Message not sent", Toast.LENGTH_LONG).show();
                    }
                }catch (JSONException e) {
                    Toast.makeText(NewMailSend.this, "Please try again.Message not sent", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerMessageURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("subject",params[0]);

                hashMap.put("msg_txt",params[1]);

                hashMap.put("msg_to",params[2]);

                System.out.printf("user sent");
                System.out.println(hashMap);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        SendMessageFunctionClass msgFunctionClass = new SendMessageFunctionClass();
        msgFunctionClass.execute(subject,msg,to);
    }

}