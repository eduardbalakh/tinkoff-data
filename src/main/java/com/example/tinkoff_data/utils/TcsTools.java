package com.example.tinkoff_data.utils;

import com.example.tinkoff_data.datastorage.entity.Timeframe;
import com.example.tinkoff_data.model.TimeInterval;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class TcsTools {
    public static List<TimeInterval> splitPeriod (
            Timeframe timeFrame, ZonedDateTime begPeriod, ZonedDateTime endPeriod) {

        ChronoUnit chronoUnit;
        int chronoSize;
        switch (timeFrame.getCode()) {
            case "MON1":
                chronoUnit = ChronoUnit.YEARS;
                chronoSize = 10;
                break;
            case "WEEK1":
                chronoUnit = ChronoUnit.YEARS;
                chronoSize = 2;
                break;
            case "DAY1":
                chronoUnit = ChronoUnit.YEARS;
                chronoSize = 1;
                break;
            case "HOUR1":
                chronoUnit = ChronoUnit.DAYS;
                chronoSize = 7;
                break;
            default:
                chronoUnit = ChronoUnit.DAYS;
                chronoSize = 1;
        }
        return DateTimeTools.splitInterval(chronoUnit, chronoSize, begPeriod, endPeriod);
    }

}
