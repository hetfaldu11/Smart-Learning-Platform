//package com.fm.smartlearningplatform.UserTest;
//
//import com.fm.smartlearningplatform.model.user.Language;
//import com.fm.smartlearningplatform.model.user.Theme;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.model.user.UserPreference;
//import com.fm.smartlearningplatform.service.user.LanguageService;
//import com.fm.smartlearningplatform.service.user.UserPreferenceService;
//import com.fm.smartlearningplatform.service.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@SpringBootTest
//public class UserPreferenceTest {
//    @Autowired
//    UserPreferenceService userPreferenceService;
//    @Autowired
//    UserService userService;
//
//    User user;
//    @Autowired
//    private LanguageService languageService;
//
//    @BeforeEach
//    public void beforeEach(){
//        User user = new User();
//        user.setEmail(System.currentTimeMillis() + "@gmail.com");
//        user.setPasswordHash("{noop}123");
//        userService.save(user);
//        this.user = user;
//
//        UserPreference userPreference = new UserPreference();
//        userPreference.setUser(this.user);
//
//        Language language = new Language();
//        language.setName("Hindi");
//        language.setCode("hi");
//
//        languageService.save(language);
//
//        userPreference.setLanguage(language);
//        userPreference.setTheme(Theme.DARK);
//        userPreference.setNotificationEnabled(true);
//
//        userPreferenceService.save(userPreference);
//    }
//
//    @Test
//    public void createUserWithPreference(){
//        UserPreference userPreference = userPreferenceService.findById(this.user.getId());
//        assertEquals(this.user.getId(),userPreference.getUser().getId());
//        assertNotNull(userPreference.getUser());
//        assertTrue(userPreference.isNotificationEnabled());
//        assertEquals(Theme.DARK,userPreference.getTheme());
//    }
//
//    @Test
//    public void deleteUserWithPreference(){
//        Long id = this.user.getId();
//        userPreferenceService.deleteById(id);
//        assertNotNull(userService.findById(id));
//        assertNull(userPreferenceService.findById(id));
//    }
//
//    @Test
//    public void updateUserPreferenceByUser(){
//        Long id = this.user.getId();
//        User user = userService.findById(id);
//        UserPreference userPreference = user.getUserPreference();
//        userPreference.setNotificationEnabled(false);
//        userPreferenceService.save(userPreference);
//        assertFalse(userPreferenceService.findById(id).isNotificationEnabled());
//    }
//}
