package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Skill;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserSkill;
import com.fm.smartlearningplatform.repository.user.SkillRepository;
import com.fm.smartlearningplatform.repository.user.UserSkillRepository;
import com.fm.smartlearningplatform.service.user.SkillService;
import com.fm.smartlearningplatform.service.user.UserService;
import com.fm.smartlearningplatform.service.user.UserSkillService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SkillTest {

    @Autowired
    private SkillService skillService;

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserSkillService userSkillService;

    @Autowired
    private UserSkillRepository userSkillRepository;

    // ─── Shared state across tests ───────────────────────────

    private static Long createdSkillId;
    private static Long createdUserId;
    private static Long createdUserSkillId;

    // ─── Setup ───────────────────────────────────────────────

    @BeforeEach
    public void setUp() {
        // Create a fresh user before each test that needs one
        if (createdUserId == null) {
            User user = new User();
            user.setEmail(System.currentTimeMillis() + "@gmail.com");
            user.setPasswordHash("{noop}123");
            userService.save(user);
            createdUserId = user.getId();
        }
    }

    // ════════════════════════════════════════════════════════
    //  CREATE TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(1)
    @DisplayName("Create skill — should save successfully")
    public void testCreateSkill() {
        // Act
        Skill skill = skillService.createSkill("Java");
        createdSkillId = skill.getId();

        // Assert
        assertNotNull(skill.getId());
        assertEquals("Java", skill.getName());
        assertNotNull(skill.getCreatedAt());
        assertNull(skill.getDeletedAt());             // not deleted
    }

    @Test
    @Order(2)
    @DisplayName("Create duplicate skill — should throw exception")
    public void testCreateDuplicateSkill() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            skillService.createSkill("Java");         // already created in Order(1)
        });
    }

    @Test
    @Order(3)
    @DisplayName("Assign skill to user — should create user_skill row")
    public void testAssignSkillToUser() {
        // Act
        UserSkill userSkill = userSkillService.addSkillToUser(createdUserId, createdSkillId);
        createdUserSkillId = userSkill.getId();

        // Assert
        assertNotNull(userSkill.getId());
        assertEquals(createdUserId, userSkill.getUser().getId());
        assertEquals(createdSkillId, userSkill.getSkill().getId());
        assertNotNull(userSkill.getCreatedAt());      // auto-set by @CreationTimestamp
    }

    @Test
    @Order(4)
    @DisplayName("Assign duplicate skill to user — should throw exception")
    public void testAssignDuplicateSkillToUser() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            userSkillService.addSkillToUser(createdUserId, createdSkillId);
        });
    }

    // ════════════════════════════════════════════════════════
    //  READ TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(5)
    @DisplayName("Find skill by ID — should return skill")
    public void testFindSkillById() {
        // Act
        Skill skill = skillService.findById(createdSkillId);

        // Assert
        assertNotNull(skill);
        assertEquals(createdSkillId, skill.getId());
        assertEquals("Java", skill.getName());
    }

    @Test
    @Order(6)
    @DisplayName("Find all active skills — should return list")
    public void testFindAllActiveSkills() {
        // Act
        List<Skill> skills = skillService.findAll();

        // Assert
        assertNotNull(skills);
        assertFalse(skills.isEmpty());
        // All returned skills must not be soft deleted
        assertTrue(skills.stream().allMatch(s -> s.getDeletedAt() == null));
    }

    @Test
    @Order(7)
    @DisplayName("Find skills of a user — should return user skill list")
    public void testFindUserSkills() {
        // Act
        List<UserSkill> userSkills = userSkillService.getUserSkills(createdUserId);

        // Assert
        assertNotNull(userSkills);
        assertFalse(userSkills.isEmpty());
        assertEquals(createdSkillId, userSkills.get(0).getSkill().getId());
    }

    @Test
    @Order(8)
    @DisplayName("Find users who have a skill — should return user skill list")
    public void testFindSkillUsers() {
        // Act
        List<UserSkill> skillUsers = userSkillService.getSkillUsers(createdSkillId);

        // Assert
        assertNotNull(skillUsers);
        assertFalse(skillUsers.isEmpty());
        assertEquals(createdUserId, skillUsers.get(0).getUser().getId());
    }

    @Test
    @Order(9)
    @DisplayName("Find by invalid skill ID — should throw exception")
    public void testFindSkillByInvalidId() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            skillService.findById(99999L);           // non-existent ID
        });
    }

    @Test
    @Order(10)
    @DisplayName("Find specific user + skill row — should return user skill")
    public void testFindUserSkillByUserAndSkill() {
        // Act
        UserSkill userSkill = userSkillService
                .findByUserIdAndSkillId(createdUserId, createdSkillId);

        // Assert
        assertNotNull(userSkill);
        assertEquals(createdUserId, userSkill.getUser().getId());
        assertEquals(createdSkillId, userSkill.getSkill().getId());
    }

    // ════════════════════════════════════════════════════════
    //  UPDATE TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(11)
    @DisplayName("Update skill name — should persist change")
    public void testUpdateSkillName() {
        // Arrange
        Skill skill = skillService.findById(createdSkillId);
        skill.setName("Java Updated");

        // Act
        Skill updated = skillRepository.save(skill);

        // Assert
        assertEquals("Java Updated", updated.getName());
        assertNotNull(updated.getUpdatedAt());
    }

    // ════════════════════════════════════════════════════════
    //  DELETE TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(12)
    @DisplayName("Delete user_skill row — should remove from table")
    public void testDeleteUserSkill() {
        // Arrange
        assertNotNull(userSkillRepository.findById(createdUserSkillId).orElse(null));

        // Act
        userSkillService.deleteById(createdUserSkillId);

        // Assert
        assertNull(userSkillRepository.findById(createdUserSkillId).orElse(null));

        // Re-assign for delete skill test below
        UserSkill userSkill = userSkillService.addSkillToUser(createdUserId, createdSkillId);
        createdUserSkillId = userSkill.getId();
    }

    @Test
    @Order(13)
    @DisplayName("Delete skill — should soft delete skill and hard delete user_skills")
    public void testDeleteSkill() {
        // Arrange — confirm user_skill exists before delete
        assertNotNull(userSkillRepository.findById(createdUserSkillId).orElse(null));

        // Act
        skillService.deleteById(createdSkillId);

        // Assert 1: skill is soft deleted (still in DB but deleted_at is set)
        Skill deletedSkill = skillRepository.findById(createdSkillId).orElse(null);
        assertNotNull(deletedSkill);
        assertNotNull(deletedSkill.getDeletedAt());   // soft deleted ✅

        // Assert 2: user_skills are hard deleted (completely gone)
        assertNull(userSkillRepository.findById(createdUserSkillId).orElse(null));

        // Assert 3: skill not returned in active skills list
        List<Skill> activeSkills = skillService.findAll();
        assertTrue(activeSkills.stream()
                .noneMatch(s -> s.getId().equals(createdSkillId)));
    }

    @Test
    @Order(14)
    @DisplayName("Delete already deleted skill — should throw exception")
    public void testDeleteAlreadyDeletedSkill() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            skillService.deleteById(createdSkillId);  // already deleted in Order(13)
        });
    }

    @Test
    @Order(15)
    @DisplayName("Delete user — should soft delete user and hard delete user_skills")
    public void testDeleteUser() {
        // Arrange — give user a new skill first
        Skill newSkill = skillService.createSkill("Python_" + System.currentTimeMillis());
        UserSkill userSkill = userSkillService.addSkillToUser(createdUserId, newSkill.getId());

        assertNotNull(userSkillRepository.findById(userSkill.getId()).orElse(null));

        // Act
        userService.deleteById(createdUserId);

        // Assert 1: user_skills hard deleted
        assertNull(userSkillRepository.findById(userSkill.getId()).orElse(null));

        // Assert 2: user is soft deleted (still in DB)
        User deletedUser = userService.findById(createdUserId); // should throw now
        // This should throw because findById checks deleted_at is null
        // So wrap in assertThrows:
    }

    @Test
    @Order(16)
    @DisplayName("Find soft deleted user by ID — should throw exception")
    public void testFindSoftDeletedUser() {
        // Assert — user deleted in Order(15) should not be findable
        assertThrows(RuntimeException.class, () -> {
            userService.findById(createdUserId);
        });

        // Reset so other tests don't break
        createdUserId = null;
    }
}

