package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.service.user.InterestService;
import com.fm.smartlearningplatform.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class InterestTest {

    @Autowired
    private InterestService interestService;
    @Autowired
    private UserService userService;

    User user;

    @BeforeEach
    public void beforeEach(){
        User user = new User();
        user.setEmail(System.currentTimeMillis() + "@gmail.com");
        user.setPasswordHash("{noop}123");

        Interest interest1 = new Interest();
        interest1.setName("Development");

        Interest interest2 = new Interest();
        interest2.setName("Business");

        interestService.save(interest1);
        interestService.save(interest2);

        user.addInterest(interest1);
        user.addInterest(interest2);

        this.user = user;

        userService.save(user);

    }
    @Test
    public void createInterest() {
        Long id = this.user.getId();
        assertNotNull(userService.findById(this.user.getId()).getInterests());
    }

    @Test
    public void updateInterest(){
        Long id = user.getId();
        User user = userService.findUserWithInterest(id);
        Set<Interest> interests = user.getInterests();

        Interest interest = interests.iterator().next();
        Long interestId = interest.getId();
        interest.setName("Cleaning");

        interestService.save(interest);

        assertEquals("Cleaning",interestService.findById(interestId).getName());
    }

    @Test
    public void deleteInterest(){

        Long id = user.getId();
        User user = userService.findUserWithInterest(id);
        Set<Interest> interests = user.getInterests();

        Interest interest = interests.iterator().next();

        assertNotNull(interest);
        long interestId = interest.getId();

        interestService.deleteById(interestId);

        assertNull(interestService.findById(interestId));
    }

}