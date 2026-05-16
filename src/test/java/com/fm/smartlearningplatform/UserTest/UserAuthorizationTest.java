package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.*;
import com.fm.smartlearningplatform.service.UserAuthorizationService;
import com.fm.smartlearningplatform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserAuthorizationTest {

    @Autowired
    UserAuthorizationService userAuthorizationService;
    @Autowired
    UserService userService;

    User user;

    @BeforeEach
    public void beforeEach(){
        User user = new User();
        user.setEmail(System.currentTimeMillis() + "@gmail.com");
        user.setPasswordHash("{noop}123");
        user.addRole(UserRole.STUDENT);
        user.addRole(UserRole.INSTRUCTOR);
        this.user = user;

        userService.saveUser(user);
    }

    @Test
    public void createUserSocialLink(){
        UserAuthorizationId id = new UserAuthorizationId(this.user.getId(),UserRole.STUDENT);
        assertNotNull(userAuthorizationService.findById(id));
        assertEquals(UserRole.STUDENT,userAuthorizationService.findById(id).getUserRole());
    }

    @Test
    public void deleteUserSocialLink(){
        UserAuthorizationId id = new UserAuthorizationId(this.user.getId(),UserRole.STUDENT);
        assertNotNull(userAuthorizationService.findById(id));
        userAuthorizationService.deleteById(id);
        assertNull(userAuthorizationService.findById(id));
    }
}