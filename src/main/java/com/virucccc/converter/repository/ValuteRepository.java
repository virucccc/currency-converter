package com.virucccc.converter.repository;

import com.virucccc.converter.model.Valute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ValuteRepository extends JpaRepository<Valute, Integer> {
    List<Valute> findAll();
}
