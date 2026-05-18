//package com.fm.smartlearningplatform.CourseTest;
//
//import com.fm.smartlearningplatform.model.course.Course;
//import com.fm.smartlearningplatform.model.course.CoursePricing;
//import com.fm.smartlearningplatform.model.course.CourseLevel;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.service.course.CoursePricingService;
//import com.fm.smartlearningplatform.service.course.CourseService;
//import com.fm.smartlearningplatform.service.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.math.BigDecimal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class CoursePricingTest {
//    private final CourseService courseService;
//
//    private final CoursePricingService coursePricingService;
//
//    private final UserService userService;
//
//    Course course;
//
//    CoursePricing coursePricing;
//
//    @Autowired
//    public CoursePricingTest(CourseService courseService, UserService userService, CoursePricingService coursePricingService)
//    {
//        this.coursePricingService = coursePricingService;
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
//        CoursePricing coursePricing = new CoursePricing();
//        coursePricing.setPrice(0);
//        coursePricing.setCourse(course);
//
//        coursePricingService.save(coursePricing);
//
//        this.course = course;
//        this.coursePricing = coursePricing;
//    }
//
//    @Test
//    public void createCoursePricing()
//    {
//        assertNotNull(coursePricingService.findById(coursePricing.getId()));
//    }
//
//    @Test
//    public void updateCoursePricing(){
//        Long id = coursePricing.getId();
//        CoursePricing coursePricing = coursePricingService.findById(id);
//        assertNotNull(coursePricing);
//        coursePricing.setDiscountPrice(50);
//        coursePricingService.save(coursePricing);
//        assertEquals(50.0,coursePricingService.findById(id).getDiscountPrice());
//    }
//
//    @Test
//    public void  deleteCoursePricing()
//    {
//        Long id= coursePricing.getId();
//        assertNotNull(coursePricingService.findById(id));
//        coursePricingService.deleteById(id);
//        assertNull(coursePricingService.findById(id));
//    }
//}
