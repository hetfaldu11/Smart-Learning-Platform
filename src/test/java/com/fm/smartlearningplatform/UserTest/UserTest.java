package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.fm.smartlearningplatform.model.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserTest {

    @Autowired
    UserService userService;

    @Test
    public void createUser(){
        User user = new User();
        user.setId(1L);
        user.setEmail("het@gmail.com");
        user.setPasswordHash("{noop}123");
        user.setEnabled(1);
        user.setLastLoginAt(LocalDateTime.now());
//        user.setfailedLoginAttempt();
        user.setPasswordChangedAt(LocalDateTime.now());
//        user.setAccountLockedntil();
        user.setLastSeenAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());
//        user.setUpdatedAt();
//        user.setDeletedAt();
        assertEquals(1,user.getId(),"User is not created.");
    }

    @Test
    public void addUser(){
        User user = new User();
        user.setEmail("het@gmail.com");
        user.setPasswordHash("{noop}123");
        user.setEnabled(1);
        user.setLastLoginAt(LocalDateTime.now());
        user.setPasswordChangedAt(LocalDateTime.now());
        user.setLastSeenAt(LocalDateTime.now());
        user.setCreatedAt(LocalDateTime.now());

        userService.saveUser(user);

        System.out.println(user.getCreatedAt() + "\n" + user.getUpdatedAt());

        assertEquals(1,userService.findById(1L).getId(),"User is not created.");
    }

    @BeforeEach
    public void beforeEach(){
        User user = new User();
        user.setEmail("hetfaldu@gmail.com");
        user.setPasswordHash("{noop}123");
        user.setEnabled(1);
        user.setLastLoginAt(LocalDateTime.now());
        user.setPasswordChangedAt(LocalDateTime.now());
        user.setLastSeenAt(LocalDateTime.now());
        userService.saveUser(user);
    }

    @Test
    public void findUser(){
        assertEquals(1,userService.findById(1).getId(),"User is not created.");
    }

    @Test
    public void updateUser(){
        User  user = userService.findById(1);
        user.setEmail("h@gmail.com");
        userService.saveUser(user);
        System.out.println(user.getCreatedAt() + "\n" + user.getUpdatedAt());
        assertEquals("h@gmail.com",userService.findById(1).getEmail(),"User is not created.");
    }

    @Test
    public void deleteUser(){
        assertNotNull(userService.findById(1));
        userService.deleteById(1);
        assertNull(userService.findById(1),"User is not created.");
    }
}