//package com.fm.smartlearningplatform.CourseTest;
//
//import com.fm.smartlearningplatform.model.course.Course;
//import com.fm.smartlearningplatform.model.course.CourseSupport;
//import com.fm.smartlearningplatform.model.course.CourseLevel;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.service.course.CourseSupportService;
//import com.fm.smartlearningplatform.service.course.CourseService;
//import com.fm.smartlearningplatform.service.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class CourseSupportTest {
//    private final CourseService courseService;
//
//    private final CourseSupportService courseSupportService;
//
//    private final UserService userService;
//
//    Course course;
//
//    CourseSupport courseSupport;
//
//    @Autowired
//    public CourseSupportTest(CourseService courseService, UserService userService, CourseSupportService courseSupportService)
//    {
//        this.courseSupportService = courseSupportService;
//        this.courseService= courseService;
//        this.userService = userService;
//    }
//
//    @BeforeEach
//    public void beforeEach() {
//
//        User user = new User();
//        user.setEmail("het@gmail.com");
//        user.setPasswordHash("{noop}123");
//
//        userService.save(user);
//
//        Course course = new Course();
//        course.setTitle("spirng boot");
//        course.setInstructor(user);
//        course.setCourseLevel(CourseLevel.ADVANCED);
//
//        courseService.save(course);
//
//        CourseSupport courseSupport = new CourseSupport();
//        courseSupport.setSupportEmail("het@gmail.com");
//        courseSupport.setSupportContact("7016960254");
//        courseSupport.setCourse(course);
//
//        courseSupportService.save(courseSupport);
//
//        this.course = course;
//        this.courseSupport = courseSupport;
//    }
//
//    @Test
//    public void createCourseSupport()
//    {
//        assertNotNull(courseSupportService.findById(courseSupport.getId()));
//    }
//
//    @Test
//    public void updateCourseSupport(){
//        Long id = courseSupport.getId();
//        CourseSupport courseSupport = courseSupportService.findById(id);
//        assertNotNull(courseSupport);
//        courseSupport.setSupportEmail("meet@gmail.com");
//        courseSupportService.save(courseSupport);
//        assertEquals("meet@gmail.com",courseSupportService.findById(id).getSupportEmail());
//    }
//
//    @Test
//    public void  deleteCourseSupport()
//    {
//        Long id= courseSupport.getId();
//        assertNotNull(courseSupportService.findById(id));
//        courseSupportService.deleteById(id);
//        assertNull(courseSupportService.findById(id));
//    }
//}
