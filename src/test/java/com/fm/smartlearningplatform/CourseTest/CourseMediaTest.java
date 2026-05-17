package com.fm.smartlearningplatform.CourseTest;

import com.fm.smartlearningplatform.model.course.Course;
import com.fm.smartlearningplatform.model.course.CourseDetail;
import com.fm.smartlearningplatform.model.course.CourseLevel;
import com.fm.smartlearningplatform.model.course.CourseMedia;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.service.course.CourseDetailService;
import com.fm.smartlearningplatform.service.course.CourseMediaService;
import com.fm.smartlearningplatform.service.course.CourseService;
import com.fm.smartlearningplatform.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseMediaTest {


    private CourseService courseService;
    private CourseMediaService courseMediaService;
    private UserService userService;
    @Autowired
    private CourseDetailService courseDetailService;

    @Autowired
    public CourseMediaTest( CourseService courseService,CourseMediaService courseMediaService, UserService userService)
    {
        this.userService = userService;
        this.courseMediaService = courseMediaService;
        this.courseService  = courseService;
    }
    CourseMedia courseMedia;
    Course course;

    @BeforeEach
    public void BeforeEach() {

        User user = new User();
        user.setEmail("het@gmail.com");
        user.setPasswordHash("{noop}123");

        userService.save(user);

        Course course = new Course();
        course.setTitle("spirng boot");
        course.setInstructor(user);
        course.setCourseLevel(CourseLevel.ADVANCED);

        courseService.save(course);

        CourseMedia courseMedia =  new CourseMedia();
        courseMedia .setCourse(course);
        courseMedia.setThumbnailUrl("www.com");
        courseMedia.setCertificateTemplateUrl("certificate.com");
        courseMedia.setPromotionalLessonUrl("hello.com");

        courseMediaService.save(courseMedia);

        this.courseMedia= courseMedia;
        this.course = course;
    }

    @Test
    public void createCourseMedia()
    {
        assertNotNull(courseMediaService.findById(courseMedia.getId()));
    }

    @Test
    public void updateCourseMedia() {
        Long id = courseMedia.getId();
        CourseMedia courseMedia = courseMediaService.findById(id);
        assertNotNull(courseMedia);
        courseMedia.setThumbnailUrl("milan.com");
        courseMediaService.save(courseMedia);
        assertEquals("milan.com", courseMedia.getThumbnailUrl());
    }

        @Test
        public void  deleteCourseMedia()
        {
            Long id= courseMedia.getId();
            assertNotNull(courseMediaService.findById(id));
            courseMediaService.deleteById(id);
            assertNull(courseMediaService.findById(id));
        }
    }

