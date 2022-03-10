package com.fintrex.fintrexfinance.Fragment;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.fintrex.fintrexfinance.Common.LoginScreen;
import com.fintrex.fintrexfinance.HelperClass.ImageAttachment;
import com.fintrex.fintrexfinance.HelperClass.ImageAttachmentAdapter;
import com.fintrex.fintrexfinance.HelperClass.InboxMail;
import com.fintrex.fintrexfinance.HelperClass.InboxMailAdapter;
import com.fintrex.fintrexfinance.HelperClass.PostRequest;
import com.fintrex.fintrexfinance.HelperClass.PromotionAdapter;
import com.fintrex.fintrexfinance.Navigation.Promotion;
import com.fintrex.fintrexfinance.QuickLinks.ExternalRequest;
import com.fintrex.fintrexfinance.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InboxFragment extends Fragment {

    public InboxFragment() {
        // Required empty public constructor
    }

    private List<InboxMail> list = new ArrayList<>();
    private RecyclerView recyclerView;

    String ServerInboxURL = "https://online.fintrexfinance.com/indexControl/getFFLMsgData?";
    URL url;
    String finalResult ;

    String decodemsg,imageId;
    public List<ImageAttachment> imagelist = new ArrayList<>();


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

                System.out.printf("inbox fragment httpresponse  ----");
                System.out.println(httpResponseMsg);

                super.onPostExecute(httpResponseMsg);

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject lcObject = array.getJSONObject(i);

                        String msg = lcObject.getString("message");
                        String msgdate = lcObject.getString("sent_on");
                        String msgid = lcObject.getString("msgid");

                        //get attachments IDs
                        JSONArray attachIDArray = lcObject.getJSONArray("result2");
                        //if (!attachIDArray.equals(null))
                        for(int j=0; j<attachIDArray.length();j++) {
                            JSONObject attachIDObject = attachIDArray.getJSONObject(j);

                            imageId = attachIDObject.getString("image");

                            String promotion = "https://online.fintrexfinance.com/getMsgAttachments?id=" + imageId;

                            List<String> proimage = new ArrayList<String>();
                            proimage.add(promotion);

                            ImageAttachment imagesurl = new ImageAttachment(promotion);
                            imagelist.add(imagesurl);

                        }

                        System.out.printf("inbox fragment imageid prolist ----");
                        System.out.println(imagelist);


                        //decode inbox msg
                        try {
                            byte[] bytemsg = Base64.decode(msg, Base64.DEFAULT);
                            decodemsg = new String(bytemsg, "UTF-8");

                        }
                        catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        InboxMail inboxMail = new InboxMail(msg,msgdate,msgid);
                        list.add(inboxMail);
                    }

                    System.out.printf("inbox fragment inbox list ----");
                    System.out.println(list);

                    recyclerView.setAdapter(new InboxMailAdapter(list,getContext(),imagelist));

                }
                catch (JSONException e) {
                    e.printStackTrace();

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