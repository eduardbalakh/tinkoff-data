package com.example.tinkoff_data.datastorage.repository;

import com.example.tinkoff_data.datastorage.entity.InstrumentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentTypeRepository extends JpaRepository<InstrumentType, Long> {

    InstrumentType findByCodeIgnoreCase(String code);

}
