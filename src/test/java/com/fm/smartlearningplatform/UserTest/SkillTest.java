package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.service.user.SkillService;
import com.fm.smartlearningplatform.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SkillTest {

    @Autowired
    private SkillService skillService;
    @Autowired
    private UserService userService;

    User user;

    @BeforeEach
    public void beforeEach(){
        User user = new User();
        user.setEmail(System.currentTimeMillis() + "@gmail.com");
        user.setPasswordHash("{noop}123");

        Skill skill1 = new Skill();
        skill1.setName("Development");

        Skill skill2 = new Skill();
        skill2.setName("Business");

        skillService.save(skill1);
        skillService.save(skill2);

        user.addSkill(skill1);
        user.addSkill(skill2);

        this.user = user;

        userService.save(user);

    }
    @Test
    public void createSkill() {
        Long id = this.user.getId();
        assertNotNull(userService.findById(this.user.getId()).getSkills());
    }

    @Test
    public void updateSkill(){
        Long id = user.getId();
        User user = userService.findUserWithSkill(id);
        Set<Skill> skills = user.getSkills();

        Skill skill= skills.iterator().next();
        Long skillId = skill.getId();
        skill.setName("Cleaning");

        skillService.save(skill);

        assertEquals("Cleaning",skillService.findById(skillId).getName());
    }

    @Test
    public void deleteSkill(){

        Long id = user.getId();
        User user = userService.findUserWithSkill(id);
        Set<Skill> skills = user.getSkills();

        Skill skill = skills.iterator().next();

        assertNotNull(skill);
        long skillId = skill.getId();

        skillService.deleteById(skillId);

        assertNull(skillService.findById(skillId));
    }
}

