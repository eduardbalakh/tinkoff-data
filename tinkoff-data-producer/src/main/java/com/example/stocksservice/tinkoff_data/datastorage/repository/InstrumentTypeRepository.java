package com.example.stocksservice.tinkoff_data.datastorage.repository;

import com.example.stocksservice.tinkoff_data.datastorage.entity.v1.InstrumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentTypeRepository extends JpaRepository<InstrumentType, Long> {

    InstrumentType findByCodeIgnoreCase(String code);

}
