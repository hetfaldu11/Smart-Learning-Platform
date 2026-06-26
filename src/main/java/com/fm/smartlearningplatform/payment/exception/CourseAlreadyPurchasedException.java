package com.fm.smartlearningplatform.payment.exception;

public class CourseAlreadyPurchasedException extends OrderException {

    public CourseAlreadyPurchasedException() {
        super("Course has already been purchased.");
    }

}