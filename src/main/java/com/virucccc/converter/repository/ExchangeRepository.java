package com.virucccc.converter.repository;

import com.virucccc.converter.model.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long> {
    List<Exchange> findAll();

    @Query("SELECT e FROM Exchange e WHERE e.date = ?1")
    List<Exchange> findAllByDate(Calendar date);

    @Query("SELECT e FROM Exchange e WHERE valute_id = ?1 AND e.date = ?2")
    Exchange findByIdDate(Integer valute_id, Calendar date);
}
