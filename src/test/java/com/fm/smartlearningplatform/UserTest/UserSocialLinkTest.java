package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.Platform;
import com.fm.smartlearningplatform.model.User;
import com.fm.smartlearningplatform.model.UserSocialLink;
import com.fm.smartlearningplatform.model.UserSocialLinkId;
import com.fm.smartlearningplatform.service.UserService;
import com.fm.smartlearningplatform.service.UserSocialLinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserSocialLinkTest {

    @Autowired
    UserService userService;

    @Autowired
    UserSocialLinkService userSocialLinkService;

    User user;

    @BeforeEach
    public void beforeEach(){
        User user = new User();
        user.setEmail(System.currentTimeMillis() + "@gmail.com");
        user.setPasswordHash("{noop}123");
        userService.saveUser(user);
        this.user = user;

        UserSocialLink userSocialLink = new UserSocialLink();
        userSocialLink.setPlatform(Platform.GITHUB);
        userSocialLink.setUrl("www.github.com/hetfaldu11");
        userSocialLink.setUser(this.user);

        userSocialLinkService.save(userSocialLink);
    }

    @Test
    public void createUserSocialLink(){
        UserSocialLinkId id = new UserSocialLinkId(this.user.getId(),Platform.GITHUB);
        assertNotNull(userSocialLinkService.findById(id));
        assertEquals(Platform.GITHUB,userSocialLinkService.findById(id).getPlatform());
    }

    @Test
    public void updateUserSocialLink(){
        UserSocialLinkId id = new UserSocialLinkId(this.user.getId(),Platform.GITHUB);

        UserSocialLink userSocialLink = userSocialLinkService.findById(id);
        assertNotNull(userSocialLink);

        userSocialLink.setUrl("www.github.com");

        userSocialLinkService.save(userSocialLink);

        assertEquals("www.github.com",userSocialLinkService.findById(id).getUrl());
    }

    @Test
    public void deleteUserSocialLink(){
        UserSocialLinkId id = new UserSocialLinkId(this.user.getId(),Platform.GITHUB);
        assertNotNull(userSocialLinkService.findById(id));

        userSocialLinkService.deleteById(id);

        assertNull(userSocialLinkService.findById(id));
    }

}
