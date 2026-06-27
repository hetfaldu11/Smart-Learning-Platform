package com.fm.smartlearningplatform.payment.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TaxService {

    @Value("${tax.percentage}")
    private BigDecimal TAX_PERCENTAGE;

    public BigDecimal calculateTax(BigDecimal taxableAmount) {

        return taxableAmount.multiply(TAX_PERCENTAGE.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP))
                .setScale(2, RoundingMode.HALF_UP);

    }
}
