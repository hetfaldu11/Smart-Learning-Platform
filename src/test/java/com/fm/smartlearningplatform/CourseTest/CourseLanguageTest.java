package com.fm.smartlearningplatform.CourseTest;

import com.fm.smartlearningplatform.model.course.Course;
import com.fm.smartlearningplatform.model.course.CourseLanguage;
import com.fm.smartlearningplatform.model.course.CourseLevel;
import com.fm.smartlearningplatform.model.user.Language;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.service.course.CourseLanguageService;
import com.fm.smartlearningplatform.service.course.CourseService;
import com.fm.smartlearningplatform.service.user.LanguageService;
import com.fm.smartlearningplatform.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CourseLanguageTest {
    private final CourseService courseService;

    private final CourseLanguageService courseLanguageService;

    private final UserService userService;

    Course course;

    CourseLanguage courseLanguage;

    LanguageService languageService;

    @Autowired
    public CourseLanguageTest(CourseService courseService, UserService userService, CourseLanguageService courseLanguageService, LanguageService languageService)
    {
        this.courseLanguageService = courseLanguageService;
        this.courseService= courseService;
        this.userService = userService;
        this.languageService = languageService;
    }

    @BeforeEach
    public void beforeEach() {

        User user = new User();
        user.setEmail("het@gmail.com");
        user.setPasswordHash("{noop}123");

        userService.save(user);

        course = new Course();
        course.setTitle("spring boot");
        course.setInstructor(user);
        course.setCourseLevel(CourseLevel.ADVANCED);

        courseService.save(course);

        Language language = new Language();
        language.setName("Hindi");
        language.setCode("hi");

        languageService.save(language);

        courseLanguage = new CourseLanguage();
        courseLanguage.setLanguage(language);

        course.addLanguage(courseLanguage);

        courseService.save(course);
    }

    @Test
    public void createCourseLanguage()
    {
        assertNotNull(courseLanguageService.findById(courseLanguage.getId()));
    }

    @Test
    public void updateCourseLanguage(){
        Long id = courseLanguage.getId();
        CourseLanguage courseLanguage = courseLanguageService.findById(id);
        assertNotNull(courseLanguage);
        courseLanguage.setPrimary(true);
        courseLanguageService.save(courseLanguage);
        assertTrue(courseLanguageService.findById(id).isPrimary());
    }

    @Test
    public void  deleteCourseLanguage()
    {
        Long id= courseLanguage.getId();
        assertNotNull(courseLanguageService.findById(id));
        courseLanguageService.deleteById(id);
        assertNull(courseLanguageService.findById(id));
    }
}
