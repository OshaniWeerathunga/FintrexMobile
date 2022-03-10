package com.fintrex.fintrexfinance.HelperClass;

import java.util.ArrayList;
import java.util.List;

public class InboxMail {

    String inboxMessage, inboxMessageDate, inboxMessageId;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }


    public InboxMail(String inboxMessage, String inboxMessageDate, String inboxMessageId) {
        this.inboxMessage = inboxMessage;
        this.inboxMessageDate = inboxMessageDate;
        this.inboxMessageId = inboxMessageId;
    }

    public String getInboxMessage() {
        return inboxMessage;
    }

    public void setInboxMessage(String inboxMessage) {
        this.inboxMessage = inboxMessage;
    }

    public String getInboxMessageDate() {
        return inboxMessageDate;
    }

    public void setInboxMessageDate(String inboxMessageDate) {
        inboxMessageDate = inboxMessageDate;
    }

    public String getInboxMessageId() {
        return inboxMessageId;
    }

    public void setInboxMessageId(String inboxMessageId) {
        this.inboxMessageId = inboxMessageId;
    }
}
