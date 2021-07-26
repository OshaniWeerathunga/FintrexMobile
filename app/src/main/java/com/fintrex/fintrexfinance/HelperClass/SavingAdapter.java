package com.fintrex.fintrexfinance.HelperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fintrex.fintrexfinance.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class SavingAdapter extends RecyclerView.Adapter<SavingAdapter.SavingViewHolder> {

    private Context mCtx;
    private List<Saving> savingList;
    Locale sriLocale = new Locale("en","LK");

    public SavingAdapter(Context mCtx, List<Saving> savingList) {
        this.mCtx = mCtx;
        this.savingList = savingList;
    }

    @NonNull
    @Override
    public SavingAdapter.SavingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.saving_list,null);
        return new SavingAdapter.SavingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavingAdapter.SavingViewHolder holder, int position) {
        Saving saving = savingList.get(position);

        holder.savingNo.setText(saving.getSavingNo());
        holder.currentBalance.setText(saving.getCurrentBalance());
        holder.accountType.setText(saving.getAccountType());

        boolean isExpanded = savingList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return savingList.size();
    }

    class SavingViewHolder extends RecyclerView.ViewHolder {

        TextView savingNo,currentBalance,accountType;
        RelativeLayout savingbtn;
        ConstraintLayout expandableLayout;

        public SavingViewHolder(@NonNull View itemView) {
            super(itemView);

            savingNo = itemView.findViewById(R.id.valueSavNo);
            currentBalance = itemView.findViewById(R.id.valueSavBalance);
            accountType = itemView.findViewById(R.id.valueSavType);

            savingbtn = itemView.findViewById(R.id.savemore);
            expandableLayout = itemView.findViewById(R.id.savexpandableLayout);


            savingbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Saving saving = savingList.get(getAdapterPosition());
                    saving.setExpanded(!saving.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }


}
