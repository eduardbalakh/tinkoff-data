package com.example.stocksservice.tinkoff_data.datastorage.entity.v1;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@Table(name = "instrument_type")

public class InstrumentType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(value = AccessLevel.PRIVATE)
    private Long id;

    private String code;

    private String name;

    @Override
    public String toString() {
        return this.code;
    }
}
