package com.fm.smartlearningplatform.UserTest;

import com.fm.smartlearningplatform.model.user.Role;
import com.fm.smartlearningplatform.model.user.User;
import com.fm.smartlearningplatform.model.user.UserRole;
import com.fm.smartlearningplatform.repository.user.RoleRepository;
import com.fm.smartlearningplatform.repository.user.UserRoleRepository;
import com.fm.smartlearningplatform.service.user.RoleService;
import com.fm.smartlearningplatform.service.user.UserService;
import com.fm.smartlearningplatform.service.user.UserRoleService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RoleTest {

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    // ─── Shared state across tests ───────────────────────────

    private static Long createdRoleId;
    private static Long createdUserId;
    private static Long createdUserRoleId;

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
    @DisplayName("Create role — should save successfully")
    public void testCreateRole() {
        // Act
        Role role = roleService.createRole("Java");
        createdRoleId = role.getId();

        // Assert
        assertNotNull(role.getId());
        assertEquals("Java", role.getName());
        assertNotNull(role.getCreatedAt());
        assertNull(role.getDeletedAt());             // not deleted
    }

    @Test
    @Order(2)
    @DisplayName("Create duplicate role — should throw exception")
    public void testCreateDuplicateRole() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            roleService.createRole("Java");         // already created in Order(1)
        });
    }

    @Test
    @Order(3)
    @DisplayName("Assign role to user — should create user_role row")
    public void testAssignRoleToUser() {
        // Act
        UserRole userRole = userRoleService.addRoleToUser(createdUserId, createdRoleId);
        createdUserRoleId = userRole.getId();

        // Assert
        assertNotNull(userRole.getId());
        assertEquals(createdUserId, userRole.getUser().getId());
        assertEquals(createdRoleId, userRole.getRole().getId());
        assertNotNull(userRole.getCreatedAt());      // auto-set by @CreationTimestamp
    }

    @Test
    @Order(4)
    @DisplayName("Assign duplicate role to user — should throw exception")
    public void testAssignDuplicateRoleToUser() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            userRoleService.addRoleToUser(createdUserId, createdRoleId);
        });
    }

    // ════════════════════════════════════════════════════════
    //  READ TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(5)
    @DisplayName("Find role by ID — should return role")
    public void testFindRoleById() {
        // Act
        Role role = roleService.findById(createdRoleId);

        // Assert
        assertNotNull(role);
        assertEquals(createdRoleId, role.getId());
        assertEquals("Java", role.getName());
    }

    @Test
    @Order(6)
    @DisplayName("Find all active roles — should return list")
    public void testFindAllActiveRoles() {
        // Act
        List<Role> roles = roleService.findAll();

        // Assert
        assertNotNull(roles);
        assertFalse(roles.isEmpty());
        // All returned roles must not be soft deleted
        assertTrue(roles.stream().allMatch(s -> s.getDeletedAt() == null));
    }

    @Test
    @Order(7)
    @DisplayName("Find roles of a user — should return user role list")
    public void testFindUserRoles() {
        // Act
        List<UserRole> userRoles = userRoleService.getUserRoles(createdUserId);

        // Assert
        assertNotNull(userRoles);
        assertFalse(userRoles.isEmpty());
        assertEquals(createdRoleId, userRoles.get(0).getRole().getId());
    }

    @Test
    @Order(8)
    @DisplayName("Find users who have a role — should return user role list")
    public void testFindRoleUsers() {
        // Act
        List<UserRole> roleUsers = userRoleService.getRoleUsers(createdRoleId);

        // Assert
        assertNotNull(roleUsers);
        assertFalse(roleUsers.isEmpty());
        assertEquals(createdUserId, roleUsers.get(0).getUser().getId());
    }

    @Test
    @Order(9)
    @DisplayName("Find by invalid role ID — should throw exception")
    public void testFindRoleByInvalidId() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            roleService.findById(99999L);           // non-existent ID
        });
    }

    @Test
    @Order(10)
    @DisplayName("Find specific user + role row — should return user role")
    public void testFindUserRoleByUserAndRole() {
        // Act
        UserRole userRole = userRoleService
                .findByUserIdAndRoleId(createdUserId, createdRoleId);

        // Assert
        assertNotNull(userRole);
        assertEquals(createdUserId, userRole.getUser().getId());
        assertEquals(createdRoleId, userRole.getRole().getId());
    }

    // ════════════════════════════════════════════════════════
    //  UPDATE TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(11)
    @DisplayName("Update role name — should persist change")
    public void testUpdateRoleName() {
        // Arrange
        Role role = roleService.findById(createdRoleId);
        role.setName("Java Updated");

        // Act
        Role updated = roleRepository.save(role);

        // Assert
        assertEquals("Java Updated", updated.getName());
        assertNotNull(updated.getUpdatedAt());
    }

    // ════════════════════════════════════════════════════════
    //  DELETE TESTS
    // ════════════════════════════════════════════════════════

    @Test
    @Order(12)
    @DisplayName("Delete user_role row — should remove from table")
    public void testDeleteUserRole() {
        // Arrange
        assertNotNull(userRoleRepository.findById(createdUserRoleId).orElse(null));

        // Act
        userRoleService.deleteById(createdUserRoleId);

        // Assert
        assertNull(userRoleRepository.findById(createdUserRoleId).orElse(null));

        // Re-assign for delete role test below
        UserRole userRole = userRoleService.addRoleToUser(createdUserId, createdRoleId);
        createdUserRoleId = userRole.getId();
    }

    @Test
    @Order(13)
    @DisplayName("Delete role — should soft delete role and hard delete user_roles")
    public void testDeleteRole() {
        // Arrange — confirm user_role exists before delete
        assertNotNull(userRoleRepository.findById(createdUserRoleId).orElse(null));

        // Act
        roleService.deleteById(createdRoleId);

        // Assert 1: role is soft deleted (still in DB but deleted_at is set)
        Role deletedRole = roleRepository.findById(createdRoleId).orElse(null);
        assertNotNull(deletedRole);
        assertNotNull(deletedRole.getDeletedAt());   // soft deleted ✅

        // Assert 2: user_roles are hard deleted (completely gone)
        assertNull(userRoleRepository.findById(createdUserRoleId).orElse(null));

        // Assert 3: role not returned in active roles list
        List<Role> activeRoles = roleService.findAll();
        assertTrue(activeRoles.stream()
                .noneMatch(s -> s.getId().equals(createdRoleId)));
    }

    @Test
    @Order(14)
    @DisplayName("Delete already deleted role — should throw exception")
    public void testDeleteAlreadyDeletedRole() {
        // Assert
        assertThrows(RuntimeException.class, () -> {
            roleService.deleteById(createdRoleId);  // already deleted in Order(13)
        });
    }

    @Test
    @Order(15)
    @DisplayName("Delete user — should soft delete user and hard delete user_roles")
    public void testDeleteUser() {
        // Arrange — give user a new role first
        Role newRole = roleService.createRole("Python_" + System.currentTimeMillis());
        UserRole userRole = userRoleService.addRoleToUser(createdUserId, newRole.getId());

        assertNotNull(userRoleRepository.findById(userRole.getId()).orElse(null));

        // Act
        userService.deleteById(createdUserId);

        // Assert 1: user_roles hard deleted
        assertNull(userRoleRepository.findById(userRole.getId()).orElse(null));

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
//import com.fm.smartlearningplatform.model.user.Role;
//import com.fm.smartlearningplatform.model.user.User;
//import com.fm.smartlearningplatform.service.user.RoleService;
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
//public class RoleTest {
//
//    @Autowired
//    private RoleService roleService;
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
//        Role role1 = new Role();
//        role1.setName("Development");
//
//        Role role2 = new Role();
//        role2.setName("Business");
//
//        roleService.save(role1);
//        roleService.save(role2);
//
//        user.addRole(role1);
//        user.addRole(role2);
//
//        this.user = user;
//
//        userService.save(user);
//
//    }
//    @Test
//    public void createRole() {
//        Long id = this.user.getId();
//        assertNotNull(userService.findById(this.user.getId()).getRoles());
//    }
//
//    @Test
//    public void updateRole(){
//        Long id = user.getId();
//        User user = userService.findUserWithRole(id);
//        Set<Role> roles = user.getRoles();
//
//        Role role= roles.iterator().next();
//        Long roleId = role.getId();
//        role.setName("Cleaning");
//
//        roleService.save(role);
//
//        assertEquals("Cleaning",roleService.findById(roleId).getName());
//    }
//
//    @Test
//    public void deleteRole(){
//
//        Long id = user.getId();
//        User user = userService.findUserWithRole(id);
//        Set<Role> roles = user.getRoles();
//
//        Role role = roles.iterator().next();
//
//        assertNotNull(role);
//        long roleId = role.getId();
//
//        roleService.deleteById(roleId);
//
//        assertNull(roleService.findById(roleId));
//    }
//}
//
