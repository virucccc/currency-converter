package com.virucccc.converter.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HistoryExchange {
    private User user;
    private Exchange from;
    private Exchange to;
    private Float fromAmount;
    private Float toAmount;
    private Calendar date;
    private static transient final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public HistoryExchange() {
    }

    public HistoryExchange(User user, Exchange from, Exchange to, Float fromVal, Float toVal, Calendar date) {
        this.user = user;
        this.from = from;
        this.to = to;
        this.fromAmount = fromVal;
        this.toAmount = toVal;
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Exchange getFrom() {
        return from;
    }

    public void setFrom(Exchange from) {
        this.from = from;
    }

    public Exchange getTo() {
        return to;
    }

    public void setTo(Exchange to) {
        this.to = to;
    }

    public Float getFromAmount() {
        return fromAmount;
    }

    public void setFromAmount(Float fromAmount) {
        this.fromAmount = fromAmount;
    }

    public Float getToAmount() {
        return toAmount;
    }

    public void setToAmount(Float toAmount) {
        this.toAmount = toAmount;
    }

    public String getDate() {
        return format.format(date.getTime());
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public String getFromValuteName() {
        return from.getValute().getFullname();
    }

    public String getToValuteName() {
        return to.getValute().getFullname();
    }
}
