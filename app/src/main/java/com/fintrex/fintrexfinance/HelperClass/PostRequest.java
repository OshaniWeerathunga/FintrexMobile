package com.fintrex.fintrexfinance.HelperClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Base64;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.LoginScreen;
import com.fintrex.fintrexfinance.Details.HomeScreen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class PostRequest {

    //list of cookies
    public static List<String> cookies;

    public static String postRequest(URL url, HashMap<String, String> Data) {

        try {
            URL posturl= url;

            HttpURLConnection httpURLConnection = (HttpURLConnection) posturl.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);

            if(cookies!=null && cookies.size()>0){
                httpURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }

            httpURLConnection.setReadTimeout(60000);

            httpURLConnection.setConnectTimeout(60000);

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoInput(true);

            httpURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(

                    new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(FinalDataParse(Data));

            bufferedWriter.flush();

            bufferedWriter.close();

            outputStream.close();

            if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                if(httpURLConnection.getHeaderFields().get("Set-Cookie")!=null){
                    cookies=httpURLConnection.getHeaderFields().get("Set-Cookie");
                    System.out.printf("cookie received");
                    System.out.println(cookies);
                }

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();
            }
            else if (httpURLConnection.getResponseCode()>=300 & (httpURLConnection.getResponseCode()<=399)){

                String redir = httpURLConnection.getHeaderField("Location");

                if (redir!=null && redir.equals("login")){
                    return "auth_fail";
                }
                return "not respond";
            }
            else {
                return "Something Went Wrong";
            }

        } catch (Exception e) {
             e.printStackTrace();
             return  null;
        }

    }

    public static String FinalDataParse(HashMap<String, String> hashMap2) throws UnsupportedEncodingException {

        StringBuilder stringBuilder = new StringBuilder();

        for(Map.Entry<String, String> map_entry : hashMap2.entrySet()){

            stringBuilder.append("&");

            stringBuilder.append(URLEncoder.encode(map_entry.getKey(), "UTF-8"));

            stringBuilder.append("=");

            stringBuilder.append(URLEncoder.encode(map_entry.getValue(), "UTF-8"));

        }

        return stringBuilder.toString();


    }

    public static String getData(URL url) {

        try {
            URL posturl= url;

            HttpURLConnection httpURLConnection = (HttpURLConnection) posturl.openConnection();

            if(cookies!=null && cookies.size()>0){
                httpURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }

            httpURLConnection.setReadTimeout(60000);

            httpURLConnection.setConnectTimeout(60000);

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoInput(true);

            httpURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpURLConnection.getOutputStream();

            outputStream.close();

            if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                if(httpURLConnection.getHeaderFields().get("Set-Cookie")!=null){
                    cookies=httpURLConnection.getHeaderFields().get("Set-Cookie");
                    System.out.printf("cookie received");
                    System.out.println(cookies);
                }


                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();

            }
            else if (httpURLConnection.getResponseCode()>=300 & (httpURLConnection.getResponseCode()<=399)){

                String redir = httpURLConnection.getHeaderField("Location");
                if (redir!=null && redir.equals("login")){
                    return "auth_fail";
                }
                return "not redirect";
            }
            else {
                return "Something Went Wrong";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static String logout(URL url) {

        try {
            URL posturl= url;

            HttpURLConnection httpURLConnection = (HttpURLConnection) posturl.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);
            if(cookies!=null && cookies.size()>0){
                httpURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }

            httpURLConnection.setReadTimeout(60000);

            httpURLConnection.setConnectTimeout(60000);

            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoInput(true);

            httpURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(

                    new OutputStreamWriter(outputStream, "UTF-8"));

            //bufferedWriter.write(FinalDataParse(Data));

            bufferedWriter.flush();

            bufferedWriter.close();

            outputStream.close();

            if (httpURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                if(httpURLConnection.getHeaderFields().get("Set-Cookie")!=null){
                    cookies=httpURLConnection.getHeaderFields().get("Set-Cookie");
                    System.out.printf("cookie received");
                    System.out.println(cookies);
                }


                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();
            }
            else if (httpURLConnection.getResponseCode()>=300 & (httpURLConnection.getResponseCode()<=399)){
                String redir = httpURLConnection.getHeaderField("Location");
                if (redir!=null && redir.equals("login")){
                    return "auth_fail";
                }
                return "Something Went Wrong";
            }
            else {
                return "Something Went Wrong";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static String getProImages(URL url, HashMap<String, String> Data) {

        try {
            URL posturl= url;

            HttpURLConnection httpURLConnection = (HttpURLConnection) posturl.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);

            if(cookies!=null && cookies.size()>0){
                httpURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }

            httpURLConnection.setReadTimeout(60000);

            httpURLConnection.setConnectTimeout(60000);

            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.setDoInput(true);

            httpURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(

                    new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(FinalDataParse(Data));

            bufferedWriter.flush();

            bufferedWriter.close();

            outputStream.close();

            System.out.printf("output stream ----- ");
            System.out.println(outputStream);

            HttpURLConnection con = (HttpsURLConnection)url.openConnection();
            StringBuilder sb = new StringBuilder();

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String json;
            while((json = bufferedReader.readLine())!=null){
                sb.append(json+"\n");
            }

            return sb.toString().trim();

        } catch (Exception e) {
            e.printStackTrace();
            return  "exception part";
        }

    }



}


