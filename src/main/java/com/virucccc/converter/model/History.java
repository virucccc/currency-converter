package com.virucccc.converter.model;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Entity
@Table(name = "histories", uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "exchange_from",
        "exchange_to", "amount_from", "amount_to"})})
public class History {
    @Id
    @SequenceGenerator(name = "history", sequenceName = "history_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "exchange_from")
    private Exchange from;
    @ManyToOne
    @JoinColumn(name = "exchange_to")
    private Exchange to;
    @Column(name = "amount_from")
    private Float fromAmount;
    @Column(name = "amount_to")
    private Float toAmount;

    private static transient final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");

    public History() {
    }

    public History(User user, Exchange from, Exchange to, Float fromAmount, Float toAmount) {
        this.user = user;
        this.from = from;
        this.to = to;
        this.fromAmount = fromAmount;
        this.toAmount = toAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFromValuteName() {
        return from.getValute().getFullname();
    }

    public String getToValuteName() {
        return to.getValute().getFullname();
    }

    public String getDate() {
        return format.format(to.getDate().getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        History history = (History) o;
        return Objects.equals(user, history.user) &&
                Objects.equals(from, history.from) &&
                Objects.equals(to, history.to) &&
                Objects.equals(fromAmount, history.fromAmount) &&
                Objects.equals(toAmount, history.toAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, from, to, fromAmount, toAmount);
    }
}
