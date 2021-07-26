package com.fintrex.fintrexfinance.Fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.DashboardScreen;
import com.fintrex.fintrexfinance.Details.LcScreen;
import com.fintrex.fintrexfinance.HelperClass.InboxMail;
import com.fintrex.fintrexfinance.HelperClass.InboxMailAdapter;
import com.fintrex.fintrexfinance.HelperClass.Lc;
import com.fintrex.fintrexfinance.HelperClass.LcAdapter;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.MyApp;
import com.fintrex.fintrexfinance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class InboxFragment extends Fragment {

    public InboxFragment() {
        // Required empty public constructor
    }

    private List<InboxMail> list = new ArrayList<>();
    private RecyclerView recyclerView;

    String ServerInboxURL = "http://202.124.175.29/Fintrex_Mobile/indexControl/getFFLMsgData?";
    URL url;
    String finalResult ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);

        recyclerView = view.findViewById(R.id.inboxrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        getChatList();

        return view;
    }


    public void getChatList(){

        class InboxFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                //timeout riderect to the dashboard
                if (httpResponseMsg.equals("Something Went Wrong")){
                    Intent intent=new Intent(getActivity(), DashboardScreen.class);
                    startActivity(intent);
                }

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject lcObject = array.getJSONObject(i);

                        String msg = lcObject.getString("message");
                        String msgdate = lcObject.getString("sent_on");

                        InboxMail inboxMail = new InboxMail(msg,msgdate);
                        list.add(inboxMail);
                    }

                    recyclerView.setAdapter(new InboxMailAdapter(list,getContext()));



                }
                catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),httpResponseMsg,Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerInboxURL);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult = PostRequest.getData(url);

                return finalResult;
            }
        }

        InboxFunctionClass inboxFunctionClass = new InboxFunctionClass();
        inboxFunctionClass.execute();
    }
}