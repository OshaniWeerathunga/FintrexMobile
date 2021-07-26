package com.fintrex.fintrexfinance.HelperClass;

public class InboxMail {

    String inboxMessage, InboxMessageDate;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public InboxMail() {
    }

    public InboxMail(String inboxMessage, String inboxMessageDate) {
        this.inboxMessage = inboxMessage;
        this.InboxMessageDate = inboxMessageDate;
    }


    public String getInboxMessage() {
        return inboxMessage;
    }

    public void setInboxMessage(String inboxMessage) {
        this.inboxMessage = inboxMessage;
    }

    public String getInboxMessageDate() {
        return InboxMessageDate;
    }

    public void setInboxMessageDate(String inboxMessageDate) {
        InboxMessageDate = inboxMessageDate;
    }
}
