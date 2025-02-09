package ru.sw.stock_price_monitoring.util;

import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Getter
public class DateRange {
    private final Set<LocalDate> dateRange;

    public DateRange() {
        dateRange = new TreeSet<>();
    }

    public boolean addDate(LocalDate date) {
        return dateRange.add(date);
    }

    public boolean addDates(List<LocalDate> dates) {
        return dateRange.addAll(dates);
    }

    public LocalDate getStartDate() {
        return dateRange.stream().findFirst().orElseThrow();
    }

    public LocalDate getEndDate() {
        return dateRange.stream().skip(dateRange.size() - 1).findFirst().orElseThrow();
    }

    public Set<LocalDate> collapse() {
        LocalDate startDate = getStartDate();
        LocalDate endDate = getEndDate();

        dateRange.clear();

        dateRange.add(startDate);
        dateRange.add(endDate);

        return dateRange;
    }

    public int getSize(){
        return dateRange.size();
    }
}
