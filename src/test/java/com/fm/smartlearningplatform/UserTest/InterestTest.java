package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Interest;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserInterest;
import com.fm.smartlearningplatform.repository.user.InterestRepository;
import com.fm.smartlearningplatform.repository.user.UserInterestRepository;
import com.fm.smartlearningplatform.service.user.InterestService;
import com.fm.smartlearningplatform.service.user.UserService;
import com.fm.smartlearningplatform.service.user.UserInterestService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InterestTest {

    @Autowired
    private InterestService interestService;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserInterestService userInterestService;

    @Autowired
    private UserInterestRepository userInterestRepository;

    // ─── Shared state across tests ───────────────────────────

    private static Long createdInterestId;
    private static Long createdUserId;
    private static Long createdUserInterestId;

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
    @DisplayName("Create interest — should save successfully")
    public void testCreateInterest() {
        // Act
        Interest interest = interestService.createInterest("Java");
        createdInterestId = interest.getId();

        // Assert
        assertNotNull(interest.getId());
        assertEquals("Java", interest.getName());
        assertNotNull(interest.getCreatedAt());
        assertNull(interest.getDeletedAt());             // not deleted
    }

    @Test
    @Order(2)
    @DisplayName("Create duplicate interest — should throw exception")
    public void testCreateDuplicateInterest() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            interestService.createInterest("Java");         // already created in Order(1)
        });
    }

    @Test
    @Order(3)
    @DisplayName("Assign interest to user — should create user_interest row")
    public void testAssignInterestToUser() {
        // Act
        UserInterest userInterest = userInterestService.addInterestToUser(createdUserId, createdInterestId);
        createdUserInterestId = userInterest.getId();

        // Assert
        assertNotNull(userInterest.getId());
        assertEquals(createdUserId, userInterest.getUser().getId());
        assertEquals(createdInterestId, userInterest.getInterest().getId());
        assertNotNull(userInterest.getCreatedAt());      // auto-set by @CreationTimestamp
    }

    @Test
    @Order(4)
    @DisplayName("Assign duplicate interest to user — should throw exception")
    public void testAssignDuplicateInterestToUser() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            userInterestService.addInterestToUser(createdUserId, createdInterestId);
        });
    }

    // ════════════════════════════════════════════════════════
    //  READ TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(5)
    @DisplayName("Find interest by ID — should return interest")
    public void testFindInterestById() {
        // Act
        Interest interest = interestService.findById(createdInterestId);

        // Assert
        assertNotNull(interest);
        assertEquals(createdInterestId, interest.getId());
        assertEquals("Java", interest.getName());
    }

    @Test
    @Order(6)
    @DisplayName("Find all active interests — should return list")
    public void testFindAllActiveInterests() {
        // Act
        List<Interest> interests = interestService.findAll();

        // Assert
        assertNotNull(interests);
        assertFalse(interests.isEmpty());
        // All returned interests must not be soft deleted
        assertTrue(interests.stream().allMatch(s -> s.getDeletedAt() == null));
    }

    @Test
    @Order(7)
    @DisplayName("Find interests of a user — should return user interest list")
    public void testFindUserInterests() {
        // Act
        List<UserInterest> userInterests = userInterestService.getUserInterests(createdUserId);

        // Assert
        assertNotNull(userInterests);
        assertFalse(userInterests.isEmpty());
        assertEquals(createdInterestId, userInterests.get(0).getInterest().getId());
    }

    @Test
    @Order(8)
    @DisplayName("Find users who have a interest — should return user interest list")
    public void testFindInterestUsers() {
        // Act
        List<UserInterest> interestUsers = userInterestService.getInterestUsers(createdInterestId);

        // Assert
        assertNotNull(interestUsers);
        assertFalse(interestUsers.isEmpty());
        assertEquals(createdUserId, interestUsers.get(0).getUser().getId());
    }

    @Test
    @Order(9)
    @DisplayName("Find by invalid interest ID — should throw exception")
    public void testFindInterestByInvalidId() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            interestService.findById(99999L);           // non-existent ID
        });
    }

    @Test
    @Order(10)
    @DisplayName("Find specific user + interest row — should return user interest")
    public void testFindUserInterestByUserAndInterest() {
        // Act
        UserInterest userInterest = userInterestService
                .findByUserIdAndInterestId(createdUserId, createdInterestId);

        // Assert
        assertNotNull(userInterest);
        assertEquals(createdUserId, userInterest.getUser().getId());
        assertEquals(createdInterestId, userInterest.getInterest().getId());
    }

    // ════════════════════════════════════════════════════════
    //  UPDATE TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(11)
    @DisplayName("Update interest name — should persist change")
    public void testUpdateInterestName() {
        // Arrange
        Interest interest = interestService.findById(createdInterestId);
        interest.setName("Java Updated");

        // Act
        Interest updated = interestRepository.save(interest);

        // Assert
        assertEquals("Java Updated", updated.getName());
        assertNotNull(updated.getUpdatedAt());
    }

    // ════════════════════════════════════════════════════════
    //  DELETE TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(12)
    @DisplayName("Delete user_interest row — should remove from table")
    public void testDeleteUserInterest() {
        // Arrange
        assertNotNull(userInterestRepository.findById(createdUserInterestId).orElse(null));

        // Act
        userInterestService.deleteById(createdUserInterestId);

        // Assert
        assertNull(userInterestRepository.findById(createdUserInterestId).orElse(null));

        // Re-assign for delete interest test below
        UserInterest userInterest = userInterestService.addInterestToUser(createdUserId, createdInterestId);
        createdUserInterestId = userInterest.getId();
    }

    @Test
    @Order(13)
    @DisplayName("Delete interest — should soft delete interest and hard delete user_interests")
    public void testDeleteInterest() {
        // Arrange — confirm user_interest exists before delete
        assertNotNull(userInterestRepository.findById(createdUserInterestId).orElse(null));

        // Act
        interestService.deleteById(createdInterestId);

        // Assert 1: interest is soft deleted (still in DB but deleted_at is set)
        Interest deletedInterest = interestRepository.findById(createdInterestId).orElse(null);
        assertNotNull(deletedInterest);
        assertNotNull(deletedInterest.getDeletedAt());   // soft deleted ✅

        // Assert 2: user_interests are hard deleted (completely gone)
        assertNull(userInterestRepository.findById(createdUserInterestId).orElse(null));

        // Assert 3: interest not returned in active interests list
        List<Interest> activeInterests = interestService.findAll();
        assertTrue(activeInterests.stream()
                .noneMatch(s -> s.getId().equals(createdInterestId)));
    }

    @Test
    @Order(14)
    @DisplayName("Delete already deleted interest — should throw exception")
    public void testDeleteAlreadyDeletedInterest() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            interestService.deleteById(createdInterestId);  // already deleted in Order(13)
        });
    }

    @Test
    @Order(15)
    @DisplayName("Delete user — should soft delete user and hard delete user_interests")
    public void testDeleteUser() {
        // Arrange — give user a new interest first
        Interest newInterest = interestService.createInterest("Python_" + System.currentTimeMillis());
        UserInterest userInterest = userInterestService.addInterestToUser(createdUserId, newInterest.getId());

        assertNotNull(userInterestRepository.findById(userInterest.getId()).orElse(null));

        // Act
        userService.deleteById(createdUserId);

        // Assert 1: user_interests hard deleted
        assertNull(userInterestRepository.findById(userInterest.getId()).orElse(null));

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
//import com.fm.smartlearningplatform.model.user.Interest;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.service.user.InterestService;
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
//public class InterestTest {
//
//    @Autowired
//    private InterestService interestService;
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
//        Interest interest1 = new Interest();
//        interest1.setName("Development");
//
//        Interest interest2 = new Interest();
//        interest2.setName("Business");
//
//        interestService.save(interest1);
//        interestService.save(interest2);
//
//        user.addInterest(interest1);
//        user.addInterest(interest2);
//
//        this.user = user;
//
//        userService.save(user);
//
//    }
//    @Test
//    public void createInterest() {
//        Long id = this.user.getId();
//        assertNotNull(userService.findById(this.user.getId()).getInterests());
//    }
//
//    @Test
//    public void updateInterest(){
//        Long id = user.getId();
//        User user = userService.findUserWithInterest(id);
//        Set<Interest> interests = user.getInterests();
//
//        Interest interest= interests.iterator().next();
//        Long interestId = interest.getId();
//        interest.setName("Cleaning");
//
//        interestService.save(interest);
//
//        assertEquals("Cleaning",interestService.findById(interestId).getName());
//    }
//
//    @Test
//    public void deleteInterest(){
//
//        Long id = user.getId();
//        User user = userService.findUserWithInterest(id);
//        Set<Interest> interests = user.getInterests();
//
//        Interest interest = interests.iterator().next();
//
//        assertNotNull(interest);
//        long interestId = interest.getId();
//
//        interestService.deleteById(interestId);
//
//        assertNull(interestService.findById(interestId));
//    }
//}
//
