package com.example.tinkoff_data.report;

import com.example.tinkoff_data.exception.CreateReportDirectoryFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.tinkoff.invest.openapi.model.rest.MarketInstrument;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
@Service
public class CommonReport<T> {

    private static final String DEFAULT_REPORT_DIRECTORY = "reports";
    private static final String DEFAULT_REPORT_CHARSET = "utf-8";
    private static final String DEFAULT_REPORT_DELIMITER = ";";

    protected String reportDirectory = DEFAULT_REPORT_DIRECTORY;
    protected String reportCharset = DEFAULT_REPORT_CHARSET;
    protected String reportDelimiter = DEFAULT_REPORT_DELIMITER;
    protected String fileName = String.format("%s.rpt", this.getClass().getSimpleName()).toLowerCase();
    protected String[] headers;
    protected String[] fields;
    protected String reportName = "";
    private List<T> reportObjects;

    private Path filePath = Paths.get(reportDirectory, fileName);

    public CommonReport() {
        setReportDirectory(DEFAULT_REPORT_DIRECTORY);
    }

    public CommonReport<T> setReportObjects(List<T> reportObjects) {
        this.reportObjects = reportObjects;
        return this;
    }

    public CommonReport<T> setReportName(String reportName) {
        this.reportName = reportName;
        return this;
    }

    public CommonReport<T> setReportDelimiter(String reportDelimiter) {
        this.reportDelimiter = reportDelimiter;
        return this;
    }

    public CommonReport<T> setReportCharset(String reportCharset) {
        this.reportCharset = reportCharset;
        return this;
    }

    public CommonReport<T> setHeaders(String[] headers) {
        this.headers = headers;
        return this;
    }

    public CommonReport<T> setFields(String[] fields) {
        this.fields = fields;
        return this;
    }

    public CommonReport<T> setFileName(String fileName) {
        this.fileName = fileName;
        setFilePath();
        return this;
    }

    public String getReportName() {
        return reportName;
    }

    public Path getFilePath() {
        return filePath;
    }

    public String[] getHeaders() {
        return headers;
    }

    public String[] getFields() {
        return fields;
    }

    public String getReportDelimiter() {
        return reportDelimiter;
    }

    public String getReportCharset() {
        return reportCharset;
    }

    public String getFileName() {
        return fileName;
    }

    public CommonReport<T> setReportDirectory(String reportDirectory)  {
        this.reportDirectory = reportDirectory;
        createDirectoryIfNecessary(Paths.get(reportDirectory));
        setFilePath();
        return this;
    }

    private void createDirectoryIfNecessary(Path directoryPath) {
        try{
            if (!Files.exists(directoryPath))
                Files.createDirectories(directoryPath);
        }
        catch (IOException e) {
            log.error("Cannot create directory for path {}", directoryPath);
            throw new CreateReportDirectoryFailedException("Cannot create directory for path: " + directoryPath , e);
        }
    }

    public String getReportDirectory() {
        return reportDirectory;
    }

    public String doExport() {
        int errCount = 0;
        String reportPath = null;
        log.info(String.format("Preparing of a report \"%s\" (%s)", getReportName(), getFileName()));
        try {
            initializeReport();
            Files.deleteIfExists(getFilePath());
            Files.write(getFilePath(), (getReportName() + "\n").getBytes(), StandardOpenOption.CREATE);
            Files.write(getFilePath(), (getReportHeader() + "\n").getBytes(), StandardOpenOption.APPEND);
            for (T reportObject : reportObjects) {
                try {
                    Files.write(
                            getFilePath(),
                            (getReportLine(reportObject) + "\n").getBytes(),
                            StandardOpenOption.APPEND);
                } catch (NoSuchFieldException | IllegalAccessException | IOException e) {
                    errCount++;
                    log.error(e.getMessage(), e);
                }
            }
            reportPath = getFilePath().toAbsolutePath().toString();
            if (errCount == 0)
                log.info(String.format("Report exported: %s", reportPath));
            else
                log.info(String.format(
                        "Report exported with %d error%s, see log for details: %s",
                        errCount,
                        errCount > 1 ? "s" : "",
                        reportPath));
        } catch (Exception e) {
            log.error(String.format("Report generation error: %s", e.getMessage()));
        }
        return reportPath;
    }

    protected void initializeReport() throws Exception {
        if (reportObjects == null)
            throw new VerifyError("No data to report");
        initializeFields();
    }

    private void setFilePath() {
        filePath = Paths.get(reportDirectory, this.fileName);
    }

    private String getReportLine(T object) throws NoSuchFieldException, IllegalAccessException {
        String line = "";
        for (String s : fields) {
            Field field = object.getClass().getDeclaredField(s);
            field.setAccessible(true);
            if (field.getType().toString().equalsIgnoreCase("class java.lang.String"))
                line = String.format("%s%s\"%s\"", line, reportDelimiter, field.get(object));
            else
                line = String.format("%s%s%s", line, reportDelimiter, field.get(object));
        }
        line = line.length() > 0 ? line.substring(1) : line;
        return line;
    }

    private String getReportHeader() {
        String header = "";
        for (String s : headers) {
            header = String.format("%s%s%s", header, reportDelimiter, s);
        }
        header = header.length() > 0 ? header.substring(1) : header;
        return header;
    }

    private void initializeFields() throws NoSuchFieldException {
        Field[] classFields = MarketInstrument.class.getDeclaredFields();
        if (fields == null || fields.length == 0) {
            fields = new String[classFields.length];
            for (int i = 0; i < classFields.length; i++) {
                fields[i] = classFields[i].getName();
            }
        }
        if (headers == null || fields.length != headers.length) {
            headers = new String[classFields.length];
            for (int i = 0; i < fields.length; i++) {
                headers[i] = classFields[i].getName();
            }
        }
        // check fields
        for (String field : fields) {
            MarketInstrument.class.getDeclaredField(field);
        }
    }

}
