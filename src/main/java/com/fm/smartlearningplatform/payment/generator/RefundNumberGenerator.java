package com.fm.smartlearningplatform.payment.generator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RefundNumberGenerator
        extends NumberGenerator {

    private final DatabaseSequenceService sequenceService;

    @Override
    protected String getPrefix() {
        return "REF";
    }

    @Override
    protected long getNextSequence() {
        return sequenceService.nextRefundSequence();
    }

}