package com.fintrex.fintrexfinance.HelperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintrex.fintrexfinance.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {

    private Context mCtx;
    private List<Promotion> prolist;

    public PromotionAdapter(Context mCtx, List<Promotion> prolist) {
        this.mCtx = mCtx;
        this.prolist = prolist;
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.item_slider,null);
        return new PromotionAdapter.PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {
        Promotion promotion = prolist.get(position);

        Picasso.get().load(promotion.getPromotionUrl()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return prolist.size();
    }


    public class PromotionViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView) itemView.findViewById(R.id.imageslider);
        }
    }
}
