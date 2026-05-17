package com.fm.smartlearningplatform.CourseTest;

import com.fm.smartlearningplatform.model.course.Course;
import com.fm.smartlearningplatform.model.course.CourseLevel;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.service.course.CourseService;
import com.fm.smartlearningplatform.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseTest {

    private final CourseService courseService;

    private final UserService userService;

    Course course;

    @Autowired
    public CourseTest(CourseService courseService, UserService userService)
    {
        this.courseService= courseService;
        this.userService = userService;
    }

    @BeforeEach
    public void beforeEach() {

        User user = new User();
        user.setEmail("het@gmail.com");
        user.setPasswordHash("{noop}123");

        userService.save(user);

        Course course = new Course();
        course.setTitle("spirng boot");
        course.setInstructor(user);
        course.setCourseLevel(CourseLevel.ADVANCED);

        courseService.save(course);

        this.course = course;
    }

    @Test
    public void createCourse()
    {
        assertNotNull(courseService.findById(course.getId()));
    }

    @Test
    public void updateCourse(){
        Long id = course.getId();
        Course course = courseService.findById(id);
        assertNotNull(course);
        course.setSubtitle("testing");
        courseService.save(course);
        assertEquals("testing", courseService.findById(id).getSubtitle());
    }

    @Test
    public void  deleteCourse()
    {
        Long id= course.getId();
        assertNotNull(courseService.findById(id));
        courseService.deleteById(id);
        assertNull(courseService.findById(id));
    }

}
