package com.example.tinkoff_data.report;

import ru.tinkoff.invest.openapi.model.rest.InstrumentType;

public interface ReportService {

    void generateAllReports() throws Exception;

    void generateReport(InstrumentType instrumentType) throws Exception;

}
