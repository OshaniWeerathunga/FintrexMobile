package com.fintrex.fintrexfinance.QuickLinks;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.fintrex.fintrexfinance.HelperClass.BaseActivity;
import com.fintrex.fintrexfinance.R;
import com.zolad.zoominimageview.ZoomInImageView;

import java.io.IOException;
import java.io.InputStream;

import static com.fintrex.fintrexfinance.HelperClass.PostRequest.cookies;

public class Rates extends BaseActivity {

    ImageView ratesback;
    ZoomInImageView ratesImage;
    String RatesImageUploadUrl = "https://online.fintrexfinance.com/getFdImage";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rates);

        ratesback = findViewById(R.id.ratesback);
        ratesImage = findViewById(R.id.rates_image);

        LoadImage loadImage = new LoadImage(ratesImage);
        loadImage.execute(RatesImageUploadUrl);

        ratesback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private class LoadImage extends AsyncTask<String,Void, Bitmap> {
        ZoomInImageView zoomInImageView;

        public LoadImage(ZoomInImageView image){
            this.zoomInImageView = ratesImage;

            progressDialog = new ProgressDialog(Rates.this);
            progressDialog.show();
            progressDialog.setContentView(R.layout.progress_layout);
            progressDialog.getWindow().setBackgroundDrawableResource(
                    android.R.color.transparent
            );
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            Bitmap bitmap = null;
            try {
                InputStream inputStream = new java.net.URL(RatesImageUploadUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.printf("bitmap output ------");
            System.out.println(bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            progressDialog.dismiss();

            ratesImage.setImageBitmap(bitmap);
        }
    }
}