package com.fm.smartlearningplatform.payment.generator;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

public interface RefundSequenceRepository extends Repository<Void, Void> {

    @Query(value = "CREATE SEQUENCE refund_sequence START WITH 1 INCREMENT BY 1;", nativeQuery = true)
    long nextVal();
}