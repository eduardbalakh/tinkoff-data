package com.example.tinkoff_data.report;

import ru.tinkoff.invest.openapi.model.rest.InstrumentType;

public interface ReportService {

    void generateAllReports();

    void generateReport(InstrumentType instrumentType);

}
