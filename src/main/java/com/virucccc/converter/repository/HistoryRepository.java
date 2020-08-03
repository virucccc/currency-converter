package com.virucccc.converter.repository;

import com.virucccc.converter.model.Exchange;
import com.virucccc.converter.model.History;
import com.virucccc.converter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Calendar;
import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Integer> {
    List<History> findByUser(User user);

    @Query("SELECT h FROM History h WHERE " +
            "h.user = ?1 AND h.from = ?2 AND h.to = ?3 AND h.fromAmount = ?4 AND h.toAmount = ?5 AND h.date = ?6")
    History contains(User user, Exchange from, Exchange to, Float amount_from, Float amount_to, Calendar date);

    @Query("SELECT h FROM History h WHERE h.user = ?1 AND h.date = ?2")
    List<History> findByUserDate(User user, Calendar date);
}
