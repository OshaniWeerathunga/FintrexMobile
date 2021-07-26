package com.fintrex.fintrexfinance.HelperClass;

public class OutboxMail {

    String outboxMessageType, outboxMessage, outboxMessageDate;

    private boolean expanded;

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public OutboxMail() {
    }

    public OutboxMail(String outboxMessageType, String outboxMessage, String outboxMessageDate) {
        this.outboxMessageType = outboxMessageType;
        this.outboxMessage = outboxMessage;
        this.outboxMessageDate = outboxMessageDate;
    }

    public String getOutboxMessageType() {
        return outboxMessageType;
    }

    public void setOutboxMessageType(String outboxMessageType) {
        this.outboxMessageType = outboxMessageType;
    }

    public String getOutboxMessage() {
        return outboxMessage;
    }

    public void setOutboxMessage(String outboxMessage) {
        this.outboxMessage = outboxMessage;
    }

    public String getOutboxMessageDate() {
        return outboxMessageDate;
    }

    public void setOutboxMessageDate(String outboxMessageDate) {
        this.outboxMessageDate = outboxMessageDate;
    }
}
