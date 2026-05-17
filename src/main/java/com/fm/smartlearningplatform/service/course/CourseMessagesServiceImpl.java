package com.fm.smartlearningplatform.service.course;

import com.fm.smartlearningplatform.model.course.CourseMessages;
import com.fm.smartlearningplatform.repository.course.CourseMessagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseMessagesServiceImpl implements CourseMessagesService{
    private final CourseMessagesRepository courseMessagesRepository;

    @Autowired
    public CourseMessagesServiceImpl(CourseMessagesRepository courseMessagesRepository) {
        this.courseMessagesRepository = courseMessagesRepository;
    }

    @Override
    public void save(CourseMessages courseMessages) {
        courseMessagesRepository.save(courseMessages);
    }

    @Override
    public CourseMessages findById(Long id) {
        return courseMessagesRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        courseMessagesRepository.deleteById(id);
    }
}
