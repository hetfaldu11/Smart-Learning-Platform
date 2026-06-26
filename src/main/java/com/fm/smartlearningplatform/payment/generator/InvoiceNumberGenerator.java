package com.fm.smartlearningplatform.payment.generator;

import com.fm.smartlearningplatform.payment.generator.DatabaseSequenceService;
import com.fm.smartlearningplatform.payment.generator.NumberGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceNumberGenerator
        extends NumberGenerator {

    private final DatabaseSequenceService sequenceService;

    @Override
    protected String getPrefix() {

        return "INV";

    }

    @Override
    protected long getNextSequence() {

        return sequenceService.nextInvoiceSequence();

    }

}