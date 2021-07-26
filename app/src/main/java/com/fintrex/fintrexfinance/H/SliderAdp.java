package com.fintrex.fintrexfinance.H;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.denzcoskun.imageslider.ImageSlider;
import com.fintrex.fintrexfinance.HelperClass.Promotion;
import com.fintrex.fintrexfinance.R;
import com.google.android.material.transition.Hold;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SliderAdp extends SliderViewAdapter<SliderAdp.Holder> {

    String[] promotionList;

    public SliderAdp(String[] promotionList) {
        this.promotionList = promotionList;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent) {
        //Init view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_slider,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder viewHolder, int position) {
        //final Promotion promotion = promotionList.get(position);
        Picasso.get().load(promotionList[position]).into(viewHolder.imageView);
        //viewHolder.imageView.setImageResource(promotionList[position]);

    }

    @Override
    public int getCount() {
        return promotionList.length;
    }


    public class Holder extends SliderViewAdapter.ViewHolder {

        public ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.imageslider);
        }
    }
}
