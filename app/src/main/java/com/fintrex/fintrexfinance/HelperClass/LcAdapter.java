package com.fintrex.fintrexfinance.HelperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fintrex.fintrexfinance.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LcAdapter extends RecyclerView.Adapter<LcAdapter.LcViewHolder> {

    private Context mCtx;
    private List<Lc> lcList;
    Locale sriLocale = new Locale("en","LK");

    public LcAdapter(Context mCtx, List<Lc> lcList) {
        this.mCtx = mCtx;
        this.lcList = lcList;
    }

    @NonNull
    @Override
    public LcAdapter.LcViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.lc_list,null);
        return new LcAdapter.LcViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LcAdapter.LcViewHolder holder, int position) {
        Lc lc = lcList.get(position);

        holder.lcNo.setText(lc.getLcNo());
        holder.lcAmount.setText(lc.getLcAmount());
        holder.lcMaturity.setText(lc.getLaMaturity());

         }

    @Override
    public int getItemCount() {
        return lcList.size();
    }

    class LcViewHolder extends RecyclerView.ViewHolder {

        TextView lcNo, lcAmount, lcMaturity;

        public LcViewHolder(@NonNull View itemView) {
            super(itemView);

            lcNo = itemView.findViewById(R.id.textLcNo);
            lcAmount = itemView.findViewById(R.id.textLcAmount);
            lcMaturity = itemView.findViewById(R.id.textLcMaturity);


        }
    }


}

