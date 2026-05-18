//package com.fm.smartlearningplatform.UserTest;
//
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.model.user.UserVerification;
//import com.fm.smartlearningplatform.service.user.UserVerificationService;
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
//public class UserVerificationTest {
//
//    @Autowired
//    UserVerificationService userVerificationService;
//    @Autowired
//    UserService userService;
//
//    User user;
//
//    @BeforeEach
//    public void beforeEach(){
//        User user = new User();
//        user.setEmail(System.currentTimeMillis() + "@gmail.com");
//        user.setPasswordHash("{noop}123");
//        userService.save(user);
//        this.user = user;
//
//        UserVerification userVerification = new UserVerification();
//        userVerification.setUser(this.user);
//        userVerification.setEmailVerified(true);
//        userVerification.setPhoneVerified(true);
//        userVerification.setTwoFactorEnabled(false);
//
//        userVerificationService.save(userVerification);
//    }
//
//    @Test
//    public void createUserWithVerification(){
//        UserVerification userVerification = userVerificationService.findById(this.user.getId());
//        assertEquals(this.user.getId(),userVerification.getUser().getId());
//        assertNotNull(userVerification.getUser());
//        assertTrue(userVerification.isEmailVerified());
//        assertFalse(userVerification.isTwoFactorEnabled());
//    }
//
//    @Test
//    public void deleteUserWithVerification(){
//        Long id = this.user.getId();
//        userVerificationService.deleteById(id);
//        assertNotNull(userService.findById(id));
//        assertNull(userVerificationService.findById(id));
//    }
//
//    @Test
//    public void updateUserVerificationByUser(){
//        Long id = this.user.getId();
//        User user = userService.findById(id);
//        UserVerification userVerification = user.getUserVerification();
//        userVerification.setTwoFactorEnabled(true);
//        userVerificationService.save(userVerification);
//        assertTrue(userVerificationService.findById(id).isTwoFactorEnabled());
//    }
//
//}
