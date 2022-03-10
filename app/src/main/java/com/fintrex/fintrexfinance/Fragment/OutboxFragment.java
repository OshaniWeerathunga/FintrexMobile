package com.fintrex.fintrexfinance.Fragment;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fintrex.fintrexfinance.HelperClass.OutboxMail;
import com.fintrex.fintrexfinance.HelperClass.OutboxMailAdapter;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class OutboxFragment extends Fragment {

    public OutboxFragment() {
        // Required empty public constructor
    }

    private List<OutboxMail> list = new ArrayList<>();
    private RecyclerView recyclerView;

    String ServerOutboxURL = "https://online.fintrexfinance.com/indexControl/getCustomerMsgData?";
    URL url;
    String finalResult ;

    String decodetype,decodemsg,decodedate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_outbox, container, false);

        recyclerView = view.findViewById(R.id.outboxrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getChatList();
        return view;
    }

    public void getChatList(){

        class OutboxFunctionClass extends AsyncTask<String,Void,String> {

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
                        JSONObject lcObject = array.getJSONObject(i);

                        String msgType = lcObject.getString("subject");
                        String msg = lcObject.getString("message");
                        String msgdate = lcObject.getString("sent_on");

                        //decode inbox msg
                        try {
                            byte[] bytetype = Base64.decode(msgType, Base64.DEFAULT);
                            byte[] bytemsg = Base64.decode(msg, Base64.DEFAULT);
                            decodetype = new String(bytetype, "UTF-8");
                            decodemsg = new String(bytemsg, "UTF-8");
                        }
                        catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        OutboxMail outboxMail = new OutboxMail(decodetype,decodemsg,msgdate);
                        list.add(outboxMail);
                    }

                    recyclerView.setAdapter(new OutboxMailAdapter(list,getContext()));



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerOutboxURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult = PostRequest.getData(url);

                return finalResult;
            }
        }

        OutboxFunctionClass outboxFunctionClass = new OutboxFunctionClass();
        outboxFunctionClass.execute();
    }
}