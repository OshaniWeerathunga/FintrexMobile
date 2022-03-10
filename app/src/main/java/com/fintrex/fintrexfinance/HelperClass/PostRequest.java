package com.fintrex.fintrexfinance.HelperClass;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import org.apache.http.client.HttpClient;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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
    public  static Bitmap bitmap;

    /*
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
                return null;
            }
            else {
                return null;
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
                return null;
            }
            else {
                return null;
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
                return null;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static String newAccount(URL url, HashMap<String, String> Data) {

        try {
            URL posturl= url;

            HttpURLConnection httpURLConnection = (HttpURLConnection) posturl.openConnection();
            httpURLConnection.setInstanceFollowRedirects(false);

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

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();
            }
            else if (httpURLConnection.getResponseCode()>=300 & (httpURLConnection.getResponseCode()<=399)){

                return "respond not OK";
            }
            else {
                return "no respond";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return  "catch";
        }

    }

     */


    public static String postRequest(URL url, HashMap<String, String> Data) {

        try {
            URL posturl= url;

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) posturl.openConnection();
            httpsURLConnection.setInstanceFollowRedirects(false);

            if(cookies!=null && cookies.size()>0){
                httpsURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }

            httpsURLConnection.setReadTimeout(60000);

            httpsURLConnection.setConnectTimeout(60000);

            httpsURLConnection.setRequestMethod("POST");

            httpsURLConnection.setDoInput(true);

            httpsURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpsURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(

                    new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(FinalDataParse(Data));

            bufferedWriter.flush();

            bufferedWriter.close();

            outputStream.close();

            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                if(httpsURLConnection.getHeaderFields().get("Set-Cookie")!=null){
                    cookies=httpsURLConnection.getHeaderFields().get("Set-Cookie");
                    System.out.printf("cookie received");
                    System.out.println(cookies);
                }

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpsURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();
            }
            else if (httpsURLConnection.getResponseCode()>=300 & (httpsURLConnection.getResponseCode()<=399)){

                String redir = httpsURLConnection.getHeaderField("Location");

                if (redir!=null && redir.equals("login")){
                    return "auth_fail";
                }
                return null;
            }
            else {
                return null;
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

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) posturl.openConnection();

            if(cookies!=null && cookies.size()>0){
                httpsURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }

            httpsURLConnection.setReadTimeout(60000);

            httpsURLConnection.setConnectTimeout(60000);

            httpsURLConnection.setRequestMethod("POST");

            httpsURLConnection.setDoInput(true);

            httpsURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpsURLConnection.getOutputStream();

            outputStream.close();

            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                if(httpsURLConnection.getHeaderFields().get("Set-Cookie")!=null){
                    cookies=httpsURLConnection.getHeaderFields().get("Set-Cookie");
                    System.out.printf("cookie received");
                    System.out.println(cookies);
                }


                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpsURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();

            }
            else if (httpsURLConnection.getResponseCode()>=300 & (httpsURLConnection.getResponseCode()<=399)){

                String redir = httpsURLConnection.getHeaderField("Location");
                if (redir!=null && redir.equals("login")){
                    return "auth_fail";
                }
                return null;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static String logout(URL url) {

        try {
            URL posturl= url;

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) posturl.openConnection();
            httpsURLConnection.setInstanceFollowRedirects(false);
            if(cookies!=null && cookies.size()>0){
                httpsURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }

            httpsURLConnection.setReadTimeout(60000);

            httpsURLConnection.setConnectTimeout(60000);

            httpsURLConnection.setRequestMethod("POST");

            httpsURLConnection.setDoInput(true);

            httpsURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpsURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(

                    new OutputStreamWriter(outputStream, "UTF-8"));

            //bufferedWriter.write(FinalDataParse(Data));

            bufferedWriter.flush();

            bufferedWriter.close();

            outputStream.close();

            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                if(httpsURLConnection.getHeaderFields().get("Set-Cookie")!=null){
                    cookies=httpsURLConnection.getHeaderFields().get("Set-Cookie");

                }

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpsURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();
            }
            else if (httpsURLConnection.getResponseCode()>=300 & (httpsURLConnection.getResponseCode()<=399)){
                String redir = httpsURLConnection.getHeaderField("Location");
                if (redir!=null && redir.equals("login")){
                    return "auth_fail";
                }
                return null;
            }
            else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  null;
        }

    }

    public static String newAccount(URL url, HashMap<String, String> Data) {

        try {
            URL posturl= url;

            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) posturl.openConnection();
            httpsURLConnection.setInstanceFollowRedirects(false);

            httpsURLConnection.setReadTimeout(60000);

            httpsURLConnection.setConnectTimeout(60000);

            httpsURLConnection.setRequestMethod("POST");

            httpsURLConnection.setDoInput(true);

            httpsURLConnection.setDoOutput(true);

            OutputStream  outputStream = httpsURLConnection.getOutputStream();

            BufferedWriter bufferedWriter = new BufferedWriter(

                    new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(FinalDataParse(Data));

            bufferedWriter.flush();

            bufferedWriter.close();

            outputStream.close();

            if (httpsURLConnection.getResponseCode() == HttpsURLConnection.HTTP_OK) {

                BufferedReader bufferedReader = new BufferedReader(
                        new InputStreamReader(
                                httpsURLConnection.getInputStream()
                        )
                );
                return bufferedReader.readLine();
            }
            else if (httpsURLConnection.getResponseCode()>=300 & (httpsURLConnection.getResponseCode()<=399)){

                return "respond not OK";
            }
            else {
                return "no respond";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return  "catch";
        }

    }



    public static Bitmap getImage(){
        Bitmap bitmap2;

        try {
            URL urlImg = new URL("https://online.fintrexfinance.com/getMsgAttachments?id=5");
            HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlImg.openConnection();

            if(cookies!=null && cookies.size()>0){
                httpsURLConnection.setRequestProperty("Cookie", TextUtils.join(";",cookies));

                System.out.printf("cookie sent");
                System.out.println(cookies);
            }
            InputStream is = httpsURLConnection.getInputStream();
            bitmap2 = BitmapFactory.decodeStream(is);

            System.out.println(bitmap2);
            return bitmap;

        }catch (Exception e ){
            e.printStackTrace();
            System.out.println("catch block.....");
            return null;
        }

    }

    

}


