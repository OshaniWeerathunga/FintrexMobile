package com.fintrex.fintrexfinance.HelperClass;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fintrex.fintrexfinance.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class LeaseAdapter extends RecyclerView.Adapter<LeaseAdapter.LeaseViewHolder> {

    private Context mCtx;
    private List<Lease> leaseList;
    Locale sriLocale = new Locale("en","LK");

    public LeaseAdapter(Context mCtx, List<Lease> leaseList) {
        this.mCtx = mCtx;
        this.leaseList = leaseList;
    }

    @NonNull
    @Override
    public LeaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.leasing_list,null);
        return new LeaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaseViewHolder holder, int position) {
        Lease lease = leaseList.get(position);

        holder.leaseNo.setText(lease.getLeaseNo());
        holder.totalOutstanding.setText(lease.getTotalOutstanding());
        String dueDate = lease.getNextPayDate();
        if (dueDate.equals("Immediate")){
            holder.nextPayDate.setText(lease.getNextPayDate());
            holder.nextPayDate.setTextColor(Color.RED);
        }else{
            holder.nextPayDate.setText(lease.getNextPayDate());
        }
        holder.maturityDate.setText(lease.getMaturityDate());
        holder.rental.setText(lease.getRental());
        holder.lastPayDate.setText(lease.getLastPayDate());
        holder.lastPayAmount.setText(lease.getLastPayAmount());
        holder.vehicleNo.setText(lease.getVehicleNo());
        holder.insuranceBy.setText(lease.getInsuranceBy());
        holder.insuranceExpire.setText(lease.getInsuranceExpire());

        boolean isExpanded = leaseList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return leaseList.size();
    }

    class LeaseViewHolder extends RecyclerView.ViewHolder {

        TextView leaseNo, totalOutstanding,nextPayDate,maturityDate,rental,lastPayAmount,lastPayDate,vehicleNo,insuranceBy,insuranceExpire;
        RelativeLayout leasebtn;
        ConstraintLayout expandableLayout;

        public LeaseViewHolder(@NonNull View itemView) {
            super(itemView);

            leaseNo = itemView.findViewById(R.id.valueLeaseNo);
            totalOutstanding = itemView.findViewById(R.id.valueLeaseOutstanding);
            nextPayDate = itemView.findViewById(R.id.valueNextPay);
            maturityDate = itemView.findViewById(R.id.valueLeaseMaturity);
            rental = itemView.findViewById(R.id.valueLeaseRentalAmt);
            lastPayAmount = itemView.findViewById(R.id.valueLeaseLastPay);
            lastPayDate = itemView.findViewById(R.id.valueLeaseLastPayDate);
            vehicleNo = itemView.findViewById(R.id.valueLeaseVehicle);
            insuranceBy = itemView.findViewById(R.id.valueLeaseInsuranceCompany);
            insuranceExpire = itemView.findViewById(R.id.valueLeaseInsurance);

            leasebtn = itemView.findViewById(R.id.leasemore);
            expandableLayout = itemView.findViewById(R.id.expandableLayout);


            leasebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Lease lease = leaseList.get(getAdapterPosition());
                    lease.setExpanded(!lease.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}
