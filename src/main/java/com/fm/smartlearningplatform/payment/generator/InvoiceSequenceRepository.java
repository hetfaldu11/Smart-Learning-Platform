package com.fm.smartlearningplatform.payment.generator;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface InvoiceSequenceRepository extends Repository<Void, Void> {

    @Query(value = "CREATE SEQUENCE invoice_sequence START WITH 1 INCREMENT BY 1;", nativeQuery = true)
    long nextVal();
}