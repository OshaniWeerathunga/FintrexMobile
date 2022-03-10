package com.fintrex.fintrexfinance.HelperClass;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fintrex.fintrexfinance.R;

import java.util.List;
import java.util.Locale;

public class LoanAdapter extends RecyclerView.Adapter<LoanAdapter.LoanViewHolder> {

    private Context mCtx;
    private List<Loan> loanList;
    Locale sriLocale = new Locale("en","LK");

    public LoanAdapter(Context mCtx, List<Loan> loanList) {
        this.mCtx = mCtx;
        this.loanList = loanList;
    }

    @NonNull
    @Override
    public LoanAdapter.LoanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.loan_list,null);
        return new LoanAdapter.LoanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoanAdapter.LoanViewHolder holder, int position) {
        Loan loan = loanList.get(position);

        holder.loanNo.setText(loan.getLoanNo());
        holder.loanOutstanding.setText(loan.getLoanOutstanding());
        holder.loanInstallment.setText(loan.getLoanInstallment());
        String loanDue = loan.getLoanNextDue();
        if (loanDue.equals("Immediate")) {
            holder.loanNextDue.setText(loan.getLoanNextDue());
            holder.loanNextDue.setTextColor(Color.RED);
        }else{
            holder.loanNextDue.setText(loan.getLoanNextDue());
        }
        holder.loanAmount.setText(loan.getLoanAmount());
        holder.loanmaturity.setText(loan.getLoanmaturity());
        holder.loanLastPayAmount.setText(loan.getLoanLastPayAmount());
        holder.loanLastPayDate.setText(loan.getLoanLastPayDate());

        boolean isExpanded = loanList.get(position).isExpanded();
        holder.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return loanList.size();
    }

    class LoanViewHolder extends RecyclerView.ViewHolder {

        TextView loanNo,loanOutstanding,loanInstallment,loanNextDue,loanAmount,loanmaturity,loanLastPayAmount,loanLastPayDate;
        RelativeLayout loanbtn;
        ConstraintLayout expandableLayout;

        public LoanViewHolder(@NonNull View itemView) {
            super(itemView);

            loanNo = itemView.findViewById(R.id.textLoanNo);
            loanOutstanding = itemView.findViewById(R.id.valueLoanOutstanding);
            loanInstallment = itemView.findViewById(R.id.valueLoanInstallment);
            loanNextDue = itemView.findViewById(R.id.valueloanDue);
            loanAmount = itemView.findViewById(R.id.valueloanAmount);
            loanmaturity = itemView.findViewById(R.id.valueLoanMaturity);
            loanLastPayAmount = itemView.findViewById(R.id.valueLoanLastPayment);
            loanLastPayDate = itemView.findViewById(R.id.valueLoanLastPayDate);

            loanbtn = itemView.findViewById(R.id.loanmore);
            expandableLayout = itemView.findViewById(R.id.loanexpandableLayout);


            loanbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Loan loan = loanList.get(getAdapterPosition());
                    loan.setExpanded(!loan.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}