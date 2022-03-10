package com.fintrex.fintrexfinance.HelperClass;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.fintrex.fintrexfinance.R;

import java.util.List;

public class OutboxMailAdapter extends RecyclerView.Adapter<OutboxMailAdapter.Holder> {

    private List<OutboxMail> outboxlist;
    private Context context;

    public OutboxMailAdapter(List<OutboxMail> outboxlist, Context context) {
        this.outboxlist = outboxlist;
        this.context = context;
    }

    @NonNull
    @Override
    public OutboxMailAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.outbox_list, parent, false);
        return new OutboxMailAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OutboxMailAdapter.Holder holder, int position) {
        OutboxMail outboxMail = outboxlist.get(position);

        holder.outboxType.setText(outboxMail.getOutboxMessageType());
        holder.outboxMail.setText(outboxMail.getOutboxMessage());
        holder.outboxDate.setText(outboxMail.getOutboxMessageDate());

        boolean isExpanded = outboxlist.get(position).isExpanded();
        holder.viewmsg.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return outboxlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView outboxType, outboxMail, outboxDate;
        LinearLayout expandable;
        LinearLayout viewmsg;

        public Holder(@NonNull View itemView) {
            super(itemView);

            outboxType = itemView.findViewById(R.id.mailType);
            outboxMail = itemView.findViewById(R.id.mail);
            outboxDate = itemView.findViewById(R.id.mailDate);
            expandable = itemView.findViewById(R.id.expandmsg);
            viewmsg = itemView.findViewById(R.id.msglinear);

            expandable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    OutboxMail outboxMail = outboxlist.get(getAdapterPosition());
                    outboxMail.setExpanded(!outboxMail.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}