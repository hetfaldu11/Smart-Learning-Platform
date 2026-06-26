package com.fm.smartlearningplatform.payment.generator;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderNumberGenerator {

    private static final String PREFIX = "ORD";

    public String generate(long sequenceNumber) {
        int year = LocalDate.now().getYear();
        int month = LocalDate.now().getMonthValue();
        int date = LocalDate.now().getDayOfMonth();
        return String.format("%s-%d%d$d%08d", PREFIX, year, month, date, sequenceNumber);
    }
}