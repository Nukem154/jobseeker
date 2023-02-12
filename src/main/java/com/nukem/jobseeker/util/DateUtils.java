package com.nukem.jobseeker.util;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class DateUtils {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");

    public static void main(String[] args) {
        String date = "3 February";
        System.out.println(parseDate(date));
    }

    public static String parseDate(String date) {
        if (date.equalsIgnoreCase("сьогодні")
                || date.equalsIgnoreCase("today")
                || date.contains("год")
                || date.contains("хвилин")) {
            return LocalDate.now().format(formatter);
        }
        if (date.equalsIgnoreCase("вчора")
                || date.equalsIgnoreCase("yesterday")
        ) {
            return LocalDate.now().minusDays(1).format(formatter);
        }
        if (date.contains("тиж")) {
            return LocalDate.now().minusWeeks(Character.getNumericValue(date.charAt(0))).format(formatter);
        }
        if (date.contains("день")
                || date.contains("дні")) {
            return LocalDate.now().minusDays(Character.getNumericValue(date.charAt(0))).format(formatter);
        }
        if (date.contains("місяц")) {
            return LocalDate.now().minusMonths(Character.getNumericValue(date.charAt(0))).format(formatter);
        }
        final AtomicReference<Month> month = new AtomicReference<>();
        final AtomicBoolean flag = new AtomicBoolean(false);
        Arrays.stream(Month.values())
                .filter(m -> StringUtils.containsIgnoreCase(date, m.toString()))
                .findFirst()
                .ifPresent(m -> {
                    month.set(m);
                    flag.set(true);
                });
        if (flag.get()) {
            return LocalDate.of(2023, month.get(), Character.getNumericValue(date.charAt(0))).format(formatter);
        }
        return date;
    }
}

