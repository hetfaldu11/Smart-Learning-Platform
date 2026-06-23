package com.fm.smartlearningplatform.lesson.model;

public enum LessonStatus {
    DRAFT,          // Being created
    REVIEW,         // Waiting for approval/review
    PUBLISHED,      // Visible to students
    SCHEDULED,      // Will be published later
    ARCHIVED,       // No longer active
    DELETED         // Soft deleted
}