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
import com.google.android.material.transition.Hold;

import org.w3c.dom.Text;

import java.util.List;

public class InboxMailAdapter extends RecyclerView.Adapter<InboxMailAdapter.Holder> {

    private List<InboxMail> inboxlist;
    private Context context;

    public InboxMailAdapter(List<InboxMail> inboxlist, Context context) {
        this.inboxlist = inboxlist;
        this.context = context;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inbox_list,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        InboxMail inboxMail = inboxlist.get(position);

        holder.inboxMail.setText(inboxMail.getInboxMessage());
        holder.inboxDate.setText(inboxMail.getInboxMessageDate());

        boolean isExpanded = inboxlist.get(position).isExpanded();
        holder.viewmsg.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return inboxlist.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        private TextView inboxMail,inboxDate;
        LinearLayout expandable;
        LinearLayout viewmsg;

        public Holder(@NonNull View itemView) {
            super(itemView);

            inboxMail = itemView.findViewById(R.id.inboxmail);
            inboxDate = itemView.findViewById(R.id.inboxmailDate);
            expandable = itemView.findViewById(R.id.expandmsg);
            viewmsg = itemView.findViewById(R.id.msglinear);

            expandable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    InboxMail inboxMail = inboxlist.get(getAdapterPosition());
                    inboxMail.setExpanded(!inboxMail.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }


}
