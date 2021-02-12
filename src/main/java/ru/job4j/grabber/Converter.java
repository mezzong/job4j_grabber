package ru.job4j.grabber;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;

public class Converter {

    private static Map<String, Integer> months;

    static {
        months.put("янв", 1);
        months.put("фев", 2);
        months.put("мар", 3);
        months.put("апр", 4);
        months.put("май", 5);
        months.put("июн", 6);
        months.put("июл", 7);
        months.put("авг", 8);
        months.put("сен", 9);
        months.put("окт", 10);
        months.put("ноя", 11);
        months.put("дек", 12);
    }

    public static LocalDateTime stringToDate(String date) {
        String[] firstSplit = date.split(", ");
        if (firstSplit.length != 2) {
            throw new IllegalArgumentException();
        }
        String[] hhmm = firstSplit[1].split(":");
        int hour = Integer.parseInt(hhmm[0]);
        int minute = Integer.parseInt(hhmm[1]);
        if ("сегодня".equals(firstSplit[0])) {
            return LocalDateTime.of(LocalDate.now(), LocalTime.of(hour, minute));
        }
        if ("вчера".equals(firstSplit[0])) {
            return LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(hour, minute));
        }
        String[] ddmmyy = firstSplit[0].split(" ");

        int year = Integer.parseInt("20" + ddmmyy[2]);
        int month = months.get(ddmmyy[1]);
        int day = Integer.parseInt(ddmmyy[0]);

        return LocalDateTime.of(year, month, day, hour, minute);
    }
}
