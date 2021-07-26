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

public class FdAdapter extends RecyclerView.Adapter<FdAdapter.FdViewHolder> {

    private Context mCtx;
    private List<Fd> fdList;
    Locale sriLocale = new Locale("en","LK");

    public FdAdapter(Context mCtx, List<Fd> fdList) {
        this.mCtx = mCtx;
        this.fdList = fdList;
    }

    @NonNull
    @Override
    public FdAdapter.FdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.fd_list,null);
        return new FdAdapter.FdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FdAdapter.FdViewHolder holder, int position) {
        Fd fd = fdList.get(position);

        holder.fdNo.setText(fd.getFdNo());
        holder.fdValue.setText(fd.getFdValue());
        holder.fdMaturity.setText(fd.getMaturity());
        holder.fdRenewal.setText(fd.getFdRenewal());
        holder.fdRate.setText(fd.getFdRate());

        boolean isExpanded = fdList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return fdList.size();
    }

    class FdViewHolder extends RecyclerView.ViewHolder {

        TextView fdNo,fdValue,fdMaturity,fdRenewal,fdRate;
        RelativeLayout fdbtn;
        ConstraintLayout expandableLayout;

        public FdViewHolder(@NonNull View itemView) {
            super(itemView);

            fdNo = itemView.findViewById(R.id.valueFdNo);
            fdValue = itemView.findViewById(R.id.valueFdValue);
            fdMaturity = itemView.findViewById(R.id.valueFdMaturity);
            fdRenewal = itemView.findViewById(R.id.valueFdRenewal);
            fdRate = itemView.findViewById(R.id.valueFdRate);

            fdbtn = itemView.findViewById(R.id.fdmore);
            expandableLayout = itemView.findViewById(R.id.fdexpandableLayout);


            fdbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fd fd = fdList.get(getAdapterPosition());
                    fd.setExpanded(!fd.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }


}
