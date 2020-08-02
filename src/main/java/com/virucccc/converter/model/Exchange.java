package com.virucccc.converter.model;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "exchanges")
public class Exchange {
    @Id
    @SequenceGenerator(name = "exchange", sequenceName = "exchange_sequence")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private Float value;
    @ManyToOne
    @JoinColumn(name = "valute_id")
    private Valute valute;
    @Temporal(TemporalType.DATE)
    private Calendar date;

    public Exchange() {
    }

    public Exchange(Float value, Valute valute, Calendar date) {
        this.value = value;
        this.valute = valute;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    public Valute getValute() {
        return valute;
    }

    public void setValute(Valute valute) {
        this.valute = valute;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exchange exchange = (Exchange) o;
        return Objects.equals(value, exchange.value) &&
                Objects.equals(valute, exchange.valute) &&
                Objects.equals(date, exchange.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, valute, date);
    }
}
