package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseLearningOutcomes;
import com.fm.smartlearningplatform.model.course.CourseMessages;

public interface CourseMessagesService {
    public  void save(CourseMessages courseMessages);

    public CourseMessages findById(Long id);

    public void deleteById(Long id);
}
