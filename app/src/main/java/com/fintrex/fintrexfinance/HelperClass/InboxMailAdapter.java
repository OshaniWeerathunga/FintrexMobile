package com.fintrex.fintrexfinance.HelperClass;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.ImageAssetDelegate;
import com.fintrex.fintrexfinance.QuickLinks.Rates;
import com.fintrex.fintrexfinance.R;
import com.zolad.zoominimageview.ZoomInImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.security.AccessController.getContext;

public class InboxMailAdapter extends RecyclerView.Adapter<InboxMailAdapter.Holder> {

    private List<InboxMail> inboxlist;
    private List<ImageAttachment> imageAttachments ;

    private Context context;

    String finalResult, msgid, id="1";

    public InboxMailAdapter(List<InboxMail> inboxlist, Context context,List<ImageAttachment> imageAttachments) {
        this.inboxlist = inboxlist;
        this.context = context;
        this.imageAttachments = imageAttachments;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inbox_list,parent,false);
        return new Holder(view);
        
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        InboxMail inboxMail = inboxlist.get(position);

        holder.inboxMail.setText(inboxMail.getInboxMessage());
        holder.inboxDate.setText(inboxMail.getInboxMessageDate());

        System.out.println("inbox adapter Image attachment list" + imageAttachments);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        holder.recyclerView.setAdapter(new ImageAttachmentAdapter(context,imageAttachments));


        boolean isExpanded = inboxlist.get(position).isExpanded();
        holder.viewmsg.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return inboxlist.size();
    }


    public class Holder extends RecyclerView.ViewHolder{
        private TextView inboxMail,inboxDate;
        LinearLayout expandable;
        LinearLayout viewmsg;
        RecyclerView recyclerView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            inboxMail = itemView.findViewById(R.id.inboxmail);
            inboxDate = itemView.findViewById(R.id.inboxmailDate);
            expandable = itemView.findViewById(R.id.expandmsg);
            viewmsg = itemView.findViewById(R.id.msglinear);
            recyclerView = itemView.findViewById(R.id.attachmentImageRecy);

            expandable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    InboxMail inboxMail = inboxlist.get(getAdapterPosition());
                    inboxMail.setExpanded(!inboxMail.isExpanded());
                    notifyItemChanged(getAdapterPosition());


                }
            });

        }
    }

/*
    public void GetMessageImageId(String id){

        class FixedFunctionClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                System.out.printf("inbox Image Attachment ID ----");
                System.out.println(httpResponseMsg);

                try {
                    JSONObject jsonObject = new JSONObject(httpResponseMsg);
                    JSONArray array = jsonObject.getJSONArray("result");

                    for(int i=0; i<array.length();i++){
                        JSONObject leaseObject = array.getJSONObject(i);

                        String bannerId = leaseObject.getString("image");

                        String promotion = "https://online.fintrexfinance.com/getMsgAttachents?id="+bannerId;

                        proimage.add(promotion);

                        //com.fintrex.fintrexfinance.HelperClass.Promotion pro = new com.fintrex.fintrexfinance.HelperClass.Promotion(promotion);
                        //prolist.add(pro);
                    }

                    System.out.printf("inbox Image Attachment Url ----");
                    System.out.println(proimage);

                    imageAttachmentAdapter = new ImageAttachmentAdapter(context,prolist);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(ServerInboxImageID);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                hashMap.put("msg_id",params[0]);

                finalResult = PostRequest.postRequest(url,hashMap);

                return finalResult;
            }
        }

        FixedFunctionClass fixedFunctionClass = new FixedFunctionClass();
        fixedFunctionClass.execute(id);
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

                System.out.printf("inboxdata in Image Attachment ----");
                System.out.println(httpResponseMsg);

            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    url = new URL(imageUri);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                finalResult =PostRequest.getImageData(url);

                System.out.printf("user sent");
                System.out.println(hashMap);

                return finalResult;
            }
        }

        InboxFunctionClass inboxFunctionClass = new InboxFunctionClass();
        inboxFunctionClass.execute();
    }

     */


}
