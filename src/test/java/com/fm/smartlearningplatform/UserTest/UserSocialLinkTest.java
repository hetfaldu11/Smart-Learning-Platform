package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Platform;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSocialLink;
import com.fm.smartlearningplatform.service.user.UserService;
import com.fm.smartlearningplatform.service.user.UserSocialLinkService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        userService.save(user);
        this.user = user;

        UserSocialLink userSocialLink = new UserSocialLink();
        userSocialLink.setPlatform(Platform.GITHUB);
        userSocialLink.setUrl("www.github.com/hetfaldu11");
        userSocialLink.setUser(this.user);

        userSocialLinkService.save(userSocialLink);
    }

    @Test
    public void createUserSocialLink(){
        Long id = userSocialLinkService.findByUserAndPlatform(user,Platform.GITHUB).getId();
        assertNotNull(userSocialLinkService.findById(id));
        assertEquals(Platform.GITHUB,userSocialLinkService.findById(id).getPlatform());
    }

    @Test
    public void updateUserSocialLink(){
        Long id = userSocialLinkService.findByUserAndPlatform(user,Platform.GITHUB).getId();
        UserSocialLink userSocialLink = userSocialLinkService.findById(id);
        assertNotNull(userSocialLink);

        userSocialLink.setUrl("www.github.com");

        userSocialLinkService.save(userSocialLink);

        assertEquals("www.github.com",userSocialLinkService.findById(id).getUrl());
    }

    @Test
    public void deleteUserSocialLink(){
        Long id = userSocialLinkService.findByUserAndPlatform(user,Platform.GITHUB).getId();
        assertNotNull(userSocialLinkService.findById(id));

        userSocialLinkService.deleteById(id);

        assertNull(userSocialLinkService.findById(id));
    }

}
