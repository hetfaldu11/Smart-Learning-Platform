package com.fm.smartlearningplatform.payment.generator;

import java.time.Year;

public abstract class NumberGenerator {

    public String generate() {

        return String.format(
                "%s-%d-%06d",
                getPrefix(),
                Year.now().getValue(),
                getNextSequence()
        );

    }

    protected abstract String getPrefix();

    protected abstract long getNextSequence();

}