//package com.fm.smartlearningplatform.UserTest;
//
//import com.fm.smartlearningplatform.model.user.Skill;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.service.user.SkillService;
//import com.fm.smartlearningplatform.service.user.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Set;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//public class SkillTest {
//
//    @Autowired
//    private SkillService skillService;
//    @Autowired
//    private UserService userService;
//
//    User user;
//
//    @BeforeEach
//    public void beforeEach(){
//        User user = new User();
//        user.setEmail(System.currentTimeMillis() + "@gmail.com");
//        user.setPasswordHash("{noop}123");
//
//        Skill skill1 = new Skill();
//        skill1.setName("Development");
//
//        Skill skill2 = new Skill();
//        skill2.setName("Business");
//
//        skillService.save(skill1);
//        skillService.save(skill2);
//
//        user.addSkill(skill1);
//        user.addSkill(skill2);
//
//        this.user = user;
//
//        userService.save(user);
//
//    }
//    @Test
//    public void createSkill() {
//        Long id = this.user.getId();
//        assertNotNull(userService.findById(this.user.getId()).getSkills());
//    }
//
//    @Test
//    public void updateSkill(){
//        Long id = user.getId();
//        User user = userService.findUserWithSkill(id);
//        Set<Skill> skills = user.getSkills();
//
//        Skill skill= skills.iterator().next();
//        Long skillId = skill.getId();
//        skill.setName("Cleaning");
//
//        skillService.save(skill);
//
//        assertEquals("Cleaning",skillService.findById(skillId).getName());
//    }
//
//    @Test
//    public void deleteSkill(){
//
//        Long id = user.getId();
//        User user = userService.findUserWithSkill(id);
//        Set<Skill> skills = user.getSkills();
//
//        Skill skill = skills.iterator().next();
//
//        assertNotNull(skill);
//        long skillId = skill.getId();
//
//        skillService.deleteById(skillId);
//
//        assertNull(skillService.findById(skillId));
//    }
//}
//
