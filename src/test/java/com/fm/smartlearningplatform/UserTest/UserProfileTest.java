package com.fm.smartlearningplatform.UserTest;

import static org.junit.jupiter.api.Assertions.*;

import com.fm.smartlearningplatform.model.user.*;
import com.fm.smartlearningplatform.service.user.ProfessionService;
import com.fm.smartlearningplatform.service.user.UserProfileService;
import com.fm.smartlearningplatform.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserProfileTest {

    @Autowired
    UserProfileService userProfileService;
    @Autowired
    UserService userService;
    @Autowired
    ProfessionService professionService;

    User user;

    @BeforeEach
    public void beforeEach(){
        User user = new User();
        user.setEmail(System.currentTimeMillis() + "@gmail.com");
        user.setPasswordHash("{noop}123");
        userService.save(user);
        this.user = user;
        UserProfile userProfile = new UserProfile();
        userProfile.setUser(this.user);
        userProfile.setFirstName("Het");
        userProfile.setLastName("Faldu");
        userProfile.setAboutMe("Hello, I am computer engineering student.");
        userProfile.setGender(Gender.MALE);
        userProfile.setPhoneNumber("7016960254");
        userProfile.setEducationLevel(EducationLevel.BACHELOR);

        Address homeAddress = new Address("1","Kalavad","Gujarat","India","361160","Govindpara");
        Address workAddress = new Address("1","Kalavad","Gujarat","India","361160","Govindpara Office");

        userProfile.setHomeAddress(homeAddress);
        userProfile.setWorkAddress(workAddress);
        Profession profession = new Profession();
        profession.setName("Student");

        professionService.save(profession);

        userProfile.setProfession(profession);

        userProfileService.save(userProfile);
    }

    @Test
    public void createUserWithProfile(){
        UserProfile userProfile = userProfileService.findById(this.user.getId());
        assertEquals(this.user.getId(),userProfile.getUser().getId());
        assertNotNull(userProfile.getUser());
        assertNotNull(userProfile.getProfession());
        assertEquals("Het", userProfile.getFirstName());
        assertEquals("Student", userProfile.getProfession().getName());
    }

    @Test
    public void deleteUserWithProfile(){
        Long id = this.user.getId();

        userService.deleteById(id);

        assertNull(userService.findById(id));
        assertNull(userProfileService.findById(id));
    }

    @Test
    public void updateUserProfileByUser(){
        Long id = this.user.getId();

        User user = userService.findById(id);
        UserProfile userProfile = user.getUserProfile();

        userProfile.setPhoneNumber("7863842509");

        userProfileService.save(userProfile);

        assertEquals("7863842509",userProfileService.findById(id).getPhoneNumber());
    }
}