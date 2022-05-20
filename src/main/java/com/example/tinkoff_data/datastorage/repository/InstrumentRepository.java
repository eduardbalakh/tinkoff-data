package com.example.tinkoff_data.datastorage.repository;

import com.example.tinkoff_data.datastorage.entity.v1.Instrument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    Instrument findByFigiIgnoreCase(String figi);

    Instrument findByTickerIgnoreCase(String ticker);

    Instrument findByFigiIgnoreCaseOrTickerIgnoreCase(String figi, String ticker);

    boolean existsInstrumentByFigiIgnoreCase(String figi);

}
