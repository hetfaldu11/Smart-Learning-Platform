//package com.fm.smartlearningplatform.CourseTest;
//
//import com.fm.smartlearningplatform.model.course.Course;
//import com.fm.smartlearningplatform.model.course.CourseDetail;
//import com.fm.smartlearningplatform.model.course.CourseLevel;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.service.course.CourseDetailService;
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
//public class CourseDetailTest {
//    private final CourseService courseService;
//
//    private final CourseDetailService courseDetailService;
//
//    private final UserService userService;
//
//    Course course;
//
//    CourseDetail courseDetail;
//
//    @Autowired
//    public CourseDetailTest(CourseService courseService, UserService userService, CourseDetailService courseDetailService)
//    {
//        this.courseDetailService = courseDetailService;
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
//        CourseDetail courseDetail = new CourseDetail();
//        courseDetail.setDescription("Hello");
//        courseDetail.setCourse(course);
//
//        courseDetailService.save(courseDetail);
//
//        this.course = course;
//        this.courseDetail = courseDetail;
//    }
//
//    @Test
//    public void createCourseDetail()
//    {
//        assertNotNull(courseDetailService.findById(courseDetail.getId()));
//    }
//
//    @Test
//    public void updateCourseDetail(){
//        Long id = courseDetail.getId();
//        CourseDetail courseDetail = courseDetailService.findById(id);
//        assertNotNull(courseDetail);
//        courseDetail.setHasProject(true);
//        courseDetailService.save(courseDetail);
//        assertTrue(courseDetailService.findById(id).isHasProject());
//    }
//
//    @Test
//    public void  deleteCourseDetail()
//    {
//        Long id= courseDetail.getId();
//        assertNotNull(courseDetailService.findById(id));
//        courseDetailService.deleteById(id);
//        assertNull(courseDetailService.findById(id));
//    }
//}
