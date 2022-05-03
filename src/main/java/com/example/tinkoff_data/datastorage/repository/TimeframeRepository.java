package com.example.tinkoff_data.datastorage.repository;

import com.example.tinkoff_data.datastorage.entity.Timeframe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeframeRepository extends JpaRepository<Timeframe, Long> {

    Timeframe findByCodeIgnoreCase(String code);

}
