package ru.sw.stock_price_monitoring.util;

import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DateUtil {
    public static List<LocalDate> generateDateListOfRange(LocalDate start, LocalDate end) {
        long countOfDaysBetween = ChronoUnit.DAYS.between(start, end);
        List<LocalDate> dates = new ArrayList<>();
        for (int i = 0; i <= countOfDaysBetween; i++) {
            dates.add(start.plusDays(i));
        }
        return dates;
    }

    public static List<DateRange> generateDateRangesOf(List<LocalDate> dates) {
        List<DateRange> dateRanges = new ArrayList<>();

        if (!CollectionUtils.isEmpty(dates)) {
            dates.sort(LocalDate::compareTo);
            DateRange currentDateRange = new DateRange();

            Iterator<LocalDate> iterator = dates.iterator();
            LocalDate start = iterator.next();
            currentDateRange.addDate(start);
            LocalDate end;
            while (iterator.hasNext()) {
                end = iterator.next();

                if (ChronoUnit.DAYS.between(start, end) > 1) {
                    dateRanges.add(currentDateRange);
                    currentDateRange = new DateRange();
                }

                currentDateRange.addDate(end);
                start = end;
            }
            dateRanges.add(currentDateRange);
        }
        return dateRanges;
    }
}
