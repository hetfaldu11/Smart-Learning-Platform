package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseMessage;

public interface CourseMessagesService {
    public  void save(CourseMessage courseMessage);

    public CourseMessage findById(Long id);

    public void deleteById(Long id);
}
