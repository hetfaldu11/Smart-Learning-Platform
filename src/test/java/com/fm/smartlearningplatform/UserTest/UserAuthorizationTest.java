package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserAuthorization;
import com.fm.smartlearningplatform.model.user.UserRole;
import com.fm.smartlearningplatform.service.user.UserAuthorizationService;
import com.fm.smartlearningplatform.service.user.UserService;
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

        userService.save(user);
    }

    @Test
    public void createUserSocialLink(){
        assertNotNull(userAuthorizationService.findByUserIdAndRole(this.user.getId(),UserRole.STUDENT));
        assertEquals(UserRole.STUDENT,userAuthorizationService.findByUserAndRole(this.user,UserRole.STUDENT).getUserRole());
    }

    @Test
    public void deleteUserSocialLink(){
        UserAuthorization userAuthorization = userAuthorizationService.findByUserAndRole(this.user,UserRole.STUDENT);
        assertNotNull(userAuthorization);
        userAuthorizationService.deleteById(userAuthorization.getId());
        assertNull(userAuthorizationService.findById(userAuthorization.getId()));
    }
}