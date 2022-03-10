package com.fintrex.fintrexfinance.HelperClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintrex.fintrexfinance.R;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import static com.fintrex.fintrexfinance.HelperClass.PostRequest.bitmap;
import static com.fintrex.fintrexfinance.HelperClass.PostRequest.cookies;

public class ImageAttachmentAdapter extends RecyclerView.Adapter<ImageAttachmentAdapter.ViewHolder> {

    private Context mCtx;
    public List<ImageAttachment> imageAttachments;

    String image;
    Bitmap bitmap2;

    public ImageAttachmentAdapter(Context mCtx,List<ImageAttachment> attachments) {
        this.mCtx = mCtx;
        this.imageAttachments = attachments;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachment,parent,false);
        return new ImageAttachmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ImageAttachment imgattach = imageAttachments.get(position);
        String imgurl = imgattach.getPromotionUrl();
        //image = array.get(position);


        getImageAttachment(holder.image,imgurl);

    }

    @Override
    public int getItemCount() {
        return imageAttachments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.attachment);

        }
    }




    public void getImageAttachment(ImageView img,String url){

        class SendMessageFunctionClass extends AsyncTask<String, Void, Bitmap> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onPostExecute(Bitmap httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                System.out.printf("onpost method-----");
                System.out.println(httpResponseMsg);
                img.setImageBitmap(bitmap2);

            }

            @Override
            protected Bitmap doInBackground(String... params) {

                try {
                    URL urlImg = new URL(url);
                    HttpsURLConnection httpsURLConnection = (HttpsURLConnection) urlImg.openConnection();

                    if(PostRequest.cookies!=null && PostRequest.cookies.size()>0){
                        httpsURLConnection.setRequestProperty("Cookie", TextUtils.join(";",PostRequest.cookies));

                        System.out.printf("cookie sent");
                        System.out.println(cookies);
                    }
                    InputStream is = httpsURLConnection.getInputStream();
                    bitmap2 = BitmapFactory.decodeStream(is);


                }catch (Exception e ){
                    e.printStackTrace();
                    System.out.println("catch block.....");
                    return null;
                }
                System.out.println(bitmap2);
                return bitmap2;
            }
        }

        SendMessageFunctionClass msgFunctionClass = new SendMessageFunctionClass();
        msgFunctionClass.execute();
    }


}
